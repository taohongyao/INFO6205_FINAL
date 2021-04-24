package info6205.virus.simulation.console;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.VirusBase;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.executor.ExecutorBase;
import info6205.virus.simulation.gui.SimulationApplicationWindows;
import info6205.virus.simulation.logger.Log;
import info6205.virus.simulation.manager.*;
import info6205.virus.simulation.map.SimulationMap;
import info6205.virus.simulation.map.Time;
import info6205.virus.simulation.map.Week;
import info6205.virus.simulation.record.DataRecord;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationApplication {
    protected List<ExecutorBase> executorBaseList;
    protected SimulationMap map;
    protected AreaManger areaManger;
    protected PeopleManger peopleManger;
    protected VirusManager virusManager;
//    protected SimulationApplicationWindows windows;


    protected int timeUnitADay;
    protected int initTimeUnitADay;
    protected int simulateSpeed;
    protected int virusType;
    protected boolean run = true;
    protected int worldTimeUnit; //every round will increase by one
    protected int days;
    protected  double mapWidth;
    protected double mapHigh;




    public SimulationApplication(int timeUnitADay, double mapWidth,double mapHigh) {
        this.timeUnitADay = timeUnitADay;
        initTimeUnitADay=timeUnitADay;
        simulateSpeed=60;
        this.mapWidth=mapWidth;
        this.mapHigh=mapHigh;
        executorBaseList=new ArrayList<>();
        map=new SimulationMap(mapWidth,mapHigh);
        run = true;
        reset();
    }

    public synchronized void reset(){
        executorBaseList=new ArrayList<>();
        map=new SimulationMap(mapWidth,mapHigh);

        EntityGenerator entityGenerator=new DemoGenerator(map,virusType);
        areaManger=new AreaManger();
        peopleManger=new PeopleManger();
        virusManager=new VirusManager();

        List<AreaBase> buildings=entityGenerator.generateBuilding();
        areaManger.addAreas(buildings);
        List<PeopleBase> people=entityGenerator.generatePeople(areaManger);

        peopleManger.addPeople(people);

        List<VirusBase> virus=entityGenerator.generateVirus(peopleManger);
        virusManager.addVirus(virus);
        executorBaseList.add(peopleManger.createExecutor());
        executorBaseList.add(virusManager.createExecutor());
        days=0;
        worldTimeUnit=0;
        DataRecord.getkFactor().clear();
    }

    public Time getTime(){
        int dayTime=worldTimeUnit%timeUnitADay;
        if(dayTime<timeUnitADay/4.0){
            return  Time.MORNING;
        }else if(dayTime<timeUnitADay/2.0){
            return  Time.AFTERNOON;
        }else if(dayTime<timeUnitADay*3.0/4){
            return Time.NIGHT;
        }else {
            return Time.MIDNIGHT;
        }
    }


    public Week getWeek(){
        int week=days%7;
        switch (week){
            case 0:
                return Week.MON;
            case 1:
                return Week.TUE;
            case 2:
                return Week.WES;
            case 3:
                return Week.THU;
            case 4:
                return Week.FRI;
            case 5:
                return Week.SAT;
            case 6:
                return Week.SUN;
            default:
        }
        return null;
    }

    public void start(){
        while (true){
            try {
                Thread.sleep(1000/simulateSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(run){

                synchronized(this){
                    worldTimeUnit++;
                    int dayTime=worldTimeUnit%timeUnitADay;
                    map.setCurrentTime(getTime());

                    // 3:00am refresh
                    if(dayTime==(int)(timeUnitADay*(7.0/8))){
                        days++;
                        for (ExecutorBase executorBase:executorBaseList){
                            executorBase.daySchedule();
                        }
                        // 5 and 6 is weekends
                        if(days%7>=5){
                            for (ExecutorBase executorBase:executorBaseList){
                                executorBase.weekendsSchedule();
                            }
                        }
                    }

                    //Record data
                    if(worldTimeUnit%10==0){
                        DataRecord.addKFactorRecord(virusManager.getKFactor());
                    }
                    //Run virus and people move
                    for (ExecutorBase executorBase:executorBaseList){
                        executorBase.roundSchedule();
                    }
                }
            }
        }
    }

    public void setSimulateSpeed(int simulateSpeed) {
        this.simulateSpeed = simulateSpeed;
        if(simulateSpeed>1000){
            double timeZoom=1-((simulateSpeed-1000)*0.00048);
            if(timeZoom<0.001) timeZoom=0.001;
            BuildingBase.setTimeSpeedZoom(timeZoom);
            PeopleBase.setTimeSpeedZoom(timeZoom);
            timeUnitADay= (int) (initTimeUnitADay*timeZoom);
            VirusBase.setDayActiveTimes(timeUnitADay);
        }else {
            BuildingBase.setTimeSpeedZoom(1);
            PeopleBase.setTimeSpeedZoom(1);
            timeUnitADay= initTimeUnitADay;
            VirusBase.setDayActiveTimes(timeUnitADay);
        }
    }


    public int getDays() {
        return days;
    }

    public int getWorldTimeUnit() {
        return worldTimeUnit;
    }

    public void setWorldTimeUnit(int worldTimeUnit) {
        this.worldTimeUnit = worldTimeUnit;
    }

    public void run(){
        run=true;
    }
    public void stop(){
        run=false;
    }

    public int getTimeUnitADay() {
        return timeUnitADay;
    }

    public AreaManger getAreaManger() {
        return areaManger;
    }

    public PeopleManger getPeopleManger() {
        return peopleManger;
    }

    public VirusManager getVirusManager() {
        return virusManager;
    }

    public SimulationMap getMap() {
        return map;
    }

    public int getVirusType() {
        return virusType;
    }

    public void setVirusType(int virusType) {
        this.virusType = virusType;
    }

    public int getSimulateSpeed() {
        return simulateSpeed;
    }



    public static void main(String[] args) {
        setLevel(Log.APP_LEVEL);
        Wini ini= null;
        try {
            ini = new Wini(new File(new File(SimulationApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath()+File.separator+"config.ini"));
            iniStart(ini);
        } catch (IOException e) {
            e.printStackTrace();
            defaultStart();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            defaultStart();
        }

    }

    public static void iniStart(Wini ini){
        int timeUnitADay=Integer.parseInt(ini.fetch("simulate","timeUnitADay"));
        int mapWidth=Integer.parseInt(ini.fetch("simulate","mapWidth"));
        int mapHigh=Integer.parseInt(ini.fetch("simulate","mapHigh"));
        double xViewPointInRealWorld=Double.parseDouble(ini.fetch("simulate","xViewPointInRealWorld"));
        double yViewPointInRealWorld=Double.parseDouble(ini.fetch("simulate","yViewPointInRealWorld"));
        double viewZoom=Double.parseDouble(ini.fetch("simulate","viewZoom"));

        SimulationApplication simulationApplication=new SimulationApplication(timeUnitADay,mapWidth,mapHigh);
        SimulationApplicationWindows windows=new SimulationApplicationWindows(simulationApplication,xViewPointInRealWorld, yViewPointInRealWorld, viewZoom);
        simulationApplication.start();
    }

    public static void defaultStart(){
        SimulationApplication simulationApplication=new SimulationApplication(60*60*24,100,80);
        SimulationApplicationWindows windows=new SimulationApplicationWindows(simulationApplication,-8, 50, 0.088);
        simulationApplication.start();
    }

    public static void setLevel(Level targetLevel) {
        Logger root = Logger.getLogger("");
        root.setLevel(targetLevel);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(targetLevel);
        }
        System.out.println("level set: " + targetLevel.getName());
    }

}
