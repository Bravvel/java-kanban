package Manager;

import Tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
public class TaskManager {
    private HashMap<Integer, Task> tasksById = new HashMap<>();
    private HashMap<Integer, Epic> epicsById = new HashMap<>();
    private HashMap<Integer, Subtask> subtasksById = new HashMap<>();

    //переменная для хранения Id задач, подзадач, эпиков
    private Integer nextId = 1;

    //получить задачу по id
    public Task getTaskById(Integer id) {
        if (tasksById.get(id) != null) {
            return tasksById.get(id);
        } else {
            System.out.println("Задачи с таким ID не существует");
            return null;
        }
    }

    // получить подзадачу по id
    public Subtask getSubtaskById(Integer id) {
        if(subtasksById.get(id) != null) {
            return subtasksById.get(id);
        } else {
            System.out.println("Подзадачи с таким ID не существует");
            return null;
        }
    }

    // получить эпик по id
    public Epic getEpicById(Integer id) {
        if(epicsById.get(id) != null) {
            return epicsById.get(id);
        }else {
            System.out.println("Эпика с таким ID не существует");
            return null;
        }
    }

    //создать задачу
    public void createTask(Task task) {
        task.setTaskId(nextId++);
        tasksById.put(task.getTaskId(), task);
    }

    //создать подзадачу
    public void createSubtask(Subtask subtask) {
        Integer subtaskId = nextId;
        subtask.setTaskId(nextId++);
        Integer tmpEpicId = subtask.getEpicId();
        ArrayList<Integer> tmpSubtasksIds = new ArrayList<>();
        subtasksById.put(subtask.getTaskId(), subtask);
        for(Subtask tmpSubtask : subtasksById.values()){
            if(tmpSubtask.getEpicId() == tmpEpicId){
                tmpSubtasksIds.add(tmpSubtask.getTaskId());
            }
        }
        epicsById.get(tmpEpicId).setSubtasksIds(tmpSubtasksIds);
        updateEpicStatus(subtask.getEpicId());
    }

    // создать эпик
    public void createEpic(Epic epic) {
        epic.setTaskId(nextId++);
        epic.setTaskStatus(TaskStatus.NEW);
        epicsById.put(epic.getTaskId(), epic);
    }

    //удалить задачу по id
    public void deleteTaskById(Integer id) {
        if(tasksById.get(id) != null) {
            tasksById.remove(id);
        } else {
            System.out.println("Задачи с таким ID не существует");
        }
    }

    //удалить подзадачу по id
    public void deleteSubtaskById(Integer id) {
        if(subtasksById.get(id) != null) {
            Integer epicId = subtasksById.get(id).getEpicId();
            subtasksById.remove(id);
            epicsById.get(epicId).subtasksIds.remove(id);
            updateEpicStatus(epicId);
        } else {
            System.out.println("Подзадачи с таким ID не существует");
        }
    }

    //удалить эпик по id
    public void deleteEpicById(Integer id) {
        if(epicsById.get(id) != null){
            for(Integer subtaskId : epicsById.get(id).subtasksIds) {
                subtasksById.remove(subtaskId); // удаление всех подзадач удаляемого эпика
            }
            epicsById.remove(id);
        } else {
            System.out.println("Эпика с таким ID не существует");
        }
    }

    //удаление всех задач
    public void deleteAllTasks() {
        tasksById.clear();
    }

    //удаление всех подзадач
    public void deleteAllSubtasks() {
        subtasksById.clear();
        for(Integer epicId : epicsById.keySet()) {
            epicsById.get(epicId).subtasksIds.clear(); // удаление из всех эпиков id всех подзадач эпика, т.к. их больше не существует
        }
        for(Integer epicId : epicsById.keySet()) {
           updateEpicStatus(epicId);
        }
    }

    //удаление всех эпиков
    public void deleteAllEpics() {
        epicsById.clear();
        deleteAllSubtasks(); // удаление всех подзадач, так как эпиков больше нет
    }

    //обновление значения задачи
    public void updateTask(Task task) {
        tasksById.put(task.getTaskId(), task);
    }

    //обновление значения подзадачи
    public void updateSubtask(Subtask subtask) {
        subtasksById.put(subtask.getTaskId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    //обновление эпика
    public void updateEpic(Epic epic) {
        epicsById.put(epic.getTaskId(), epic);
    }

    //получение списка всех задач
    public ArrayList<Task> findAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>(tasksById.values());
        return allTasks;
    }

    //получение списка всех подзадач
    public ArrayList<Subtask> findAllSubtasks() {
        ArrayList<Subtask> allSubtasks = new ArrayList<>(subtasksById.values());
        return allSubtasks;
    }

    // получение списка всех эпиков
    public ArrayList<Epic> findAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>(epicsById.values());
        return allEpics;
    }

    //получение списка всех подзадач определенного эпика
    public ArrayList<Subtask> getAllSubtasksOfEpic(Epic epic){
        ArrayList<Integer> allSubtasksIdsOfEpic = new ArrayList<>(epic.getSubtasksIds());
        ArrayList<Subtask> allSubtasksOfEpic = new ArrayList<>();
        Integer needId = epic.getTaskId();
        for (int i = 0; i < allSubtasksIdsOfEpic.size(); i++) {
                allSubtasksOfEpic.add(subtasksById.get(allSubtasksIdsOfEpic.get(i)));
        }
        return allSubtasksOfEpic;
    }

    //обновление статуса эпика
    public void updateEpicStatus(Integer epicId){
        ArrayList<Integer> allSubtasksIdsOfEpic = new ArrayList<>(epicsById.get(epicId).getSubtasksIds());
        ArrayList<Subtask> allSubtasksOfEpic = new ArrayList<>();
        int cntNew = 0;
        int cntDone = 0;
        for(int i = 0; i < allSubtasksIdsOfEpic.size(); i++){
            allSubtasksOfEpic.add(subtasksById.get(allSubtasksIdsOfEpic.get(i)));
        }
        for(Subtask tmpSubtask : allSubtasksOfEpic){
            if(tmpSubtask.getTaskStatus().equals(TaskStatus.NEW)) {
                cntNew++;
            } else if(tmpSubtask.getTaskStatus().equals(TaskStatus.DONE)){
                cntDone++;
            }
        }
        if(cntNew ==  allSubtasksOfEpic.size()){
            epicsById.get(epicId).setTaskStatus(TaskStatus.NEW);
        } else if(cntDone == allSubtasksOfEpic.size()){
            epicsById.get(epicId).setTaskStatus(TaskStatus.DONE);
        } else{
            epicsById.get(epicId).setTaskStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
