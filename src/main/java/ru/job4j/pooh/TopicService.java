package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private static final String POST = "POST";
    private static final String GET = "GET";
    private final Map<String, Map<String, ConcurrentLinkedQueue<String>>> topic = new ConcurrentHashMap<>();

    @Override
    public Response process(Request request) {
        String status = "200";
        String text = "";
        if (POST.equals(request.getHttpRequestType())) {
            for (ConcurrentLinkedQueue<String> clientQueue : topic.get(request.getSourceName()).values()) {
                clientQueue.add(request.getParam());
            }
        }
        if (GET.equals(request.getHttpRequestType())) {
            topic.putIfAbsent(request.getSourceName(), new ConcurrentHashMap<>());
            topic.get(request.getSourceName()).putIfAbsent(request.getParam(), new ConcurrentLinkedQueue<>());
            text = topic.get(request.getSourceName()).get(request.getParam()).poll();
        }
        if (text == null) {
            status = "204";
            text = "";
        }
        return new Response(text, status);
    }
}
