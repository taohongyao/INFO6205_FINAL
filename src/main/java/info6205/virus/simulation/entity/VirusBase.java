package info6205.virus.simulation.entity;

import info6205.virus.simulation.map.GridElement;

import java.util.*;

public abstract class VirusBase {
    protected static int rateScala=1000;
    protected static double dayActiveTimes=60*60*24;
    protected String id;
    protected double infectRate;
    protected int potentialDay;
    protected int aliveDay;
    protected double vaccineEfficacy;
    protected double deadRate;
    protected static Random random;
    static {
        random=new Random();
    }

    protected PeopleBase peopleBase;
    protected GridElement location;
    protected int timeToDeadDayWithoutPeople;

    protected Map<PeopleBase,Integer> contactRecord;
    protected Set<PeopleBase> infectRecord;

    abstract public VirusBase generate();
    abstract public List<VirusBase> findCarrierAndInfect();


    public VirusBase(double infectRate,int potentialDay,int timeToDeadDayWithoutPeople) {
        this.infectRate = infectRate;
        this.potentialDay=potentialDay;
        this.timeToDeadDayWithoutPeople=timeToDeadDayWithoutPeople;
        id= UUID.randomUUID().toString();
        infectRecord=new HashSet<>();
        contactRecord=new HashMap<>();
        aliveDay=0;
    }

    public static double getDayActiveTimes() {
        return dayActiveTimes;
    }

    public static void setDayActiveTimes(double dayActiveTimes) {
        VirusBase.dayActiveTimes = dayActiveTimes;
    }

    public static int getRateScala() {
        return rateScala;
    }

    public static void setRateScala(int rateScala) {
        VirusBase.rateScala = rateScala;
    }

    public double getVaccineEfficacy() {
        return vaccineEfficacy;
    }

    public void setVaccineEfficacy(double vaccineEfficacy) {
        this.vaccineEfficacy = vaccineEfficacy;
    }

    public double getDeadRate() {
        return deadRate;
    }

    public void setDeadRate(double deadRate) {
        this.deadRate = deadRate;
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


    public double getInfectRate() {
        return infectRate;
    }

    public void setInfectRate(double infectRate) {
        this.infectRate = infectRate;
    }

    public void minusPotentialDay(){
        if(potentialDay!=0&& peopleBase!=null) potentialDay--;
    }
    public void minusAttachedVirusAliveDay(){
        if(haveAttachPlace()&&timeToDeadDayWithoutPeople!=0){
            timeToDeadDayWithoutPeople--;
        }
    }
    public void aliveDayIncreaseOneDay(){
        aliveDay++;
    }


    public int getTimeToDeadDayWithoutPeople() {
        return timeToDeadDayWithoutPeople;
    }

    public void setTimeToDeadDayWithoutPeople(int timeToDeadDayWithoutPeople) {
        this.timeToDeadDayWithoutPeople = timeToDeadDayWithoutPeople;
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
        recordInfectPeople(peopleBase);
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

    public boolean isAlive(){
        if(haveAttachPlace()&&timeToDeadDayWithoutPeople>0){
            return true;
        }else if(haveInfectedInPeopleBody()){
            return true;
        }
        return false;
    }

    public abstract void makeCarrierPeopleChangeState();

    public Map<PeopleBase, Integer> getContactRecord() {
        return contactRecord;
    }

    public Set<PeopleBase> getInfectRecord() {
        return infectRecord;
    }

    private void recordInfectPeople(PeopleBase peopleBase){
        infectRecord.add(peopleBase);
    }

    protected void recordContactPeople(PeopleBase peopleBase){
        Integer count=contactRecord.get(peopleBase);
        if(!peopleBase.isInfected(this)){
            if(count==null){
                contactRecord.put(peopleBase,1);
            }else {
                contactRecord.put(peopleBase,count+1);
            }
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
