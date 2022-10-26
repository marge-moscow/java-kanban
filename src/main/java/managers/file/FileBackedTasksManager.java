package managers.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import exceptions.NoTimeException;
import model.TaskStatus;
import managers.Managers;
import managers.TaskManager;
import managers.memoryManager.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private static final String TASKS_FILENAME = "tasks.json";
    private static final String EPIC_FILENAME = "epics.json";
    private static final String SUBTASKS_FILENAME = "subtasks.json";
    protected static List <Integer> history;

    public FileBackedTasksManager () {

        //написать метод load
        tasks = loadTasks();
    }

    Gson gson = new Gson();

    public static void main(String[] args) throws Exception {

        TaskManager taskManager = Managers.getFileBackedTasksManager();

        Task task = new Task(0, "task1", "task1description");

        taskManager.addTask(task);

        task.setStartTime(LocalDateTime.of(2022,10,26,20,0));

        taskManager.updateTask(task);

        task.setDuration(Duration.ofMinutes(20));

        taskManager.updateTask(task);

        TaskManager taskManager2 = Managers.getFileBackedTasksManager();

        System.out.println(taskManager2.getTasks());




    }

    //TODO вынести все save в FileWriter
    public void saveMapToJson (Map<Integer, ? extends Task> map, String fileName) {
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

    public void save() {
        try (Writer fileWriter = new FileWriter("managers.file.csv")) {
            fileWriter.write("id,type,name,status,description,startTime,duration,endTime,epicId\n");
            for (Integer id: tasks.keySet()) {
                fileWriter.write(TaskSaver.toString(tasks.get(id)));
                fileWriter.append("\n");
            }
            for (Integer id: epics.keySet()) {
                fileWriter.write(TaskSaver.toString(epics.get(id)));
                fileWriter.append("\n");
            }
            for (Integer id: subtasks.keySet()) {
                fileWriter.write(TaskSaver.toString(subtasks.get(id)));
                fileWriter.append("\n");
            }

            fileWriter.append("\n");
            fileWriter.write(TaskSaver.historyToString(historyManager));


        } catch (IOException e) {
            throw new NoTimeException("Ничего не получилось.");
        }
    }

    public void saveTask() {
        saveMapToJson(tasks, TASKS_FILENAME);
    }

    public void saveEpic() {
        try (Writer fileWriter = new FileWriter(TASKS_FILENAME)) {
            String jsonTasks = gson.toJson(tasks);
            fileWriter.write(jsonTasks);
        } catch (IOException e) {
            throw new RuntimeException("Не получилось записать задачу.");
        }
    }

    public void saveSubtask() {
        try (Writer fileWriter = new FileWriter("managers.file.csv")) {
            fileWriter.write("id,type,name,status,description,startTime,duration,endTime,epicId\n");
            for (Integer id: tasks.keySet()) {
                fileWriter.write(TaskSaver.toString(tasks.get(id)));
                fileWriter.append("\n");
            }
            for (Integer id: epics.keySet()) {
                fileWriter.write(TaskSaver.toString(epics.get(id)));
                fileWriter.append("\n");
            }
            for (Integer id: subtasks.keySet()) {
                fileWriter.write(TaskSaver.toString(subtasks.get(id)));
                fileWriter.append("\n");
            }

            fileWriter.append("\n");
            fileWriter.write(TaskSaver.historyToString(historyManager));


        } catch (IOException e) {
            throw new NoTimeException("Ничего не получилось.");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws Exception {

        FileBackedTasksManager tasksManager = Managers.getFileBackedTasksManager();

        String content = TaskLoader.readFileContentsOrNull(file);
        String[] lines = content.split("\n");
        int startId = 0;
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.isEmpty()) {
                history = TaskLoader.historyFromString(lines[i + 1]);
                break;
            }

            Task task = TaskLoader.fromString(line);
            int id = task.getId();
            if (id > startId) {
                startId = id;
            }
            tasksManager.addTask(task);

        }

        tasksManager.startId = startId;
        return tasksManager;
    }

    public Map<Integer, Task>  loadTasks() {
        return (Map<Integer, Task>) loadMap(TASKS_FILENAME);
    }
    public Map<Integer, ? extends Task> loadMap(String fileName) {
        try {
            if (!Files.exists(Path.of(fileName))) {
                return new HashMap<>();
            }

            try (Reader fileReader = new FileReader(fileName)) {
                Type type = new TypeToken<Map<Integer, ? extends Task>>(){}.getType();
                return gson.fromJson(fileReader, type);

            }

        } catch (IOException e) {
            throw new RuntimeException("Не получилось записать задачу.");
        }

    }

    @Override
    public Map<Integer, Task> getTasks() {
        Map<Integer, Task> hashMap = super.getTasks();
        save();
        return hashMap;
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        Map<Integer, Epic> hashMap = super.getEpics();
        save();
        return hashMap;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        Map<Integer, Subtask> hashMap = super.getSubtasks();
        save();
        return hashMap;
    }


    //TODO Во всех методах удаления исправить save на новый в соответствии с задачей
    @Override
    public void deleteTasks() {
        super.deleteTasks();
        saveTask();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Task getEpicById(int id) {
        Task task = super.getEpicById(id);
        save();
        return task;
    }

    @Override
    public Task getSubtaskById(int id) {
        Task task = super.getSubtaskById(id);
        save();
        return task;
    }

    /*@Override
    public void addItem(Task task) {
        super.addItem(task);
        save();
    }*/

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        saveTask();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateItem(Task task, TaskStatus status) {
        super.updateItem(task, status);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        saveTask();
    }
    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }
    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public List<Subtask> getSubtasksByEpic(int id) {
        final List<Subtask> newSubtasksList = super.getSubtasksByEpic(id);
        save();
        return newSubtasksList;
    }

    @Override
    public List<String> getHistory() {
        List<String> getHistoryList = super.getHistory();
        save();
        return  getHistoryList;
    }

}



