package com.br.coffeeandit.repository;

import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.model.LinhaDigitavel;
import com.br.coffeeandit.model.StatusPix;
import com.br.coffeeandit.model.Transaction;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransactionRepository {
    void adicionar(final LinhaDigitavel linhaDigitavel, final BigDecimal valor, final Chave chave);

    Optional<Transaction> alterarStatusTransacao(final String uuid, final StatusPix statusPix);

    Optional<Document> findOne(final String uuid);
}
