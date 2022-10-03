package managers.history;

public class Node <Task>{
    public Task task;
    public Node <Task> next;
    public Node <Task> prev;

    public Node(Node<Task> prev, Task task, Node<Task> next){
        this.task = task;
        this.next = next;
        this.prev = prev;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Node<Task> getNext() {
        return next;
    }

    public void setNext(Node<Task> next) {
        this.next = next;
    }

    public Node<Task> getPrev() {
        return prev;
    }

    public void setPrev(Node<Task> prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Node{" +
                "task=" + task +
                ", next=" + next +
                ", prev=" + prev +
                '}';
    }
}
