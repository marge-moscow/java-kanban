import managers.Managers;
import managers.TaskManger;
import taskTypes.Epic;
import taskTypes.Subtask;
import taskTypes.Task;
import annex.TaskStatus;

public class Main {
    public static void main(String[] args) {

        TaskManger taskManager = Managers.getDefault();

        Task task1 = new Task(
                0,
                "Купить корм для рыбок.",
                "Корм для рыб Зоомир Гурман-3 30г."
        );

        taskManager.addItem(task1);
        taskManager.addItem(task1);
        taskManager.addItem(task1);
        taskManager.addItem(task1);

        Task task2 = new Task(
                0,
                "Договориться с директором 'Астры' о встрече.",
                "Телефон компании +74955634788. Директор - Пётр Анатольевич."
        );

        taskManager.addItem(task2);

        Epic epic1 = new Epic(
                0,
                "Навести порядок в кладовке.",
                "Разобрать все вещи и организовать систему хранения."
        );

        taskManager.addItem(epic1);
        taskManager.addItem((Task)epic1);


        Subtask subtask1 = new Subtask(
                0,
                "Разобрать вещи в кладовке.",
                "Выкинуть всё, что не надевал в течение 2 лет.",
                epic1.getId()
        );

        taskManager.addItem(subtask1);

        Subtask subtask2 = new Subtask(
                0,
                "Организовать систему хранения.",
                "Купить доски для полок и вакуумные пакеты.",
                epic1.getId()
        );

        taskManager.addItem(subtask2);

        Epic epic2 = new Epic(
                0,
                "Организовать отгрузку товара в Тверь.",
                "Отгрузка запланирована на 12:00. До этого времени необходимо подготовить документы."
        );

        taskManager.addItem(epic2);

        Subtask subtask3 = new Subtask(
                0,
                "Подписать товарные накладные.",
                "Подписать все наклыдные на товар, который отправляется в Тверь.",
                epic2.getId()
        );

        taskManager.addItem(subtask3);

        subtask1 = new Subtask(
                subtask1.getId(),
                "Разобрать вещи в кладовке.",
                "Выкинуть всё, что не надевал в течение 5 лет.",
                epic1.getId());

        taskManager.updateItem(subtask1, TaskStatus.DONE);


//        System.out.println(task1);
//        System.out.println(task2);
//        System.out.println(epic1);
//
//        System.out.println(epic2);
//        System.out.println(subtask1);
//        System.out.println(subtask2);
//        System.out.println(subtask3);
//
//        System.out.println(manager.getSubtasksByEpic(3));
//        System.out.println(manager.getEpicById(6));
//        manager.deleteTaskById(2);
//        manager.getTasks();

      // manager.deleteEpicById(3);
        //taskManager.getEpics();
       // taskManager.getSubtasks();

        //taskManager.getTasks();

        System.out.println(taskManager.getTaskById(1));
        //taskManager.getHistory();
        taskManager.getTaskById(2);
        //taskManager.getHistory();
        taskManager.getEpicById(3);
        //taskManager.getHistory();
        taskManager.getSubtaskById(4);

        taskManager.getSubtaskById(5);
        taskManager.getEpicById(6);

        taskManager.getSubtaskById(7);
        taskManager.getTaskById(1);
        taskManager.getSubtaskById(4);
        taskManager.getEpicById(6);
        taskManager.getEpicById(3);
        taskManager.getSubtaskById(7);
        taskManager.getHistory();




    }
}