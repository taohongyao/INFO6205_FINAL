package info6205.virus.simulation.entity.people;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.building.House;
import info6205.virus.simulation.entity.building.School;

public class Teen extends PeopleBase {
    public Teen(House house, School school) {
        super(house);
        this.school=school;
        sleepTimeDuration=8*60*60;
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
        needToSchool=true;
    }
}
