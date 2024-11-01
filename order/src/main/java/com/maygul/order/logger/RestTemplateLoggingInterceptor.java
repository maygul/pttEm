package com.maygul.order.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    public RestTemplateLoggingInterceptor(){

    }
    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        this.logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        CachedClientHttpResponse cachedResponse = new CachedClientHttpResponse(response);
        this.logResponse(cachedResponse);
        return cachedResponse;
    }

    private void logRequest(HttpRequest request, byte[] body) {

        var requestBody = new String(body, StandardCharsets.UTF_8);
        var method = request.getMethod().toString();
        var uri = request.getURI().toString();
        var headers = request.getHeaders();
        var dto =
                RequestLogDto.builder()
                        .type(LogTypeEnum.REQUEST_OUT)
                        .method(method)
                        .url(uri)
                        .mediaType(
                                request.getHeaders().getContentType() != null
                                        ? request.getHeaders().getContentType().toString()
                                        : null)
                        .methodParams(null)
                        .headers(headers.toSingleValueMap())
                        .requestBody(requestBody)
                        .build();

        log.info("Request : {}", LogTypeEnum.serialize(dto));
    }


    private void logResponse(ClientHttpResponse response) throws IOException {
        var body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
        var httpStatus = response.getStatusCode().value();
        var mediaType = response.getHeaders().getContentType();
        var headers = response.getHeaders();
        var dto =
                ResponseLogDto.builder()
                        .type(LogTypeEnum.RESPONSE_IN)
                        .httpStatus(httpStatus)
                        .mediaType(mediaType)
                        .responseBody(body)
                        .headers(headers.toSingleValueMap())
                        .build();

        log.info("Response : {}", LogTypeEnum.serialize(dto));
    }

    private static class CachedClientHttpResponse implements ClientHttpResponse {
        private final ClientHttpResponse originalResponse;
        private byte[] body;

        public CachedClientHttpResponse(ClientHttpResponse originalResponse) {
            this.originalResponse = originalResponse;
        }

        @Override
        public InputStream getBody() throws IOException {
            if (this.body == null) {
                this.body = StreamUtils.copyToByteArray(originalResponse.getBody());
            }
            return new ByteArrayInputStream(this.body);
        }

        @Override
        public HttpHeaders getHeaders() {
            return originalResponse.getHeaders();
        }

        @Override
        public HttpStatusCode getStatusCode() throws IOException {
            return originalResponse.getStatusCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return originalResponse.getStatusText();
        }

        @Override
        public void close() {
            originalResponse.close();
        }
    }
}
