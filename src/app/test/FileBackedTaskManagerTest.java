package app.test;

import app.enums.HistoryManager;
import app.enums.TypeTES;
import app.history.FileBackedTaskManager;
import app.intefaces.TaskManager;
import app.taskmanager.Managers;
import app.tasks.Epic;
import app.tasks.Subtask;
import app.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.Assert.*;

class FileBackedTaskManagerTest {

    protected FileBackedTaskManager fileBackedTaskManager;

    protected Task task;
    protected Epic epic;
    protected Subtask subtask;
    File testFile;

    @BeforeEach
    public void setUp() {

        fileBackedTaskManager = new FileBackedTaskManager();

        task = new Task("Задача 1", "Описание задачи 1");
        epic = new Epic("Эпик 1", "Описание эпика 1");
        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic);
    }

    @Test//+
    public void testThatInstancesClassTaskEqualIfTheirIdSimilar() {
        task.setTypeTES(TypeTES.TASK);
        task.setId(1);

        Task taskTWO = new Task("Задача 2", "Описание задачи 2");
        taskTWO.setTypeTES(TypeTES.TASK);
        taskTWO.setId(1);

        assertEquals(task, taskTWO);
    }

    @Test//+
    public void testThatHeirsOfTaskEqualIfTheirIdEqual() {
        task.setTypeTES(TypeTES.TASK);
        task.setId(1);

        epic.setTypeTES(TypeTES.EPIC);
        epic.setId(1);

        assertEquals(task.getId(), epic.getId());
    }

    @Test//+
    public void testThatSubtaskCantMakeSelfEpic() {
        subtask.setEpicId(subtask.getId());
        assertFalse(fileBackedTaskManager.getSubtasks().contains(subtask));
    }

    @Test//+
    public void testThatUtilityClassAlwaysReturnsInitializedAndReadyUseInstancesOfManagers() {
        TaskManager managerTM = Managers.getDefault();
        HistoryManager managerHM = Managers.getDefaultHistory();

        assertNotNull("Утилитарный класс TaskManager проинициализирован", managerTM);
        assertNotNull("Утилитарный класс HistoryManager проинициализирован", managerHM);
    }

    @Test
    public void testThatFileBackedTaskManagerTestAddTasksDifferentTypesAndCanFindThemById() {
        // Добавляем задачи в менеджер задач
        fileBackedTaskManager.addTask(task);
        fileBackedTaskManager.addEpic(epic);
        fileBackedTaskManager.addSubtask(subtask);


        assertEquals(task.getId(), 1);
        assertEquals(epic.getId(), 2);
        assertEquals(subtask.getId(), 3);

    }
}
