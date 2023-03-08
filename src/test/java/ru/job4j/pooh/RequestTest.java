package ru.job4j.pooh;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class RequestTest {
    @Test
    public void whenUnknownRequest() {
        String ls = System.lineSeparator();
        String content = "UPDATE /queue/weather HTTP/1.1" + ls +
                "Host: localhost:9000" + ls +
                "User-Agent: curl/7.72.0" + ls +
                "Accept: */*" + ls +
                "Content-Length: 14" + ls +
                "Content-Type: application/x-www-form-urlencoded" + ls +
                "" + ls +
                "temperature=18" + ls;
        assertThatThrownBy(() -> Request.of(content))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown request");
    }

    @Test
    public void whenQueueModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /queue/weather HTTP/1.1" + ls +
                "Host: localhost:9000" + ls +
                "User-Agent: curl/7.72.0" + ls +
                "Accept: */*" + ls +
                "Content-Length: 14" + ls +
                "Content-Type: application/x-www-form-urlencoded" + ls +
                "" + ls +
                "temperature=18" + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType()).isEqualTo("POST");
        assertThat(request.getPoohMode()).isEqualTo("queue");
        assertThat(request.getSourceName()).isEqualTo("weather");
        assertThat(request.getParam()).isEqualTo("temperature=18");
    }

    @Test
    public void whenQueueModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /queue/weather HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType()).isEqualTo("GET");
        assertThat(request.getPoohMode()).isEqualTo("queue");
        assertThat(request.getSourceName()).isEqualTo("weather");
        assertThat(request.getParam()).isEqualTo("");
    }

    @Test
    public void whenTopicModePostMethod() {
        String ls = System.lineSeparator();
        String content = "POST /topic/weather HTTP/1.1" + ls +
                "Host: localhost:9000" + ls +
                "User-Agent: curl/7.72.0" + ls +
                "Accept: */*" + ls +
                "Content-Length: 14" + ls +
                "Content-Type: application/x-www-form-urlencoded" + ls +
                "" + ls +
                "temperature=18" + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType()).isEqualTo("POST");
        assertThat(request.getPoohMode()).isEqualTo("topic");
        assertThat(request.getSourceName()).isEqualTo("weather");
        assertThat(request.getParam()).isEqualTo("temperature=18");
    }

    @Test
    public void whenTopicModeGetMethod() {
        String ls = System.lineSeparator();
        String content = "GET /topic/weather/client407 HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        Request request = Request.of(content);
        assertThat(request.getHttpRequestType()).isEqualTo("GET");
        assertThat(request.getPoohMode()).isEqualTo("topic");
        assertThat(request.getSourceName()).isEqualTo("weather");
        assertThat(request.getParam()).isEqualTo("client407");
    }
}