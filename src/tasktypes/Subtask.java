package tasktypes;

import annex.TaskType;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String name, String description, TaskType type, int epicId) {
        super(id, name, description, type);
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