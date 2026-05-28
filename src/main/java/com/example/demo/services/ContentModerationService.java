package com.example.demo.services;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ContentModerationService {

    public enum Categoria {
        ADULTO,
        CRIME_VIOLENCIA,
        DROGAS,
        AUTOAGRESSAO,
        PROMPT_INJECTION,
        COMPROMETIMENTO_APP,
        OK
    }

    public record Resultado(boolean bloqueado, Categoria categoria, String detalhe) {
        public static Resultado ok() {
            return new Resultado(false, Categoria.OK, null);
        }

        public static Resultado bloquear(Categoria categoria, String detalhe) {
            return new Resultado(true, categoria, detalhe);
        }
    }

    private static final List<Pattern> ADULTO = compilar(List.of(
            "pornografi", "pornograf", "sexo explicit", "nudez", "conteudo adulto",
            "sexual explicit", "explicit sexual", "nsfw"
    ));

    private static final List<Pattern> CRIME_VIOLENCIA = compilar(List.of(
            "como fazer (uma )?bomba", "fabricar explosiv", "construir explosiv",
            "como matar", "assassinar", "assassinato", "homicidio", "esfaquear",
            "como roubar", "como furtar", "sequestrar pessoa",
            "lavagem de dinheiro", "como traficar",
            "fazer arma", "fabricar arma", "imprimir arma",
            "agredir alguem", "espancar"
    ));

    private static final List<Pattern> DROGAS = compilar(List.of(
            "como sintetizar (cocaina|metanfetamina|heroina|lsd|ecstasy|crack)",
            "como produzir (cocaina|metanfetamina|heroina|lsd|ecstasy|crack)",
            "como fabricar droga", "receita de droga"
    ));

    private static final List<Pattern> AUTOAGRESSAO = compilar(List.of(
            "como me matar", "como se matar", "metodos de suicidio",
            "como cometer suicidio", "se cortar", "automutilacao"
    ));

    private static final List<Pattern> PROMPT_INJECTION = compilar(List.of(
            "ignore (as |todas )?(suas )?instruco",
            "ignore (the |all |your )?(previous |above )?instruction",
            "desconsidere (as |suas )?instruco",
            "esquec(a|e) (as |suas )?instruco",
            "voce agora e ", "voce e agora ", "you are now ",
            "act as (dan|jailbreak)", "modo jailbreak", "modo developer",
            "system prompt", "prompt do sistema", "revele o prompt",
            "mostre seu prompt", "show your prompt",
            "finja que ", "pretenda ser ", "pretend to be ",
            "sem filtros", "sem restrico", "no restriction"
    ));

    private static final List<Pattern> COMPROMETIMENTO_APP = compilar(List.of(
            "drop table", "delete from tbl_", "truncate table",
            "; *--", "union select", "or 1=1",
            "select \\* from tbl_", "update tbl_",
            "<script", "javascript:", "onerror=", "onload=",
            "\\.\\./\\.\\.", "etc/passwd", "windows/system32",
            "jwt secret", "chave jwt", "credenciais do banco", "db password",
            "senha do banco", "exfiltrar dados", "vazar dados",
            "rm -rf", "format c:", "shutdown /", "kill -9"
    ));

    private static List<Pattern> compilar(List<String> regexes) {
        return regexes.stream()
                .map(r -> Pattern.compile(r, Pattern.CASE_INSENSITIVE))
                .toList();
    }

    public Resultado moderarEntrada(String texto) {
        if (texto == null || texto.isBlank()) {
            return Resultado.bloquear(Categoria.OK, "Pergunta vazia");
        }
        if (texto.length() > 2000) {
            return Resultado.bloquear(Categoria.OK, "Pergunta excede 2000 caracteres");
        }
        return analisar(texto);
    }

    public Resultado moderarSaida(String texto) {
        if (texto == null || texto.isBlank()) {
            return Resultado.ok();
        }
        return analisar(texto);
    }

    private Resultado analisar(String texto) {
        String normalizado = normalizar(texto);

        if (corresponde(normalizado, PROMPT_INJECTION)) {
            return Resultado.bloquear(Categoria.PROMPT_INJECTION,
                    "Tentativa de manipular o comportamento da IA");
        }
        if (corresponde(normalizado, COMPROMETIMENTO_APP)) {
            return Resultado.bloquear(Categoria.COMPROMETIMENTO_APP,
                    "Conteúdo que pode comprometer a aplicação");
        }
        if (corresponde(normalizado, ADULTO)) {
            return Resultado.bloquear(Categoria.ADULTO, "Conteúdo adulto");
        }
        if (corresponde(normalizado, CRIME_VIOLENCIA)) {
            return Resultado.bloquear(Categoria.CRIME_VIOLENCIA,
                    "Conteúdo de crime ou violência");
        }
        if (corresponde(normalizado, DROGAS)) {
            return Resultado.bloquear(Categoria.DROGAS, "Produção de drogas ilícitas");
        }
        if (corresponde(normalizado, AUTOAGRESSAO)) {
            return Resultado.bloquear(Categoria.AUTOAGRESSAO,
                    "Conteúdo de autoagressão");
        }
        return Resultado.ok();
    }

    private boolean corresponde(String texto, List<Pattern> padroes) {
        for (Pattern p : padroes) {
            if (p.matcher(texto).find()) {
                return true;
            }
        }
        return false;
    }

    private String normalizar(String texto) {
        String semAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return semAcentos.toLowerCase();
    }
}
