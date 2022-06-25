package managers;

import tasktypes.Task;

public interface HistoryManager {

    void add(Task task);

    void getHistory();
}
