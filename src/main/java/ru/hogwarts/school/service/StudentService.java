package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.save(updatedStudent);
    }

    public void removeStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
