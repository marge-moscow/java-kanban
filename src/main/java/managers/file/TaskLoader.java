package managers.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Task;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TaskLoader {

    static Gson gson = new Gson();

    public static Map<Integer, ? extends Task> loadMap(String fileName) {
        try {
            if (!Files.exists(Path.of(fileName))) {
                return new HashMap<>();
            }

            try (Reader fileReader = new FileReader(fileName)) {
                Map<Integer, ? extends Task> map = new HashMap<>();
                Type type = new TypeToken<Map<Integer, ? extends Task>>(){}.getType();
                Optional<Map<Integer, ? extends Task>> optionalMap = Optional.ofNullable(gson.fromJson(fileReader, type));
                if (optionalMap.isPresent()) {
                    map = optionalMap.get();
                }
                return map;

            }

        } catch (IOException e) {
            throw new RuntimeException("Не получилось записать задачу.");
        }

    }

    public static List<Integer> loadHistoryList(String fileName) {
        try {
            if (!Files.exists(Path.of(fileName))) {
                return new ArrayList<>();
            }

            try (Reader fileReader = new FileReader(fileName)) {
                List<Integer> list = new ArrayList<>();

                Type type = new TypeToken<List<Integer>>(){}.getType();
                Optional<List<Integer>> optionalList = Optional.ofNullable(gson.fromJson(fileReader, type));
                if (optionalList.isPresent()) {
                    list = optionalList.get();
                }
                return list;

            }

        } catch (IOException e) {
            throw new RuntimeException("Не получилось записать задачу.");
        }
    }

}

