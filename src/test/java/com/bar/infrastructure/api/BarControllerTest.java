package com.bar.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bar.domain.bar.Bar;
import com.bar.domain.exception.EmptyNameException;
import com.bar.domain.exception.NullNameException;
import com.bar.infrastructure.web.controller.dto.CreateBarTableRequest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import junit.framework.TestCase;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class BarControllerTest extends TestCase {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public BarControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }
    
    private final String basePath = "/api/bar";

    @Test
    @DisplayName("""
        When I try to create a Bar
        Then it returns a 201
    """)
    void create() throws Exception {

        createBar("Phenomenon");
    }

    @Test
    @DisplayName("""
        When I try to create a duplicated name bar
        Then it throws a DataIntegrityViolationException
    """)
    void duplicateName() throws Exception {

        createBar("El Loco");  

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"El Loco\"}"))
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.content().string("The bar name 'El Loco' already exists."));
    }

    @Test
    @DisplayName("""
        When I try to create a Bar with an empty name
        Then it throws an EmptyNameException
    """)
    void emptyName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"\"}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof EmptyNameException));
    }

    @Test
    @DisplayName("""
        When I try to create a Bar with a null name
        Then it throws an NullNameException
    """)
    void nullName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":null}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof NullNameException));
    }

    @Test
    @DisplayName("""
        When I try to get an item by its Id
        Then it returns a 200
    """)
    void getBar() throws Exception {

        Bar bar = createBar("Sala Moon");

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/" + bar.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bar.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bar.getName().toString()));
    }

    @Test
    @DisplayName("""
        When I try to get an item by a nonexistent Id
        Then it returns a 404
    """)
    void getNonExistentId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/-2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("""
        When I try to modify an item
        Then it returns a 200
    """)
    void modify() throws Exception {

        Bar bar = createBar("Tasca");

        String updatedBarJson = "{\"name\":\"Tasca Gat\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", bar.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBarJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/{id}", bar.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tasca Gat"));
    }

    @Test
    @DisplayName("""
        When I try to modify an item by a nonexistent Id
        Then it returns a 404
    """)
    void modifyNonExistentId() throws Exception {

        String updatedBarJson = "{\"name\": \"Malos Pasos\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", "-2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBarJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("""
        When I try to modify an item changing its name to an empty name
        Then it returns a 400
    """)
    void modifyEmptyName() throws Exception {

        Bar bar = createBar("Galieta");

        String updatedBarJson = "{\"name\": \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", bar.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBarJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Name cannot be empty"));        
    }

    @Test
    @DisplayName("""
        When I try to modify an item changing its name to a null name
        Then it returns a 400
    """)
    void modifyNullName() throws Exception {

        Bar bar = createBar("Toppings");

        String updatedBarJson = "{\"name\": null}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", bar.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBarJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Name cannot be null"));  
    }

    @Test
    @DisplayName("""
        Having one or more items created
        When I try to get a list of items
        Then it returns a 200
    """)
    void getAll() throws Exception {

        createBar("Millenary Falcon");

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "?page=0&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(Matchers.greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("""
        When I try to delete an item
        Then it returns a 204 when done, and a 404 while trying to find it after deletion
    """)
    void delete() throws Exception {

        Bar bar = createBar("Black Note");

        mockMvc.perform(MockMvcRequestBuilders.delete(basePath + "/" + bar.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + bar.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("""
        When I try to create a BarTable
        Then it returns a 201 when created
    """)
    void createBarTableSuccessfully() throws Exception {

        Bar bar = createBar("Restaurante Ivan");

        CreateBarTableRequest request = new CreateBarTableRequest("Barra 1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bar/" + bar.getId().toString() + "/createTable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Barra 1"));
    }

    @Test
    @DisplayName("""
        When I try to create a BarTable
        With a non-existent BarId
        Then it returns a 404 Not Found and a EntityNotFoundException
    """)
    void createBarTableWithNonExistentBarId() throws Exception {

        String nonExistentBarId = "non-existent-id";

        CreateBarTableRequest request = new CreateBarTableRequest("Barra 1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bar/" + nonExistentBarId + "/createTable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    private Bar createBar(String name) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonContent = "{\"name\":\"" + name + "\"}";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(basePath)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonContent))
            .andExpect(MockMvcResultMatchers.status().isCreated()) 
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name)) 
            .andReturn();

        Bar createdBar = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            Bar.class);

        return createdBar;
    }
}
