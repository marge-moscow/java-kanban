package managers;

import tasktypes.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    protected List<Task> taskHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        checkHistorySize();
        taskHistory.add(task);

    }

    @Override
    public void getHistory() {
        System.out.println("История просмотренных задач:");
        for (Task task: taskHistory){
            System.out.println(task.getId() + " " + task.getName());
        }
    }

    public void checkHistorySize(){
        if(taskHistory.size() >= 10) {
            taskHistory.remove(0);
        }
    }

}
