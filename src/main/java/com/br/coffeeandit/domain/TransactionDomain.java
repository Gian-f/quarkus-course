package com.br.coffeeandit.domain;

import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.model.LinhaDigitavel;
import com.br.coffeeandit.model.StatusPix;
import com.br.coffeeandit.model.Transaction;
import com.br.coffeeandit.repository.TransactionPanacheRepository;
import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TransactionDomain {
    @Inject
    TransactionPanacheRepository repository;

    @Inject
    @Channel("transacao")
    Emitter<Transaction> transactionEmitter;

    public void adicionarTransacao(final LinhaDigitavel linhaDigitavel, final BigDecimal valor, final Chave chave) {
        repository.adicionar(linhaDigitavel, valor, chave);
    }

    public Optional<Transaction> aprovarTransacao(final String uuid) {
        Optional<Transaction> optTransaction = repository.alterarStatusTransacao(uuid, StatusPix.APPROVED);
        optTransaction.ifPresent(this::enviarTransacao);
        return optTransaction;
    }

    private void enviarTransacao(final Transaction transaction) {
        Log.infof("Enviando mensagem de nova transação: %s", transaction);

        transactionEmitter.send(
                Message.of(transaction).addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
                        .withKey(transaction.getId())
                        .withHeaders(new RecordHeaders().add("x-linha", transaction.getLinha().getBytes(StandardCharsets.UTF_8)))
                        .build()));
    }


    public List<Transaction> buscarTransacoes(final Date dataInicio, final Date dataFim) {
        return repository.buscarTransacoes(dataInicio, dataFim);
    }

    public Optional<Transaction> reprovarTransacao(final String uuid) {
        return repository.alterarStatusTransacao(uuid, StatusPix.REPROVED);
    }

    public Optional<Transaction> iniciarProcessamento(final String uuid) {
        return repository.alterarStatusTransacao(uuid, StatusPix.IN_PROCESS);
    }

    public Optional<Transaction> findById(final String uuid) {
        return repository.findOne(uuid);
    }
}
