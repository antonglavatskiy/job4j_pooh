package ru.job4j.pooh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoohServer {
    private static final int THREADS_COUNT = Runtime.getRuntime().availableProcessors();
    private final HashMap<String, Service> modes = new HashMap<>();

    public void start() {
        modes.put("queue", new QueueService());
        modes.put("topic", new TopicService());
        ExecutorService pool = Executors.newFixedThreadPool(THREADS_COUNT);
        try (ServerSocket serverSocket = new ServerSocket(9000)) {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                pool.execute(
                        () -> {
                            try (OutputStream out = socket.getOutputStream();
                                 InputStream in = socket.getInputStream()) {
                                byte[] buffer = new byte[1_000_000];
                                int total = in.read(buffer);
                                String content = new String(
                                        Arrays.copyOfRange(buffer, 0, total), StandardCharsets.UTF_8
                                );
                                Request request = Request.of(content);
                                Response response = modes.get(request.getPoohMode()).process(request);
                                String ls = System.lineSeparator();
                                out.write(("HTTP/1.1 " + response.getStatus() + ls + ls).getBytes());
                                out.write((response.getText().concat(ls)).getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PoohServer().start();
    }
}
