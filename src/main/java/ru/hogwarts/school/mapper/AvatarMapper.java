package ru.hogwarts.school.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.dto.AvatarDto;

import static ru.hogwarts.school.controller.AvatarController.BASE_URI;

@Component
public class AvatarMapper {
    @Value("${server.port}")
    private int port;

    public AvatarDto toDto(Avatar avatar) {
        AvatarDto dto = new AvatarDto();
        dto.setId(avatar.getId());
        dto.setStudentName(avatar.getStudent().getName());
        dto.setUrl(UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path(BASE_URI)
                .pathSegment("get/from-db")
                .queryParam("studentId", avatar.getStudent().getId())
                .build()
                .toString());
        return dto;
    }
}
