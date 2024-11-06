package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.dto.AvatarView;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.impl.AvatarServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public long uploadAvatar(@RequestParam("studentId") Long studentId, @RequestBody MultipartFile file) throws IOException {
        return avatarService.uploadAvatar(studentId, file);
    }

    @GetMapping(value = "/get/from-db")
    public ResponseEntity<byte[]> getAvatarFromDb(@RequestParam("studentId") Long studentId) {
        Avatar avatar = avatarService.getAvatarFromDb(studentId);
        return ResponseEntity.status(HttpStatus.OK).
                contentType(MediaType.parseMediaType(avatar.getMediaType())).
                body(avatar.getData());

    }

    @GetMapping(value = "get/from-local")
    public ResponseEntity<byte[]> getAvatarFromLocal(@RequestParam("studentId") Long studentId) throws IOException {
        AvatarView view = avatarService.getAvatarFromLocal(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(view.getMediaType())
                .body(view.getContent());
    }
}
