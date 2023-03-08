package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private static final String POST = "POST";
    private static final String GET = "GET";
    private final Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Response process(Request request) {
        String status = "200";
        String text = "";
        if (POST.equals(request.getHttpRequestType())) {
            queue.putIfAbsent(request.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(request.getSourceName()).add(request.getParam());
        }
        if (GET.equals(request.getHttpRequestType())) {
            queue.putIfAbsent(request.getSourceName(), new ConcurrentLinkedQueue<>());
            text = queue.get(request.getSourceName()).poll();
        }
        if (text == null) {
            status = "204";
            text = "";
        }
        return new Response(text, status);
    }
}
