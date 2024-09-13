package com.br.coffeeandit.service;

import com.br.coffeeandit.config.RedisCache;
import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.model.TipoChave;
import com.br.coffeeandit.model.TipoPessoa;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;
import java.util.Objects;

@ApplicationScoped
public class DictService {

    @Inject
    RedisCache redisCache;

    @ConfigProperty(name = "pix.chave")
    private String chave;

    @ConfigProperty(name = "pix.ispb")
    private String ispb;

    @ConfigProperty(name = "pix.cnpj")
    private String cnpj;

    @ConfigProperty(name = "pix.nome")
    private String nome;

    public Chave buscarChave(String chave) {
        return new Chave(TipoChave.EMAIL, chave, ispb, TipoPessoa.JURIDICA, cnpj, nome, LocalDateTime.now());
    }

    public Chave buscarDetalhesChave(String key) {
        var chave = buscarChaveCache(key);
        if (Objects.isNull(chave)) {
            var mockChave = buscarChave(key);
            redisCache.set(key, mockChave);
            return mockChave;
        }
        return chave;
    }

    private Chave buscarChaveCache(String key) {
        var chave = redisCache.get(key);
        Log.infof("Chave encontrada no cache %s", chave);
        return chave;
    }
}
