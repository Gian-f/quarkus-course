package com.br.coffeeandit.service;

import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.model.LinhaDigitavel;
import com.br.coffeeandit.model.qrcode.DadosEnvio;
import com.br.coffeeandit.model.qrcode.QrCode;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@ApplicationScoped
public class PixService {

    public static final String QRCODE_PATH = "C:\\Users\\gfsilva\\Desenvolvimento\\curso\\quarkus3\\src\\main\\resources\\tmp";

    public BufferedInputStream gerarQrCode(final String uuid) throws IOException {
        // TODO RECUPERAR DO CACHE
        var imagePath = QRCODE_PATH + uuid + ".png";
        try {
            return new BufferedInputStream(new FileInputStream(imagePath));
        } finally {
            Files.deleteIfExists(Paths.get(imagePath));
        }
    }

    public LinhaDigitavel gerarLinhaDigitavel(final Chave chave, BigDecimal valor, String cidadeRemetente) {

        var qrCode = new QrCode(new DadosEnvio(chave, valor, cidadeRemetente));
        var uuid = UUID.randomUUID().toString();
        var imagePath = QRCODE_PATH + uuid + ".png";
        qrCode.save(Path.of(imagePath));
        // TODO IMPLEMENTAR CACHE SALVAR
        String qrCodeString = qrCode.toString();

        return new LinhaDigitavel(qrCodeString, uuid);
    }
}