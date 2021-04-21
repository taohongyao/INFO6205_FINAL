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

    private void execute(List<VirusBase> list){
        List<VirusBase> copy=new ArrayList<>(list);
        for (VirusBase virusBase:copy){
            List<VirusBase> newVirus=virusBase.findCarrierAndInfect();
            virusManager.addVirus(newVirus);
        }
    }
    private void dailyExecute(List<VirusBase> list){
        for (VirusBase virusBase:list){
            virusBase.minusPotentialDay();
            virusBase.makeCarrierPeopleChangeState();
        }
    }

    @Override
    public void roundSchedule() {
        execute(virusManager.getPeopleInfectedVirus());
        execute(virusManager.getPlaceAttachedVirus());
    }

    @Override
    public void daySchedule() {
        dailyExecute(virusManager.getPeopleInfectedVirus());
        dailyExecute(virusManager.getPlaceAttachedVirus());
    }

    @Override
    public void weekendsSchedule() {

    }
}
