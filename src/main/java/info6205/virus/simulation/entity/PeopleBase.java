package info6205.virus.simulation.entity;

import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.task.TaskBase;

import java.util.*;

public class PeopleBase {
    protected String id;
    protected GridElement location;
    protected List<VirusBase> virus;
    protected MaskBase maskBase;
    protected Queue<TaskBase> tasks;

    public PeopleBase() {
        id= UUID.randomUUID().toString();
        virus =new ArrayList<>();
        tasks=new LinkedList<>();
    }


    //  Mask Operation-------------------
    public MaskBase getMaskBase() {
        return maskBase;
    }

    public void setMaskBase(MaskBase maskBase) {
        this.maskBase = maskBase;
    }

    //  Virus Operation----------------
    public List<VirusBase> getVirus() {
        return new ArrayList<>(virus);
    }

    public void infectVirus(VirusBase base){
        virus.add(base);
    }

    public void cleanVirus(){
        virus.clear();
    }

    //  Location operation--------------
    public GridElement getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(GridElement location) {
        this.location = location;
    }

    //   Task Operation--------------------
    public void cleanAllTasks(){
        tasks.clear();
    }

    public int currentTaskSize(){
        return tasks.size();
    }

    public void addTask(TaskBase taskBase){
        tasks.add(taskBase);
    }

    public TaskBase getCurrentTask() {
        return tasks.peek();
    }

    public void FinishCurrentTask(){
        tasks.remove();
    }
}
