package info6205.virus.simulation.entity;

import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;
import info6205.virus.simulation.task.TaskBase;

import java.util.*;

public abstract class PeopleBase {
    protected String id;
    protected GridElement location;
    protected double x;
    protected double y;
    protected List<VirusBase> virus;
    protected MaskBase maskBase;
    protected Queue<TaskBase> tasks;
    protected SimulationMap map;

    public PeopleBase() {
        id= UUID.randomUUID().toString();
        virus =new ArrayList<>();
        tasks=new LinkedList<>();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public RoadArea getCurrentRoadArea(){
        for (AreaBase base:getLocation().getAreas()){
            if(base instanceof RoadArea){
                return (RoadArea) base;
            }
        }
        return null;
    }

    public SimulationMap getMap() {
        return map;
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

    protected void removeVirus(VirusBase base){
        int i=0;
        for (VirusBase virusBase:virus){
            if(virusBase.getId().equals(base.getId())){
                virus.remove(i);
                return;
            }
            i++;
        }
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

    private void setLocation(double x, double y) throws Exception {
        setX(x);
        setY(y);
        GridElement location = map.getGridElimentByXY(x,y);
        this.location = location;
        location.addPeople(this);
    }

    // Move to next location
    public void moveToNextLocation(double x, double y) throws Exception {
        GridElement currentLocation=getLocation();
        if(currentLocation!=null){
            currentLocation.removePeople(this);
        }
        setLocation(x,y);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeopleBase that = (PeopleBase) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
