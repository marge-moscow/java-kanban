package tasktypes;

import annex.TaskType;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(int id, String name, String description, TaskType type) {
        super(id, name, description, type);
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