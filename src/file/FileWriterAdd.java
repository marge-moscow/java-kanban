package file;

import annex.TaskType;
import managers.HistoryManager;
import tasktypes.Subtask;
import tasktypes.Task;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterAdd {

    public static void createFile() {
        Path path = Path.of("C://Users//Marge//IdeaProjects//java-kanban//file.csv");
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
            line = String.join(",", Integer.toString(id), subtask.getType().toString(), subtask.getName(), subtask.getStatus().toString(), subtask.getDescription(), Integer.toString(subtask.getEpicId()));
        } else {
            line = String.join(",", Integer.toString(id), task.getType().toString(), task.getName(), task.getStatus().toString(), task.getDescription());
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
