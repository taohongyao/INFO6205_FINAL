package info6205.virus.simulation.executor;

import info6205.virus.simulation.entity.VirusBase;
import info6205.virus.simulation.manager.VirusManager;

import java.util.ArrayList;
import java.util.List;

public class VirusExecutor implements ExecutorBase {
    protected VirusManager virusManager;


    public VirusExecutor(VirusManager virusManager) {
        this.virusManager = virusManager;
    }

    @Override
    public void roundSchedule() {
        List<VirusBase> copy=new ArrayList<>(virusManager.getPeopleInfectedVirus());
        for (VirusBase virusBase:copy){
            List<VirusBase> newVirus=virusBase.findCarrierAndInfect();
            virusManager.addVirus(newVirus);
        }
        copy = new ArrayList<>(virusManager.getPlaceAttachedVirus());
        for (VirusBase virusBase:copy){
            List<VirusBase> newVirus=virusBase.findCarrierAndInfect();
            virusManager.addVirus(newVirus);
        }
    }

    @Override
    public void daySchedule() {
        for (VirusBase virusBase:virusManager.getPeopleInfectedVirus()){
            virusBase.minusPotentialDay();
        }
        for (VirusBase virusBase:virusManager.getPlaceAttachedVirus()){
            virusBase.minusAliveDay();
        }
    }
}
