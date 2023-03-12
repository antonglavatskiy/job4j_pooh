package ru.job4j.pooh;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class TopicServiceTest {
    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Request("POST", "topic", "weather", paramForPublisher)
        );
        Response result1 = topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber1)
        );
        Response result2 = topicService.process(
                new Request("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.getText()).isEqualTo("temperature=18");
        assertThat(result2.getText()).isEqualTo("");
    }
}