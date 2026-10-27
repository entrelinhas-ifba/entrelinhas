package com.entrelinhas.api.service;

import com.entrelinhas.api.dto.LanguageDetectionDTO;
import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;
import org.springframework.stereotype.Service;

@Service
public class LanguageDetectionService {

    private final LanguageDetector detector;

    public LanguageDetectionService() {

        this.detector = LanguageDetectorBuilder
                .fromLanguages(
                        Language.PORTUGUESE,
                        Language.ENGLISH,
                        Language.SPANISH,
                        Language.FRENCH,
                        Language.ITALIAN,
                        Language.GERMAN
                )
                .build();
    }

    public LanguageDetectionDTO identificarIdioma(String texto) {

        Language idioma = detector.detectLanguageOf(texto);

        return new LanguageDetectionDTO(
                idioma.name()
        );
    }
}