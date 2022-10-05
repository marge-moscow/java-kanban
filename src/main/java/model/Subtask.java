package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String name, String description, TaskType type, int epicId) {
        super(id, name, description, type);
        this.epicId = epicId;
    }

    public Subtask() {
        setType(TaskType.SUBTASK);

    }

    //Конструктор для ТЗ 7 спринта со временем начала и продолжительностью
    public Subtask(int id, String name, String description, int epicId) {
        super(id, name, description);
        setType(TaskType.SUBTASK);
        setEpicId(epicId);
    }



    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "\n" + super.toString() + ". Номер эпика: " + epicId;
    }
}