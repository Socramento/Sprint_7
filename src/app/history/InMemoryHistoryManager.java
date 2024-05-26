package app.history;

import app.enums.HistoryManager;
import app.tasks.Task;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private HashMap<Integer, Node> taskNodes; // Хранит узлы списка задач по их id
    private Node<Task> head; // Голова списка
    private Node<Task> tail; // Хвост списка

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
        Node<Task> newNode = new Node<>(task);// новый узел newNode с данными задачи task.
        if (head == null) { //Если список пуст,
            head = newNode; //тогда новый узел newNode становится и головой списка
            tail = newNode;// и хвостом
        } else {
            tail.next = newNode;// иначе новый узел добавляется после текущего хвоста tail.Текущий хвост ссылается на новый узел через поле next
            newNode.prev = tail; //а новый узел устанавливает связь с предыдущим хвостом через поле prev
            tail = newNode;// обновляется указатель tail на новый узел, так как он стал последним в списке
        }
    }

    public List<Task> getTasks() {
        List<Task> listGetTask = new ArrayList<>();// список, который будет хранить задачи из истории
        Node<Task> current = head; //current устанавливается на голову списка, чтобы начать проходить по всем узлам списка, начиная с головы.
        while (current != null) { // пока текущий узел не равен нулю
            listGetTask.add(current.data);// в список добавляется очередной узел с даннымм
            current = current.next; // текущему узлу присваивается ссылка на следующий узел
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

    public void removeNode(Node<Task> node) {  // Метод для удаления узла из списка
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
