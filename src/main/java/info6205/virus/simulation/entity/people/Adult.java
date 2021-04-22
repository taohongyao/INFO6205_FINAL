package info6205.virus.simulation.entity.people;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.building.House;
import info6205.virus.simulation.entity.building.Office;

public class Adult extends PeopleBase {


    public Adult(int sleepTimeDuration, int eatingTimeDuration, double walkSpeed, double socialDistance, double keepSocialDistanceRate, House home, Office office) {
        super(sleepTimeDuration, eatingTimeDuration, walkSpeed, socialDistance, keepSocialDistanceRate, home);
        this.office=office;
        DailyStatusRefresh();
    }

    public Adult(House house, Office office) {
        super(house);
        this.office=office;
        sleepTimeDuration=6*60*60;
        eatingTimeDuration=20*60;
        DailyStatusRefresh();
    }

    @Override
    public void DailyStatusRefresh() {
        needToMorningWork=true;
        needToAfternoonWork=true;
        needToEatDinner=true;
        needToEatBreakFast=true;
        needToEatLunch=true;
        needToSleep=true;
        needToSchool=false;
    }
}
