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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Random;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class StudentControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    Faker faker = new Faker();

    @Test
    void contextLoads() throws Exception {
        assertThat(studentRepository).isNotNull();
    }


    @Test
    void addStudentTest() {
        //data
        Student expected = generateStudent();
        //test
        ResponseEntity<Student> actualEntity = restTemplate.postForEntity(generateUrl("/student"), expected, Student.class);

        //check
        assertThat(actualEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Student actual = actualEntity.getBody();
        assertThat(actual).isNotNull();
        assertThat(actual).usingRecursiveComparison().
                ignoringFields("id", "faculty").
                isEqualTo(expected);


        Student actualInBase = studentRepository.findById(actual.getId()).orElseThrow();
        assertThat(actualInBase).
                usingRecursiveComparison().
                ignoringFields("id", "faculty").
                isEqualTo(expected);

    }

    @Test
    void getStudent() {
        //data
        Student expected = generateStudent();
        studentRepository.save(expected);
        //test
        ResponseEntity<Student> actualEntity = restTemplate.getForEntity(generateUrl("/student/{id}"), Student.class, expected.getId());
        //check
        // assertThat(actualEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        Student actual = actualEntity.getBody();
        System.out.println(actual);
        assertThat(actual).isNotNull();
        assertThat(actual).usingRecursiveComparison().
                ignoringFields("id", "faculty").
                isEqualTo(expected);

    }

    @Test
    void updateStudentTest() {
        //data
        int age = faker.number().numberBetween(1, 100);
        String name = faker.name().firstName();
        Student expected = generateStudent();

        expected.setName(name);
        expected.setAge(age);

        //test
        restTemplate.put(generateUrl("/student"), expected);
        //check
        Student actual = studentRepository.findById(expected.getId()).orElseThrow();
        assertThat(actual.getAge()).isEqualTo(age);
        assertThat(actual.getName()).isEqualTo(name);
    }

    @Test
    void deleteStudent() {
        //data
        Student expected = generateStudent();
        studentRepository.save(expected);
        //test
        ResponseEntity<Student> response = restTemplate.exchange(generateUrl("/student/{id}"),
                HttpMethod.DELETE,
                null,
                Student.class,
                expected.getId());
        System.out.println(expected);
        //check
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
        Student actual = studentRepository.findById(expected.getId()).orElseThrow();
        assertThat(actual).isNotNull();
    }


    private String generateUrl(String path) {
        return "http://localhost:" + port + path;

    }

    private Student generateStudent(Faculty faculty) {
        Student student = new Student(faker.harryPotter().character(), new Random().nextInt());
        student.setFaculty(faculty);
        return student;
    }

    private Student generateStudent() {
        Student student = new Student(faker.harryPotter().character(), new Random().nextInt());
        Faculty faculty = generateFaculty();
        facultyRepository.save(faculty);
        student.setFaculty(faculty);
        studentRepository.save(student);
        return student;
    }

    private Faculty generateFaculty() {
        return new Faculty(faker.harryPotter().house(), faker.color().name());

    }

    @AfterEach
    public void tearDown() throws Exception {
        studentRepository.deleteAll();
    }
}