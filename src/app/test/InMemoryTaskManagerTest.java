package app.test;

import app.enums.HistoryManager;
import app.enums.Status;
import app.enums.TypeTES;
import app.history.InMemoryHistoryManager;
import app.history.InMemoryTaskManager;
import app.intefaces.TaskManager;
import app.taskmanager.Managers;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    protected InMemoryTaskManager taskManager;
    protected InMemoryHistoryManager historyManager;

    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    @BeforeEach
    public void setUp() throws IOException {
        taskManager = new InMemoryTaskManager();

        task = new Task("Задача 1", "Описание задачи 1", Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 26, 18, 10));
        epic = new Epic("Эпик 1", "Описание эпика 1");
        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic, Duration.ofMinutes(3), LocalDateTime.now());

        File f = File.createTempFile("test", "csv");

    }

    @Test
    public void testThatInstancesClassTaskEqualIfTheirIdSimilar() {// проверьте, что экземпляры класса Task равны друг другу, если равен их id;
        task.setTypeTES(TypeTES.TASK);
        task.setId(1);

        Task taskTWO = new Task("Задача 2", "Описание задачи 2");
        taskTWO.setTypeTES(TypeTES.TASK);
        taskTWO.setId(1);

        assertEquals(task, taskTWO);
    }

    @Test
    public void testThatHeirsOfTaskEqualIfTheirIdEqual() {// проверьте, что наследники класса Task равны друг другу, если равен их id;
        task.setTypeTES(TypeTES.TASK);
        task.setId(1);

        epic.setTypeTES(TypeTES.EPIC);
        epic.setId(1);

        assertEquals(task.getId(), epic.getId());
    }

    @Test
    public void testThatSubtaskCantMakeSelfEpic() {// проверьте, что объект Subtask нельзя сделать своим же эпиком;;
        subtask.setEpicId(subtask.getId());

        assertFalse(taskManager.getSubtasks().contains(subtask));
    }

    @Test
    public void testThatUtilityClassAlwaysReturnsInitializedAndReadyUseInstancesOfManagers() {// убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;;
        TaskManager managerTM = Managers.getDefault();//экземпляр утилитарного класса. Метод getDefault() возвращает объект типа TaskManager(интрефейс InMemoryTaskManager)

        HistoryManager managerHM = Managers.getDefaultHistory();

        assertNotNull(managerTM, "Утилитарный класс TaskManager проинициализирован");
        assertNotNull(managerHM, "Утилитарный класс HistoryManager проинициализирован");
    }

    @Test
    public void testThatInmemorytaskmanagerAddTasksDifferentTypesAndCanFindThemById() {// проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
        // Добавляем задачи в менеджер задач
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        // Проверяем, что задачи успешно добавлены и найдены по их id
        assertEquals(task.getId(), 1);
        assertEquals(epic.getId(), 2);
        assertEquals(subtask.getId(), 3);
    }

    @Test
    public void testThatTasksWithInstalledIdMotConflictWithTasksGenerateID() {// проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
        Task taskGenerateID = new Task("Задача", "Где id установлен самостоятельно");
        taskGenerateID.setId(1);

        taskManager.addTask(taskGenerateID);
        taskManager.addTask(task);

        List<Task> tasksList = taskManager.getTasks();

        assertTrue(tasksList.contains(taskGenerateID));
        assertTrue(tasksList.contains(task));
        assertNotEquals(taskGenerateID, task);
    }

    @Test
    public void testNotModificationTaskWhenAddInManager() {// создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
        taskManager.addTask(task);
        Task newTask = new Task("Задача 1", "Описание задачи 1", Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 27, 18, 10));
        taskManager.addTask(newTask);

        assertNotEquals(task.getId(), newTask.getId());
        assertEquals(task.getName(), newTask.getName());
        assertEquals(task.getDescription(), newTask.getDescription());
        assertEquals(task.getStatus(), newTask.getStatus());
        assertEquals(task.getTypeTES(), newTask.getTypeTES());
    }

    @Test
    public void testDeletedSubtasksShouldNotStoreOldIDsInsideThemselves() {//Удаляемые подзадачи не должны хранить внутри себя старые id
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);
        taskManager.removeSubtask(subtask);

        assertNull(taskManager.findSubtask(subtask));

        assertEquals(0, epic.getListSubtask().size());
    }

    @Test
    public void testTaskManagerContainsOriginalTaskAfterFieldChanges() {
        Task originalTask = new Task("Задача 1", "Описание задачи 1", Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 28, 18, 10));
        originalTask.setTypeTES(TypeTES.TASK);

        taskManager.addTask(originalTask);

        originalTask.setName("Измененное название задачи");
        originalTask.setDescription("Измененное описание задачи");
        originalTask.setTypeTES(TypeTES.EPIC); // Изменяем тип задачи

        assertEquals("Измененное название задачи", originalTask.getName(), "Имя не изменилось");//
        assertEquals("Измененное описание задачи", originalTask.getDescription(), "Описание не изменилось");
        assertEquals(TypeTES.EPIC, originalTask.getTypeTES(), "Статус не поменялся");
    }

    @Test
    public void testCalculateEpicStatus() {

        ArrayList<Subtask> subtasksList = new ArrayList<>();

        // a. Все подзадачи со статусом NEW.
        for (int i = 0; i < 3; i++) {
            Subtask subtask = new Subtask("Подзадача " + i, "Описание", epic, Duration.ofMinutes(10), LocalDateTime.now());
            subtask.setStatus(Status.NEW);
            subtasksList.add(subtask);
        }
        epic.getListSubtask().addAll(subtasksList);
        taskManager.addEpic(epic);
        taskManager.updateEpicStatus(epic);
        assertEquals(Status.NEW, epic.getStatus());

        // b. Все подзадачи со статусом DONE.
        for (Subtask subtask : subtasksList) {
            subtask.setStatus(Status.DONE);
        }
        taskManager.updateEpicStatus(epic);
        assertEquals(Status.DONE, epic.getStatus());

        // c. Подзадачи со статусами NEW и DONE.
        subtasksList.get(0).setStatus(Status.NEW);
        subtasksList.get(1).setStatus(Status.DONE);
        taskManager.updateEpicStatus(epic);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        // d. Подзадачи со статусом IN_PROGRESS.
        subtasksList.get(1).setStatus(Status.IN_PROGRESS);
        taskManager.updateEpicStatus(epic);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }
}
