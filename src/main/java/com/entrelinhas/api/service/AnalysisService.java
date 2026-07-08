package com.entrelinhas.api.service;

import com.entrelinhas.api.Dto.InterpretationResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AnalysisService {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private OcrService ocrService;

    @Autowired
    private TextCleaningService textCleaningService;

    @Autowired
    private OllamaInterpretationService interpretationService;

    public InterpretationResponseDTO analisar(MultipartFile imagem)
            throws Exception {

        // Upload
        String caminhoImagem =
                uploadService.salvar(imagem);

        // Pré-processamento
        String imagemCinza =
                imageService.processarParaOCR(caminhoImagem);

        // OCR
        String texto =
                ocrService.extrairTexto(imagemCinza);

        // Limpeza
        texto =
                textCleaningService.limpar(texto);

        texto =
                textCleaningService.limparTexto(texto);

        // IA
        return interpretationService.interpretar(texto);
    }

}