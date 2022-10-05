package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected TaskStatus status;
    protected TaskType type;
    protected Duration duration;
    protected LocalDateTime startTime;

    //Первоначальный конструктор для ТЗ 4-6
    public Task(int id, String name, String description, TaskType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.type = type;
    }

    //Конструктор для тестов
    public Task() {
        setType(TaskType.TASK);

    }

    //Конструктор для ТЗ 7 спринта со временем начала и продолжительностью
    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        setType(TaskType.TASK);
        //Нужно ли сюда выносить getEndTime?
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }


    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return id + "." + name + "\n  Описание: " + description + "\n  Статус: " + status;
    }
}