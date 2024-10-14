package com.bar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bar.controller.BarController;
import com.bar.controller.dto.CreateBarTableRequest;
import com.bar.domain.bar.Bar;
import com.bar.domain.exceptions.EmptyNameException;
import com.bar.domain.exceptions.NullNameException;
import com.bar.infrastructure.BarRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BarControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BarRepository barRepository;

    @InjectMocks
    private BarController barController;

    private ObjectMapper objectMapper = new ObjectMapper();
    
    private final String basePath = "/api/bar";

    @Test
    @DisplayName("""
        When I try to create a Bar
        Then it returns a 201
    """)
    void create() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Phenomenon\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Phenomenon"));
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
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegrityViolationException))
            .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("""
        When I try to create a Bar with an empty name
        Then it throws an EmptyNameException
    """)
    void emptyName() throws Exception {

        Exception exception = assertThrows(ServletException.class, () -> {
                mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"));
        });

        assertTrue(exception.getCause() instanceof EmptyNameException);
    }

    @Test
    @DisplayName("""
        When I try to create a Bar with a null name
        Then it throws an NullNameException
    """)
    void nullName() throws Exception {

        Exception exception = assertThrows(ServletException.class, () -> {
                mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":null}"));
        });

        assertTrue(exception.getCause() instanceof NullNameException);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bar.getName()));
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

        String updatedBarJson = "{\"name\": \"Tasca Gat\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", bar.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBarJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/{id}", bar.getId()))
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

        String updatedBarJson = "{\"name\": \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBarJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("""
        When I try to modify an item changing its name to a null name
        Then it returns a 400
    """)
    void modifyNullName() throws Exception {

        String updatedBarJson = "{\"name\": null}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBarJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
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

    @Test
    @DisplayName("""
        When I try to create two BarTables
        With the same name in the same Bar
        Then it returns a 409 Conflict and a DataIntegrityViolationException
    """)
    void createDuplicateBarTableNameInSameBar() throws Exception {

        Bar bar = createBar("Lo de Ponxe en el Kinto Pino");
        CreateBarTableRequest request = new CreateBarTableRequest("Barra 1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bar/" + bar.getId().toString() + "/createTable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bar/" + bar.getId().toString() + "/createTable")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof DataIntegrityViolationException))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    private Bar createBar(String name) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated()) 
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name)) 
                .andReturn();

        Bar createdBar = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                Bar.class);

        return createdBar;
    }
}
