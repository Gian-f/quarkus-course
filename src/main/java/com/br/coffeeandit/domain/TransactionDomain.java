package com.br.coffeeandit.domain;

import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.model.LinhaDigitavel;
import com.br.coffeeandit.model.StatusPix;
import com.br.coffeeandit.model.Transaction;
import com.br.coffeeandit.repository.TransactionPanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TransactionDomain {

    @Inject
    TransactionPanacheRepository repository;

    public void adicionarTransacao(final LinhaDigitavel linhaDigitavel, final BigDecimal valor, final Chave chave) {
        repository.adicionar(linhaDigitavel, valor, chave);
    }

    public Optional<Transaction> aprovarTransacao(final String uuid) {
        try {
            return repository.alterarStatusTransacao(uuid, StatusPix.APROVED);
        } finally {
            iniciarProcessamento(uuid);
        }
    }

    public List<Transaction> buscarTransacoes(final Date dataInicio, final Date dataFim) {
        return repository.buscarTransacoes(dataInicio, dataFim);
    }

    public Optional<Transaction> reprovarTransacao(final String uuid) {
        return repository.alterarStatusTransacao(uuid, StatusPix.APROVED);
    }

    public Optional<Transaction> iniciarProcessamento(final String uuid) {
        return repository.alterarStatusTransacao(uuid, StatusPix.IN_PROCESS);
    }

    public Optional<Transaction> findById(final String uuid) {
        return repository.findOne(uuid);
    }
}
