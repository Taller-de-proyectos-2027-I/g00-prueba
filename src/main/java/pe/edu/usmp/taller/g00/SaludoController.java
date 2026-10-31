package pe.edu.usmp.taller.g00;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class SaludoController {

    @GetMapping("/saludo")
    public Map<String, String> saludo(
            @RequestParam(defaultValue = "mundo")
            @Size(max = 50)
            @Pattern(regexp = "^[\\p{L} ]+$")
            String nombre) {
        return Map.of("mensaje", "Hola, " + nombre);
    }
}
