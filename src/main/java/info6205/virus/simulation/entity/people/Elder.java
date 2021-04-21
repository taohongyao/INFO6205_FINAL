package info6205.virus.simulation.entity.people;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.building.House;

public class Elder extends PeopleBase {
    public Elder(House house) {
        super(house);
        sleepTimeDuration=5*60;
        eatingTimeDuration=30*60;
        DailyStatusRefresh();
    }

    @Override
    public void DailyStatusRefresh() {
        needToMorningWork=false;
        needToAfternoonWork=false;
        needToEatDinner=true;
        needToEatBreakFast=true;
        needToEatLunch=true;
        needToSleep=true;
        needToSchool=false;
    }
}
