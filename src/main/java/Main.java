/*
import exceptions.ManagerSaveException;
import model.*;
import managers.Managers;
import managers.TaskManger;

import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws ManagerSaveException {

        TaskManger taskManager = Managers.getDefault();

        Task task1 = new Task(
                0,
                "Task1",
                "Task1Description",
                LocalDateTime.of(2022,10,07,23,00),
                Duration.ofMinutes(15)
        );

        taskManager.addItem(task1);
        taskManager.addItem(task1);
        taskManager.addItem(task1);
        taskManager.addItem(task1);

        Task task2 = new Task(
                0,
                "Task2",
                "Task2Description",
                LocalDateTime.of(2022,10,05,18,00),
                Duration.ofMinutes(15)
        );

        taskManager.addItem(task2);

        Epic epic1 = new Epic(
                0,
                "Epic1",
                "Epic1Description"
        );

        taskManager.addItem(epic1);
        taskManager.addItem((Task)epic1);


        Subtask subtask1 = new Subtask(
                0,
                "Subtask1",
                "Subtask1Description",
                LocalDateTime.of(2022,10,04,07,00),
                Duration.ofMinutes(15),
                3
        );

        taskManager.addItem(subtask1);



        Subtask subtask2 = new Subtask(
                0,
                "Subtask2",
                "Subtask2Description",
                Duration.ofMinutes(15),
                3
        );

        taskManager.addItem(subtask2);

        subtask1 = new Subtask(
                subtask1.getId(),
                "Разобрать вещи в кладовке.",
                "Выкинуть всё, что не надевал в течение 5 лет.",
                LocalDateTime.of(2022,10,25,07,00),
                Duration.ofMinutes(15),
                epic1.getId());

        taskManager.updateItem(subtask1, TaskStatus.DONE);

        */
/*Epic epic2 = new Epic(
                0,
                "Организовать отгрузку товара в Тверь.",
                "Отгрузка запланирована на 12:00. До этого времени необходимо подготовить документы.",
                TaskType.EPIC
        );

        taskManager.addItem(epic2);

        Subtask subtask3 = new Subtask(
                0,
                "Подписать товарные накладные.",
                "Подписать все наклыдные на товар, который отправляется в Тверь.",
                TaskType.SUBTASK,
                epic1.getId()
        );

        taskManager.addItem(subtask3);

        subtask1 = new Subtask(
                subtask1.getId(),
                "Разобрать вещи в кладовке.",
                "Выкинуть всё, что не надевал в течение 5 лет.",
                TaskType.SUBTASK,
                epic1.getId());

        taskManager.updateItem(subtask1, TaskStatus.DONE);*//*



        */
/*taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        //taskManager.getSubtaskById(7);
        taskManager.getEpicById(3);
        taskManager.getSubtaskById(4);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(4);
        taskManager.getEpicById(3);
        taskManager.getSubtaskById(5);
        taskManager.getTaskById(1);
        //taskManager.getEpicById(6);

        System.out.println(taskManager.getHistory());*//*


     */
/*   taskManager.deleteTaskById(1);
        taskManager.deleteSubtaskById(5);

        System.out.println(taskManager.getHistory());

        System.out.println(epic1);

        taskManager.deleteEpicById(3);
        System.out.println(taskManager.getHistory());

        taskManager.getSubtaskById(4);
        taskManager.getEpicById(6);
        System.out.println(taskManager.getHistory());

        taskManager.deleteSubtasks();
        System.out.println(taskManager.getHistory());
*//*


        System.out.println(taskManager.getPrioritizedTasks());





*/
/*

      taskManager.deleteTaskById(66);
        taskManager.deleteEpicById(66);
        taskManager.getSubtaskById(89);
        System.out.println(taskManager.getTaskById(1));*//*








    }
}*/
