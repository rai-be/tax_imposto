package com.impostos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLOutput;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


    // ExtendWith insere dependencias automaticamente, sem eu precisar fazer manualmente
    @ExtendWith(MockitoExtension.class)

    // O @SpringBootTest cria uma instância completa do Spring Boot, o que significa que tudo, incluindo os beans e o contexto da aplicação, é carregado.
    public class ImpostoServiceTest {

    @Mock // O @Mock cria um objeto simulado (mock) que pode ser utilizado para testar funcionalidades sem interagir com dependências reais.
    private ImpostoRepository impostoRepository; // Mockando o repositório

    @InjectMocks
    private ImpostoService impostoService; // Serviço que quero testar

    private Imposto imposto;

    @BeforeEach
    public void setUp() {
        imposto = new Imposto( "ICMS", "Imposto sobre Circulação de Mercadorias e Serviços", 18.0); // Eu estava gerando um id com UUID automaticamente então, eu não precisava por o id no construtor e chamar ele aqui no objeto, era oque estava fazendo o teste não passar....
    }

    @Test
    public void testCadastrarTipoImposto() {

        Mockito.when(impostoRepository.save(Mockito.any(Imposto.class))).thenReturn(imposto);

        Imposto resultado = impostoService.cadastrar(imposto);

        assertNotNull(resultado);
        assertEquals("ICMS", resultado.getNome());
        Mockito.verify(impostoRepository, Mockito.times(1)).save(imposto);
        System.out.println("Teste 'testCadastrarTipoImposto' passou!");
    }

    @Test
    public void testCalcularImposto() {
        double valorBase = 1000.0;
        Mockito.when(impostoRepository.findById(imposto.getId())).thenReturn(Optional.of(imposto));

        double valorImposto = impostoService.calcularImposto(imposto.getId(), valorBase); // Chamando o serviço para calcular o imposto

        assertEquals(180.0, valorImposto);

        Mockito.verify(impostoRepository, Mockito.times(1)).findById(imposto.getId());
        System.out.println("Teste 'testCalcularImposto' passou!");
    }

    @Test
    public void testBuscarImpostoPorId() {
        Mockito.when(impostoRepository.findById(imposto.getId())).thenReturn(Optional.of(imposto)); // Simulando (mockando) o comportamento do repositório

        Imposto resultado = impostoService.buscarPorId(imposto.getId()); // Chamando o serviço para buscar o imposto

        assertNotNull(resultado);
        assertEquals("ICMS", resultado.getNome());
        System.out.println("Teste 'testBuscarImpostoPorId' passou!");
    }

    @Test
    public void testExcluirImposto() {

        Mockito.when(impostoRepository.findById(imposto.getId())).thenReturn(Optional.of(imposto));

        impostoService.excluir(imposto.getId());

        Mockito.verify(impostoRepository, Mockito.times(1)).findById(imposto.getId());
        Mockito.verify(impostoRepository, Mockito.times(1)).deleteById(imposto.getId());
        System.out.println("Teste 'testExcluirImposto' passou!");

    }
}
