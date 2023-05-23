package msv.management.system.filters;

import msv.management.system.configs.ConfigurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class WebSecurityFilter extends OncePerRequestFilter {

    private static final String[] IGNORE_PATHS = {
            "/api/v1/configs",
    };
    private RestTemplate authRestTemplate;

    @Autowired
    public WebSecurityFilter(RestTemplate authRestTemplate) {
        this.authRestTemplate = authRestTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (!ConfigurationConstants.getAuthEnabled()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean shouldNotFilter = false;
        if (!ConfigurationConstants.getAuthEnabled()) {
            return true;
        }
        for (String path : IGNORE_PATHS) {
            if (new AntPathMatcher().match(path, request.getServletPath())) {
                shouldNotFilter = true;
            }
        }
        return shouldNotFilter;
    }
}
