package app.main;

import app.enums.Status;
import app.taskmamager.TaskManager;
import app.tasks.Epic;
import app.tasks.SubTask;
import app.tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task = new Task("Задача     ", " Целая  ");

        Epic epic1 = new Epic("Эпик 1 ####", " ПЕРВЫЙ ");
        SubTask subTask1 = new SubTask("########## ", " ##sT1##");
        SubTask subTask2 = new SubTask("########## ", " ##sT2##");
        //SubTask subTask3 = new SubTask("########## ", " ##ST3##");

        Epic epic2 = new Epic("Эпик 2 @@@@", " ВТОРОЙ");
        SubTask subTaskA = new SubTask("@@@@@@@@@@ ", " @@sTA@@");
        //SubTask subTaskB = new SubTask("@@@@@@@@@@ ", " @@sTB@@");

        /**            a. Получение списка всех задач.        */
//        System.out.println(taskManager.getListTask());
//        System.out.println(taskManager.getListEpic());
//        System.out.println(taskManager.getListSubTask());

        /**            d. Создание. Сам объект должен передаваться в качестве параметра.        */
        taskManager.addTask(task);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        subTask1.setEpicId(epic1.getId());
        subTask2.setEpicId(epic1.getId());
        //subTask3.setEpicId(epic1.getId());

        subTaskA.setEpicId(epic2.getId());
        //subTaskB.setEpicId(epic2.getId());

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        //taskManager.addSubTask(subTask3);

        taskManager.addSubTask(subTaskA);
        //taskManager.addSubTask(subTaskB);

        /**            a. Получение списка всех задач.                                         */
//        System.out.println(taskManager.getListTask());
//        System.out.println(taskManager.getListEpic());
//        System.out.println(taskManager.getListSubTask());


        /**            b. Удаление всех задач.                                                 */
//        taskManager.clearTask();
//        taskManager.clearEpic();
//        taskManager.clearSubTask();

        /**            h. Изменение статуса.                                                 */
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        //subTask3.setStatus(Status.DONE);

        /**            a. Получение списка всех задач.                                         */
        System.out.println(taskManager.getListTask());
        System.out.println(taskManager.getListEpic());
        System.out.println(taskManager.getListSubTask());
        /**            a. Получение статуса.                                         */
//        System.out.println(task.getStatus());
//        System.out.println(epic1.getStatus());
//        System.out.println(subTask1.getStatus());

        /**            c. Получение по идентификатору.                                         */
//        System.out.println(taskManager.getTaskById(task));
//        System.out.println(taskManager.getEpicById(epic1));
//        System.out.println(taskManager.getEpicById(epic2));
//        System.out.println(taskManager.getSubTaskById(subTack1));
//        System.out.println(taskManager.getSubTaskById(subTack2));
//        System.out.println(taskManager.getSubTaskById(subTack3));

        /**            e. Обновление.                                                          */
//        taskManager.updateTask(task);
//        taskManager.updateEpic(epic2);
//        taskManager.updateSubTask(subTask1);

        /**            f. Удаление по идентификатору.                                          */
        taskManager.removeTaskById(task);
        taskManager.removeEpicById(epic2);
        taskManager.removeSubTaskById(subTask2);
        System.out.println("-----------------------------------");
        /**            a. Получение списка всех задач.                                         */
        System.out.println(taskManager.getListTask());
        System.out.println(taskManager.getListEpic());
        System.out.println(taskManager.getListSubTask());

        /**            3а. Получение списка всех подзадач определённого эпика.                 */
//        epic1.addSubTaskInEpic(subTask1);
//        epic1.addSubTaskInEpic(subTask2);
//        epic1.addSubTaskInEpic(subTask3);
//        epic2.addSubTaskInEpic(subTaskA);
//        epic2.addSubTaskInEpic(subTaskB);
//
//        System.out.println(epic1.getSubTaskInEpic());
//        System.out.println(epic2.getSubTaskInEpic());
//        System.out.println(taskManager.getListEpic());


//        System.out.println(epic2.getSubTaskInEpic());

        /**            3б. Удаление списка всех подзадач определённого эпика.                 */
//        epic1.clearSubTaskInEpic();
//        System.out.println(epic1.getSubTaskInEpic());
    }
}
