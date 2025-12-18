package fr.bar.cocktails.game;

import java.time.LocalDateTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderLog {
    private static final OrderLog instance = new OrderLog();
    private List<LogEntry> logs = new ArrayList<>();
    private final int MAX_LOGS = 20;

    private OrderLog() {}

    public static OrderLog getInstance() {
        return instance;
    }

    public static class LogEntry {
        public String message;
        public String type; // "order", "serveur", "barman", "completed", "error"
        public long timestamp;

        public LogEntry(String message, String type) {
            this.message = message;
            this.type = type;
            this.timestamp = System.currentTimeMillis();
        }

        public String getFormattedTime() {
            Instant instant = Instant.ofEpochMilli(timestamp);
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
    }

    public void addLog(String message, String type) {
        logs.add(0, new LogEntry(message, type)); // Ajouter au début
        if (logs.size() > MAX_LOGS) {
            logs.remove(logs.size() - 1); // Supprimer le plus ancien
        }
    }

    public List<LogEntry> getLogs() {
        return new ArrayList<>(logs);
    }

    public void clear() {
        logs.clear();
    }
}
