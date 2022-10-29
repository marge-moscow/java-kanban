package managers.file;

import com.google.gson.Gson;
import managers.Managers;
import managers.TaskManager;
import managers.memoryManager.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private static final String TASKS_FILENAME = "tasks.json";
    private static final String EPIC_FILENAME = "epics.json";
    private static final String SUBTASKS_FILENAME = "subtasks.json";

    private static final String HISTORY_FILENAME = "history.json";


    public FileBackedTasksManager () {
        super();
        tasks = loadTasks();
        epics = loadEpics();
        subtasks = loadSubtasks();
        loadHistory();
    }

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getFileBackedTasksManager();

        Task task = new Task(0, "task1", "task1description");

        taskManager.addTask(task);

        task.setStartTime(LocalDateTime.of(2022,10,26,20,0));

        taskManager.updateTask(task);

        task.setDuration(Duration.ofMinutes(20));

        taskManager.updateTask(task);

        TaskManager taskManager2 = Managers.getFileBackedTasksManager();

        Task task2 = new Task(0, "task2", "task2description");

        taskManager2.addTask(task2);

        System.out.println(taskManager2.getTasks());

        System.out.println(taskManager2.getHistory());


    }


    public void saveTask() {
        TaskSaver.saveMapToJson(tasks, TASKS_FILENAME);
    }

    public void saveEpic() {
        TaskSaver.saveMapToJson(epics, EPIC_FILENAME);
    }

    public void saveSubtask() {
        TaskSaver.saveMapToJson(subtasks, SUBTASKS_FILENAME);
    }

    public Map<Integer, Task>  loadTasks() {
        return (Map<Integer, Task>) TaskLoader.loadMap(TASKS_FILENAME);
    }

    public Map<Integer, Epic>  loadEpics() {
        return (Map<Integer, Epic>) TaskLoader.loadMap(EPIC_FILENAME);
    }

    public Map<Integer, Subtask>  loadSubtasks() {
        return (Map<Integer, Subtask>) TaskLoader.loadMap(SUBTASKS_FILENAME);
    }

    public void loadHistory() {

        TaskLoader.loadHistoryList(HISTORY_FILENAME).forEach(id -> {
            if (tasks.containsKey(id)) {
                historyManager.add(tasks.get(id));
            } else if (epics.containsKey(id)) {
                historyManager.add(epics.get(id));
            } else if (subtasks.containsKey(id)) {
                historyManager.add(subtasks.get(id));
            }
        });
    }

    public void saveHistory() {
        List<Integer> historyTaskId = historyManager.getHistory().stream().map(Task::getId).collect(Collectors.toList());
        TaskSaver.saveListToJson(historyTaskId, HISTORY_FILENAME);
    }


    @Override
    public Map<Integer, Task> getTasks() {
        saveHistory();
        return super.getTasks();
    }

    @Override
    public Map<Integer, Epic> getEpics() {
        Map<Integer, Epic> hashMap = super.getEpics();
        saveEpic();
        return hashMap;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        Map<Integer, Subtask> hashMap = super.getSubtasks();
        saveSubtask();
        return hashMap;
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        saveTask();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        saveEpic();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        saveSubtask();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        saveTask();
        return task;
    }

    @Override
    public Task getEpicById(int id) {
        Task task = super.getEpicById(id);
        saveEpic();
        return task;
    }

    @Override
    public Task getSubtaskById(int id) {
        Task task = super.getSubtaskById(id);
        saveSubtask();
        return task;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        saveTask();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        saveEpic();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        saveSubtask();
    }

        @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        saveTask();
    }
    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        saveEpic();
    }
    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        saveSubtask();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        saveTask();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        saveEpic();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        saveSubtask();
    }

    @Override
    public List<Subtask> getSubtasksByEpic(int id) {
        final List<Subtask> newSubtasksList = super.getSubtasksByEpic(id);
        saveSubtask();
        return newSubtasksList;
    }

}



