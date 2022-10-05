package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Subtask> subtasks = new ArrayList<>();
    protected LocalDateTime endTime;

    public Epic(int id, String name, String description, TaskType type) {
        super(id, name, description, type);
    }

    public Epic() {
        setType(TaskType.EPIC);

    }

    //Конструктор для ТЗ 7 спринта со временем начала и продолжительностью
    public Epic(int id, String name, String description) {
        super(id, name, description);
        setType(TaskType.EPIC);
    }

    public void setStartTime() {
        LocalDateTime startTime = LocalDateTime.now(); //Как правильно проинициализировать переменную?
        for(int i = 0; i < subtasks.size(); i++) {
            if (subtasks.get(i++).getStartTime().isBefore(subtasks.get(i).getStartTime())) {
               startTime = subtasks.get(i++).getStartTime();
            } else {
                startTime = subtasks.get(i).getStartTime();
            }
        }
        this.startTime = startTime;
    }

    public void setDuration() {
        Duration duration = null; //Как правильно проинициализировать переменную?
        for(int i = 0; i < subtasks.size(); i++) {
            duration = duration.plus(subtasks.get(i).getDuration());
        }
        this.duration = duration;

    }

    public LocalDateTime getEndTime() {
        endTime = LocalDateTime.now(); //Как правильно проинициализировать переменную?
        for(int i = 0; i < subtasks.size(); i++) {
            if (subtasks.get(i++).getEndTime().isAfter(subtasks.get(i).getStartTime())) {
                endTime = subtasks.get(i++).getEndTime();
            } else {
                endTime = subtasks.get(i).getEndTime();
            }
        }
        return endTime;
    }

    public void addSubtask(Subtask subtask){
        subtasks.add(subtask);
    }
    public void deleteSubtask(Subtask subtask){
        subtasks.remove(subtask);
    }
    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public TaskType getType() {
        return type;
    }

    @Override
    public void setType(TaskType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString() + "\n  Подзадачи:\n" + subtasks;
    }
}