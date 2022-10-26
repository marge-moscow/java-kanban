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
    protected LocalDateTime endTime;

    //Первоначальный конструктор для ТЗ 4-6
    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.type = TaskType.TASK;
    }

    //Конструктор для тестов
    public Task(LocalDateTime startTime, Duration duration) {
        setType(TaskType.TASK);
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = getEndTime();

    }

    public Task(){
        setType(TaskType.TASK);
    }

    //Конструктор для ТЗ 7 спринта со временем начала и продолжительностью
    public Task(int id, String name, String description, LocalDateTime startTime, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        setType(TaskType.TASK);
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = getEndTime();

    }

    //Для задач, у которых не задано время начала
    public Task(int id, String name, String description, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        setType(TaskType.TASK);
        this.duration = duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
        this.endTime = calculateEndTime();
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        this.endTime = calculateEndTime();
    }

    public LocalDateTime getEndTime() {
        return calculateEndTime();
    }

    public LocalDateTime calculateEndTime () {
        return startTime != null && duration != null ? startTime.plus(duration) : null;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (getName() != null ? !getName().equals(task.getName()) : task.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(task.getDescription()) : task.getDescription() != null)
            return false;
        return getType() == task.getType();
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

}

