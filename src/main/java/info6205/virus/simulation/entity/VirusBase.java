package info6205.virus.simulation.entity;

import java.util.UUID;

public abstract class VirusBase {
    protected String id;
    protected double kFactor;
    protected double rFactor;

    protected PeopleBase peopleBase;

    abstract public VirusBase generate();

    public VirusBase(double kFactor, double rFactor) {
        this.kFactor = kFactor;
        this.rFactor = rFactor;
        id= UUID.randomUUID().toString();
    }

    //    peopel relative operation---------------
    public PeopleBase getPeopleBase() {
        return peopleBase;
    }

    public void setPeopleBase(PeopleBase peopleBase) {
        this.peopleBase = peopleBase;
    }
    public void leaveFromPeople(){
        peopleBase = null;
    }

    //    set base virus info------------------
    public double getkFactor() {
        return kFactor;
    }

    public void setkFactor(double kFactor) {
        this.kFactor = kFactor;
    }

    public double getrFactor() {
        return rFactor;
    }

    public void setrFactor(double rFactor) {
        this.rFactor = rFactor;
    }
}
