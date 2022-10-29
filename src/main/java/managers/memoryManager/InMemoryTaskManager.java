package managers.memoryManager;

import exceptions.NoTimeException;
import managers.TaskManager;
import managers.history.InMemoryHistoryManager;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.*;
import java.util.stream.Collectors;



public class InMemoryTaskManager implements TaskManager {
    protected static InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    protected static int startId;
    public Map<Integer, Task> tasks = new HashMap<>();
    public Map<Integer, Epic> epics = new HashMap<>();
    public Map<Integer, Subtask> subtasks = new HashMap<>();

    private final Comparator<Task> comparator = Comparator.comparing(Task::getStartTime);
    public Set<Task> prioritizedSet = new TreeSet<>(comparator);

    public List<Task> taskListNoStartTime = new ArrayList<>();


    @Override
    public int generateId() {
        return ++startId;
    }

    // 2.Методы для каждого из типа задач(Задача/Эпик/Подзадача):
    // 2.1.Получение списка всех задач.

    @Override
    public Map<Integer, Task> getTasks() {
        addTaskToHistory(tasks);
        return tasks;

    }

    @Override
    public Map<Integer, Epic> getEpics() {
        addTaskToHistory(epics);
        return epics;
    }

    @Override
    public Map<Integer, Subtask> getSubtasks() {
        addTaskToHistory(subtasks);
        return subtasks;
    }

    private void addTaskToHistory(Map<Integer, ? extends Task> list) {
        for (Task task : list.values()) {
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

    private void deleteTaskHistory(Map<Integer, ? extends Task> list) {
        for (int id : list.keySet()) {
            historyManager.remove(id);
        }
        list.clear();
        prioritizedSet.clear();
        taskListNoStartTime.clear();
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

    private Task getById(Map<Integer, ? extends Task> list, int id) {
        Task task = null;
        try {
            task = list.get(id);
            historyManager.add(task);
        } catch (Exception e) {
            System.out.println("Задачи с ID " + id + " нет в списке задач");
        }

        return task;
    }

    // 2.4.Создание. Сам объект должен передаваться в качестве параметра.

    /*public <T extends Task> void addItem(T item, Map<Integer, T > map) {

        if (map.containsValue(item)) {
            return;
        }

        int id = generateId();

        if (checkTimeIntersection(item)) {
            map.put(id, item);
            if (checkTaskHasStartTime(item)) {
                prioritizedSet.add(item);
            } else {
                taskListNoStartTime.add(item);
            }
        } else {
            throw new NoTimeException("Измените время задачи");
        }

    }*/


    @Override
    public void addTask(Task task) {
        if (tasks.containsValue(task)) {
            return;
        }

        int id = generateId();
        task.setId(id);

        if (checkTimeIntersection(task)) {
            tasks.put(id, task);
            if (checkTaskHasStartTime(task)) {
                prioritizedSet.add(task);
            } else {
                taskListNoStartTime.add(task);
            }
        } else {
            throw new NoTimeException("Измените время задачи");
        }
    }

    @Override
    public void addEpic(Epic epic) {
        if (epics.containsValue(epic)) {
            return;
        }

        int id = generateId();
        epic.setId(id);

        if (checkTimeIntersection(epic)) {
            epics.put(id, epic);
            calculateEpicStatus(id);
            if (checkTaskHasStartTime(epic)) {
                prioritizedSet.add(epic);
            } else {
                taskListNoStartTime.add(epic);
            }
        } else {
            throw new NoTimeException("Измените время задачи");
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (subtasks.containsValue(subtask)) {
            return;
        }

        int id = generateId();
        subtask.setId(id);

        if (checkTimeIntersection(subtask)) {
            subtasks.put(id, subtask);
            int epicId = subtask.getEpicId();
            Epic epic = epics.get(epicId);
            epic.addSubtask(subtask);
            calculateEpicStatus(subtask.getEpicId());
            calculateEpicStartTimeAndDuration(subtask.getEpicId());
            if (checkTaskHasStartTime(subtask)) {
                prioritizedSet.add(subtask);
            } else {
                taskListNoStartTime.add(subtask);
            }
        } else {
            throw new NoTimeException("Измените время задачи");
        }
    }

    // 2.5. Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.

    @Override
    public void updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            return;
        }

        if (!checkTimeIntersection(task)) {
            prioritizedSet = prioritizedSet
                    .stream()
                    .filter(item -> item.getId() != task.getId())
                    .collect(Collectors.toSet());
        }

        if (checkTimeIntersection(task)) {
            tasks.put(task.getId(), task);

        } else {
            throw new NoTimeException("Измените время задачи");
        }

        if (!checkTaskHasStartTime(task)) {
            taskListNoStartTime = taskListNoStartTime
                    .stream()
                    .filter(item -> item.getId() != task.getId())
                    .collect(Collectors.toList());
            taskListNoStartTime.add(task);
        } else {
            prioritizedSet.add(task);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (!checkTimeIntersection(subtask)) {
            prioritizedSet = prioritizedSet
                    .stream()
                    .filter(item -> item.getId() != subtask.getId())
                    .collect(Collectors.toSet());
        }

        if (checkTimeIntersection(subtask)) {
            subtasks.put(subtask.getId(), subtask);

            Epic epic = epics.get(subtask.getEpicId());

            List<Subtask> subtasksOfEpic = epic.getSubtasks();

            subtasksOfEpic = subtasksOfEpic.stream()
                    .map(s -> s.getId() == subtask.getId() ? subtask : s)
                    .collect(Collectors.toList());

            epic.setSubtasks(subtasksOfEpic);
            epics.replace(epic.getId(), epic);

            calculateEpicStatus(subtask.getEpicId());
            calculateEpicStartTimeAndDuration(subtask.getEpicId());

        } else {
            throw new NoTimeException("Измените время задачи");
        }

        if (!checkTaskHasStartTime(subtask)) {
            taskListNoStartTime = taskListNoStartTime
                    .stream()
                    .filter(item -> item.getId() != subtask.getId())
                    .collect(Collectors.toList());
            taskListNoStartTime.add(subtask);
        } else {
            prioritizedSet.add(subtask);
        }

    }

    @Override
    public void updateEpic(Epic epic) {  //TODO Проверить, если в эпик передадут новый эпик с измененнным списком сабтаска
        if (!checkTimeIntersection(epic)) {
            prioritizedSet = prioritizedSet
                    .stream()
                    .filter(item -> item.getId() != epic.getId())
                    .collect(Collectors.toSet());
        }

        if (checkTimeIntersection(epic)) {
            epics.put(epic.getId(), epic);

        } else {
            throw new NoTimeException("Измените время задачи");
        }

        if (!checkTaskHasStartTime(epic)) {
            taskListNoStartTime = taskListNoStartTime
                    .stream()
                    .filter(item -> item.getId() != epic.getId())
                    .collect(Collectors.toList());
            taskListNoStartTime.add(epic);
        } else {
            prioritizedSet.add(epic);
        }
    }

    // 2.6. Удаление по идентификатору.
    @Override
    public void deleteTaskById(int id) {
        if (tasks.get(id) == null) {
            return;
        }
        Task task = getTaskById(id);

        tasks.remove(id);
        historyManager.remove(id);

        prioritizedSet.remove(task);
        taskListNoStartTime.remove(task);

    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.get(id) == null) {
            return;
        }
        Epic epic = epics.get(id);
        epics.remove(id);


        for (Subtask item : epic.getSubtasks()) {
            subtasks.remove(item.getId());
            historyManager.remove(item.getId());
        }

        historyManager.remove(id);

        prioritizedSet.remove(epic);
        taskListNoStartTime.remove(epic);
    }

    @Override
    public void deleteSubtaskById(int id) {
        if (subtasks.get(id) == null) {
            return;
        }
        Subtask subtask = (Subtask) getSubtaskById(id);
        epics.get(subtasks.get(id).getEpicId()).deleteSubtask(subtasks.get(id));
        subtasks.remove(id);
        historyManager.remove(id);
        prioritizedSet.remove(subtask);
        taskListNoStartTime.remove(subtask);

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

    private void calculateEpicStatus(int id) {
        boolean statusDone = false;
        boolean statusNew = false;

        if (epics.get(id).getSubtasks() == null || epics.get(id).getSubtasks().isEmpty()) {
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

        if (statusNew && statusDone) {
            epics.get(id).setStatus(TaskStatus.IN_PROGRESS);
        } else if (!statusNew && statusDone) {
            epics.get(id).setStatus(TaskStatus.DONE);
        } else if (statusNew) {
            epics.get(id).setStatus(TaskStatus.NEW);
        }
    }

    private void calculateEpicStartTimeAndDuration(int id) {
        epics.get(id).setStartTime();
        epics.get(id).setDuration();
        epics.get(id).getEndTime();
    }

    private boolean checkTaskHasStartTime(Task task) {
        return task.getStartTime() != null;
    }

    private boolean checkTimeIntersection(Task newTask) {
        if (newTask.getStartTime() == null) {
            return true;
        }
        return prioritizedSet.stream()
                .noneMatch(task ->
                        ((newTask.getStartTime().isAfter(task.getStartTime()) || newTask.getStartTime().isEqual(task.getStartTime()))
                                && (newTask.getStartTime().isBefore(task.getEndTime()) || newTask.getStartTime().isEqual(task.getEndTime())))
                                ||
                                ((newTask.getEndTime().isAfter(task.getStartTime()) || newTask.getEndTime().isEqual(task.getStartTime()))
                                        && (newTask.getEndTime().isBefore(task.getEndTime()) || newTask.getEndTime().isEqual(task.getEndTime()))));
    }

    //5. Вывод списка задач в порядке приоритета
    @Override
    public List<Task> getPrioritizedTasks() {

        List<Task> taskList = new ArrayList(prioritizedSet);
        taskList.addAll(taskListNoStartTime);

        return taskList;
    }

    //Специально созданный метод для красивого вывода истории (бед детальной информации)
    @Override
    public List<String> getHistory() {
        List<String> prettyPrintHistoryList = new ArrayList<>();
        for (Task task : historyManager.getHistory()) {
            String text;
            text = "\n" + task.getId() + ". " + task.getName();
            prettyPrintHistoryList.add(text);
        }
        return prettyPrintHistoryList;
    }

}