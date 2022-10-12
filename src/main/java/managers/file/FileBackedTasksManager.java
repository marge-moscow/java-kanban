package managers.file;

import exceptions.ManagerSaveException;
import managers.history.HistoryManager;
import managers.history.InMemoryHistoryManager;
import model.TaskStatus;
import managers.Managers;
import managers.TaskManager;
import managers.memoryManager.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    protected static List <Integer> history;


    public static void main(String[] args) throws Exception {

        TaskManager taskManager = Managers.getFileBackedTasksManager();

        FileWriterAdd.createFile();
        Task task1 = new Task(
                0,
                "Купить корм для рыбок.",
                "Корм для рыб Зоомир Гурман-3 30г.",
                LocalDateTime.of(2022,10,07,23,00),
                Duration.ofMinutes(15)
        );

        taskManager.addItem(task1);

        Epic epic1 = new Epic(
                0,
                "Навести порядок в кладовке.",
                "Разобрать все вещи и организовать систему хранения."
        );

        taskManager.addItem(epic1);

        Subtask subtask1 = new Subtask(
                0,
                "Разобрать вещи в кладовке.",
                "Выкинуть всё.",
                LocalDateTime.of(2022,10,07,23,00),
                Duration.ofMinutes(15),
                epic1.getId()
        );

        taskManager.addItem(subtask1);

        Subtask subtask2 = new Subtask(
                0,
                "Разобрать вещи в кладовке.",
                "Выкинуть всё.",
                LocalDateTime.of(2022,10,01,23,00),
                Duration.ofMinutes(15),
                epic1.getId()
        );

        taskManager.addItem(subtask2);

        Subtask subtask3 = new Subtask(
                0,
                "Разобрать вещи в кладовке.",
                "Выкинуть всё.",
                Duration.ofMinutes(15),
                epic1.getId()
        );

        taskManager.addItem(subtask3);



        taskManager.getSubtaskById(3);
        taskManager.getTaskById(1);

        taskManager.getTaskById(89);

        System.out.println(taskManager.getHistory());


       FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(new File("managers.file.csv"));
       fileBackedTasksManager.getEpics();


        Subtask subtask4 = new Subtask(
                0,
                "Кукареку",
                "Ку-ку",
                epic1.getId()
        );

        fileBackedTasksManager.addItem(subtask4);
        fileBackedTasksManager.getSubtaskById(6);
      //System.out.println(fileBackedTasksManager.getHistory());


        // "Удаление всех задач и эпиков и сабтасок не приводит к очистке файла и истории".
        // Евгений, привет!
        // А может быть такое, что в твоем примере (ниже) история не удаляется,
        // потому что мы уже создали новый объект fileBackedTasksManager и считали в него/с ним файл.
        // А удалить пытаемся в старом taskManager.
        // Потому что, если удалять в fileBackedTasksManager (пробую ниже), то всё удаляется и история меняется.
        // Ровно как и если удалять у taskManager.deleteTasks(), но не вызывать после этого
        // просмотр System.out.println(fileBackedTasksManager.getHistory())
        // - который был создан и считан до изменений.
        // Я надеюсь, что правильно сформулировала свою мысль...


        //Твой пример теста:
/*        System.out.println("History");
        taskManager.deleteTasks();
        taskManager.deleteEpics();
        taskManager.deleteSubtasks();
        System.out.println(fileBackedTasksManager.getHistory());*/


        //Так работает:
/*        System.out.println("History");
        taskManager.deleteTasks();
        taskManager.deleteEpics();
        taskManager.deleteSubtasks();
        System.out.println(taskManager.getHistory());*/


        //И так тоже работает:
/*        System.out.println("History");
        fileBackedTasksManager.deleteTasks();
        fileBackedTasksManager.deleteSubtasks();
        fileBackedTasksManager.deleteEpics();
        System.out.println(fileBackedTasksManager.getHistory());*/



    }

    public void save() {
        try {
            Writer fileWriter = new FileWriter("managers.file.csv");
            fileWriter.write("id,type,name,status,description,startTime,duration,endTime,epicId\n");
            for (Integer id: tasks.keySet()) {
                fileWriter.write(FileWriterAdd.toString(tasks.get(id)));
                fileWriter.append("\n");
                if (tasks.isEmpty()){
                    throw new ManagerSaveException ("Ничего не выходит :(");
                }
            }
            for (Integer id: epics.keySet()) {
                fileWriter.write(FileWriterAdd.toString(epics.get(id)));
                fileWriter.append("\n");
                if (epics.isEmpty()){
                    throw new ManagerSaveException ("Ничего не выходит :(");
                }
            }
            for (Integer id: subtasks.keySet()) {
                fileWriter.write(FileWriterAdd.toString(subtasks.get(id)));
                fileWriter.append("\n");
                if (subtasks.isEmpty()){
                    throw new ManagerSaveException ("Ничего не выходит :(");
                }
            }

            fileWriter.append("\n");
            fileWriter.write(FileWriterAdd.historyToString(historyManager));
            fileWriter.close();

        } catch (ManagerSaveException | IOException e) {
            System.out.println(e.getMessage());

        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws Exception {

        FileBackedTasksManager tasksManager = Managers.getFileBackedTasksManager();

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
    public HashMap<Integer, Task> getTasks() {
        HashMap<Integer, Task> hashMap = super.getTasks();
        save();
        return hashMap;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        HashMap<Integer, Epic> hashMap = super.getEpics();
        save();
        return hashMap;
    }

    @Override
    public HashMap<Integer, Subtask> getSubtasks() {
        HashMap<Integer, Subtask> hashMap = super.getSubtasks();
        save();
        return hashMap;
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



