package com.entrelinhas.api.service;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_imgproc.CLAHE;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.*;


@Service
public class ImageService {
    @Value("${app.upload.dir}")
    private String uploadDir;

    @org.springframework.beans.factory.annotation.Value("${app.processed.dir}")
    private String processedDir;

    public String processarParaOCR(String caminhoImagem) throws IOException {
        Mat image = imread(caminhoImagem);

        if (image.empty()) {
            throw new RuntimeException("Imagem inválida");
        }

        Mat gray = new Mat();
        Mat claheImage = new Mat();
        Mat resized = new Mat();

        // Escala de cinza
        cvtColor(
                image,
                gray,
                COLOR_BGR2GRAY
        );

        // CLAHE
        CLAHE clahe = createCLAHE();
        clahe.setClipLimit(2.0);
        clahe.apply(
                gray,
                claheImage
        );

        // Resize
        resize(
                claheImage,
                resized,
                new Size(),
                2.0,
                2.0,
                INTER_CUBIC
        );

        Path processedPath = Paths.get(processedDir);

        if (!Files.exists(processedPath)) {
            Files.createDirectories(processedPath);
        }

        String nomeArquivo =
                Paths.get(caminhoImagem)
                        .getFileName()
                        .toString()
                        .replace(".png", "");

        String grayPath =
                processedPath.resolve(
                        nomeArquivo + "_gray.png"
                ).toString();

        String clahePath =
                processedPath.resolve(
                        nomeArquivo + "_clahe.png"
                ).toString();

        String finalPath =
                processedPath.resolve(
                        nomeArquivo + "_final.png"
                ).toString();

        // Salva cada etapa
        imwrite(grayPath, gray);

        imwrite(clahePath, claheImage);

        imwrite(finalPath, resized);

        return finalPath;
    }
}

