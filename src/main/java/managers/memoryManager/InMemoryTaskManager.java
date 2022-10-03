package managers.memoryManager;

import managers.TaskManger;
import managers.history.InMemoryHistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManger {
    protected InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    protected int startId;
    public HashMap<Integer, Task> tasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();


    @Override
    public int generateId(){
        return ++startId;
    }

    // 2.Методы для каждого из типа задач(Задача/Эпик/Подзадача):
    // 2.1.Получение списка всех задач.

    @Override
    public void getTasks() {
        printTask(tasks);
    }

    @Override
    public void getEpics() {
        printTask(epics);
    }

    @Override
    public void getSubtasks() {
        printTask(subtasks);
    }

    private void printTask (HashMap <Integer, ? extends Task> list){
        for(Task task: list.values()) {
            System.out.println(task);
            historyManager.add(task);
        }
    }

    // 2.2.Удаление всех задач.
    @Override
    public void deleteTasks() {
        deleteTaskHistory(tasks);
    }

    @Override
    public void deleteEpics() {
        deleteTaskHistory(epics);
        deleteTaskHistory(subtasks);
    }

    @Override
    public void deleteSubtasks() {
        deleteTaskHistory(subtasks);
    }

    private void deleteTaskHistory(HashMap <Integer, ? extends Task> list){
        for(int id: list.keySet()){
            historyManager.remove(id);
        }
        list.clear();
    }

    // 2.3.Получение по идентификатору.

    @Override
    public Task getTaskById(int id) {
        return getById(tasks, id);
    }
    @Override
    public Task getEpicById(int id) {
        return getById(epics, id);
    }
    @Override
    public Task getSubtaskById(int id) {
        return getById(subtasks, id);
    }

    private Task getById (HashMap <Integer, ? extends Task> list, int id){
        Task task = list.get(id);
        if (task == null) {
            return (Task) Collections.EMPTY_LIST;
        }
        historyManager.add(task);
        return task;
    }

    // 2.4.Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public void addItem(Task task) {
        if(task.getId() <= 0) {
            int id = generateId();
            task.setId(id);
            switch (task.getType()) {
                case TASK:
                    tasks.put(id, task);
                    break;
                case EPIC:
                    epics.put(id, (Epic) task);
                    break;
                case SUBTASK:
                    subtasks.put(id, (Subtask) task);
                    Subtask subtask = (Subtask) task;
                    int epicId = subtask.getEpicId();
                    Epic epic = epics.get(epicId);
                    epic.addSubtask((Subtask) task);
                    break;
                default:
                    System.out.println("Добавьте тип задачи.");
            }
        } else {
            updateItem(task, task.getStatus());
        }
    }


    // 2.5. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Override
    public void updateItem(Task task, TaskStatus status) {
        task.setStatus(status);
        switch (task.getType()) {
            case TASK:
                tasks.put(task.getId(), task);
                break;
            case EPIC:
                epics.put(task.getId(), (Epic) task);
                break;
            case SUBTASK:
                subtasks.put(task.getId(), (Subtask) task);
                Subtask subtask = (Subtask) task;
                for (int i = 0; i < epics.get(subtask.getEpicId()).getSubtasks().size(); i++){
                    Subtask item = epics.get(subtask.getEpicId()).getSubtasks().get(i);
                    if(task.getId() == item.getId()){
                        epics.get(subtask.getEpicId()).getSubtasks().set(i, subtask);
                    }
                }
                checkEpicStatus(subtask.getEpicId());
                break;
            default:
                System.out.println("Добавьте тип задачи.");
        }

    }

    // 2.6. Удаление по идентификатору.
    @Override
    public void deleteTaskById(int id) {
        if (tasks.get(id) == null) {
            return;
        }
        tasks.remove(id);
        historyManager.remove(id);

    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.get(id) == null) {
            return;
        }
        Epic epic = epics.get(id);
        epics.remove(id);
        historyManager.remove(id);

        for (Subtask item: epic.getSubtasks()) {
            subtasks.remove(item.getId());
            historyManager.remove(item.getId());
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        if (subtasks.get(id) == null) {
            return;
        }
        epics.get(subtasks.get(id).getEpicId()).deleteSubtask(subtasks.get(id));
        subtasks.remove(id);
        historyManager.remove(id);
    }

    // 3. Дополнительные методы:
    // 3.1. Получение списка всех подзадач определённого эпика.
    @Override
    public List<Subtask> getSubtasksByEpic(int id) {
        if (epics.get(id) == null) {
            return Collections.EMPTY_LIST;
        }
        return epics.get(id).getSubtasks();
    }

    // 4. Управление статусами:
    // 4.2. Статус для TaskTypes.Epic
    @Override
    public void checkEpicStatus(int id) {
        boolean statusDone = false;
        boolean statusNew = false;

        if (epics.get(id).getSubtasks() == null || epics.get(id).getSubtasks().size() == 0) {
            epics.get(id).setStatus(TaskStatus.NEW);
            return;
        }

        for (int i = 0; i < epics.get(id).getSubtasks().size(); i++) {
            Subtask item = epics.get(id).getSubtasks().get(i);
            switch (item.getStatus()) {
                case IN_PROGRESS:
                    epics.get(id).setStatus(TaskStatus.IN_PROGRESS);
                    return;
                case NEW:
                    statusNew = true;
                    break;
                case DONE:
                    statusDone = true;
                    break;
            }
        }

        if (statusNew  && statusDone) {
            epics.get(id).setStatus(TaskStatus.IN_PROGRESS);
            return;
        } else if (!statusNew && statusDone) {
            epics.get(id).setStatus(TaskStatus.DONE);
            return;
        } else if (statusNew && !statusDone) {
            epics.get(id).setStatus(TaskStatus.NEW);
            return;
        }
    }


    @Override
            //Специально созданный метод для красивого вывода истории (бед детальной информации)

    public List<String> getHistory(){
        List<String> prettyPrintHistoryList = new ArrayList<>();
        for (Task task: historyManager.getHistory()) {
            String text;
            text = "\n"+ task.getId() + ". " + task.getName();
            prettyPrintHistoryList.add(text);
        }
        return prettyPrintHistoryList;
    }

}