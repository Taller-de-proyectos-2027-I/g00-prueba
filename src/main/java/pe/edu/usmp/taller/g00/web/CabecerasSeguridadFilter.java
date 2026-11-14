package pe.edu.usmp.taller.g00.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Cabeceras de seguridad en todas las respuestas, incluidas las de error.
 * Corrige los hallazgos de ZAP "X-Content-Type-Options Header Missing",
 * "Insufficient Site Isolation Against Spectre" y "Re-examine Cache-control".
 */
@Component
public class CabecerasSeguridadFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Cross-Origin-Resource-Policy", "same-origin");
        response.setHeader("Cross-Origin-Opener-Policy", "same-origin");
        response.setHeader("Cache-Control", "no-store");
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;   // también en las páginas de error (404, 500)
    }
}
