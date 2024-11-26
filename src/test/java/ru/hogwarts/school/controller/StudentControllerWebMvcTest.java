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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private StudentServiceImpl studentService;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;

    private final Faker faker = new Faker();

    @Test
    void shouldAddStudent_WhenCorrectEntity_ThenAddStudent() throws Exception {
        Student expected = generateStudent();
        String content = objectMapper.writeValueAsString(expected);
        when(studentRepository.save(any(Student.class))).thenReturn(expected);

        //test & check
        mockMvc.perform(post("/student")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.age").value(expected.getAge()));
    }
    @Test
    void shouldGetStudent_WhenCorrectEntity_ThenGetStudent() throws Exception {
        Long studentId = faker.random().nextLong();
        Student expected = generateStudent();
        expected.setId(studentId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(expected));

        //test & check
        mockMvc.perform(get("/student/{id}", expected.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.age").value(expected.getAge()));

        verify(studentRepository, times(1)).findById(studentId);
    }
    @Test
    void shouldUpdateStudent_WhenCorrectEntity_ThenUpdateStudent() throws Exception {
        Long studentId = faker.random().nextLong();
        Student expected = generateStudent();
        expected.setId(studentId);
        String content = objectMapper.writeValueAsString(expected);
        when(studentRepository.save(any(Student.class))).thenReturn(expected);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(expected));

        mockMvc.perform(put("/student/{id}", expected.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name").value(expected.getName()))
                .andExpect(jsonPath("$.age").value(expected.getAge()));
    }
    @Test
    void shouldDeleteStudent_WhenCorrectEntity_ThenDeleteStudent() throws Exception {
        Long studentId = faker.random().nextLong();
        Student expected = generateStudent();
        expected.setId(studentId);
        doNothing().when(studentRepository).delete(eq(expected));

        mockMvc.perform(delete("/student/{id}", expected.getId()))
                .andExpect(status().isOk());
    }

    private Student generateStudent() {
        String name = faker.harryPotter().character();
        int age = faker.number().numberBetween(18, 101);

        Student student = new Student(name, age);

        Faculty faculty = generateFaculty();
        facultyRepository.save(faculty);
        student.setFaculty(faculty);
        return student;
    }

    private Faculty generateFaculty() {
        return new Faculty(faker.harryPotter().house(), faker.color().name());
    }
}
