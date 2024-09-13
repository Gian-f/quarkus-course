package com.br.coffeeandit.domain;

import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.model.LinhaDigitavel;
import com.br.coffeeandit.repository.TransacaoPixMongoClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.transaction.Transactional;

import java.math.BigDecimal;

@ApplicationScoped
public class TransactionDomain {

    @Inject
    TransacaoPixMongoClientRepository repository;

    @Transactional
    public void adicionarTransacao(final LinhaDigitavel linhaDigitavel, final BigDecimal valor, final Chave chave) {
        repository.adicionar(linhaDigitavel, valor, chave);
    }
}
