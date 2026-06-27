package com.entrelinhas.api.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class OcrService {
    @Autowired
    private ITesseract tesseract;

    public String extrairTexto(MultipartFile arquivo) throws Exception {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + arquivo.getOriginalFilename());
        arquivo.transferTo(convFile);

        try {
            BufferedImage img = ImageIO.read(convFile);

            int largura = img.getWidth();
            int altura = img.getHeight();

            BufferedImage topo =
                    img.getSubimage(
                            0,
                            0,
                            largura,
                            altura / 2
                    );

            BufferedImage baixo =
                    img.getSubimage(
                            0,
                            altura / 2,
                            largura,
                            altura / 2
                    );

            System.out.println("Largura: " + img.getWidth());
            System.out.println("Altura: " + img.getHeight());
            String textoTopo = tesseract.doOCR(topo);
            String textoBaixo = tesseract.doOCR(baixo);

            return textoTopo + "\n" + textoBaixo;
        } catch (TesseractException e) {
            throw new RuntimeException("Erro ao ler a imagem", e);
        }
    }
}
