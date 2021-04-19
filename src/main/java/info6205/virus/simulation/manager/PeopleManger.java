package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.people.Adult;
import info6205.virus.simulation.entity.people.Elder;
import info6205.virus.simulation.entity.people.Teen;
import info6205.virus.simulation.executor.ExecutorBase;
import info6205.virus.simulation.executor.PeopleExecutor;

import java.util.ArrayList;
import java.util.List;

public class PeopleManger extends ManagerBase{
    protected List<PeopleBase> teens;
    protected List<PeopleBase> adults;
    protected List<PeopleBase> elders;

    public List<PeopleBase> getTeens() {
        return teens;
    }

    public List<PeopleBase> getAdults() {
        return adults;
    }

    public List<PeopleBase> getElders() {
        return elders;
    }

    public void addPeople(List<PeopleBase> list){
        for (PeopleBase peopleBase:list){
            addPeople(peopleBase);
        }
    }

    public void addPeople(PeopleBase peopleBase){
        if(peopleBase instanceof Teen){
            teens.add(peopleBase);
        }else if(peopleBase instanceof Adult){
            adults.add(peopleBase);
        }else if(peopleBase instanceof Elder){
            elders.add(peopleBase);
        }
    }


    public PeopleManger() {
        teens=new ArrayList<>();
        adults=new ArrayList<>();
        elders=new ArrayList<>();
    }

    @Override
    public ExecutorBase createExecutor() {
        return new PeopleExecutor(this);
    }
}
