package org.example;

public class RequestBody {
    private String title;
    private String body;
    private int userId;

    // Constructor
    public RequestBody(String title, String body, int userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    // Getters and Setters (Optional, if needed for Gson)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

