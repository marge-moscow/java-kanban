package managers.history;

import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class HistoryManagerTest<T extends HistoryManager> {
    T manager;
    protected Task task;
    protected Task task2;
    protected Task task3;
    public abstract T getManager();

    //a. Пустая история задач.
    //b. Дублирование.
    //с. Удаление из истории: начало, середина, конец.

    @BeforeEach
    void init(){
        manager = getManager();
        task = new Task(
                1,
                "Task1",
                "DescriptionTask1",
                LocalDateTime.of(2022, 10,10, 20, 00),
                Duration.ofMinutes(15)
        );

        task2 = new Task(
                2,
                "Task2",
                "DescriptionTask2",
                LocalDateTime.of(2022, 10,11, 20, 00),
                Duration.ofMinutes(15)
        );

        task3 = new Task(
                3,
                "Task3",
                "DescriptionTask3",
                LocalDateTime.of(2022, 10,12, 20, 00),
                Duration.ofMinutes(15)
        );
    }

    @Test
    void addTest() {
        manager.add(task);
        List<Task> expected = List.of(task);
        List<Task> actual = manager.getHistory();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void addTaskTwiceTest() {
        manager.add(task);
        manager.add(task);
        List<Task> expected = List.of(task);
        List<Task> actual = manager.getHistory();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void getHistory() {
        manager.add(task);
        manager.add(task2);
        manager.add(task3);
        List<Task> expected = List.of(task, task2, task3);
        List<Task> actual = manager.getHistory();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void getEmptyHistory() {
        List<Task> expected = List.of();
        List<Task> actual = manager.getHistory();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void removeFirstTask() {
        manager.add(task);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task.getId());
        List<Task> expected = List.of(task2, task3);
        List<Task> actual = manager.getHistory();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void removeTaskInTheMiddle() {
        manager.add(task);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task2.getId());
        List<Task> expected = List.of(task, task3);
        List<Task> actual = manager.getHistory();
        assertEquals(expected, actual, "Не совпадает");
    }

    @Test
    void removeLastTask() {
        manager.add(task);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task3.getId());
        List<Task> expected = List.of(task, task2);
        List<Task> actual = manager.getHistory();
        assertEquals(expected, actual, "Не совпадает");
    }
}