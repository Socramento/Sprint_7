package app.test;

import app.intefaces.TaskManager;
import app.tasks.Epic;
import app.tasks.Subtask;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class TaskManagerTest<T extends TaskManager> {

    protected T taskManager;

    @Test
    void testSubtaskHasEpic() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic, Duration.ofMinutes(3), LocalDateTime.of(2024, Month.JUNE, 25, 18, 10));

        taskManager.addEpic(epic);
        taskManager.addSubtask(subtask);

        assertEquals(epic, subtask.getEpicId(), "У подзадачи нет Эпика");
    }
}