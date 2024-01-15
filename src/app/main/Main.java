package app.main;

import app.taskmamager.TaskManager;
import app.tasks.Epic;
import app.tasks.SubTask;
import app.tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task = new Task("Задача     ", "  Целая");

        Epic epic1 = new Epic("Эпик 1", " ПЕРВЫЙ");
        SubTask subTack1 = new SubTask("Подзадача#", "   номер 1");
        SubTask subTack2 = new SubTask("Подзадача#", "   номер 2");
        SubTask subTack3 = new SubTask("Подзадача#", "   номер 3");

        Epic epic2 = new Epic("Эпик 2", " ВТОРОЙ");
        SubTask subTackA = new SubTask("Подзадача@", "   литер А");
        SubTask subTackB = new SubTask("Подзадача@", "   литер В");

        /**            a. Получение списка всех задач.        */
//        System.out.println(taskManager.getListTask());
//        System.out.println(taskManager.getListEpic());
//        System.out.println(taskManager.getListSubTask());

        /**            d. Создание. Сам объект должен передаваться в качестве параметра.        */
//        taskManager.addTask(task);
//        taskManager.addEpic(epic1);
//        taskManager.addEpic(epic2);

//        taskManager.addSubTask( subTack1);
//        taskManager.addSubTask(subTack2);
//        taskManager.addSubTask(subTack3);

//        taskManager.addSubTask(subTackA);/**ДОБАВИЛ САБТАСКИ*/
//        taskManager.addSubTask(subTackB);

        /**            a. Получение списка всех задач.                                         */
//        System.out.println(taskManager.getListTask());
//        System.out.println(taskManager.getListEpic());
//        System.out.println(taskManager.getListSubTask());


        /**            b. Удаление всех задач.                                                 */
//        taskManager.clearTask();
//        taskManager.clearEpic();
//        taskManager.clearSubTask();

        /**            a. Получение списка всех задач.                                         */
//        System.out.println(taskManager.getListTask());
//        System.out.println(taskManager.getListEpic());
//        System.out.println(taskManager.getListSubTask());

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
//        taskManager.updateSubTask(subTack1);

        /**            f. Удаление по идентификатору.                                          */
//        taskManager.removeTaskById(task);
//        taskManager.removeEpicById(epic);
//        taskManager.removeSubTaskById(subTack1);

        /**            3а. Получение списка всех подзадач определённого эпика.                 */
//        epic1.addSubTaskInEpic(subTack1);
//        epic1.addSubTaskInEpic(subTack2);
//        epic1.addSubTaskInEpic(subTack3);
//        epic2.addSubTaskInEpic(subTackA);
//        epic2.addSubTaskInEpic(subTackB);
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
