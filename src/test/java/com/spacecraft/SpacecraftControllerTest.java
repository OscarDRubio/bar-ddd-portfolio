package com.spacecraft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacecraft.controller.SpacecraftController;
import com.spacecraft.domain.exceptions.EmptyNameException;
import com.spacecraft.domain.exceptions.NullNameException;
import com.spacecraft.domain.spacecraft.Spacecraft;
import com.spacecraft.infrastructure.SpacecraftRepository;

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
class SpacecraftControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SpacecraftRepository spacecraftRepository;

    @InjectMocks
    private SpacecraftController spacecraftController;
    
    private final String basePath = "/api/spacecraft";

    @Test
    @DisplayName("""
        When I try to create a Spacecraft
        Then it returns a 201
    """)
    void create() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Soyuz\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Soyuz"));
    }

    @Test
    @DisplayName("""
        When I try to create a duplicated name spacecraft
        Then it throws a DataIntegrityViolationException
    """)
    void duplicateName() throws Exception {

        createSpacecraft("TARDIS");

        Exception exception = assertThrows(ServletException.class, () -> {
                mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"TARDIS\"}"));
        });

        assertTrue(exception.getCause() instanceof DataIntegrityViolationException);
    }

    @Test
    @DisplayName("""
        When I try to create a Spacecraft with an empty name
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
        When I try to create a Spacecraft with a null name
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
    void getSpacecraft() throws Exception {

        Spacecraft spacecraft = createSpacecraft("Apollo 11");

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/" + spacecraft.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(spacecraft.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(spacecraft.getName()));
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

        Spacecraft spacecraft = createSpacecraft("Discovery");

        String updatedSpacecraftJson = "{\"name\": \"Discovery One\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", spacecraft.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSpacecraftJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/{id}", spacecraft.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Discovery One"));
    }

    @Test
    @DisplayName("""
        When I try to modify an item by a nonexistent Id
        Then it returns a 404
    """)
    void modifyNonExistentId() throws Exception {

        String updatedSpacecraftJson = "{\"name\": \"Mars Rover Perseverance\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", "-2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSpacecraftJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("""
        When I try to modify an item changing its name to an empty name
        Then it returns a 400
    """)
    void modifyEmptyName() throws Exception {

        String updatedSpacecraftJson = "{\"name\": \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSpacecraftJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("""
        When I try to modify an item changing its name to a null name
        Then it returns a 400
    """)
    void modifyNullName() throws Exception {

        String updatedSpacecraftJson = "{\"name\": null}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSpacecraftJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("""
        Having one or more items created
        When I try to get a list of items
        Then it returns a 200
    """)
    void getAll() throws Exception {

        createSpacecraft("Millenary Falcon");

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

        Spacecraft spacecraft = createSpacecraft("The Milano");

        mockMvc.perform(MockMvcRequestBuilders.delete(basePath + "/" + spacecraft.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + spacecraft.getId()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private Spacecraft createSpacecraft(String name) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + name + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Asegura que el estado sea 201
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))  // Asegura que el nombre sea correcto
                .andReturn();

        Spacecraft createdSpacecraft = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                 Spacecraft.class);

        return createdSpacecraft;
    }
}
