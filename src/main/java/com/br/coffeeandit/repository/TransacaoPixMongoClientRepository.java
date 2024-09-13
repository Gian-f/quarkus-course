package com.br.coffeeandit.repository;

import com.br.coffeeandit.domain.TransactionConverterApply;
import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.model.LinhaDigitavel;
import com.br.coffeeandit.model.StatusPix;
import com.br.coffeeandit.model.Transaction;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import jakarta.inject.Inject;
import org.bson.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

public class TransacaoPixMongoClientRepository implements TransactionRepository {

    @Inject
    MongoClient mongoClient;

    public static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";

    @Override
    public void adicionar(LinhaDigitavel linhaDigitavel, BigDecimal valor, Chave chave) {
        var document = new Document();
        document.append(TransactionConverterApply.ID, linhaDigitavel.uuid())
                .append(TransactionConverterApply.VALOR, valor)
                .append(TransactionConverterApply.TIPO_CHAVE, chave.tipoChave())
                .append(TransactionConverterApply.CHAVE, chave.chave())
                .append(TransactionConverterApply.LINHA, linhaDigitavel.linha())
                .append(TransactionConverterApply.DATA, LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        getCollection().insertOne(document);

    }

    private MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("pix").getCollection("transacao_pix");
    }

    @Override
    public Optional<Transaction> alterarStatusTransacao(String uuid, StatusPix statusPix) {
        return Optional.empty();
    }

    @Override
    public Optional<Document> findOne(String uuid) {
        return Optional.empty();
    }
}
