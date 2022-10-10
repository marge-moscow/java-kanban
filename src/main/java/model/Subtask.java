package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String name, String description, int epicId) {
        super(id, name, description);
        this.epicId = epicId;
        setType(TaskType.SUBTASK);
    }

    public Subtask() {
        setType(TaskType.SUBTASK);
        this.status = TaskStatus.NEW;

    }

    //Конструктор для ТЗ 7 спринта со временем начала и продолжительностью
    public Subtask(int id, String name, String description,LocalDateTime startTime, Duration duration, int epicId) {
        super(id, name, description, startTime, duration);
        setType(TaskType.SUBTASK);
        setEpicId(epicId);
    }

    //Для задач, у которых не задано время начала
    public Subtask(int id, String name, String description, Duration duration, int epicId) {
        super(id, name, description, duration);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return getEpicId() == subtask.getEpicId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEpicId());
    }
}