package info6205.virus.simulation.task.executor;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.task.TaskBase;

public abstract class ExecutorBase {
    public abstract void updatePeopleState(PeopleBase peopleBase, TaskBase taskBase);
}
