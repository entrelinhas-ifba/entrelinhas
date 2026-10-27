package com.entrelinhas.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "O arquivo enviado está vazio."
            );
        }

        BufferedImage imagem = ImageIO.read(file.getInputStream());

        if (imagem == null) {
            throw new IllegalArgumentException(
                    "O arquivo enviado não é uma imagem válida ou suportada."
            );
        }

        Path diretorio = Paths.get(uploadDir);
        Files.createDirectories(diretorio);

        String identificador = Long.toHexString(
                System.currentTimeMillis()
        ).toUpperCase();

        String nomeArquivo = "imagem-" + identificador + ".png";
        Path caminho = diretorio.resolve(nomeArquivo);

        if (!ImageIO.write(imagem, "png", caminho.toFile())) {
            throw new IOException("Não foi possível converter a imagem para PNG.");
        }

        return caminho.toString();
    }
}