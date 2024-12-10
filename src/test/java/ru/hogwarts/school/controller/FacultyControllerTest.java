package ru.hogwarts.school.controller;

import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FacultyControllerTest {

//    @LocalServerPort
//    int port;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private FacultyRepository facultyRepository;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    Faker faker = new Faker();
//
//
//    @Test
//    void contextLoads() throws Exception {
//        assertThat(facultyRepository).isNotNull();
//    }
//
//    @Test
//    void addFaculty() {
//        //data
//        Faculty expected = generateFaculty();
//        //test
//        ResponseEntity<Faculty> actualEntity = restTemplate.postForEntity(generateUrl("/faculty/add"),
//                expected,
//                Faculty.class);
//        //check
//        assertThat(actualEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Faculty actual = actualEntity.getBody();
//        assertThat(actual).isNotNull();
//        assertThat(actual).usingRecursiveComparison().
//                ignoringFields("id", "students").
//                isEqualTo(expected);
//
//        Faculty actualInBase = facultyRepository.findById(actual.getId()).orElseThrow();
//        assertThat(actualInBase).usingRecursiveComparison().ignoringFields("id", "students").isEqualTo(expected);
//
//    }
//
//    @Test
//    void getFaculty() {
//        //data
//        Faculty expected = generateFaculty();
//        facultyRepository.save(expected);
//        //check
//        assertThat(facultyRepository.findById(expected.getId())).isPresent();
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
//        restTemplate.put(generateUrl("/faculty/" + expected.getId()), expected);
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
//        ResponseEntity<Faculty> response = restTemplate.exchange(generateUrl("/faculty/" + expected.getId()),
//                HttpMethod.DELETE,
//                null,
//                Faculty.class,
//                expected.getId());
//        //check
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNull();
//        // Ensure the student is deleted from the database
//        Faculty actual = facultyRepository.findById(expected.getId()).orElse(null);
//        assertThat(actual).isNull(); // Faculty should be deleted
//
//    }
//
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
//    private Faculty generateFaculty() {
//        return new Faculty(faker.harryPotter().house(), faker.color().name());
//
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        studentRepository.deleteAll();
//        facultyRepository.deleteAll();
//    }
}