package info6205.virus.simulation.entity.people;

import info6205.virus.simulation.entity.PeopleBase;

public class Teen extends PeopleBase {
    public Teen() {
        sleepTimeDuration=8*60*60;
        eatingTimeDuration=30*60*60;
    }

    @Override
    public void DailyStatusRefresh() {
        needToMorningWork=false;
        needToAfternoonWork=false;
        needToEatDinner=true;
        needToEatBreakFast=true;
        needToEatLunch=true;
        needToSleep=true;
        needToSchool=true;
    }
}
