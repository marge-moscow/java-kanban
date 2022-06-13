import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(int id, String name, String description) {
        super(id, name, description);
    }
    public void addSubtask(Subtask subtask){
        subtasks.add(subtask);
    }
    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public String toString() {
        return super.toString() + "\n  Подзадачи:\n" + subtasks;
    }
}