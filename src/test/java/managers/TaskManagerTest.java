package managers;

import exceptions.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> {

    T manager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    public abstract T getManager();

    @BeforeEach
    void init(){
        manager = getManager();
        task = new Task(
                0,
                "Task1",
                "DescriptionTask1",
                LocalDateTime.of(2022, 10,10, 20, 00),
                Duration.ofMinutes(15)
        );
        epic = new Epic();

    }

    //Стандартное поведение
    //Пустой список задач
    //Неверный идентификатор

    @Test
    void getTasksTestStandard() {
        manager.addItem(task);
        HashMap<Integer, Task> expected = new HashMap<>(){
            {
                put(task.getId(), task);
            }
        };
        HashMap<Integer, Task> actual = manager.getTasks();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void getTasksTestEmptyTasksList() {
        HashMap<Integer, Task> expected = new HashMap<>();
        HashMap<Integer, Task> actual = manager.getTasks();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void getEpicsTestStandard() {
        manager.addItem(epic);
        HashMap<Integer, Epic> expected = new HashMap<>(){
            {
                put(epic.getId(), epic);
            }
        };
        HashMap<Integer, Epic> actual = manager.getEpics();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void getEpicsTestEmptyTasksList() {
        HashMap<Integer, Epic> expected = new HashMap<>();
        HashMap<Integer, Epic> actual = manager.getEpics();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void getSubtasksTestStandard() {
        manager.addItem(epic);
        subtask = new Subtask(  //ВОТ ТОЛЬКО ВОПРОС, МОЖНО ЛИ ТАК ДЕЛАТЬ
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);
        HashMap<Integer, Subtask> expected = new HashMap<>(){
            {
                put(subtask.getId(), subtask);
            }
        };
        HashMap<Integer, Subtask> actual = manager.getSubtasks();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void getSubtasksTestEmptyTasksList() {
        HashMap<Integer, Subtask> expected = new HashMap<>();
        HashMap<Integer, Subtask> actual = manager.getSubtasks();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void deleteTasksTest() {
        manager.addItem(task);
        manager.deleteTasks();
        HashMap<Integer, Task> actual = null;
        assertNull(actual, "Не совпадает");
    }

    @Test
    void deleteEpicsTest() {
        manager.addItem(epic);
        manager.deleteEpics();
        HashMap<Integer, Epic> actual = null;
        assertNull(actual, "Не совпадает");
    }

    @Test
    void deleteSubtasksTest() {
        manager.addItem(epic);
        subtask = new Subtask(
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);
        manager.deleteSubtasks();
        HashMap<Integer, Subtask> actual = null;
        assertNull(actual, "Не совпадает");
    }

    @Test
    void getTaskByIdTestStandard() throws ManagerSaveException { //Может ли тестовый метод выкидывать исключение
        manager.addItem(task);
        int id = task.getId();
        Task expected = task;
        Task actual = manager.getTaskById(id);
        assertEquals(expected, actual, "Не совпадает");

    }

    @Test
    void getTaskByIdTestEmptyTasksList() { //ВОПРОС С ДАНЫМ МЕТОДОМ
        HashMap<Integer, Task> actual = null;
        assertNull(actual, "Не совпадает");

    }

    @Test
    void getTaskByIdTestWrongId() throws ManagerSaveException {
        Task actual = manager.getTaskById(89);
        assertNull(actual, "Не совпадает");

    }

    @Test
    void getEpicByIdTestStandard() throws ManagerSaveException { //Может ли тестовый метод выкидывать исключение
        manager.addItem(epic);
        int id = epic.getId();
        Epic expected = epic;
        Epic actual = (Epic) manager.getEpicById(id);
        assertEquals(expected, actual, "Не совпадает");

    }

    @Test
    void getEpicByIdTestEmptyTasksList() { //ВОПРОС С ДАНЫМ МЕТОДОМ
        HashMap<Integer, Epic> actual = null;
        assertNull(actual, "Не совпадает");

    }

    @Test
    void getEpicByIdTestWrongId() throws ManagerSaveException {
        Epic actual = (Epic) manager.getEpicById(89);
        assertNull(actual, "Не совпадает");

    }

    @Test
    void getSubtaskByIdTestStandard() throws ManagerSaveException { //Может ли тестовый метод выкидывать исключение
        manager.addItem(epic);
        subtask = new Subtask(
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);
        int id = subtask.getId();
        Task expected = subtask;
        Task actual = manager.getSubtaskById(id);
        assertEquals(expected, actual, "Не совпадает");

    }

    @Test
    void getSubtaskByIdTestEmptyTasksList() { //ВОПРОС С ДАНЫМ МЕТОДОМ
        HashMap<Integer, Epic> actual = null;
        assertNull(actual, "Не совпадает");

    }

    @Test
    void getSubtaskByIdTestWrongId() throws ManagerSaveException {
        Task actual = manager.getSubtaskById(89);
        assertNull(actual, "Не совпадает");

    }

    @Test
    void addTaskTest() {
        manager.addItem(task);
        Map<Integer, Task> expected = new HashMap<>() {
            {
                put(task.getId(), task);
            }
        };
        Map<Integer, Task> actual = manager.getTasks();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void updateItemTest() {
        manager.addItem(task);
        manager.updateItem(task, TaskStatus.DONE);
        TaskStatus expected = TaskStatus.DONE;
        TaskStatus actual = task.getStatus();
        assertEquals(expected, actual, "Не совпадает");

        manager.addItem(epic);
        manager.updateItem(epic, TaskStatus.DONE);
        TaskStatus expectedEpicStatus = TaskStatus.DONE;
        TaskStatus actualEpicStatus = epic.getStatus();
        assertEquals(expectedEpicStatus, actualEpicStatus, "Не совпадает");

        subtask = new Subtask(
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);
        manager.updateItem(subtask, TaskStatus.IN_PROGRESS);
        TaskStatus expectedSubtaskStatus = TaskStatus.IN_PROGRESS;
        TaskStatus actualSubtaskStatus = subtask.getStatus();
        expectedEpicStatus = TaskStatus.IN_PROGRESS;
        actualEpicStatus = epic.getStatus();
        assertEquals(expectedSubtaskStatus, actualSubtaskStatus, "Не совпадает");
        assertEquals(expectedEpicStatus, actualEpicStatus, "Не совпадает");


    }

    @Test
    void deleteTaskByIdTestStandard() {
        manager.addItem(task);
        manager.deleteTaskById(task.getId());
        HashMap<Integer, Task> expected = new HashMap<>();
        HashMap<Integer, Task> actual = manager.getTasks();
        assertEquals(expected, actual, "Не совпадает");

    }

    @Test
    void deleteTaskByIdTestWrongId() {
        manager.addItem(task);
        manager.deleteTaskById(89);
        HashMap<Integer, Task> expected = new HashMap<>();
        expected.put(task.getId(), task);
        HashMap<Integer, Task> actual = manager.getTasks();
        assertEquals(expected, actual, "Не совпадает");

    }

    @Test
    void deleteEpicByIdTestStandard() {
        manager.addItem(epic);
        subtask = new Subtask(
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);
        manager.deleteEpicById(epic.getId());
        HashMap<Integer, Epic> expected = new HashMap<>();
        HashMap<Integer, Epic> actual = manager.getEpics();

        HashMap<Integer, Subtask> expectedSubtaskMap = new HashMap<>();
        HashMap<Integer, Subtask> actualSubtaskMap = manager.getSubtasks();
        
        assertEquals(expected, actual, "Не совпадает");
        assertEquals(expectedSubtaskMap, actualSubtaskMap, "Не совпадает");

    }

    @Test
    void deleteEpicByIdTestWrongId() {
        manager.addItem(epic);
        manager.deleteEpicById(89);
        HashMap<Integer, Epic> expected = new HashMap<>();
        expected.put(epic.getId(), epic);
        HashMap<Integer, Epic> actual = manager.getEpics();
        assertEquals(expected, actual, "Не совпадает");
    }


    @Test
    void deleteSubtaskByIdTestStandard() {
        manager.addItem(epic);
        subtask = new Subtask(
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);
        manager.deleteSubtaskById(subtask.getId());
        HashMap<Integer, Subtask> expected = new HashMap<>();
        HashMap<Integer, Subtask> actual = manager.getSubtasks();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void deleteSubtaskByIdTestWrongId() {
        manager.addItem(epic);
        subtask = new Subtask(
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);
        manager.deleteSubtaskById(89);
        HashMap<Integer, Subtask> expected = new HashMap<>();
        expected.put(subtask.getId(), subtask);
        HashMap<Integer, Subtask> actual = manager.getSubtasks();
        assertEquals(expected, actual, "Не совпадает");

    }

    @Test
    void getSubtasksByEpicTestStandard() {
        manager.addItem(epic);
        subtask = new Subtask(
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);

        List<Subtask> expected = new ArrayList<>();
        expected.add(subtask);
        List<Subtask> actual = manager.getSubtasksByEpic(epic.getId());

        assertEquals(expected, actual, "Не совпадает");

    }

    @Test
    void getSubtasksByEpicTestWrongId() {
        manager.addItem(epic);
        subtask = new Subtask(
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);

        List<Subtask> expected = new ArrayList<>();
        List<Subtask> actual = manager.getSubtasksByEpic(89);

        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void getPrioritizedTasksTest() {
        manager.addItem(task);
        manager.addItem(epic);
        subtask = new Subtask(
                0,
                "Subtask1",
                "SubtaskDescription1",
                LocalDateTime.of(2022,10,11,15,00),
                Duration.ofMinutes(20),
                epic.getId()
        );
        manager.addItem(subtask);

        List<Task> expected = new ArrayList<>();
        expected.add(task);
        expected.add(subtask);

        List<Task> actual = manager.getPrioritizedTasks();

        assertEquals(expected, actual, "Не совпадает");




    }












}