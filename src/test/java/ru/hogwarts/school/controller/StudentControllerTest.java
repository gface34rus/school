package ru.hogwarts.school.controller;

import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class StudentControllerTest {

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
//    @Test
//    void contextLoads() throws Exception {
//        assertThat(studentRepository).isNotNull();
//    }
//
//
//    @Test
//    void addStudentTest() {
//        //data
//        Student expected = generateStudent();
//        //test
//        ResponseEntity<Student> actualEntity = restTemplate.postForEntity(generateUrl("/student"),
//                expected,
//                Student.class);
//
//        //check
//        assertThat(actualEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Student actual = actualEntity.getBody();
//        assertThat(actual).isNotNull();
//        assertThat(actual).usingRecursiveComparison().
//                ignoringFields("id", "faculty").
//                isEqualTo(expected);
//
//        Student actualInBase = studentRepository.findById(actual.getId()).orElseThrow();
//        assertThat(actualInBase).usingRecursiveComparison().ignoringFields("id", "faculty").isEqualTo(expected);
//    }
//
//    @Test
//    void getStudent() {
//        //data
//        Student expected = generateStudent();
//        studentRepository.save(expected); // Сохраняем студента
//
//        // Проверяем, что студент действительно сохранен
//        assertThat(studentRepository.findById(expected.getId())).isPresent();
//
//        //test
//        ResponseEntity<Student> actualEntity = restTemplate.getForEntity(generateUrl("/student/{id}"), Student.class, expected.getId());
//
//        //check
//        assertThat(actualEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Student actual = actualEntity.getBody();
//        assertThat(actual).isNotNull();
//        assertThat(actual.getId()).isEqualTo(expected.getId());
//    }
//
//    @Test
//    void updateStudentTest() {
//        //data
//        int age = faker.number().numberBetween(1, 100);
//        String name = faker.name().firstName();
//
//        Student expected = generateStudent();
//        studentRepository.save(expected);
//
//        expected.setName(name);
//        expected.setAge(age);
//        studentRepository.save(expected);
//
//        //test
//        restTemplate.put(generateUrl("/student"), expected);
//        //check
//        Student actual = studentRepository.findById(expected.getId()).orElseThrow();
//        assertThat(actual.getAge()).isEqualTo(age);
//        assertThat(actual.getName()).isEqualTo(name);
//    }
//
//    @Test
//    void deleteStudent() {
//        //data
//        Student expected = generateStudent();
//        studentRepository.save(expected);
//        //test
//        ResponseEntity<Student> response = restTemplate.exchange(generateUrl("/student/{id}"),
//                HttpMethod.DELETE,
//                null,
//                Student.class,
//                expected.getId());
//        //check
//        assertThat(response).isNotNull();
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNull();
//        // Ensure the student is deleted from the database
//        Student actual = studentRepository.findById(expected.getId()).orElse(null);
//        assertThat(actual).isNull(); // Student should be deleted
//    }
//
//
//    @Test
//    void getStudentsByAgeBetween(){
//        Integer minAge = faker.number().numberBetween(1, 100);
//        Integer maxAge = faker.number().numberBetween(1, 100);
//
//        ResponseEntity<List> response = restTemplate.exchange(
//                "http://localhost:" + port + "/student//find-by-age_between?minAge=" + minAge + "&maxAge=" + maxAge,
//                HttpMethod.GET,
//                null,
//                List.class);
//
//        assertThat(response.getStatusCode().value()).isEqualTo(200);
//        assertThat(response.getBody()).isNotNull();
//    }
//
//    private String generateUrl(String path) {
//        return "http://localhost:" + port + path;
//    }
//
//    private Student generateStudent() {
//        // Генерация данных с ограничением на возраст от 18 до 100
//        String name = faker.harryPotter().character();
//        int age = faker.number().numberBetween(18, 101); // Возраст от 18 до 100
//
//        Student student = new Student(name, age);
//
//        Faculty faculty = generateFaculty();
//        facultyRepository.save(faculty);
//        student.setFaculty(faculty);
//        //studentRepository.save(student);
//        return student;
//    }
//
//    private Faculty generateFaculty() {
//        return new Faculty(faker.harryPotter().house(), faker.color().name());
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        studentRepository.deleteAll();
//    }
}