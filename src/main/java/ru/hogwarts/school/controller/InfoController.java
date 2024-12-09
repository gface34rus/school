package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class InfoController {
    @Value("${server.port}")
    private int port;

    @GetMapping("/port")
    public int getPort() {
        return port;
    }

    @GetMapping("/test")
    public long test() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> ++a)
                .parallel()
                .limit(10_000_000)
                .reduce(0, Integer::sum);
        return System.currentTimeMillis() - start;
    }
}
