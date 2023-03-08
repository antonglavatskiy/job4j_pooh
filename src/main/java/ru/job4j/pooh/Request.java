package ru.job4j.pooh;

public class Request {
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String QUEUE_MODE = "queue";
    private static final String TOPIC_MODE = "topic";
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Request(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    private static String[] parse(String content) {
        String[] rsl = new String[4];
        String[] arr = content.split(System.lineSeparator());
        String[] line = arr[0].split("\\s");
        String[] params = line[1].split("/");
        rsl[0] = line[0];
        rsl[1] = params[1];
        rsl[2] = params[2];
        if (POST.equals(line[0])) {
            rsl[3] = arr[arr.length - 1];
        } else if (GET.equals(line[0]) && QUEUE_MODE.equals(params[1])) {
            rsl[3] = "";
        } else if (GET.equals(line[0]) && TOPIC_MODE.equals(params[1])) {
            rsl[3] = params[3];
        } else {
            throw new IllegalArgumentException("Unknown request");
        }
        return rsl;
    }

    public static Request of(String content) {
        String[] arr = parse(content);
        return new Request(arr[0], arr[1], arr[2], arr[3]);
    }

    public String getHttpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
