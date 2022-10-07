package managers;


import managers.memoryManager.InMemoryTaskManager;
import model.Epic;
import model.Subtask;
import model.TaskStatus;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;


public class EpicTest {

    protected Epic epic;
    protected Subtask subtask1;
    protected Subtask subtask2;
    protected TaskManger manager;
    protected int epicId;
    protected TaskStatus actual;
    protected TaskStatus expected;

    @BeforeEach
    public void init () {
        manager = new InMemoryTaskManager();
        epic = new Epic();
        manager.addItem(epic);

        epicId = epic.getId();

        subtask1 = new Subtask(LocalDateTime.of(2022, 10,6,13, 00), Duration.ofMinutes(15));
        subtask2 = new Subtask(LocalDateTime.of(2022, 10,6,14, 00), Duration.ofMinutes(20));

        subtask1.setEpicId(epicId);
        subtask2.setEpicId(epicId);

    }

    @Test
    public void testEpicStatusEmptySubtaskList() {
        expected = TaskStatus.NEW;
        //manager.checkEpicStatus(epicId);
        actual = epic.getStatus();

        assertEquals(expected, actual, "Статус не совпадает.");

    }

    @Test
    public void testEpicStatusAllSubtasksNew() {
        //subtask1.setStatus(TaskStatus.NEW);
        manager.addItem(subtask1);
        expected = TaskStatus.NEW;
        //manager.checkEpicStatus(epicId);
        actual = epic.getStatus();

        assertEquals(expected, actual, "Статус не совпадает.");

    }

    @Test
    public void testEpicStatusAllSubtasksDone() {
        subtask1.setStatus(TaskStatus.DONE);
        manager.addItem(subtask1);
        expected = TaskStatus.DONE;
        //manager.checkEpicStatus(epicId);
        actual = epic.getStatus();

        assertEquals(expected, actual, "Статус не совпадает.");

    }

    @Test
    public void testEpicStatusSubtasksNewAndDone() {
        //subtask1.setStatus(TaskStatus.NEW);
        subtask2.setStatus(TaskStatus.DONE);
        manager.addItem(subtask1);
        manager.addItem(subtask2);
        expected = TaskStatus.IN_PROGRESS;
        //manager.checkEpicStatus(epicId);
        actual = epic.getStatus();

        assertEquals(expected, actual, "Статус не совпадает.");

    }

    @Test
    public void testEpicStatusAllSubtasksInProgress() {
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        manager.addItem(subtask1);
        expected = TaskStatus.IN_PROGRESS;
        //manager.checkEpicStatus(epicId);
        actual = epic.getStatus();

        assertEquals(expected, actual, "Статус не совпадает.");

    }

    @Test
    public void testCalculateEpicStartTime() {
        manager.addItem(subtask1);
        manager.addItem(subtask2);
        assertEquals(subtask1.getStartTime(), epic.getStartTime());

    }

    @Test
    public void testCalculateEpicEndTime() {
        manager.addItem(subtask1);
        manager.addItem(subtask2);
        assertEquals(subtask2.getEndTime(), epic.getEndTime());

    }
    @Test
    public void testCalculateEpicDuration() {
        manager.addItem(subtask1);
        manager.addItem(subtask2);
        Duration expectedDuration = Duration.ofMinutes(35);
        assertEquals(expectedDuration, epic.getDuration());

    }

    @Test
    public void test() {


    }


}
