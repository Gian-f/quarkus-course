package com.br.coffeeandit.service;

import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.model.TipoChave;
import com.br.coffeeandit.model.TipoPessoa;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDateTime;

@ApplicationScoped
public class DictService {

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
}
