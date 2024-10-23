package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {
    private Map<Long, Faculty> facultyMap = new HashMap<>();
    private Long idCounter = 1L;

    public Faculty addFaculty(Faculty faculty) {

        faculty.setId(idCounter++);
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }


    public Faculty getFaculty(long id) {
        return facultyMap.get(id);
    }

    public Faculty updateFaculty(Long id, Faculty updatedFaculty) {
        if (facultyMap.containsKey(id)) {
            updatedFaculty.setId(id);
            facultyMap.put(id, updatedFaculty);
            return updatedFaculty;

        }
        return null;
    }

    public Faculty removeFaculty(Long id) {
        if (facultyMap.containsKey(id)) {
            Faculty removedFaculty = facultyMap.remove(id);
            return removedFaculty;
        }
        return null;
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyMap.values();
    }
}
