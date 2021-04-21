package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.PeopleBase;

public class WaitTask extends TaskBase{
    protected int timeToEnd;

    public WaitTask(int timeToEnd) {
        this.timeToEnd = timeToEnd;
    }

    public int getTimeToEnd() {
        return timeToEnd;
    }

    public void setTimeToEnd(int timeToEnd) {
        this.timeToEnd = timeToEnd;
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        timeToEnd--;
        if(timeToEnd<=0){
            finish();
        }
    }
}
