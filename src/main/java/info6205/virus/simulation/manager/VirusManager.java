package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.VirusBase;
import info6205.virus.simulation.executor.ExecutorBase;
import info6205.virus.simulation.executor.VirusExecutor;

import java.util.ArrayList;
import java.util.List;

public class VirusManager extends ManagerBase{
    protected List<VirusBase> peopleInfectedVirus;
    protected List<VirusBase> placeAttachedVirus;


    public void addVirus(List<VirusBase> virusList){
        for (VirusBase virusBase:virusList){
            addVirus(virusBase);
        }
    }

    public void addVirus(VirusBase virusBase){
        if(virusBase.haveAttachPlace()){
            placeAttachedVirus.add(virusBase);
        }else {
            peopleInfectedVirus.add(virusBase);
        }
    }

    public VirusManager() {
        peopleInfectedVirus=new ArrayList<>();
        placeAttachedVirus=new ArrayList<>();
    }

    public List<VirusBase> getPeopleInfectedVirus() {
        return peopleInfectedVirus;
    }

    public List<VirusBase> getPlaceAttachedVirus() {
        return placeAttachedVirus;
    }

    @Override
    public ExecutorBase createExecutor() {
        return new VirusExecutor(this);
    }
}
