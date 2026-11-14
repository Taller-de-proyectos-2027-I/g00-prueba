package pe.edu.usmp.taller.g00.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Pruebas con el contexto completo de Spring (validación, manejador de errores y
 * filtro de cabeceras tal como corren en producción).
 */
@SpringBootTest
class SaludoControllerTest {

    @Autowired
    private WebApplicationContext contexto;

    @Autowired
    private CabecerasSeguridadFilter cabeceras;

    private MockMvc mvc;

    @BeforeEach
    void preparar() {
        mvc = MockMvcBuilders.webAppContextSetup(contexto).addFilters(cabeceras).build();
    }

    @Test
    void saludoValido() throws Exception {
        mvc.perform(get("/api/saludo").param("nombre", "Ana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Hola, Ana"));
    }

    @Test
    void nombreDemasiadoLargoDa400() throws Exception {          // caso "Buffer Overflow" de ZAP
        mvc.perform(get("/api/saludo").param("nombre", "a".repeat(500)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cadenaDeFormatoDa400() throws Exception {               // caso "Format String" de ZAP
        mvc.perform(get("/api/saludo").param("nombre", "%n%s%n%s"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void respuestasLlevanCabecerasDeSeguridad() throws Exception {
        mvc.perform(get("/api/saludo"))
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().string("Cross-Origin-Resource-Policy", "same-origin"))
                .andExpect(header().string("Cache-Control", "no-store"));
    }
}
