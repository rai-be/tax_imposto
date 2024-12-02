package com.impostos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @SpringBootTest
    @AutoConfigureMockMvc // Configura automaticamente o MockMvc para simular chamadas HTTP nos testes e ainda, Permite testar endpoints GET, POST, sem precisar subir o servidor (facilita muito)

    class ImpostoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean //substituindo o mock "normal"
    private ImpostoService impostoService;
    @Test
    void testCadastrarImposto() throws Exception {
        Imposto imposto = new Imposto("123", 15.0);
        when(impostoService.cadastrar(any(Imposto.class))).thenReturn(imposto);


        mockMvc.perform(post("/impostos")
               .contentType(MediaType.APPLICATION_JSON)
               .content("{\"id\": \"123\", \"taxa\": 15.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.taxa").value(15.0));

        verify(impostoService, times(1)).cadastrar(any(Imposto.class));
    }


        @Test
        void testCalcularImposto() throws Exception {
        Imposto imposto = new Imposto("123", 15.0);
        when(impostoService.calcularImposto(eq("123"), anyDouble())).thenReturn(30.0);

        mockMvc.perform(post("/impostos/calcular")
                 .param("id", "123")
                 .param("valorBase", "200.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(30.0));

        verify(impostoService, times(1)).calcularImposto(eq("123"), anyDouble());
    }

        @Test
        void testBuscarImpostoPorId() throws Exception {
        Imposto imposto = new Imposto("123", 15.0);
        when(impostoService.buscarPorId("123")).thenReturn(imposto);

        mockMvc.perform(get("/impostos/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.taxa").value(15.0));

        verify(impostoService, times(1)).buscarPorId("123");
    }
}

