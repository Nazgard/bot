package dev.makarov.bot.rocket;

public class RocketStatusRDTO {

    private String message;
    private String status;

    public String getMessage() {
        return message;
    }

    public RocketStatusRDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public RocketStatusRDTO setStatus(String status) {
        this.status = status;
        return this;
    }
}
