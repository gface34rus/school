package ru.hogwarts.school.service.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }


    @Override
    public Faculty getFaculty(long id) {
        return facultyRepository.findById(id).orElseThrow(()->new FacultyNotFoundException(id));
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty updatedFaculty) {
        System.out.println(updatedFaculty);
        Faculty save = facultyRepository.save(updatedFaculty);
        System.out.println(save);
        return save;
    }

    @Override
    public void removeFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findFacultyByNameOrColorIgnoreCase(String name, String color) {
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(long facultyId) {
        return facultyRepository.findById(facultyId).orElseThrow().getStudents();
    }
}
