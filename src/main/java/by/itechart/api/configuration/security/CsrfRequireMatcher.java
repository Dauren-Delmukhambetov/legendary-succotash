package by.itechart.api.configuration.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

public class CsrfRequireMatcher implements RequestMatcher {
    private static final Pattern ALLOWED_METHODS =
            Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
    private static final List<String> LOCALHOST_PATTERNS =
            List.of("127.0.0.1", "0:0:0:0:0:0:0:1");

    @Override
    public boolean matches(HttpServletRequest request) {
        // CSRF disabled on GET, HEAD, TRACE, OPTIONS (i.e. enabled for POST, PUT, DELETE)
        if (ALLOWED_METHODS.matcher(request.getMethod()).matches()) return false;

        // CSRF not required on localhost when swagger-ui is referer
        final String remoteHost = request.getRemoteHost();
        final String referer = request.getHeader("Referer");
        return remoteHost == null || !LOCALHOST_PATTERNS.contains(remoteHost)
                || !"http://localhost:8080/swagger-ui/".equals(referer);
        // otherwise, CSRF is required
    }
}
