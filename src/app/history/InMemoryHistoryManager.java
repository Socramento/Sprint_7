package app.history;

import app.enums.HistoryManager;
import app.tasks.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private HashMap<Integer, Node> taskNodes;
    private Node<Task> head;
    private Node<Task> tail;

    List<Task> historyList = new LinkedList<>();

    public InMemoryHistoryManager() {
        taskNodes = new HashMap<>();
        head = null;
        tail = null;
    }

    private static class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

    public void linkLast(Task task) {
        Node<Task> newNode = new Node<>(task);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public List<Task> getTasks() {
        List<Task> listGetTask = new ArrayList<>();
        Node<Task> current = head;
        while (current != null) {
            listGetTask.add(current.data);
            current = current.next;
        }
        return listGetTask;
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node<Task> current = head;
        while (current != null) {
            history.add(current.data);
            current = current.next;
        }
        return history;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            System.out.println("Пусто");
            return;
        }

        Node<Task> newNode = new Node<>(task);
        taskNodes.put(task.getId(), newNode);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    @Override
    public void remove (int id) {
        historyList.remove(id);
    }

    public void removeNode(Node<Task> node) {
        if (node == null) {
            return;
        }
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        taskNodes.remove(node.data.getId());
    }
}
