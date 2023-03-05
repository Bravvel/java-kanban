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
        return tasksById.get(id);
    }

    // получить подзадачу по id
    public Subtask getSubtaskById(Integer id) {
        return subtasksById.get(id);
    }

    // получить эпик по id
    public Epic getEpicById(Integer id) {
        return epicsById.get(id);
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
//        epicsById.get(tmpEpicId).subtasksIds.add(subtaskId);
//        ArrayList<Integer> tmpSubtasksIds = new ArrayList<>(epicsById.get(tmpEpicId).getSubtasksIds());
//        tmpSubtasksIds.add(subtaskId);
//        epicsById.get(tmpEpicId).setSubtasksIds(tmpSubtasksIds);
        ArrayList<Integer> tmpSubtasksIds = new ArrayList<>();
        subtasksById.put(subtask.getTaskId(), subtask);
        for(Subtask tmpSubtask : subtasksById.values()){
            if(tmpSubtask.getEpicId() == tmpEpicId){
                tmpSubtasksIds.add(tmpSubtask.getTaskId());
            }
        }
        epicsById.get(tmpEpicId).setSubtasksIds(tmpSubtasksIds);
        updateEpicStatus(subtask);
    }

    // создать эпик
    public void createEpic(Epic epic) {
        epic.setTaskId(nextId++);
        epic.setTaskStatus(TaskStatus.NEW);
        epicsById.put(epic.getTaskId(), epic);
    }

    //удалить задачу по id
    public void deleteTaskById(Integer id) {
        tasksById.remove(id);
    }

    //удалить подзадачу по id
    public void deleteSubtaskById(Integer id) {
        subtasksById.remove(id);
    }

    //удалить эпик по id
    public void deleteEpicById(Integer id) {
        epicsById.remove(id);
    }

    //удаление всех задач
    public void deleteAllTasks() {
        tasksById.clear();
    }

    //удаление всех подзадач
    public void deleteAllSubtasks() {
        subtasksById.clear();
    }

    //удаление всех эпиков
    public void deleteAllEpics() {
        epicsById.clear();
    }

    //обновление значения задачи
    public void updateTask(Task task) {
        tasksById.put(task.getTaskId(), task);
    }

    //обновление значения подзадачи
    public void updateSubtask(Subtask subtask) {
        subtasksById.put(subtask.getTaskId(), subtask);
        updateEpicStatus(subtask);
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
//            if(needId == subtasksById.get(allSubtasksIdsOfEpic.get(i)).getTaskId()){
                allSubtasksOfEpic.add(subtasksById.get(allSubtasksIdsOfEpic.get(i)));
//            }
        }
        return allSubtasksOfEpic;
    }

    //обновление статуса эпика
    public void updateEpicStatus(Subtask subtask){
        ArrayList<Integer> allSubtasksIdsOfEpic = new ArrayList<>(epicsById.get(subtask.getEpicId()).getSubtasksIds());
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
            epicsById.get(subtask.getEpicId()).setTaskStatus(TaskStatus.NEW);
        } else if(cntDone == allSubtasksOfEpic.size()){
            epicsById.get(subtask.getEpicId()).setTaskStatus(TaskStatus.DONE);
        } else{
            epicsById.get(subtask.getEpicId()).setTaskStatus(TaskStatus.IN_PROGRESS);
        }
    }
}
