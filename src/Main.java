public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = new Task("Задача     ", "  Обычная задача (без разделения)", Status.NEW);
        Epic epic = new Epic("Эпик       ", "  Содержит в себе несколько подзадач", Status.NEW);
        SubTask subTack1 = new SubTask("Подзадача_1", "  Составляющая Эпика 1st", Status.NEW);
        SubTask subTack2 = new SubTask("Подзадача_2", "  Составляющая Эпика 2nd", Status.DONE);
        SubTask subTack3 = new SubTask("Подзадача_3", "  Составляющая Эпика 3rd", Status.DONE);
//
//        taskManager.addTask(task);
//        taskManager.addEpic(epic);
//        taskManager.addSubTask(subTack1);
//        taskManager.addSubTask(subTack2);

//
//        taskManager.clearTask();
//        taskManager.clearEpic();
//        taskManager.clearSubTask();
//
//        epic.addSubTaskInEpic(subTack1);
//        epic.addSubTaskInEpic(subTack2);
//
//        taskManager.getTask_forId(task, taskManager.taskId);
//        taskManager.getEpic_forId(epic, taskManager.epicId);
//        taskManager.getSubTask_forId(subTack1, taskManager.subTaskId);
//        taskManager.getSubTask_forId(subTack2, taskManager.subTaskId);
//
//        epic.getSubTask(subTack1);
//        epic.getSubTask(subTack2);
//
////        System.out.println(task.getId_Task());
////        System.out.println(epic.getId_Epic());
////        System.out.println(epic.setId(subTack1.getId()));
////        System.out.println(epic.setId(subTack2.getId()));
////        System.out.println(epic.setId(subTack3.getId()));
//
//        System.out.println(taskManager.getTask(task));//        System.out.println(task);
//        System.out.println(taskManager.getEpic(epic));//        System.out.println(epic);
//        System.out.println(taskManager.getSubTask(subTack1));// System.out.println(subTack1);
//        System.out.println(taskManager.getSubTask(subTack2));// System.out.println(subTack2);
//        System.out.println(taskManager.getSubTask(subTack3));// System.out.println(subTack2);


    }
}
