package info6205.virus.simulation.entity.people;

import info6205.virus.simulation.entity.PeopleBase;

public class Adult extends PeopleBase {

    public Adult() {
        sleepTimeDuration=6*60*60;
        eatingTimeDuration=20*60*60;
    }
}
