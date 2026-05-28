package com.example.demo.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GeminiClient {

    @Value("${ia.gemini.apiKey:}")
    private String apiKey;

    @Value("${ia.gemini.model:gemini-2.0-flash}")
    private String model;

    @Value("${ia.gemini.baseUrl:https://generativelanguage.googleapis.com/v1beta}")
    private String baseUrl;

    @Value("${ia.gemini.timeoutMs:20000}")
    private int timeoutMs;

    private RestClient client;

    private RestClient client() {
        if (client == null) {
            client = RestClient.builder()
                    .baseUrl(baseUrl)
                    .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }
        return client;
    }

    public boolean estaConfigurado() {
        return apiKey != null && !apiKey.isBlank();
    }

    public String gerar(String systemPrompt, String userPrompt) {
        if (!estaConfigurado()) {
            throw new IllegalStateException(
                    "IA não configurada. Defina a propriedade ia.gemini.apiKey.");
        }

        Map<String, Object> body = Map.of(
                "system_instruction", Map.of(
                        "parts", List.of(Map.of("text", systemPrompt))
                ),
                "contents", List.of(Map.of(
                        "role", "user",
                        "parts", List.of(Map.of("text", userPrompt))
                )),
                "generationConfig", Map.of(
                        "temperature", 0.4,
                        "topP", 0.9,
                        "maxOutputTokens", 800
                ),
                "safetySettings", List.of(
                        Map.of("category", "HARM_CATEGORY_HARASSMENT",
                               "threshold", "BLOCK_MEDIUM_AND_ABOVE"),
                        Map.of("category", "HARM_CATEGORY_HATE_SPEECH",
                               "threshold", "BLOCK_MEDIUM_AND_ABOVE"),
                        Map.of("category", "HARM_CATEGORY_SEXUALLY_EXPLICIT",
                               "threshold", "BLOCK_LOW_AND_ABOVE"),
                        Map.of("category", "HARM_CATEGORY_DANGEROUS_CONTENT",
                               "threshold", "BLOCK_MEDIUM_AND_ABOVE")
                )
        );

        String path = "/models/" + model + ":generateContent?key=" + apiKey;

        Map<?, ?> resposta = client()
                .post()
                .uri(path)
                .body(body)
                .retrieve()
                .body(Map.class);

        return extrairTexto(resposta);
    }

    @SuppressWarnings("unchecked")
    private String extrairTexto(Map<?, ?> resposta) {
        if (resposta == null) {
            return null;
        }

        Object promptFeedback = resposta.get("promptFeedback");
        if (promptFeedback instanceof Map<?, ?> pf && pf.get("blockReason") != null) {
            return null;
        }

        Object candidatos = resposta.get("candidates");
        if (!(candidatos instanceof List<?> lista) || lista.isEmpty()) {
            return null;
        }

        Object primeiro = lista.get(0);
        if (!(primeiro instanceof Map<?, ?> candidato)) {
            return null;
        }

        Object content = candidato.get("content");
        if (!(content instanceof Map<?, ?> contentMap)) {
            return null;
        }

        Object parts = contentMap.get("parts");
        if (!(parts instanceof List<?> partsList) || partsList.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (Object p : partsList) {
            if (p instanceof Map<?, ?> partMap) {
                Object texto = partMap.get("text");
                if (texto != null) {
                    sb.append(texto);
                }
            }
        }
        String resultado = sb.toString().trim();
        return resultado.isBlank() ? null : resultado;
    }
}
