import java.util.HashMap;

public class Manager {
    int startId;
    public HashMap<Integer, Task> tasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public int generateId(){
        startId = startId + 1;
        return startId;
    }

    // 2.Методы для каждого из типа задач(Задача/Эпик/Подзадача):
    // 2.1.Получение списка всех задач.
    public void getTasks() {
        for(Task task: tasks.values()){
            System.out.println(task);
        }
    }

    public void getEpics() {
        for(Epic task: epics.values()){
            System.out.println(task);
        }
    }

    public void getSubtasks() {
        for(Subtask task: subtasks.values()){
            System.out.println(task);
        }
    }

    // 2.2.Удаление всех задач.
    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteSubtasks() {
        subtasks.clear();
    }

    // 2.3.Получение по идентификатору.
    public void getTaskById(int id) {
        System.out.println(tasks.get(id));
    }
    public void getEpicById(int id) {
        System.out.println(epics.get(id));
    }
    public void getSubtaskById(int id) {
        System.out.println(subtasks.get(id));
    }

    // 2.4.Создание. Сам объект должен передаваться в качестве параметра.
    public void addItem(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addItem(Epic task) {
        epics.put(task.getId(), task);
    }

    public void addItem(Subtask task) {
        subtasks.put(task.getId(), task);

        int epicId = task.getEpicId();
        Epic epic = epics.get(epicId);
        epic.addSubtask(task);
    }

    // 2.5. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
    public void updateItem(Task task, String status) {
        task.setStatus(status);
        tasks.put(task.getId(), task);
    }

    public void updateItem(Epic task) {
        epics.put(task.getId(), task);
    }

    public void updateItem(Subtask task, String status) {
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
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.get(id);
        epics.remove(id);

        for (Subtask item: epic.getSubtasks()) {
            subtasks.remove(item.getId());
        }
    }

    public void deleteSubtaskById(int id) {
        subtasks.remove(id);
    }

    // 3. Дополнительные методы:
    // 3.1. Получение списка всех подзадач определённого эпика.
    public void getSubtasksByEpic(int id) {
        System.out.println(epics.get(id).getSubtasks());
    }

    // 4. Управление статусами:
    // 4.2. Статус для Epic
    private void checkEpicStatus(int id) {
        boolean statusDone = false;
        boolean statusNew = false;
        for (int i = 0; i < epics.get(id).getSubtasks().size(); i++) {
            Subtask item = epics.get(id).getSubtasks().get(i);
            switch (item.getStatus()) {
                case "IN_PROGRESS":
                    epics.get(id).setStatus("IN_PROGRESS");
                    return;
                case "NEW":
                    statusNew = true;
                    break;
                case "DONE":
                    statusDone = true;
                    break;
            }
        }

        if (statusNew  && statusDone) {
            epics.get(id).setStatus("IN_PROGRESS");
        } else if (!statusNew && statusDone) {
            epics.get(id).setStatus("DONE");
        }
    }
}