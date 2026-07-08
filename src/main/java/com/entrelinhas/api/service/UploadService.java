package com.entrelinhas.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public String salvar(MultipartFile file) throws IOException {

        Path diretorio = Paths.get(uploadDir);

        if (!Files.exists(diretorio)) {
            Files.createDirectories(diretorio);
        }

        Path caminho = diretorio.resolve(file.getOriginalFilename());

        Files.copy(
                file.getInputStream(),
                caminho,
                StandardCopyOption.REPLACE_EXISTING
        );

        return caminho.toString();
    }

}