package com.br.coffeeandit.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;

public record Chave(
        @Schema(description = "Tipo da chave Pix") TipoChave tipoChave,
        @Schema(description = "chave pix") String chave,
        @Schema(description = "Primeiros 6 digitos do CNPJ(ispb)") String ispb,
        @Schema(description = "Tipo de pessoa. Ex: Física ou Jurídica") TipoPessoa tipoPessoa,
        @Schema(description = "CPF ou CNPJ de acordo com o tipo de pessoa") String cpfCnpj,
        @Schema(description = "Nome da chave pix") String nome,
        LocalDateTime dataHoraCriacao) {
}
