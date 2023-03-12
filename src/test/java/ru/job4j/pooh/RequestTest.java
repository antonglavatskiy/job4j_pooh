package ru.job4j.pooh;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class RequestTest {
    @Test
    public void whenUnknownRequest() {
        String content = """
                UPDATE /queue/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.72.0
                Accept: */*
                Content-Length: 14
                Content-Type: application/x-www-form-urlencoded
                
                temperature=18
                
                """;
        assertThatThrownBy(() -> Request.of(content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown request");
    }

    @Test
    public void whenQueueModePostMethod() {
        String content = """
                POST /queue/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.72.0
                Accept: */*
                Content-Length: 14
                Content-Type: application/x-www-form-urlencoded
                
                temperature=18
                
                """;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType()).isEqualTo("POST");
        assertThat(request.getPoohMode()).isEqualTo("queue");
        assertThat(request.getSourceName()).isEqualTo("weather");
        assertThat(request.getParam()).isEqualTo("temperature=18");
    }

    @Test
    public void whenQueueModeGetMethod() {
        String content = """
                GET /queue/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.72.0
                Accept: */*
 
 
 
                """;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType()).isEqualTo("GET");
        assertThat(request.getPoohMode()).isEqualTo("queue");
        assertThat(request.getSourceName()).isEqualTo("weather");
        assertThat(request.getParam()).isEqualTo("");
    }

    @Test
    public void whenTopicModePostMethod() {
        String content = """
                POST /topic/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.72.0
                Accept: */*
                Content-Length: 14
                Content-Type: application/x-www-form-urlencoded
                
                temperature=18
                
                """;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType()).isEqualTo("POST");
        assertThat(request.getPoohMode()).isEqualTo("topic");
        assertThat(request.getSourceName()).isEqualTo("weather");
        assertThat(request.getParam()).isEqualTo("temperature=18");
    }

    @Test
    public void whenTopicModeGetMethod() {
        String content = """
                GET /topic/weather/client407 HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.72.0
                Accept: */*
                
                

                """;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType()).isEqualTo("GET");
        assertThat(request.getPoohMode()).isEqualTo("topic");
        assertThat(request.getSourceName()).isEqualTo("weather");
        assertThat(request.getParam()).isEqualTo("client407");
    }
}