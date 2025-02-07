package recetasya.com.msvc_gateway_server.filters;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class GlobalFilter implements Filter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(GlobalFilter.class);

    @Override
    public int getOrder() {
        return 100; // El orden de ejecución del filtro
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // Chequea si el request tiene un refreshToken
        String refreshToken = httpRequest.getParameter("refreshToken");
        if (refreshToken != null && !refreshToken.isEmpty()) {
            logger.info("Refresh token recibido: " + refreshToken);
            // Aquí puedes agregar más lógica para procesar el refresh token si es necesario
        }
        
        logger.info("Llamada filtro SampleGlobalFilter::doFilter");
        chain.doFilter(request, response);
    }
}
