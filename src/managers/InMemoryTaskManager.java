package managers;

import tasktypes.Epic;
import tasktypes.Subtask;
import tasktypes.Task;
import annex.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManger {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    int startId;
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
    public void getTaskById(int id) {
        getById(tasks, id);
    }
    @Override
    public void getEpicById(int id) {
        getById(epics, id);
    }
    @Override
    public void getSubtaskById(int id) {
        getById(subtasks, id);
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
            int id = setStartId(task);
            tasks.put(id, task);
        } else {
            updateItem(task, task.getStatus());
        }
    }

    @Override
    public void addItem(Epic task) {
        if(task.getId() <= 0) {
            int id = setStartId(task);
            epics.put(id, task);
        } else {
            updateItem(task);
        }
    }
    @Override
    public void addItem(Subtask task) {
        if(task.getId() <= 0) {
            int id = setStartId(task);
            subtasks.put(id, task);

            int epicId = task.getEpicId();
            Epic epic = epics.get(epicId);
            epic.addSubtask(task);

        } else {
            updateItem(task, task.getStatus());
        }
    }

    private int setStartId(Task element){
        int id = generateId();
        element.setId(id);
        return id;
    }



    // 2.5. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Override
    public void updateItem(Task task, TaskStatus status) {
        task.setStatus(status);
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateItem(Epic task) {
        epics.put(task.getId(), task);
    }

    @Override
    public void updateItem(Subtask task, TaskStatus status) {
        task.setStatus(status);
        subtasks.put(task.getId(), task);

        for (int i = 0; i < epics.get(task.getEpicId()).getSubtasks().size(); i++){
            Subtask item = epics.get(task.getEpicId()).getSubtasks().get(i);
            if(task.getId() == item.getId()){
                epics.get(task.getEpicId()).getSubtasks().set(i, task);
            }
        }

        checkEpicStatus(task.getEpicId());
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
    private void checkEpicStatus(int id) {
        boolean statusDone = false;
        boolean statusNew = false;
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
        } else if (!statusNew && statusDone) {
            epics.get(id).setStatus(TaskStatus.DONE);
        }
    }


    @Override
            //Я специально создала метод getHistory, чтобы не печатать всю информацию из toString для истории.
            //То есть если вызывать getTaskById(), то идет полная информация с помощью toString.
            //А в истории только id и название.
            //Но если важно убрать, уберу.

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