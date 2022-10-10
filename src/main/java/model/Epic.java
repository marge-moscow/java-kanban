package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Epic extends Task {
    protected ArrayList<Subtask> subtasks = new ArrayList<>();

    protected Duration duration;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    public Epic(int id, String name, String description) {
        super(id, name, description);
        setType(TaskType.EPIC);
        setStartTime();
        setDuration();
        this.endTime = getEndTime();
    }

    public Epic() {
        setType(TaskType.EPIC);
        setStartTime();
        setDuration();
        this.endTime = getEndTime();
    }

    public void setStartTime() {
        this.startTime = calculateStartTime();
    }

    public LocalDateTime calculateStartTime() {
        Optional<LocalDateTime> minOptional = subtasks.stream()
                .map(Subtask::getStartTime)
                .filter(Objects :: nonNull)
                .min(LocalDateTime::compareTo);
        return minOptional.orElse(null);
    }

    public void setDuration() {
        this.duration = calculateDuration();
    }

    public Duration calculateDuration() {
        return subtasks.stream()
                .map(Subtask::getDuration)
                .filter(Objects :: nonNull) //ТУТ ВНЕСЛА ИЗМЕНЕНИЕ
                .reduce(Duration.ofMinutes(0), Duration::plus);

    }

    public LocalDateTime getEndTime() {
        Optional<LocalDateTime> maxOptional = subtasks.stream()
                .map(Subtask::getEndTime)
                .filter(Objects :: nonNull)
                .max(LocalDateTime::compareTo);
        return maxOptional.orElse(null);
    }

    //.map(Subtask::getEndTime)

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
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