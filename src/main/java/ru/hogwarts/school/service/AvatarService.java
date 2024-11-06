package ru.hogwarts.school.service;


import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.dto.AvatarView;

import java.io.*;




public interface AvatarService {
    public long uploadAvatar(Long studentId, MultipartFile file) throws IOException;

    public Avatar getAvatarFromDb(Long studentId);

    public AvatarView getAvatarFromLocal(Long studentId) throws IOException;

}
