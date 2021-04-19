package info6205.virus.simulation.entity;

import info6205.virus.simulation.map.GridElement;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public abstract class VirusBase {
    protected String id;
    protected double kFactor;
    protected double rFactor;
    protected int potentialDay;
    protected static Random random;

    protected PeopleBase peopleBase;
    protected GridElement location;
    protected int aliveDayWithoutPeople;

    abstract public VirusBase generate();
    abstract public List<VirusBase> findCarrierAndInfect();


    public VirusBase(double kFactor, double rFactor,int potentialDay,int aliveDayWithoutPeople) {
        this.kFactor = kFactor;
        this.rFactor = rFactor;
        this.potentialDay=potentialDay;
        this.aliveDayWithoutPeople=aliveDayWithoutPeople;
        random=new Random();
        id= UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public int getPotentialDay() {
        return potentialDay;
    }

    public static Random getRandom() {
        return random;
    }


    public GridElement getLocation() {
        return location;
    }

    public void setLocation(GridElement location) {
        this.location = location;
    }

    //    peopel relative operation---------------
    public PeopleBase getPeopleBase() {
        return peopleBase;
    }

    public void setPeopleBase(PeopleBase peopleBase) {
        this.peopleBase = peopleBase;
    }

    public void leaveFromPeople(){
        peopleBase.removeVirus(this);
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

    public void minusPotentialDay(){
        if(potentialDay!=0) potentialDay--;
    }

    public void minusAliveDay(){
        if(aliveDayWithoutPeople!=0) aliveDayWithoutPeople--;
    }

    public boolean isAlive(){
        if(aliveDayWithoutPeople==0&&peopleBase!=null) return false;
        return true;
    }

    public boolean isHaveSymptom(){
        if(peopleBase!=null&&potentialDay==0) return true;
        return false;
    }

    public boolean haveInfectedInPeopleBody(){
        if(peopleBase==null) return false;
        return true;
    }

    public boolean haveAttachPlace(){
        if(location!=null) return true;
        return false;
    }

    public void infectPeople(PeopleBase peopleBase){
        setPeopleBase(peopleBase);
        peopleBase.infectVirus(this);
    }

    public void attachInAbsolutePlace(GridElement gridElement){
        setLocation(gridElement);
        gridElement.attachVirus(this);
    }

    public void detachInAbsolutePlace(){
        getLocation().removeVirus(this);
        setLocation(null);
    }

    public void cleanVirus(){
        if(haveAttachPlace()){
            detachInAbsolutePlace();
        }else {
            leaveFromPeople();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VirusBase virusBase = (VirusBase) o;
        return Objects.equals(id, virusBase.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
