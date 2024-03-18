package parkingnomad.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import parkingnomad.dto.error.ErrorResponse;
import parkingnomad.exception.AuthException;
import parkingnomad.exception.InvalidTokenFormatException;
import parkingnomad.jwt.TokenParser;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static parkingnomad.exception.AuthExceptionCode.INVALID_TOKEN_FORMAT;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private static final String ACCESS_TOKEN_PREFIX = "Bearer ";
    private static final String MEMBER_ID = "X-Member-Id";
    private static final List<String> EXCLUDE_URI = List.of("/api/auth/kakao", "/api/auth/refresh", "/api/auth/logout");

    private final TokenParser tokenParser;
    private final ObjectMapper objectMapper;

    public AuthFilter(final TokenParser tokenParser, final ObjectMapper objectMapper) {
        super(Config.class);
        this.tokenParser = tokenParser;
        this.objectMapper = objectMapper;
    }

    private static String extractToken(final ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(token) || !token.startsWith(ACCESS_TOKEN_PREFIX)) {
            throw new InvalidTokenFormatException(INVALID_TOKEN_FORMAT.getCode());
        }
        return token.replace(ACCESS_TOKEN_PREFIX, "");
    }

    @Override
    public GatewayFilter apply(final Config config) {
        return (exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            final String path = request.getURI().getPath();
            for (String excludeUri : EXCLUDE_URI) {
                if (excludeUri.equals(path)) {
                    return chain.filter(exchange);
                }
            }

            try {
                String token = extractToken(exchange);
                final Long memberId = tokenParser.parseToMemberIdFromAccessToken(token);
                exchange.getRequest().mutate().header(MEMBER_ID, String.valueOf(memberId));
                return chain.filter(exchange);
            } catch (AuthException exception) {
                return handleUnauthenticated(exchange, exception);
            } catch (Exception exception) {
                return handleUnexpectedException(exchange, exception);
            }
        };
    }

    private Mono<Void> handleUnauthenticated(final ServerWebExchange exchange, final AuthException exception) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(APPLICATION_JSON);
        final ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), exception.getMessage());
        return getVoidMono(errorResponse, response);
    }

    private Mono<Void> handleUnexpectedException(final ServerWebExchange exchange, final Exception exception) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        response.getHeaders().setContentType(APPLICATION_JSON);
        final ErrorResponse errorResponse = ErrorResponse.internalServerError(exception.getMessage());
        return getVoidMono(errorResponse, response);
    }

    private Mono<Void> getVoidMono(final ErrorResponse errorResponse, final ServerHttpResponse response) {
        try {
            byte[] responseBytes = objectMapper.writeValueAsBytes(errorResponse);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBytes)));
        } catch (JsonProcessingException e) {
            return response.setComplete();
        }
    }

    public static class Config {
    }
}
