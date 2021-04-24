package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.MaskBase;
import info6205.virus.simulation.entity.PeopleBase;

public class SleepTask extends TaskBase {

    private int timeToEnd;

    public SleepTask(int sleepDuration) {
        this.timeToEnd = sleepDuration;
        name="Sleeping";
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        timeToEnd--;
        if(timeToEnd<=0){
            peopleBase.setNeedToSleep(false);
            finish();
        }
    }
}
