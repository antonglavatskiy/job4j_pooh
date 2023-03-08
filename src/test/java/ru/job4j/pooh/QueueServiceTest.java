package ru.job4j.pooh;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Request("POST", "queue", "weather", paramForPostMethod)
        );
        Response first = queueService.process(
                new Request("GET", "queue", "weather", null)
        );
        Response second = queueService.process(
                new Request("GET", "queue", "weather", null)
        );
        assertThat(first.getText()).isEqualTo("temperature=18");
        assertThat(first.getStatus()).isEqualTo("200");
        assertThat(second.getText()).isEqualTo("");
        assertThat(second.getStatus()).isEqualTo("204");
    }
}