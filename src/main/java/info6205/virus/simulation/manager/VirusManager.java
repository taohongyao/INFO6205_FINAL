package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.PeopleBase;
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

    public double getKFactor(){
        int i=0;
        double c=0;
        for(VirusBase base:peopleInfectedVirus){
            int subi=0;
            for(Integer contactTimes:base.getContactRecord().values()){
                if(contactTimes>0){
                    subi++;
                }
            }
            i+=subi;
            if(subi==0) subi=100;
            c+=(0.0+base.getInfectRecord().size()/subi);
        }
        return i*c/peopleInfectedVirus.size();
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
