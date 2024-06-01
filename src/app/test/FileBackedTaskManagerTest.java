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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager>{

    protected FileBackedTaskManager fileBackedTaskManager;

    protected Task task;
    protected Epic epic;
    protected Subtask subtask;
    File testFile;
    @BeforeEach
    public void setUp() {

        taskManager = new FileBackedTaskManager();

        task = new Task("Задача 1", "Описание задачи 1", Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 25, 18, 10));
        epic = new Epic("Эпик 1", "Описание эпика 1");
        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic, Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 25, 18, 10));
    }

//    @Test
//    public void testSaveAndLoadEmptyFile() throws IOException {// сохранение и загрузка пустого файла;
//
//        testFile = fileBackedTaskManager.text;
//        BufferedReader brt = new BufferedReader(new FileReader(testFile));
//        assertEquals(brt.readLine(), null);
//
//    }
    @Test
    public void testThatFileBackedTaskManagerAddTasksDifferentTypesAndCanFindThemById() {// проверьте, что FileBackedTaskManagerTest действительно добавляет задачи разного типа и может найти их по id;
        // Добавляем задачи в менеджер задач
        taskManager.addTask(task);
        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        // Проверяем, что задачи успешно добавлены и найдены по их id
        assertEquals(task.getId(), 1);
        assertEquals(epic.getId(), 2);
        assertEquals(subtask.getId(), 3);
    }

    @Test//+
    public void testThatInstancesClassTaskEqualIfTheirIdSimilar() {// проверьте, что экземпляры класса Task равны друг другу, если равен их id;
        task.setTypeTES(TypeTES.TASK);
        task.setId(1);

        Task taskTWO = new Task("Задача 2", "Цельная", Duration.ofMinutes(30), LocalDateTime.of(2024, Month.JUNE, 25, 18, 0));
        taskTWO.setTypeTES(TypeTES.TASK);
        taskTWO.setId(1);

        assertEquals(task, taskTWO);
    }

    @Test//+
    public void testThatHeirsOfTaskEqualIfTheirIdEqual() {// проверьте, что наследники класса Task равны друг другу, если равен их id;
        task.setTypeTES(TypeTES.TASK);
        task.setId(1);

        epic.setTypeTES(TypeTES.EPIC);
        epic.setId(1);

        assertEquals(task.getId(), epic.getId());
    }

    @Test//+
    public void testThatSubtaskCantMakeSelfEpic() {// проверьте, что объект Subtask нельзя сделать своим же эпиком;;
        subtask.setEpicId(subtask.getId()); // Устанавливаем id подзадачи в качестве id эпика

        // Убеждаемся, что подзадача не была добавлена в список подзадач
        assertFalse(taskManager.getSubtasks().contains(subtask));
    }

    @Test//+
    public void testThatUtilityClassAlwaysReturnsInitializedAndReadyUseInstancesOfManagers() {// убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;;
        TaskManager managerTM = Managers.getDefault();//экземпляр утилитарного класса. Метод getDefault() возвращает объект типа TaskManager(интрефейс InMemoryTaskManager)
        //Managers.getDefault() - это коробка (один из менеджеров), а TaskManager - это инструкция к ней)
        HistoryManager managerHM = Managers.getDefaultHistory();

        assertNotNull("Утилитарный класс TaskManager проинициализирован", managerTM.toString());
        //assertNotNull("Утилитарный класс HistoryManager проинициализирован", managerHM);
    }


}


