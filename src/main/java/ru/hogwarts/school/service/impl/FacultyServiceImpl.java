package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }


    @Override
    public Faculty getFaculty(long id) {
        logger.info("Was invoked method for get faculty");
        return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException(id));
    }

    @Override
    public Faculty updateFaculty(Long id, Faculty updatedFaculty) {
        logger.info("Was invoked method for update faculty");
        System.out.println(updatedFaculty);
        Faculty save = facultyRepository.save(updatedFaculty);
        System.out.println(save);
        return save;
    }

    @Override
    public void removeFaculty(Long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    @Override
    public Collection<Faculty> findFacultyByNameOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for find faculty by name or color ignore case ");
        return facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @Override
    public Collection<Student> getStudentsByFacultyId(long facultyId) {
        logger.info("Was invoked method for get students by faculty id");
        return facultyRepository.findById(facultyId).orElseThrow().getStudents();
    }

    @Override
    public String getFacultyLongestName() {
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(()-> new FacultyNotFoundException(0L));
    }
}
