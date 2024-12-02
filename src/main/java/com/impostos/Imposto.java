package com.impostos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

    @Entity
    public class Imposto {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private Double taxa;

    public Imposto() {}


    public Imposto(String nome, String descricao, Double taxa) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.descricao = descricao;
        this.taxa = taxa;
    }

    public Imposto(String number, double v) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public void setAliquota(double v) {
    }
}

