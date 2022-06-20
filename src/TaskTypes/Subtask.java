package TaskTypes;

public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String name, String description, int epicId) {
        super(id, name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
    @Override
    public String toString() {
        return "\n" + id + "." + name + " Описание: " + description + " Статус: " + status + ". Номер эпика: " + epicId;
    }
}