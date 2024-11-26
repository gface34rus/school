package ru.hogwarts.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student getStudent(Long id);

    Student addStudent(Student student);

    Student updateStudent(Long id, Student updatedStudent);

    void removeStudent(Long id);

    Collection<Student> getAllStudents();

    Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge);

    Faculty getStudentFaculty(Long id);

    long getAllStudentsCount();

    double findAverageAgeByAllStudentsInTable();

    Page<Student> findLastFiveStudents();

}
