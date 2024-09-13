package com.br.coffeeandit.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public record LinhaDigitavel(
        @Schema(description = "Linha Digitável") String linha,
        @Schema(description = "UUID gerado") String uuid) {
}
