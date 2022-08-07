package managers;

import file.FileBackedTasksManager;
import managers.memoryManager.InMemoryHistoryManager;
import managers.memoryManager.InMemoryTaskManager;

public class Managers {
    public static TaskManger getDefault() {
       return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getFileBackedTasksManager() {
        return new FileBackedTasksManager();
    }
}
