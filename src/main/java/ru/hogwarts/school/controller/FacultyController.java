package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyServiceImpl facultyServiceImpl;

    public FacultyController(FacultyServiceImpl facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    @PostMapping("/add")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyServiceImpl.addFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyServiceImpl.getFaculty(id);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty updatedFaculty) {
        System.out.println(updatedFaculty+" "+id);
        Faculty faculty = facultyServiceImpl.updateFaculty(id, updatedFaculty);

        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyServiceImpl.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@PathVariable String color) {
        List<Faculty> filteredFaculties = facultyServiceImpl.getAllFaculties().stream()
                .filter(faculty -> faculty.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filteredFaculties);
    }

    @GetMapping("/find-all")
    public Collection<Faculty> getAllFaculties() {
        return facultyServiceImpl.getAllFaculties();

    }

    @GetMapping("/find-by-name-or-color")
    public Collection<Faculty> getFacultyByNameOrColor(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String color) {
        return facultyServiceImpl.findFacultyByNameOrColorIgnoreCase(name, color);
    }

    @GetMapping("/get-students/{facultyId}")
    public Collection<Student> getFacultyStudents(@PathVariable Long facultyId) {
        return facultyServiceImpl.getStudentsByFacultyId(facultyId);
    }
}
