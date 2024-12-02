package com.impostos;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/impostos")
public class ImpostoController {

    private final ImpostoService impostoService;

    public ImpostoController(ImpostoService impostoService) {
        this.impostoService = impostoService;
    }

    @PostMapping
    public ResponseEntity<Imposto> cadastrarImposto(@RequestBody Imposto imposto) {
        Imposto novoImposto = impostoService.cadastrar(imposto);
        return ResponseEntity.status(201).body(novoImposto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imposto> buscarImpostoPorId(@PathVariable String id) {
        Imposto imposto = impostoService.buscarPorId(id);
        return ResponseEntity.ok(imposto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirImposto(@PathVariable String id) {
        impostoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calcular")
    public ResponseEntity<Double> calcularImposto(@RequestParam String id, @RequestParam double valorBase) {
        double valorImposto = impostoService.calcularImposto(id, valorBase);
        return ResponseEntity.ok(valorImposto);
    }
}

