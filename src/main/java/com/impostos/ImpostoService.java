package com.impostos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

    @Service
    public class ImpostoService {

    @Autowired
    private ImpostoRepository impostoRepository;

    public Imposto cadastrar(Imposto imposto) {

        if (imposto.getId() == null) {
            imposto.setId(UUID.randomUUID().toString());
        }

        if (imposto.getTaxa() <= 0 || imposto.getTaxa() > 100) {
            throw new IllegalArgumentException("A taxa do imposto deve ser entre 0 e 100.");
        }

        return impostoRepository.save(imposto);
    }

    public Imposto buscarPorId(String id) {
        return impostoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Imposto não encontrado com o ID: " + id));
    }

    public double calcularImposto(String id, double valorBase) {
        Imposto imposto = impostoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imposto não encontrado"));
        return (imposto.getTaxa() / 100) * valorBase;
    }

    public void excluir(String id) {
        Imposto imposto = impostoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Imposto não encontrado para exclusão com o ID: " + id));
        impostoRepository.deleteById(imposto.getId());
    }
}
