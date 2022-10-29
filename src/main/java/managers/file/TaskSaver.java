package managers.file;

import com.google.gson.Gson;
import model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class TaskSaver {

    static Gson gson = new Gson();

    public static void saveMapToJson (Map<Integer, ? extends Task> map, String fileName) {
        try {
            if (!Files.exists(Path.of(fileName))) {
                Files.createFile(Path.of(fileName));
            }

            try (Writer fileWriter = new FileWriter(fileName)) {

                String jsonMap = gson.toJson(map);
                fileWriter.write(jsonMap);
            }

        } catch (IOException e) {
            throw new RuntimeException("Не получилось записать задачу.");
        }
    }

    public static void saveListToJson (List<Integer> list, String fileName) {
        try {
            if (!Files.exists(Path.of(fileName))) {
                Files.createFile(Path.of(fileName));
            }

            try (Writer fileWriter = new FileWriter(fileName)) {

                String jsonMap = gson.toJson(list);
                fileWriter.write(jsonMap);
            }

        } catch (IOException e) {
            throw new RuntimeException("Не получилось записать задачу.");
        }
    }

}
