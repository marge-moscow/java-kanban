public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task1 = new Task(
                manager.generateId(),
                "Купить корм для рыбок.",
                "Корм для рыб Зоомир Гурман-3 30г."
        );

        manager.addItem(task1);

        Task task2 = new Task(
                manager.generateId(),
                "Договориться с директором 'Астры' о встрече.",
                "Телефон компании +74955634788. Директор - Пётр Анатольевич."
        );

        manager.addItem(task2);

        Epic epic1 = new Epic(
                manager.generateId(),
                "Навести порядок в кладовке.",
                "Разобрать все вещи и организовать систему хранения."
        );

        manager.addItem(epic1);

        Subtask subtask1 = new Subtask(
                manager.generateId(),
                "Разобрать вещи в кладовке.",
                "Выкинуть всё, что не надевал в течение 2 лет.",
                epic1.getId()
        );

        manager.addItem(subtask1);

        Subtask subtask2 = new Subtask(
                manager.generateId(),
                "Организовать систему хранения.",
                "Купить доски для полок и вакуумные пакеты.",
                epic1.getId()
        );

        manager.addItem(subtask2);

        Epic epic2 = new Epic(
                manager.generateId(),
                "Организовать отгрузку товара в Тверь.",
                "Отгрузка запланирована на 12:00. До этого времени необходимо подготовить документы."
        );

        manager.addItem(epic2);

        Subtask subtask3 = new Subtask(
                manager.generateId(),
                "Подписать товарные накладные.",
                "Подписать все наклыдные на товар, который отправляется в Тверь.",
                epic2.getId()
        );

        manager.addItem(subtask3);

        subtask1 = new Subtask (
                subtask1.getId(),
                "Разобрать вещи в кладовке.",
                "Выкинуть всё, что не надевал в течение 5 лет.",
                subtask1.getEpicId()
        );
        manager.updateItem(subtask1, "DONE");


        System.out.println(task1);
        System.out.println(task2);
        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println(subtask3);

        manager.getSubtasksByEpic(3);
        manager.getEpicById(3);
        manager.deleteTaskById(2);
        manager.getTasks();

        manager.deleteEpicById(3);
        manager.getEpics();
        manager.getSubtasks();




    }
}