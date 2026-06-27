package com.entrelinhas.api.config;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {

    @Bean
    public ITesseract tesseract() {
        Tesseract tesseract = new Tesseract();

        tesseract.setDatapath(
                "C:\\Program Files\\Tesseract\\tessdata"
        );

        tesseract.setLanguage("por");

        tesseract.setPageSegMode(3);

        tesseract.setOcrEngineMode(3);

        return tesseract;
    }
}