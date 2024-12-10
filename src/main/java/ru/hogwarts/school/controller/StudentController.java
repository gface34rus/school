package ru.hogwarts.school.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student createdStudent = studentService.addStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Student student = studentService.updateStudent(id, updatedStudent);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<Student>> getStudentsByAge(@PathVariable int age) {
        List<Student> filteredStudents = studentService.getAllStudents().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filteredStudents);
    }

    @GetMapping("/find-all")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/find-by-age_between")
    public Collection<Student> getStudentsByAgeBetween(@RequestParam Integer minAge, @RequestParam Integer maxAge) {
        return studentService.getStudentsByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/get-faculty/{id}")
    public Faculty getFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }

    @GetMapping("/find-all-count")
    public long getAllStudentsCount() {
        return studentService.getAllStudentsCount();
    }

    @GetMapping("/find-average-age")
    public double findAverageAgeByAllStudentsInTable() {
        return studentService.findAverageAgeByAllStudentsInTable();
    }

    @GetMapping("/find-last-5-students")
    public ResponseEntity<List<Student>> lastFiveStudents() {
        return ResponseEntity.ok(studentService.findLastFiveStudents());
    }

    @GetMapping("/a")
    public List<String> getStudentsStartingWithA() {
        return studentService.getStudentsStartingWithA();
    }

    @GetMapping("/get-avg-age")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/print-parallel")
    public void printParallel() {
        studentService.printParallel();
    }
    @GetMapping("/print-synchronized")
    public void printSynchronized() {
        studentService.printSynchronized();
    }
}
