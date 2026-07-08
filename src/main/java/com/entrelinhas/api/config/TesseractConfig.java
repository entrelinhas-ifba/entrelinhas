package com.entrelinhas.api.config;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TesseractConfig {

    @Value("${tesseract.datapath}")
    private String datapath;

    @Value("${tesseract.language}")
    private String language;

    @Value("${tesseract.page-seg-mode}")
    private int pageSegMode;

    @Value("${tesseract.ocr-engine-mode}")
    private int ocrEngineMode;

    @Bean
    public ITesseract tesseract() {
        Tesseract tesseract = new Tesseract();

        tesseract.setDatapath(datapath);
        tesseract.setLanguage(language);
        tesseract.setPageSegMode(pageSegMode);
        tesseract.setOcrEngineMode(ocrEngineMode);

        return tesseract;
    }
}