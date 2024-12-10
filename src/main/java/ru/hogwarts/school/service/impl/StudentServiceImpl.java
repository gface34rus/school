package ru.hogwarts.school.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Was invoked method for get student");
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method for add student");
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {
        logger.info("Was invoked method for update student");
        return studentRepository.save(updatedStudent);
    }

    @Override
    public void removeStudent(Long id) {
        logger.info("Was invoked method for remove student");
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    @Override
    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method for get students by age between");
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty getStudentFaculty(Long id) {
        logger.info("Was invoked method for get student faculty");
        return studentRepository.findById(id).get().getFaculty();
    }

    @Override
    public long getAllStudentsCount() {
        logger.info("Was invoked method for get all students count");
        return studentRepository.count();
    }

    @Override
    public double findAverageAgeByAllStudentsInTable() {
        logger.info("Was invoked method for find average age by all students");
        return studentRepository.findAverageAgeByAllStudentsInTable();
    }

    @Override
    public List<Student> findLastFiveStudents() {
        logger.info("Was invoked method for find last five students");
        return studentRepository.findLastFiveStudents();
    }

    @Override
    public List<String> getStudentsStartingWithA() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(upperCase -> upperCase.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public double getAverageAge() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }

    @Override
    public void printParallel() {
        List<Student> students = studentRepository.findAll().stream()
                .limit(6)
                .toList();
        System.out.println(students.get(0));
        System.out.println(students.get(1));

        Thread thread = new Thread(() -> {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        });
        Thread thread2 = new Thread(() -> {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        });
        thread.start();
        thread2.start();
    }

    @Override
    public void printSynchronized() {
        List<Student> students = studentRepository.findAll().stream()
                .limit(6)
                .toList();
        printStudents(students, 0, 1);

        Thread thread = new Thread(() -> {
            printStudents(students, 2, 3);
        });
        Thread thread2 = new Thread(() -> {
            printStudents(students, 4, 5);
        });
        thread.start();
        thread2.start();
    }

    private synchronized void printStudents(List<Student> students, int a, int b) {
        System.out.println(students.get(a));
        System.out.println(students.get(b));
    }
}

