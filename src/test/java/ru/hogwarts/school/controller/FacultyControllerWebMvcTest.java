package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Optional;

import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private FacultyServiceImpl facultyService;
    @MockBean
    private FacultyRepository facultyRepository;

    private final Faker faker = new Faker();

    @Test
    void shouldAddFaculty_WhenCorrectEntity_ThenAddFaculty() throws Exception {
        Faculty expected = generateFaculty();
        String content = objectMapper.writeValueAsString(expected);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(expected);

        //test & check
        mockMvc.perform(post("/faculty/add")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.color").value(expected.getColor()));
    }

    @Test
    void shouldGetFaculty_WhenCorrectEntity_ThenGetFaculty() throws Exception {
        Long facultyId = faker.random().nextLong();
        Faculty expected = generateFaculty();
        expected.setId(facultyId);

        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(expected));

        //test & check
        mockMvc.perform(get("/faculty/{id}", expected.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.color").value(expected.getColor()));

        verify(facultyRepository, times(1)).findById(facultyId);
    }

    @Test
    void shouldUpdateFaculty_WhenCorrectEntity_ThenUpdateFaculty() throws Exception {
        Long facultyId = faker.random().nextLong();
        Faculty expected = generateFaculty();
        expected.setId(facultyId);
        String content = objectMapper.writeValueAsString(expected);
        when(facultyRepository.save(any(Faculty.class))).thenReturn(expected);
        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(expected));

        //check & test
        mockMvc.perform(put("/faculty/{id}", expected.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.color").value(expected.getColor()));

    }

    @Test
    void shouldDeleteFaculty_WhenCorrectEntity_ThenDeleteFaculty() throws Exception {
        Long facultyId = faker.random().nextLong();
        Faculty expected = generateFaculty();
        doNothing().when(facultyRepository).delete(eq(expected));

        //test & check
        mockMvc.perform(delete("/faculty/{id}", facultyId))
                .andExpect(status().isOk());
    }


    private Faculty generateFaculty() {
        return new Faculty(faker.harryPotter().house(), faker.color().name());
    }
}
