import java.time.LocalDate;

public class Request {
    private int requestId;
    private final int clientId;
    private final int dispatcherId;
    private final LocalDate createdAt;
    private final String description;
    private final String priority;
    private final String status;

    public Request(int clientId, int dispatcherId, LocalDate createdAt, String description, String priority, String status) {
        this.clientId = clientId;
        this.dispatcherId = dispatcherId;
        this.createdAt = createdAt;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public Request(int requestId, int clientId, int dispatcherId, LocalDate createdAt, String description, String priority, String status) {
        this.requestId = requestId;
        this.clientId = clientId;
        this.dispatcherId = dispatcherId;
        this.createdAt = createdAt;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getDispatcherId() {
        return dispatcherId;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }


    @Override
    public String toString() {
        return "ID заявки: " + requestId + "\n" +
                "ID клієнта: " + clientId + "\n" +
                "ID диспетчера: " + dispatcherId + "\n" +
                "Дата створення: " + createdAt + "\n" +
                "Опис: " + description + "\n" +
                "Пріоритет: " + priority + "\n" +
                "Статус: " + status;
    }
}
