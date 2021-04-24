package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.mask.N95;
import info6205.virus.simulation.entity.people.Adult;
import info6205.virus.simulation.entity.people.Elder;
import info6205.virus.simulation.entity.people.Teen;
import info6205.virus.simulation.entity.virus.Covid19;
import info6205.virus.simulation.entity.virus.SARS;
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

    public void setSocialDistanceAndKeepRate(Double socialDistance,Double distanceKeepRate){
        setSocialDistanceAndKeepRate(getAdults(),socialDistance,distanceKeepRate);
        setSocialDistanceAndKeepRate(getElders(),socialDistance,distanceKeepRate);
        setSocialDistanceAndKeepRate(getTeens(),socialDistance,distanceKeepRate);
    }


    public void setAllHaveMask(){
        setAllHaveMask(getTeens());
        setAllHaveMask(getAdults());
        setAllHaveMask(getElders());
    }
    public void removeAllMask(){
        removeAllMask(getTeens());
        removeAllMask(getAdults());
        removeAllMask(getElders());
    }

    public void setAllHaveMask(List<PeopleBase> list){
        for (PeopleBase peopleBase:list){
            peopleBase.setMaskBase(new N95());
        }
    }

    public void removeAllMask(List<PeopleBase> list){
        for (PeopleBase peopleBase:list){
            peopleBase.setMaskBase(null);
        }
    }

    public void setSocialDistanceAndKeepRate(List<PeopleBase> list,Double socialDistance,Double distanceKeepRate){
        for (PeopleBase peopleBase:list){
            peopleBase.setSocialDistance(socialDistance);
            peopleBase.setKeepSocialDistanceRate(distanceKeepRate);
        }
    }


    public PeopleManger() {
        teens=new ArrayList<>();
        adults=new ArrayList<>();
        elders=new ArrayList<>();
    }

    public int getInfectedAdultCount(){
        return getInfectedCount(getAdults());
    }
    public int getInfectedTeenCount(){
        return getInfectedCount(getElders());
    }
    public int getInfectedElderCount(){
        return getInfectedCount(getTeens());
    }

    public static int getInfectedCount(List<PeopleBase> peopleBases){
        int sum=0;
        Covid19 cov=new Covid19();
        SARS sars=new SARS();
        for(PeopleBase peopleBase:peopleBases){
            if(peopleBase.isInfected(cov)||peopleBase.isInfected(sars)){
                sum++;
            }
        }
        return sum;
    }

    @Override
    public ExecutorBase createExecutor() {
        return new PeopleExecutor(this);
    }
}
