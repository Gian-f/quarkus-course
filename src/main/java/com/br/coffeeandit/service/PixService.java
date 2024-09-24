package com.br.coffeeandit.service;

import com.br.coffeeandit.config.LinhaDigitavelCache;
import com.br.coffeeandit.domain.TransactionDomain;
import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.model.LinhaDigitavel;
import com.br.coffeeandit.model.Transaction;
import com.br.coffeeandit.model.qrcode.DadosEnvio;
import com.br.coffeeandit.model.qrcode.QrCode;
import com.br.coffeeandit.repository.S3ImageClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PixService {

    public static final String QRCODE_PATH = "/tmp/qrcode";

    @Inject
    TransactionDomain transactionDomain;

    @Inject
    LinhaDigitavelCache linhaDigitavelCache;

    @Inject
    S3ImageClientRepository s3ImageClientRepository;


    public BufferedInputStream gerarQrCode(final String uuid) {
        return new BufferedInputStream(s3ImageClientRepository.getObjects(uuid).asInputStream());
    }

    public LinhaDigitavel gerarLinhaDigitavel(final Chave chave, BigDecimal valor, String cidadeRemetente) {

        var qrCode = new QrCode(new DadosEnvio(chave, valor, cidadeRemetente));
        var uuid = UUID.randomUUID().toString();
        Path dirPath = Path.of(QRCODE_PATH);
        if (!Files.exists(dirPath)) {
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        var imagePath = QRCODE_PATH + uuid + ".png";

        qrCode.save(Path.of(imagePath));
        salvarImagem(imagePath, uuid);
        String qrCodeString = qrCode.toString();
        var linhaDigitavel = new LinhaDigitavel(qrCodeString, uuid);
        salvarLinhaDigitavel(chave, valor, linhaDigitavel);
        return linhaDigitavel;
    }

    private void salvarImagem(String imagePath, String uuid) {
        s3ImageClientRepository.putObject(Paths.get(imagePath), uuid);
    }

    private void salvarLinhaDigitavel(Chave chave, BigDecimal valor, LinhaDigitavel linhaDigitavel) {
        transactionDomain.adicionarTransacao(linhaDigitavel, valor, chave);
        linhaDigitavelCache.set(linhaDigitavel.uuid(), linhaDigitavel);
    }

    public Optional<Transaction> findById(final String uuid) {
        return transactionDomain.findById(uuid);
    }

    public Optional<Transaction> aprovarTransacao(final String uuid) {
        return transactionDomain.aprovarTransacao(uuid);
    }

    private void processarPix() {

    }

    public List<Transaction> buscarTransacoes(final Date dataInicio, final Date dataFim) {
        return transactionDomain.buscarTransacoes(dataInicio, dataFim);
    }

    public Optional<Transaction> reprovarTransacao(final String uuid) {
        return transactionDomain.reprovarTransacao(uuid);
    }
}
