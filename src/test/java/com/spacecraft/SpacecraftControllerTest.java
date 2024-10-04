package com.spacecraft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spacecraft.controller.SpacecraftController;
import com.spacecraft.domain.exceptions.EmptyNameException;
import com.spacecraft.domain.exceptions.NullNameException;
import com.spacecraft.domain.spacecraft.Name;
import com.spacecraft.domain.spacecraft.Spacecraft;
import com.spacecraft.infrastructure.SpacecraftRepository;

import jakarta.servlet.ServletException;
import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
    void testCreateSpacecraft() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Spacecraft\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Spacecraft"));
    }

    @Test
    void testCreateSpacecraft_duplicate() throws Exception {

        testCreateSpacecraft();

        Exception exception = assertThrows(ServletException.class, () -> {
                mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Spacecraft\"}"));
        });

        assertTrue(exception.getCause() instanceof DataIntegrityViolationException);
    }

    @Test
    void emptyName() throws Exception {

        Exception exception = assertThrows(ServletException.class, () -> {
                mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"));
        });

        assertTrue(exception.getCause() instanceof EmptyNameException);
    }

    @Test
    void nullName() throws Exception {

        Exception exception = assertThrows(ServletException.class, () -> {
                mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":null}"));
        });

        assertTrue(exception.getCause() instanceof NullNameException);
    }

    @Test
    void testGetSpacecraft() throws Exception {

        Spacecraft spacecraft = createSpacecraft("Apollo 11");

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/" + spacecraft.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Spacecraft"));
    }

    @Test
    @Order(5)
    void testGetSpacecraft_nonexistent_id() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/-2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(6)
    void testModifySpacecraft() throws Exception {

        String updatedSpacecraftJson = "{\"name\": \"Apollo 13\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSpacecraftJson))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Apollo 13"));
    }

    @Test
    @Order(7)
    void testModifySpacecraft_nonexistent_id() throws Exception {

        String updatedSpacecraftJson = "{\"name\": \"Apollo 13\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSpacecraftJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(8)
    void testModifySpacecraft_emptyornullname() throws Exception {

        String updatedSpacecraftJson = "{\"name\": \"\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSpacecraftJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        updatedSpacecraftJson = "{\"name\": null}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSpacecraftJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(9)
    void testGetSpacecrafts() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "?page=0&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1));
    }

    @Test
    @Order(10)
    void testDeleteSpacecraft() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private Spacecraft createSpacecraft(String name) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":" + name + "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())  // Asegura que el estado sea 201
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))  // Asegura que el nombre sea correcto
                .andReturn();

        Spacecraft createdSpacecraft = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                 Spacecraft.class);

        return createdSpacecraft;
    }
}
