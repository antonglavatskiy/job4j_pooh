package ru.job4j.pooh;

public class Response {
    private final String text;
    private final String status;

    public Response(String text, String status) {
        this.text = text;
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public String getStatus() {
        return status;
    }
}
