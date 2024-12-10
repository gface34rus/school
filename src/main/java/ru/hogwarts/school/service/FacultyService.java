package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty getFaculty(long id);

    Faculty updateFaculty(Long id, Faculty updatedFaculty);

    void removeFaculty(Long id);

    Collection<Faculty> getAllFaculties();

    Collection<Faculty> findFacultyByNameOrColorIgnoreCase(String name, String color);

    Collection<Student> getStudentsByFacultyId(long facultyId);

    String getFacultyLongestName();

    ResponseEntity<List<Faculty>> getFacultiesByColor(String color);
}
