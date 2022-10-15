package managers.history;

public class InMemoryHistoryManagerTest extends HistoryManagerTest<InMemoryHistoryManager> {

    @Override
    public InMemoryHistoryManager getManager() {
        return new InMemoryHistoryManager();
    }
}
