package com.spacecraft;

import com.spacecraft.controller.SpacecraftController;
import com.spacecraft.repository.SpacecraftRepository;
import junit.framework.TestCase;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpacecraftControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SpacecraftRepository spacecraftRepository;

    @InjectMocks
    private SpacecraftController spacecraftController;
    
    private final String basePath = "/api/spacecraft";

    @Test
    @Order(1)
    public void testCreateSpacecraft() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Spacecraft\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Spacecraft"));
    }

    @Test
    @Order(2)
    public void testCreateSpacecraft_duplicate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Spacecraft\"}"))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @Order(3)
    public void testCreateSpacecraft_emptyornullname() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders.post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":null}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Order(4)
    public void testGetSpacecraft() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Spacecraft"));
    }

    @Test
    @Order(5)
    public void testGetSpacecraft_nonexistent_id() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "/-2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(6)
    public void testModifySpacecraft() throws Exception {

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
    public void testModifySpacecraft_nonexistent_id() throws Exception {

        String updatedSpacecraftJson = "{\"name\": \"Apollo 13\"}";
        mockMvc.perform(MockMvcRequestBuilders.put(basePath + "/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSpacecraftJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(8)
    public void testModifySpacecraft_emptyornullname() throws Exception {

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
    public void testGetSpacecrafts() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "?page=0&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1));
    }

    @Test
    @Order(10)
    public void testDeleteSpacecraft() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get(basePath + "1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
