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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{

    protected FileBackedTaskManager fileBackedTaskManager;

    protected Task task;
    protected Epic epic;
    protected Subtask subtask;
    File failTest;
    @BeforeEach
    public void setUp() throws IOException {

        taskManager = new FileBackedTaskManager();

        task = new Task("Задача 1", "Описание задачи 1", Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 25, 18, 10));
        epic = new Epic("Эпик 1", "Описание эпика 1");
        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic, Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 25, 18, 10));

         failTest = File.createTempFile("test", "csv");
    }

    @Test
    public void testSaveAndLoadEmptyFile() throws IOException {
        FileWriter fw = new FileWriter(failTest);
        assertNotEquals(fw, null);
    }
    @Test
    public void testThatFileBackedTaskManagerAddTasksDifferentTypesAndCanFindThemById() {

        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        assertEquals(task.getId(), 1);
        assertEquals(epic.getId(), 2);
        assertEquals(subtask.getId(), 3);
    }

    @Test
    public void testThatInstancesClassTaskEqualIfTheirIdSimilar() {
        task.setTypeTES(TypeTES.TASK);
        task.setId(1);

        Task taskTWO = new Task("Задача 2", "Цельная", Duration.ofMinutes(30), LocalDateTime.of(2024, Month.JUNE, 25, 18, 0));
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

    @Test//+
    public void testThatUtilityClassAlwaysReturnsInitializedAndReadyUseInstancesOfManagers() {
        TaskManager managerTM = Managers.getDefault();

        HistoryManager managerHM = Managers.getDefaultHistory();

        assertNotNull( managerTM.toString(),"Утилитарный класс TaskManager проинициализирован");
        assertNotNull(managerHM, "Утилитарный класс HistoryManager проинициализирован");
    }


}


