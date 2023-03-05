import Tasks.*;
import Manager.*;
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task();
        task1.setTaskName("Задача 1");
        task1.setTaskDescription("Описание задачи 1");
        task1.setTaskStatus(TaskStatus.NEW);
        taskManager.createTask(task1);
        System.out.println(task1);

        Task task2 = new Task();
        task2.setTaskName("Задача 2");
        task2.setTaskDescription("Описание задачи 2");
        task2.setTaskStatus(TaskStatus.NEW);
        taskManager.createTask(task2);
        System.out.println(task2);

        System.out.println("Список всех задач: ");
        System.out.println(taskManager.findAllTasks());

        Epic epic1 = new Epic();
        epic1.setTaskName("Эпик 1");
        epic1.setTaskDescription("Описание Эпик 1");
        taskManager.createEpic(epic1);

        Epic epic2 = new Epic();
        epic2.setTaskName("Эпик 2");
        epic2.setTaskDescription("Описание Эпик 2");
        taskManager.createEpic(epic2);

        Subtask firstSubtaskEpic1 = new Subtask();
        firstSubtaskEpic1.setEpicId(3);
        firstSubtaskEpic1.setTaskName("Подзадача номер 1 Эпика 1");
        firstSubtaskEpic1.setTaskDescription("Описание подзадачи номера 1 Эпика 1");
        firstSubtaskEpic1.setTaskStatus(TaskStatus.NEW);
        taskManager.createSubtask(firstSubtaskEpic1);

        Subtask secondSubtaskEpic1 = new Subtask();
        secondSubtaskEpic1.setEpicId(3);
        secondSubtaskEpic1.setTaskName("Подзадача номер 2 Эпика 1");
        secondSubtaskEpic1.setTaskDescription("Описание подзадачи номера 2 Эпика 1");
        secondSubtaskEpic1.setTaskStatus(TaskStatus.NEW);
        taskManager.createSubtask(secondSubtaskEpic1);

//        Subtask thirdSubtaskEpic1 = new Subtask();
//        thirdSubtaskEpic1.setEpicId(3);
//        thirdSubtaskEpic1.setTaskName("Подзадача номер 3 Эпика 1");
//        thirdSubtaskEpic1.setTaskDescription("Описание подзадачи номера 3 Эпика 1");
//        thirdSubtaskEpic1.setTaskStatus(TaskStatus.NEW);
//        taskManager.createSubtask(thirdSubtaskEpic1);

        Subtask firstSubtaskEpic2 = new Subtask();
        firstSubtaskEpic2.setEpicId(4);
        firstSubtaskEpic2.setTaskName("Подзадача номер 1 Эпика 2");
        firstSubtaskEpic2.setTaskDescription("Описание подзадачи номера 1 Эпика 2");
        firstSubtaskEpic2.setTaskStatus(TaskStatus.NEW);
        taskManager.createSubtask(firstSubtaskEpic2);

//        Subtask secondSubtaskEpic2 = new Subtask();
//        secondSubtaskEpic2.setEpicId(4);
//        secondSubtaskEpic2.setTaskName("Подзадача номер 2 Эпика 2");
//        secondSubtaskEpic2.setTaskDescription("Описание подзадачи номера 2 Эпика 2");
//        secondSubtaskEpic2.setTaskStatus(TaskStatus.NEW);
//        taskManager.createSubtask(secondSubtaskEpic2);

        System.out.println("Список всех подзадач: ");
        System.out.println(taskManager.findAllSubtasks());
        System.out.println("Список всех Эпиков: ");
        System.out.println(taskManager.findAllEpics());

        System.out.println("ВСЁ РАБОТАЕТ ????");

        firstSubtaskEpic1.setEpicId(3);
        firstSubtaskEpic1.setTaskName("Подзадача номер 1 Эпика 1");
        firstSubtaskEpic1.setTaskDescription("О нет! Как же я сильно изменился!");
        firstSubtaskEpic1.setTaskStatus(TaskStatus.DONE);
        taskManager.updateSubtask(firstSubtaskEpic1);

        System.out.println("Список всех подзадач: ");
        System.out.println(taskManager.findAllSubtasks());
        System.out.println("Список всех Эпиков: ");
        System.out.println(taskManager.findAllEpics());

        taskManager.deleteSubtaskById(5);

        System.out.println("ВСЁ РАБОТАЕТ ????");

        System.out.println("Список всех подзадач: ");
        System.out.println(taskManager.findAllSubtasks());
        System.out.println("Список всех Эпиков: ");
        System.out.println(taskManager.findAllEpics());

    }
}
