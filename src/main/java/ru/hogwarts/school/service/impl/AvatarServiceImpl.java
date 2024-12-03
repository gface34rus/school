package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.dto.AvatarDto;
import ru.hogwarts.school.model.dto.AvatarView;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AvatarServiceImpl implements AvatarService {
    @Value("${image.path}")
    private Path pathDir;
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    private final AvatarMapper avatarMapper;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarServiceImpl(AvatarMapper avatarMapper, AvatarRepository avatarRepository, StudentRepository studentRepository) {
        this.avatarMapper = avatarMapper;
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }


    @Override
    public long uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Was invoked method for upload avatar");
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        Path path = saveAvatarLocal(file);

        System.out.println(path);

        Avatar avatar = new Avatar(
                path.toString(),
                file.getSize(),
                file.getContentType(),
                file.getBytes(),
                student
        );
        avatarRepository.findByStudentId(studentId)
                .ifPresent((x) -> {
                    try {
                        Files.delete(Path.of(x.getFilePath()));
                    } catch (IOException e) {
                        logger.error("new AvatarNotFoundException(\"Avatar not found\")");
                        throw new AvatarNotFoundException("Avatar not found");
                    }
                    avatar.setId(x.getId());
                });

        return avatarRepository.save(avatar).getId();
    }

    @Override
    public Avatar getAvatarFromDb(Long studentId) {
        logger.info("Was invoked method for get avatar from db");
        return avatarRepository.
                findByStudentId(studentId).
                orElseThrow(() -> new AvatarNotFoundException("Avatar not found"));
    }

    @Override
    public AvatarView getAvatarFromLocal(Long studentId) throws IOException {
        logger.info("Was invoked method for get avatar from local file");
        Avatar avatar = avatarRepository
                .findByStudentId(studentId)
                .orElseThrow(() -> new AvatarNotFoundException("Avatar not found"));
        byte[] bytes = Files.readAllBytes(Path.of(avatar.getFilePath()));
        return new AvatarView(MediaType.parseMediaType(avatar.getMediaType()), bytes);
    }

    private Path saveAvatarLocal(MultipartFile file) throws IOException {
        createDirectoryIfNotExists();
        if (file.getOriginalFilename() == null) {
            logger.error("new RuntimeException(\"Incorrect file extension\")");
            throw new RuntimeException("Incorrect file extension");
        }
        Path path = Path.of(pathDir.toString(), UUID.randomUUID() + getExtension(file.getOriginalFilename()));
        file.transferTo(path);
        return path;
    }

    private void createDirectoryIfNotExists() throws IOException {
        if (Files.notExists(pathDir)) {
            Files.createDirectory(pathDir);
        }
    }

    private String getExtension(String path) {
        return path.substring(path.lastIndexOf('.'));
    }

    @Override
    public Page<Avatar> getAllAvatars(Pageable pageable) {
        logger.info("Was invoked method for get all avatars");
        return avatarRepository.findAll(pageable);
    }

    @Override
    public List<AvatarDto> getAvatars(int page, int size) {
        logger.info("Was invoked method for get all avatars 2");
        return avatarRepository.findAll(PageRequest.of(page, size))
                .get()
                .map(avatarMapper::toDto)
                .collect(Collectors.toList());
    }
}
