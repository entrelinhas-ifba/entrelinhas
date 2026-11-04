package com.entrelinhas.api.service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class OcrService {
    private final ObjectProvider<ITesseract> tesseractProvider;

    public OcrService(ObjectProvider<ITesseract> tesseractProvider) {
        this.tesseractProvider = tesseractProvider;
    }

    public String extrairTexto(String caminhoImagem) throws Exception {
        try {

            BufferedImage img = ImageIO.read(new File(caminhoImagem));


            if (img == null) {
                throw new IllegalArgumentException(
                        "Não foi possível carregar a imagem."
                );
            }

            int largura = img.getWidth();
            int altura = img.getHeight();
            int metadeAltura = altura / 2;

            BufferedImage topo = img.getSubimage(
                    0, 0, largura, metadeAltura
            );

            BufferedImage baixo = img.getSubimage(
                    0, metadeAltura, largura, altura - metadeAltura
            );

            ITesseract tesseract = tesseractProvider.getObject();

            String textoTopo = tesseract.doOCR(topo);
            String textoBaixo = tesseract.doOCR(baixo);

            return textoTopo + "\n" + textoBaixo;

        } catch (IOException | TesseractException e) {
            throw new RuntimeException("Erro ao executar OCR.", e);
        }
    }
}
