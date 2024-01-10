public class SubTask extends Task {
    private static int subTaskIdCounter = 300;

    public SubTask(String title, String description, Status status) {
        super(title, description, status); // вызываем конструктор Task и наследуем поля
        setId(subTaskIdCounter++);
    }

    public int getId() {
        return super.id;
    }

}
