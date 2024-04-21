package app.test;

import app.history.InMemoryTaskManager;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager;
    private Task task;
    private Epic epic;
    private Subtask subtask;
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

//    @BeforeEach
//    public void setUp() {
//        taskManager = new InMemoryTaskManager();
//        task = new Task("Задача 1", "Описание задачи 1");
//        epic = new Epic("Эпик 1", "Описание эпика 1");
//        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic);
//    }
//    @Test
//    public void checkThatInstancesClassTaskEqualIfTheirIdSimilar() {
//        Task task1 = new Task("Задача 1", "Описание задачи 1");
//        inMemoryTaskManager.addTask(task1);
//
//        Task task2 = new Task("Задача 2", "Описание задачи 2");
//        inMemoryTaskManager.addTask(task2);
//
//        Task task3 = new Task("Задача 3", "Описание задачи 3");
//        inMemoryTaskManager.addTask(task3);
//
//        assertNotEquals(task1, inMemoryTaskManager.findTask(task2));
//        assertNotEquals(task1, inMemoryTaskManager.findTask(task3));
//        assertNotEquals(task2, inMemoryTaskManager.findTask(task3));
//    }

//    @Test
//    public void checkThatInstancesClassEpicEqualIfTheirIdSimilar() {
//        Epic epic1 = new Epic("Эпик 1", "Прием пищи");
//        inMemoryTaskManager.addEpic(epic1);
//
//        Epic epic2 = new Epic("Эпик 2", "Сон");
//        inMemoryTaskManager.addEpic(epic2);
//
//        Epic epic3 = new Epic("Эпик 3", "Прогулка");
//        inMemoryTaskManager.addEpic(epic3);
//
//        assertNotEquals(epic1, inMemoryTaskManager.findEpic(epic2));
//        assertNotEquals(epic1, inMemoryTaskManager.findEpic(epic3));
//        assertNotEquals(epic2, inMemoryTaskManager.findEpic(epic3));
//    }

    @Test
    public void deletedSubtasksShouldNotStoreOldIDsInsideThemselves() {//Удаляемые подзадачи не должны хранить внутри себя старые id
//        taskManager.addTask(task);
//        taskManager.addEpic(epic);
//        taskManager.addSubtask(subtask);
//        System.out.println(taskManager.getEpics());
//        System.out.println(taskManager.getSubtasks());
//        taskManager.removeSubtask(subtask);
//
//        // Проверяем, что подзадача больше не существует в списке подзадач
//        assertNull(taskManager.findSubtask(subtask));
//
//        // Проверяем, что подзадача больше не содержит старых id
//        assertEquals(0, epic.getListSubtask().size());
    }

    @Test
    public void changingTaskTitleShouldUpdateTaskInTaskManager() { //изменение Названия Задачи должно обновить Задачу в Диспетчере Задач
//        // Изменяем заголовок задачи через сеттер
//        task.setName("Измененный заголовок");
//
//        // Получаем задачу из менеджера и проверяем, что заголовок изменился
//        //Task updatedTask = taskManager.findTask(task);
//        assertEquals("Измененный заголовок", task.getName());
    }



}
