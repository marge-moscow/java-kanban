package managers.file;

import model.TaskType;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    public static String readFileContentsOrNull(File file) throws Exception {
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            throw new FileNotFoundException("Невозможно прочитать файл.");
        }
    }

    public static Task fromString(String value){
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[2];
        String description = parts[4];
        TaskType type = TaskType.valueOf(parts[1]);

        Task task;

        if (TaskType.valueOf(parts[1]) == TaskType.SUBTASK) {
            int epicId = Integer.parseInt(parts[5]);
            task = new Subtask(id, name, description, epicId);
        } else if (TaskType.valueOf(parts[1]) == TaskType.EPIC) {
            task = new Epic(id, name, description);
        } else {
            task = new Task(id, name, description);
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

