package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.MaskBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.entity.building.Restaurant;
import info6205.virus.simulation.map.Time;

public class EatingTask extends TaskBase{
    private int timeToEnd;
    private boolean start=true;

    public EatingTask(int eatingTime) {
        timeToEnd=eatingTime;
    }
    @Override
    public void executeTask(PeopleBase peopleBase) {
        if(start){
            Time currentTime=peopleBase.getMap().getCurrentTime();
            switch (currentTime){
                case NIGHT:
                    peopleBase.setNeedToEatDinner(false);
                    break;
                case MORNING:
                    peopleBase.setNeedToEatBreakFast(false);
                    break;
                case AFTERNOON:
                    peopleBase.setNeedToEatLunch(false);
                    break;
                default:
            }
            start=false;
        }
        timeToEnd--;
        if(timeToEnd<=0){
            finish();
        }
    }
}
