package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "SELECT COUNT(*) AS allStudentsCount FROM student", nativeQuery = true)
    long findAllStudentsInTable();

    @Query(value = "SELECT AVG(age) FROM Student ", nativeQuery = true)
    double findAverageAgeByAllStudentsInTable();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC", nativeQuery = true)
    Page<Student> findLastFiveStudents(Pageable pageable);
}


