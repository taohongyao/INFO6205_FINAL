package info6205.virus.simulation.entity.people;

import info6205.virus.simulation.entity.PeopleBase;

public class Elder extends PeopleBase {
    public Elder() {
        sleepTimeDuration=5*60*60;
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
