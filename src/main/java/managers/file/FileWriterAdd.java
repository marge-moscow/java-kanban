package managers.file;

import model.TaskType;
import managers.history.HistoryManager;
import model.Subtask;
import model.Task;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterAdd {

    public static void createFile() {
        Path path = Path.of("managers.file.csv");
        try {
            Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Запись в ранее созданный файл!");
        }
    }

    public static String toString(Task task){
        int id = task.getId();
        String line;

        if (task.getType() == TaskType.SUBTASK) {
            Subtask subtask = (Subtask) task;
            line = String.join(",", Integer.toString(id), subtask.getType().toString(), subtask.getName(), subtask.getStatus().toString(), subtask.getDescription(),  checkStartTime(subtask), checkDuration(subtask), checkEndTime(subtask), Integer.toString(subtask.getEpicId()));
        } else {
            line = String.join(",", Integer.toString(id), task.getType().toString(), task.getName(), task.getStatus().toString(), task.getDescription(), checkStartTime(task), checkDuration(task), checkEndTime(task));
        }

        return line;
    }

    public static String checkStartTime(Task task) {
        String line;
        if (task.getStartTime() == null) {
            line = "no StartTime";
        } else {
           line = task.getStartTime().toString();
        }
        return line;
    }

    private static String checkEndTime(Task task) {
        String line;
        if (task.getEndTime() == null) {
            line = "no EndTime";
        } else {
            line = task.getEndTime().toString();
        }
        return line;
    }

    private static String checkDuration(Task task) {
        String line;
        if (task.getDuration() == null) {
            line = "no Duration";
        } else {
            line = task.getDuration().toString();
        }
        return line;
    }

    static String historyToString(HistoryManager manager) {
        StringBuilder sb = new StringBuilder();
        for (Task task : manager.getHistory()) {
            sb.append(task.getId()).append(",");
        }
        return sb.toString();
    }

}
