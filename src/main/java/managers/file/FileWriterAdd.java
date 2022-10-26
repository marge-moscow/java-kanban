package managers.file;

import model.TaskType;
import managers.history.HistoryManager;
import model.Subtask;
import model.Task;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriterAdd {

    static final String START_TIME = "STARTTIME";
    static final String DURATION = "DURATION";
    static final String END_TIME = "ENDTIME";

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
            line = String.join(",", Integer.toString(id), subtask.getType().toString(), subtask.getName(), subtask.getStatus().toString(), subtask.getDescription(),  checkTime(subtask, START_TIME), checkTime(subtask, DURATION), checkTime(subtask, END_TIME), Integer.toString(subtask.getEpicId()));
        } else {
            line = String.join(",", Integer.toString(id), task.getType().toString(), task.getName(), task.getStatus().toString(), task.getDescription(), checkTime(task, START_TIME), checkTime(task, DURATION), checkTime(task, END_TIME));
        }

        return line;
    }

    public static String checkTime(Task task, String item) {
        String line = "no data";
        switch (item) {
            case START_TIME:
                if(task.getStartTime() != null) {
                    line = task.getStartTime().toString();
                }
                break;
            case DURATION:
                if(task.getDuration() != null) {
                    line = task.getDuration().toString();
                }
                break;
            case END_TIME:
                if(task.getEndTime() != null) {
                    line = task.getEndTime().toString();
                }
                break;
            default:
                line = "no data";
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
