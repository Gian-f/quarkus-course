package com.br.coffeeandit.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

public record Pix(
        @Schema(description = "Chave cadastrada do recebedor") String chave,
        @Schema(description = "Valor da transação") BigDecimal valor,
        @Schema(description = "Cidade remetente do pagador") String cidadeRemetente) {
}
