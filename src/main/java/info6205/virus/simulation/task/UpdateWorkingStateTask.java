package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.map.Time;

public class UpdateWorkingStateTask extends TaskBase{
    private Time workingTime;
    private boolean newStatus;

    public UpdateWorkingStateTask(Time workingTime,boolean newStatus) {
        this.workingTime = workingTime;
        this.newStatus=newStatus;
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        if(workingTime==Time.MIDNIGHT){
            peopleBase.setNeedToMorningWork(newStatus);
        }else if(workingTime==Time.AFTERNOON) {
            peopleBase.setNeedToAfternoonWork(newStatus);
        }
        finish();
    }
}
