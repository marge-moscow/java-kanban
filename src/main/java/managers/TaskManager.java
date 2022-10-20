package managers;

import model.Epic;
import model.TaskStatus;
import model.Subtask;
import model.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public interface TaskManager {

    int generateId();

    // 2.Методы для каждого из типа задач(Задача/Эпик/Подзадача):
    // 2.1.Получение списка всех задач.
    HashMap<Integer, Task> getTasks();

    HashMap<Integer, Epic> getEpics();

    HashMap<Integer, Subtask> getSubtasks();

    // 2.2.Удаление всех задач.
    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    // 2.3.Получение по идентификатору.
    Task getTaskById(int id);
    Task getEpicById(int id);
    Task getSubtaskById(int id);

    // 2.4.Создание. Сам объект должен передаваться в качестве параметра.
    void addItem(Task task);

    // 2.5. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    void updateItem(Task task, TaskStatus status);


    // 2.6. Удаление по идентификатору.
    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    // 3. Дополнительные методы:
    // 3.1. Получение списка всех подзадач определённого эпика.
    List<Subtask> getSubtasksByEpic(int id);

    List<String> getHistory();

    List<Task> getPrioritizedTasks();



}