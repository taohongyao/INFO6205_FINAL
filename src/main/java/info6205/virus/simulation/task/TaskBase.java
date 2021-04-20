package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.PeopleBase;

import java.util.UUID;

public abstract class TaskBase {
    protected String name;
    protected String id;
    protected boolean isFinished;

    public TaskBase() {
        id= UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public abstract void executeTask(PeopleBase peopleBase);

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void finish(){
        setFinished(true);
    }
}
