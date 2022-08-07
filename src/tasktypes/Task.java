package tasktypes;

import annex.TaskStatus;
import annex.TaskType;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected TaskStatus status;
    protected TaskType type;

    public Task(int id, String name, String description, TaskType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = TaskStatus.NEW;
        this.type = type;
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