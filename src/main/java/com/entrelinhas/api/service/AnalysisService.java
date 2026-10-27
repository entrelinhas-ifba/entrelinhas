package com.entrelinhas.api.service;

import com.entrelinhas.api.dto.DeepInterpretationResponseDTO;
import com.entrelinhas.api.dto.InterpretationResponseDTO;
import com.entrelinhas.api.dto.LanguageDetectionDTO;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
public class AnalysisService {

    private final UploadService uploadService;
    private final ImageService imageService;
    private final OcrService ocrService;
    private final TextCleaningService textCleaningService;
    private final LanguageDetectionService languageDetectionService;
    private final DeepInterpretationService deepInterpretationService;
    private final ResourceLoader resourceLoader;
    private final OllamaClient ollamaClient;

    public AnalysisService(
            UploadService uploadService,
            ImageService imageService,
            OcrService ocrService,
            TextCleaningService textCleaningService,
            LanguageDetectionService languageDetectionService,
            DeepInterpretationService deepInterpretationService, ResourceLoader resourceLoader, OllamaClient ollamaClient
    ) {
        this.uploadService = uploadService;
        this.imageService = imageService;
        this.ocrService = ocrService;
        this.textCleaningService = textCleaningService;
        this.languageDetectionService = languageDetectionService;
        this.deepInterpretationService = deepInterpretationService;
        this.resourceLoader = resourceLoader;
        this.ollamaClient = ollamaClient;
    }

    public DeepInterpretationResponseDTO analisarProfundamente(
            MultipartFile imagem
    ) throws Exception {

        String texto = extrairTexto(imagem);

        LanguageDetectionDTO idioma =
                languageDetectionService.identificarIdioma(texto);

        return deepInterpretationService.interpretar(
                texto,
                idioma.getIdioma()
        );
    }

    private String extrairTexto(
            MultipartFile imagem
    ) throws Exception {

        String caminhoImagem =
                uploadService.salvar(imagem);

        String imagemCinza =
                imageService.processarParaOCR(caminhoImagem);

        String texto =
                ocrService.extrairTexto(imagemCinza);

        texto =
                textCleaningService.limpar(texto);

        texto =
                textCleaningService.limparTexto(texto);

        return texto;
    }

    public InterpretationResponseDTO interpretar(MultipartFile imagem) throws Exception {
        String texto = extrairTexto(imagem);

        Resource resource = resourceLoader.getResource(
                "classpath:prompts/interpretacao-simples.txt"
        );

        try (var inputStream = resource.getInputStream()) {
            String template = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            String prompt = template.replace("{{texto}}", texto);

            String resposta = ollamaClient.generate(prompt);
            InterpretationResponseDTO  resultado = converterResposta(resposta);
            resultado.setTextoOriginal(texto);

            return resultado;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Não foi possível carregar o prompt de interpretação.",
                    e
            );
        }
    }

    private InterpretationResponseDTO converterResposta(String resposta) {

        String resumo = extrairCampo(resposta, "Resumo:", "Tema principal:");
        String tema = extrairCampo(resposta, "Tema principal:", "Intenção do autor:");
        String intencao = extrairCampo(resposta, "Intenção do autor:", "Ideias principais:");

        List<String> ideias = Arrays.stream(
                        resposta.substring(resposta.indexOf("Ideias principais:"))
                                .replace("Ideias principais:", "")
                                .split("\n"))
                .map(String::trim)
                .filter(l -> l.startsWith("-"))
                .map(l -> l.substring(1).trim())
                .toList();

        return InterpretationResponseDTO.builder()
                .resumo(resumo)
                .tema(tema)
                .intencaoAutor(intencao)
                .ideiasPrincipais(ideias)
                .build();
    }

    private String extrairCampo(
            String texto, String inicio, String fim
    ) {
        int i = texto.indexOf(inicio);

        if (i == -1) {
            return "";
        }

        i += inicio.length();

        int f = texto.indexOf(fim, i);

        if (f == -1) {
            f = texto.length();
        }

        return texto.substring(i, f).trim();
    }
}