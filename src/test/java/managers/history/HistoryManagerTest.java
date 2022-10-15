package managers.history;

import managers.TaskManager;
import model.Epic;
import model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public abstract class HistoryManagerTest<T extends HistoryManager> {
    T manager;
    protected Task task;
    public abstract T getManager();

    //a. Пустая история задач.
    //b. Дублирование.
    //с. Удаление из истории: начало, середина, конец.

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

    }

    @Test
    void addTest() { //TODO сделать с 2-3 задачами.
        manager.add(task);
        List<Task> expected = List.of(task);
        List<Task> actual = manager.getHistory();
        assertEquals(expected, actual, "Не совпадает");

    }

    @Test
    void getHistory() {
    }

    @Test
    void remove() {
    }
}