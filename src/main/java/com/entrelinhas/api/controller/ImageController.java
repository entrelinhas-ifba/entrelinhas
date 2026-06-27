package com.entrelinhas.api.controller;

import com.entrelinhas.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/preprocess")
    public ResponseEntity<String> preprocess(
            @RequestParam("file") MultipartFile file) {
        try {

            Path uploadDir = Paths.get("uploads");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(file.getOriginalFilename());

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            String caminhoLimpo =
                    imageService.processarParaOCR(filePath.toString());

            return ResponseEntity.ok(caminhoLimpo);

        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao processar imagem: " + e.getMessage());
        }
    }
}
