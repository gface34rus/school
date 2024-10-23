package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {
    private Map<Long, Student> students = new HashMap<>();
    private Long idCounter = 1L;

    public Student addStudent(Student student) {

        student.setId(idCounter++);
        students.put(student.getId(), student);
        return student;
    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        if (students.containsKey(id)) {
            updatedStudent.setId(id);
            students.put(id, updatedStudent);
            return updatedStudent;
        }
        return null;
    }

    public Student removeStudent(Long id) {
        if (students.containsKey(id)) {
            Student removedStudent = students.remove(id);
            return removedStudent;
        }
        return null;
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }
}
