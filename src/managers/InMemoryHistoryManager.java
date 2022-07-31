package managers;

import annex.Node;
import tasktypes.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    Map<Integer, Node> nodeMap = new HashMap<>();

    private Node <Task> head;

    private Node <Task> tail;

    private boolean isEmpty(){
       return head == null;
    }

    public void linkLast(Task task){
        final Node<Task> newNode = new Node<> (tail, task, null);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        newNode.prev = tail;
        tail = newNode;
    }


    public List<Task> getTasks(){
        List<Task> taskHistory = new ArrayList<>();
        Node node = head;
        while(node != null){
           taskHistory.add((Task) node.getTask());
           node = node.getNext();
       }


        return taskHistory;

    }

    @Override
    public void add(Task task) {
        if(nodeMap.containsKey(task.getId())){
            remove(task.getId());
        }
        linkLast(task);
        nodeMap.put(task.getId(), tail);

    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id){
        removeNode(id);
        nodeMap.remove(id);

    }

    public void removeNode(int id){
        Node node = nodeMap.get(id);

        if (node == null){
            return;
        }

        if (node.next != null && node.prev != null){
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        if (node.next == null && node.prev != null){
            tail.prev.next = null;
            tail = tail.prev;
        }

        if (node.next != null && node.prev == null){
            head.next.prev = null;
            head = head.next;
        }

        if (node.next == null && node.prev == null){
            head = null;
            tail = null;
        }
    }


}
