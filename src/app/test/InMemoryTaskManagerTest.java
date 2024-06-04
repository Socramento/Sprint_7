package app.test;
//ПУЛ РЕКВЕСТ
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
    public void testThatInstancesClassTaskEqualIfTheirIdSimilar() {
        task.setTypeTES(TypeTES.TASK);
        task.setId(1);

        Task taskTWO = new Task("Задача 2", "Описание задачи 2");
        taskTWO.setTypeTES(TypeTES.TASK);
        taskTWO.setId(1);

        assertEquals(task, taskTWO);
    }

    @Test
    public void testThatHeirsOfTaskEqualIfTheirIdEqual() {
        task.setTypeTES(TypeTES.TASK);
        task.setId(1);

        epic.setTypeTES(TypeTES.EPIC);
        epic.setId(1);

        assertEquals(task.getId(), epic.getId());
    }

    @Test
    public void testThatSubtaskCantMakeSelfEpic() {
        subtask.setEpicId(subtask.getId());

        assertFalse(taskManager.getSubtasks().contains(subtask));
    }

    @Test
    public void testThatUtilityClassAlwaysReturnsInitializedAndReadyUseInstancesOfManagers() {
        TaskManager managerTM = Managers.getDefault();

        HistoryManager managerHM = Managers.getDefaultHistory();

        assertNotNull(managerTM, "Утилитарный класс TaskManager проинициализирован");
        assertNotNull(managerHM, "Утилитарный класс HistoryManager проинициализирован");
    }

    @Test
    public void testThatInmemorytaskmanagerAddTasksDifferentTypesAndCanFindThemById() {

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        assertEquals(task.getId(), 1);
        assertEquals(epic.getId(), 2);
        assertEquals(subtask.getId(), 3);
    }

    @Test
    public void testThatTasksWithInstalledIdMotConflictWithTasksGenerateID() {
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
    public void testNotModificationTaskWhenAddInManager() {
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
    public void testDeletedSubtasksShouldNotStoreOldIDsInsideThemselves() {
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
        originalTask.setTypeTES(TypeTES.EPIC);

        assertEquals("Измененное название задачи", originalTask.getName(), "Имя не изменилось");//
        assertEquals("Измененное описание задачи", originalTask.getDescription(), "Описание не изменилось");
        assertEquals(TypeTES.EPIC, originalTask.getTypeTES(), "Статус не поменялся");
    }

    @Test
    public void testCalculateEpicStatus() {

        ArrayList<Subtask> subtasksList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Subtask subtask = new Subtask("Подзадача " + i, "Описание", epic, Duration.ofMinutes(10), LocalDateTime.now());
            subtask.setStatus(Status.NEW);
            subtasksList.add(subtask);
        }
        epic.getListSubtask().addAll(subtasksList);
        taskManager.addEpic(epic);
        taskManager.updateEpicStatus(epic);
        assertEquals(Status.NEW, epic.getStatus());

        for (Subtask subtask : subtasksList) {
            subtask.setStatus(Status.DONE);
        }
        taskManager.updateEpicStatus(epic);
        assertEquals(Status.DONE, epic.getStatus());

        subtasksList.get(0).setStatus(Status.NEW);
        subtasksList.get(1).setStatus(Status.DONE);
        taskManager.updateEpicStatus(epic);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subtasksList.get(1).setStatus(Status.IN_PROGRESS);
        taskManager.updateEpicStatus(epic);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }
}
