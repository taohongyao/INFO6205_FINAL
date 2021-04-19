package info6205.virus.simulation.executor;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.people.Adult;
import info6205.virus.simulation.entity.people.Elder;
import info6205.virus.simulation.entity.people.Teen;
import info6205.virus.simulation.manager.PeopleManger;

import java.util.List;

public class PeopleExecutor implements ExecutorBase{
    protected PeopleManger peopleManger;

    public PeopleExecutor(PeopleManger peopleManger) {
        this.peopleManger=peopleManger;
    }


    @Override
    public void roundSchedule() {

    }

    @Override
    public void daySchedule() {

    }
}
