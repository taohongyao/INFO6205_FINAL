package info6205.virus.simulation.console;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.VirusBase;
import info6205.virus.simulation.executor.ExecutorBase;
import info6205.virus.simulation.manager.*;
import info6205.virus.simulation.map.SimulationMap;

import java.util.ArrayList;
import java.util.List;

public class SimulationApplication {
    protected List<ExecutorBase> executorBaseList;
    protected SimulationMap map;
    protected AreaManger areaManger;
    protected PeopleManger peopleManger;
    protected VirusManager virusManager;

    protected int roundADay;
    protected int simulateSpeed;
    protected boolean run;
    protected int rounds;
    protected int days;

    public SimulationApplication(int roundADay, double mapWidth,double mapHigh) {
        this.roundADay = roundADay;
        simulateSpeed=1;
        executorBaseList=new ArrayList<>();
        map=new SimulationMap(mapWidth,mapHigh);

        EntityGenerator entityGenerator=new DemoGenerator(map);
        areaManger=new AreaManger();
        peopleManger=new PeopleManger();
        virusManager=new VirusManager();

        List<AreaBase> buildings=entityGenerator.generateBuilding();
//        List<PeopleBase> people=entityGenerator.generatePeople(buildings);
//        List<VirusBase> virus=entityGenerator.generateVirus(people);

        areaManger.addAreas(buildings);
//        peopleManger.addPeople(people);
//        virusManager.addVirus(virus);
//        executorBaseList.add(peopleManger.createExecutor());
//        executorBaseList.add(virusManager.createExecutor());
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(1000/simulateSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(run){
                rounds++;
                if(rounds%roundADay==0){
                    days++;
                    for (ExecutorBase executorBase:executorBaseList){
                        executorBase.daySchedule();
                    }
                }
                for (ExecutorBase executorBase:executorBaseList){
                    executorBase.roundSchedule();
                }
                //render windows
            }
        }
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

    public static void main(String[] args){
        SimulationApplication simulationApplication=new SimulationApplication(60*60*24,70,50);
        simulationApplication.run();
    }

}
