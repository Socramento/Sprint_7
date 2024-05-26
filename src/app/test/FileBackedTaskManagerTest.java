//package app.test;
//
//import app.enums.HistoryManager;
//import app.enums.TypeTES;
//import app.history.FileBackedTaskManager;
//import app.intefaces.TaskManager;
//import app.taskmanager.Managers;
//import app.tasks.Epic;
//import app.tasks.Subtask;
//import app.tasks.Task;
//import org.junit.jupiter.api.Test;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class FileBackedTaskManagerTest {
//
//    protected FileBackedTaskManager fileBackedTaskManager;
//
//    protected Task task;
//    protected Epic epic;
//    protected Subtask subtask;
//    File testFile;
//    @Test
//    public void setUp() {
//
//        fileBackedTaskManager = new FileBackedTaskManager();
//
//        task = new Task("Задача 1", "Описание задачи 1");
//        epic = new Epic("Эпик 1", "Описание эпика 1");
//        subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic);
//    }
//
//    @Test
//    public void testSaveAndLoadEmptyFile() throws IOException {// сохранение и загрузка пустого файла;
//
//        testFile = fileBackedTaskManager.text;
//        BufferedReader brt = new BufferedReader(new FileReader(testFile));
//        assertEquals(brt.readLine(), null);
//
//    }
//    @Test
//    public void testThatFileBackedTaskManagerTestAddTasksDifferentTypesAndCanFindThemById() {// проверьте, что FileBackedTaskManagerTest действительно добавляет задачи разного типа и может найти их по id;
//        // Добавляем задачи в менеджер задач
//        fileBackedTaskManager.addTask(task);
//        fileBackedTaskManager.addEpic(epic);
//        fileBackedTaskManager.addSubtask(subtask);
//
//        // Проверяем, что задачи успешно добавлены и найдены по их id
//        assertEquals(task.getId(), 1);
//        assertEquals(epic.getId(), 2);
//        assertEquals(subtask.getId(), 3);
//    }
//
////    @Test
////    void testSave() {
////
////
////    }
////
////    @Test
////    void historyFromString() {
////    }
////
////    @Test
////    void fromString() {
////    }
////
////    @Test
////    void historyToString() {
////    }
////
////    @Test
////    void loadFromFile() {
////    }
//
//
//    @Test//+
//    public void testThatInstancesClassTaskEqualIfTheirIdSimilar() {// проверьте, что экземпляры класса Task равны друг другу, если равен их id;
//        task.setTypeTES(TypeTES.TASK);
//        task.setId(1);
//
//        Task taskTWO = new Task("Задача 2", "Описание задачи 2");
//        taskTWO.setTypeTES(TypeTES.TASK);
//        taskTWO.setId(1);
//
//        assertEquals(task, taskTWO);
//    }
//
//    @Test//+
//    public void testThatHeirsOfTaskEqualIfTheirIdEqual() {// проверьте, что наследники класса Task равны друг другу, если равен их id;
//        task.setTypeTES(TypeTES.TASK);
//        task.setId(1);
//
//        epic.setTypeTES(TypeTES.EPIC);
//        epic.setId(1);
//
//        assertEquals(task.getId(), epic.getId());
//    }
//
//    @Test//+
//    public void testThatSubtaskCantMakeSelfEpic() {// проверьте, что объект Subtask нельзя сделать своим же эпиком;;
//        subtask.setEpicId(subtask.getId()); // Устанавливаем id подзадачи в качестве id эпика
//
//        // Убеждаемся, что подзадача не была добавлена в список подзадач
//        assertFalse(fileBackedTaskManager.getSubtasks().contains(subtask));
//    }
//
//    @Test//+
//    public void testThatUtilityClassAlwaysReturnsInitializedAndReadyUseInstancesOfManagers() {// убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;;
//        TaskManager managerTM = Managers.getDefault();//экземпляр утилитарного класса. Метод getDefault() возвращает объект типа TaskManager(интрефейс InMemoryTaskManager)
//        //Managers.getDefault() - это коробка (один из менеджеров), а TaskManager - это инструкция к ней)
//        HistoryManager managerHM = Managers.getDefaultHistory();
//
//        assertNotNull("Утилитарный класс TaskManager проинициализирован", managerTM.toString());
//        //assertNotNull("Утилитарный класс HistoryManager проинициализирован", managerHM);
//    }
//
//
//
//
//
//
////    @Test
////    public void testSaveSeveralTasks() throws IOException {// сохранение нескольких задач;
////        FileWriter fwt = new FileWriter(testFile);
////        fwt.write(fileBackedTaskManager.fromString(task.toString()).getId());
////        //System.out.println(testFile);
////        fwt.close();
////    }
//
//}
//
//
