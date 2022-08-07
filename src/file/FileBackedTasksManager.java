package file;

import annex.ManagerSaveException;
import annex.TaskStatus;
import annex.TaskType;
import managers.Managers;
import managers.TaskManger;
import managers.memoryManager.InMemoryTaskManager;
import tasktypes.Epic;
import tasktypes.Subtask;
import tasktypes.Task;

import java.io.*;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManger {
    protected static List <Integer> history;


    public static void main(String[] args) throws Exception {

        TaskManger taskManager = Managers.getFileBackedTasksManager();

        FileWriterAdd.createFile();
        Task task1 = new Task(
                0,
                "Купить корм для рыбок.",
                "Корм для рыб Зоомир Гурман-3 30г.",
                TaskType.TASK
        );

        taskManager.addItem(task1);

        Epic epic1 = new Epic(
                0,
                "Навести порядок в кладовке.",
                "Разобрать все вещи и организовать систему хранения.",
                TaskType.EPIC
        );

        taskManager.addItem(epic1);

        Subtask subtask1 = new Subtask(
                0,
                "Разобрать вещи в кладовке.",
                "Выкинуть всё.",
                TaskType.SUBTASK,
                epic1.getId()
        );

        taskManager.addItem(subtask1);



        taskManager.getSubtaskById(3);
        taskManager.getTaskById(1);

        System.out.println(taskManager.getHistory());


        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File("file.csv"));
        fileBackedTasksManager.getEpics();


        Subtask subtask3 = new Subtask(
                0,
                "Кукареку",
                "Ку-ку",
                TaskType.SUBTASK,
                epic1.getId()
        );

        fileBackedTasksManager.addItem(subtask3);

        fileBackedTasksManager.getSubtaskById(4);
        System.out.println(fileBackedTasksManager.getHistory());

    }

    public void save() {
        try {
            Writer fileWriter = new FileWriter("file.csv");
            fileWriter.write("id,type,name,status,description,epicId\n");
            for (Integer id: tasks.keySet()) {
                fileWriter.write(FileWriterAdd.toString(tasks.get(id)));
                fileWriter.append("\n");
            }
            for (Integer id: epics.keySet()) {
                fileWriter.write(FileWriterAdd.toString(epics.get(id)));
                fileWriter.append("\n");
            }
            for (Integer id: subtasks.keySet()) {
                fileWriter.write(FileWriterAdd.toString(subtasks.get(id)));
                fileWriter.append("\n");
            }

            if (tasks.isEmpty()){
                throw new ManagerSaveException ("Ничего не выходит :(");
            }

            fileWriter.append("\n");
            fileWriter.write(FileWriterAdd.historyToString(historyManager));
            fileWriter.close();

        } catch (ManagerSaveException | IOException e) {
            System.out.println(e.getMessage());

        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws Exception {

        FileBackedTasksManager tasksManager = new FileBackedTasksManager();

        String content = FileReader.readFileContentsOrNull(file);
        String[] lines = content.split("\n");
        int startId = 0;
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            if (line.isEmpty()) {
                history = FileReader.historyFromString(lines[i + 1]);
                break;
            }
            Task task = FileReader.fromString(line);
            int id = task.getId();
            if (id > startId) {
                startId = id;
            }
            tasksManager.addItem(task);
        }

        tasksManager.startId = startId;
        return tasksManager;
    }

    @Override
    public void getTasks() {
        super.getTasks();
        save();
    }

    @Override
    public void getEpics() {
        super.getEpics();
        save();
    }

    @Override
    public void getSubtasks() {
        super.getSubtasks();
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
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

    @Override
    public void addItem(Task task) {
        super.addItem(task);
        save();
    }

    @Override
    public void updateItem(Task task, TaskStatus status) {
        super.updateItem(task, status);
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



