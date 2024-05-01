package app.test;

import app.enums.HistoryManager;
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

import java.util.List;

import static org.junit.Assert.*;

class InMemoryTaskManagerTest {
    protected InMemoryTaskManager taskManager;
    protected InMemoryHistoryManager historyManager;

    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();

        task = new Task("Задача 1", "Описание задачи 1");
        epic = new Epic("Эпик 1", "Описание эпика 1");
        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic);
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

        assertNotNull("Утилитарный класс TaskManager проинициализирован", managerTM);
        assertNotNull("Утилитарный класс HistoryManager проинициализирован", managerHM);
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
        Task newTask = new Task("Задача 1", "Описание задачи 1");
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

        Task originalTask = new Task("Оригинальная задача", "Описание оригинальной задачи");
        originalTask.setTypeTES(TypeTES.TASK);

        int taskId = taskManager.addTask(originalTask);

        originalTask.setName("Измененное название задачи");
        originalTask.setDescription("Измененное описание задачи");
        originalTask.setTypeTES(TypeTES.EPIC); // Изменяем тип задачи

        //Task taskFromManager = taskManager.getTasks().get(0);

        assertEquals("Название задачи должно быть изменено", originalTask.getName(), "Измененное название задачи");//
        assertEquals("Описание задачи должно быть изменено", originalTask.getDescription(),  "Измененное описание задачи");
        assertEquals("Тип задачи должен быть изменен", TypeTES.EPIC, originalTask.getTypeTES());
    }
}
