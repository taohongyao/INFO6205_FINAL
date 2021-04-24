package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.MaskBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.entity.building.Restaurant;
import info6205.virus.simulation.map.Time;

public class EatingTask extends TaskBase{
    enum Meal{
        BREAKFAST,
        DINNER,
        LUNCH
    }
    private Meal mealType;
    private int timeToEnd;
    private boolean start=true;

    public EatingTask(int eatingTime, Meal type) {
        timeToEnd=eatingTime;
        this.mealType=type;
        name="Eating";
    }
    @Override
    public void executeTask(PeopleBase peopleBase) {
        if(start){
            Time currentTime=peopleBase.getMap().getCurrentTime();
            if(mealType!=null)
            switch (mealType){
                case BREAKFAST:
                    peopleBase.setNeedToEatBreakFast(false);
                    break;
                case LUNCH:
                    peopleBase.setNeedToEatLunch(false);
                    break;
                case DINNER:
                    peopleBase.setNeedToEatDinner(false);
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
