package managers.file;

import exceptions.UnreadableFileException;
import model.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskLoader {
    public static String readFileContentsOrNull(File file) throws IOException {
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new UnreadableFileException("Невозможно прочитать файл.");
        }
    }

    public static Task fromString(String value){
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[2];
        String description = parts[4];
        LocalDateTime startTime;
        try {
            startTime = LocalDateTime.parse(parts[5]);
        } catch (DateTimeParseException e) {
            startTime = null;
        }
        Duration duration = Duration.parse(parts[6]);


        Task task;

        if (TaskType.valueOf(parts[1]) == TaskType.SUBTASK) {
            int epicId = Integer.parseInt(parts[8]);
            task = new Subtask(id, name, description, startTime, duration, epicId);
        } else if (TaskType.valueOf(parts[1]) == TaskType.EPIC) {
            task = new Epic(id, name, description);
        } else {
            task = new Task(id, name, description, startTime, duration);
        }

        return task;
    }

    public static List<Integer> historyFromString(String value) {
        String[] parts = value.split(",");
        List<Integer> history = new ArrayList<>();
        for (String part: parts) {
            history.add(Integer.parseInt(part));
        }

        return history;
    }

}

