package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FacultyController.class)
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @SpyBean
    private FacultyService facultyService;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentRepository studentRepository;


    Faker faker = new Faker();

    @Test
    void contextLoads() throws Exception {
        assertThat(facultyRepository).isNotNull();
    }

    @Test
    void shouldAddFaculty_WhenCorrectEntity_ThenAddFaculty() throws Exception {
        String content = objectMapper.writeValueAsString(generateFaculty());
        //test & check
        mockMvc.perform(post("/faculty/add").
                        content(content).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("S").exists());


    }


    //    @Test
//    void addFaculty() {
//        //data
//        studentRepository.deleteAll();
//        Faculty expected = generateFaculty();
//        facultyRepository.save(expected);
//        //test
//        ResponseEntity<Faculty> actualEntity = restTemplate.postForEntity(generateUrl("/faculty"),
//                expected,
//                Faculty.class);
//        //check
//        assertThat(actualEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
//        Faculty actual = actualEntity.getBody();
//        assertThat(actual).isNotNull();
//        assertThat(actual).usingRecursiveComparison().
//                ignoringFields("id", "students").
//                isEqualTo(expected);
//
//        Faculty actualInBase = facultyRepository.findById(actual.getId()).orElseThrow();
//        assertThat(actualInBase).
//                usingRecursiveComparison().
//                ignoringFields("id", "students").
//                isEqualTo(expected);
//
//
//    }
//
//    @Test
//    void getFaculty() {
//        //data
//        Faculty expected = generateFaculty();
//        facultyRepository.save(expected);
//        //check
//        ResponseEntity<Faculty> actualEntity = restTemplate.getForEntity(generateUrl("/faculty/{id}"),
//                Faculty.class,
//                expected.getId());
//        //test
//        assertThat(actualEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
//        Faculty actual = actualEntity.getBody();
//        assertThat(actual).isNotNull();
//        assertThat(actual).usingRecursiveComparison().
//                ignoringFields("id", "students").
//                isEqualTo(expected);
//
//    }
//
//    @Test
//    void updateFaculty() {
//        //data
//        String color = faker.color().name();
//        String name = faker.name().firstName();
//        Faculty expected = generateFaculty();
//        facultyRepository.save(expected);
//
//        expected.setName(name);
//        expected.setColor(color);
//
//        //test
//        restTemplate.put(generateUrl("/faculty"), expected);
//        //check
//        Faculty actual = facultyRepository.findById(expected.getId()).orElseThrow();
//        assertThat(actual.getColor()).isEqualTo(color);
//        assertThat(actual.getName()).isEqualTo(name);
//    }
//
//    @Test
//    void deleteFaculty() {
//        Faculty expected = generateFaculty();
//        facultyRepository.save(expected);
//
//    }
//
//    @Test
//    void getAllFaculties() {
//    }
//
//    private String generateUrl(String path) {
//        return "http://localhost:" + port + path;
//
//    }
//
//    private Student generateStudent() {
//        Student student = new Student(faker.harryPotter().character(), new Random().nextInt());
//        Faculty faculty = generateFaculty();
//        facultyRepository.save(faculty);
//        student.setFaculty(faculty);
//        return student;
//    }
//
    private Faculty generateFaculty() {
        return new Faculty(faker.harryPotter().house(), faker.color().name());

    }

    @AfterEach
    public void tearDown() throws Exception {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();

    }
}