package managers;

import taskTypes.Task;

public interface HistoryManager {

    void add(Task task);

    void getHistory();
}
