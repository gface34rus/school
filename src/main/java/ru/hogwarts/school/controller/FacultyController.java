package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/add")
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        return ResponseEntity.ok(faculty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty updatedFaculty) {
        Faculty faculty = facultyService.updateFaculty(id, updatedFaculty);
        return ResponseEntity.ok(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<List<Faculty>> getFacultiesByColor(@PathVariable String color) {
        List<Faculty> filteredFaculties = facultyService.getAllFaculties().stream()
                .filter(faculty -> faculty.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
        return ResponseEntity.ok(filteredFaculties);
    }

    @GetMapping("/find-all")
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();

    }

    @GetMapping("/find-by-name-or-color")
    public Collection<Faculty> getFacultyByNameOrColor(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) String color) {
        return facultyService.findFacultyByNameOrColorIgnoreCase(name, color);
    }

    @GetMapping("/get-students/{facultyId}")
    public Collection<Student> getFacultyStudents(@PathVariable Long facultyId) {
        return facultyService.getStudentsByFacultyId(facultyId);
    }
}
