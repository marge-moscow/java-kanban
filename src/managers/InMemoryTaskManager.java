package managers;

import tasktypes.Epic;
import tasktypes.Subtask;
import tasktypes.Task;
import annex.TaskStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManger {
    HistoryManager historyManager = Managers.getDefaultHistory();

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
        for(Task task: tasks.values()){
            System.out.println(task);
        }
    }

    @Override
    public void getEpics() {
        for(Epic task: epics.values()){
            System.out.println(task);
        }
    }

    @Override
    public void getSubtasks() {
        for(Subtask task: subtasks.values()){
            System.out.println(task);
        }
    }

    // 2.2.Удаление всех задач.
    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        subtasks.clear();
    }

    // 2.3.Получение по идентификатору.
    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }
    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }
    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    // 2.4.Создание. Сам объект должен передаваться в качестве параметра.
    @Override
    public void addItem(@NotNull Task task) {
        if(task.getId() <= 0) {
            int id = generateId();
            tasks.put(id, task);
            task.setId(id);
        } else {
            updateItem(task, task.getStatus());
        }
    }

    @Override
    public void addItem(@NotNull Epic task) {
        if(task.getId() <= 0) {
            int id = generateId();
            epics.put(id, task);
            task.setId(id);
        } else {
            updateItem(task);
        }
    }
    @Override
    public void addItem(@NotNull Subtask task) {
        if(task.getId() <= 0) {
            int id = generateId();
            subtasks.put(id, task);
            task.setId(id);

            int epicId = task.getEpicId();
            Epic epic = epics.get(epicId);
            epic.addSubtask(task);

        } else {
            updateItem(task, task.getStatus());
        }
    }

    // 2.5. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    @Override
    public void updateItem(@NotNull Task task, TaskStatus status) {
        task.setStatus(status);
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateItem(Epic task) {
        epics.put(task.getId(), task);
    }

    @Override
    public void updateItem(@NotNull Subtask task, TaskStatus status) {
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
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        epics.remove(id);

        for (Subtask item: epic.getSubtasks()) {
            subtasks.remove(item.getId());
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        subtasks.remove(id);
    }

    // 3. Дополнительные методы:
    // 3.1. Получение списка всех подзадач определённого эпика.
    @Override
    public ArrayList<Subtask> getSubtasksByEpic(int id) {
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
    public void getHistory(){
        historyManager.getHistory();
    }

}