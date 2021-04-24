package info6205.virus.simulation.entity;

import info6205.virus.simulation.entity.building.House;
import info6205.virus.simulation.entity.building.Office;
import info6205.virus.simulation.entity.building.School;
import info6205.virus.simulation.entity.virus.Covid19;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;
import info6205.virus.simulation.task.TaskBase;

import java.util.*;

public abstract class PeopleBase {
    protected String id;
    protected String peopleType;
    protected GridElement location;
    protected double x;
    protected double y;
    protected Set<VirusBase> virus;
    protected Set<VirusBase> vaccine;
    protected Queue<TaskBase> tasks;
    protected MaskBase maskBase;
    protected SimulationMap map;
    protected static double timeSpeedZoom=1;
    protected int sleepTimeDuration=6*60*60;
    protected int eatingTimeDuration=20*60;
    protected double walkSpeed=0.2;
    protected double socialDistance=2;
    protected double keepSocialDistanceRate=0.8;
    protected boolean isAlive=true;

    protected boolean needToEatBreakFast;
    protected boolean needToEatLunch;
    protected boolean needToEatDinner;
    protected boolean needToMorningWork;
    protected boolean needToAfternoonWork;
    protected boolean needToSleep;
    protected boolean needToSchool;
    protected boolean feelSick;
    protected House home;
    protected Office office;
    protected School school;
    protected static Random random=new Random();

    public void initial(){
        id= UUID.randomUUID().toString();
        virus =new HashSet<>();
        tasks=new LinkedList<>();
        vaccine =new HashSet<>();
        location=home.getRandomWalkableGridElement();
        map=home.map;
        x=location.getRealX();
        y=location.getRealY();
        try {
            moveToNextLocation(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PeopleBase(House home) {
        this.home=home;
        initial();
    }

    public PeopleBase(int sleepTimeDuration, int eatingTimeDuration, double walkSpeed, double socialDistance, double keepSocialDistanceRate, House home) {
        this.sleepTimeDuration = sleepTimeDuration;
        this.eatingTimeDuration = eatingTimeDuration;
        this.walkSpeed = walkSpeed;
        this.socialDistance = socialDistance;
        this.keepSocialDistanceRate = keepSocialDistanceRate;
        this.home = home;
        initial();
    }

    public void weekendDailyRefresh(){
        needToEatBreakFast=true;
        needToEatLunch=true;
        needToEatDinner=true;
        needToMorningWork=false;
        needToAfternoonWork=false;
        needToSleep=true;
        needToSchool=false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public double getRandomSpeed(){
        double speed=getWalkSpeed()*(1-0.3*random.nextDouble());
        return speed;
    }


    public String getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(String peopleType) {
        this.peopleType = peopleType;
    }

    public double getSocialDistance() {
        return socialDistance;
    }

    public void setSocialDistance(double socialDistance) {
        this.socialDistance = socialDistance;
    }

    public double getKeepSocialDistanceRate() {
        return keepSocialDistanceRate;
    }

    public void setKeepSocialDistanceRate(double keepSocialDistanceRate) {
        this.keepSocialDistanceRate = keepSocialDistanceRate;
    }

    public double getWalkSpeed() {
        return walkSpeed/timeSpeedZoom;
    }

    public void setWalkSpeed(double walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

    public static double getTimeSpeedZoom() {
        return timeSpeedZoom;
    }

    public static void setTimeSpeedZoom(double timeSpeedZoom) {
        PeopleBase.timeSpeedZoom = timeSpeedZoom;
    }

    public boolean isFeelSick() {
        return feelSick;
    }

    public void setFeelSick(boolean feelSick) {
        this.feelSick = feelSick;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public House getHome() {
        return home;
    }

    public void setHome(House home) {
        this.home = home;
    }

    public boolean isNeedToSchool() {
        return needToSchool;
    }

    public void setNeedToSchool(boolean needToSchool) {
        this.needToSchool = needToSchool;
    }

    public int getEatingTimeDuration() {
        return (int) (eatingTimeDuration*timeSpeedZoom);
    }

    public void setEatingTimeDuration(int eatingTimeDuration) {
        this.eatingTimeDuration = eatingTimeDuration;
    }

    public int getSleepTimeDuration() {
        return (int) (sleepTimeDuration*timeSpeedZoom);
    }

    public void setSleepTimeDuration(int sleepTimeDuration) {
        this.sleepTimeDuration = sleepTimeDuration;
    }

    public boolean isNeedToEatBreakFast() {
        return needToEatBreakFast;
    }

    public void setNeedToEatBreakFast(boolean needToEatBreakFast) {
        this.needToEatBreakFast = needToEatBreakFast;
    }

    public boolean isNeedToEatLunch() {
        return needToEatLunch;
    }

    public void setNeedToEatLunch(boolean needToEatLunch) {
        this.needToEatLunch = needToEatLunch;
    }

    public boolean isNeedToEatDinner() {
        return needToEatDinner;
    }

    public void setNeedToEatDinner(boolean needToEatDinner) {
        this.needToEatDinner = needToEatDinner;
    }

    public boolean isNeedToMorningWork() {
        return needToMorningWork;
    }

    public void setNeedToMorningWork(boolean needToMorningWork) {
        this.needToMorningWork = needToMorningWork;
    }

    public boolean isNeedToAfternoonWork() {
        return needToAfternoonWork;
    }

    public void setNeedToAfternoonWork(boolean needToAfternoonWork) {
        this.needToAfternoonWork = needToAfternoonWork;
    }

    public boolean isNeedToSleep() {
        return needToSleep;
    }

    public void setNeedToSleep(boolean needToSleep) {
        this.needToSleep = needToSleep;
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


    abstract public void DailyStatusRefresh();

    public void dead(){
        isAlive=false;
        location.removePeople(this);
        location.addDeadPeople(this);
    }

    public void revive(){
        isAlive=true;
        moveToNextLocation(x,y);
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
    public Set<VirusBase> getVirus() {
        return virus;
    }

    public void infectVirus(VirusBase base){
        virus.add(base);
    }

    protected void removeVirus(VirusBase base){
        virus.remove(base);
    }
    public boolean isVaccine(){
        for(VirusBase virusBase:vaccine){
                return true;
        }
        return false;
    }

    public boolean isVaccine(Object classType){
        for(VirusBase virusBase:vaccine){
            if(classType.getClass().isInstance(virusBase)){
                return true;
            }
        }
        return false;
    }

    public boolean isInfected(){
        return virus.size()!=0?true:false;
    }

    public boolean isInfected(Object classType){

        for(VirusBase virusBase:virus){
            if(classType.getClass().isInstance(virusBase)){
                return true;
            }
        }
        return false;
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
        GridElement location = map.getGridElimentByXY(x,y);
        setX(x);
        setY(y);
        this.location = location;
        location.addPeople(this);
    }
    private void setLocation(GridElement location) throws Exception {
        setX(location.getRealX());
        setY(location.getRealY());
        this.location = location;
        location.addPeople(this);
    }

    // Move to next location
    public void moveToNextLocation(double x, double y) {
        GridElement currentLocation=getLocation();
        if(currentLocation!=null){
            currentLocation.removePeople(this);
        }
        try {
            setLocation(x,y);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void moveToNextLocation(GridElement location) throws Exception {
        GridElement currentLocation=getLocation();
        if(currentLocation!=null){
            currentLocation.removePeople(this);
        }
        setLocation(location);
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

    public void deleteCurrentTask(){
        tasks.remove();
    }

    public void exchangeCurrentTask(List<TaskBase> list){
        LinkedList<TaskBase> castTasks= (LinkedList<TaskBase>) tasks;
        castTasks.remove(0);
        for (int i=list.size()-1;i>=0;i--){
            castTasks.addFirst(list.get(i));
        }
    }

    public void addVaccine(VirusBase virusBase){
        vaccine.add(virusBase);
    }

    public boolean isAtHome(){
        GridElement gridElement=getLocation();
        for(AreaBase areaBase:gridElement.getAreas()){
            if (areaBase instanceof House){
                return true;
            }
        }
        return false;
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
