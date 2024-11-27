package ru.hogwarts.school.controller;

import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.dto.AvatarDto;
import ru.hogwarts.school.model.dto.AvatarView;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.impl.AvatarServiceImpl;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(AvatarController.BASE_URI)
@Validated
public class AvatarController {
    public static final String BASE_URI = "/avatar";
    private final AvatarServiceImpl avatarService;

    public AvatarController(AvatarServiceImpl avatarService) {
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

    @GetMapping("/get-all")
    public ResponseEntity<Page<Avatar>> getAllAvatars(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return ResponseEntity.ok(avatarService.getAllAvatars(pageable));
    }

    @GetMapping("/get-all2")
    public List<AvatarDto> getAvatars(
            @RequestParam @Min(0) int page,
            @RequestParam @Min(1) int size) {
        return avatarService.getAvatars(page, size);
    }
}
