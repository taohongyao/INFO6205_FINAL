package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.*;
import info6205.virus.simulation.executor.ExecutorBase;

import java.util.ArrayList;
import java.util.List;

public class AreaManger extends ManagerBase {
    protected List<House> houses;
    protected List<Hospital> hospitals;
    protected List<Mall> malls;
    protected List<Office> offices;
    protected List<Park> parks;
    protected List<Restaurant> restaurants;
    protected List<School> schools;
    protected List<RoadArea> roadAreas;

    public AreaManger() {
        houses=new ArrayList<>();
        hospitals=new ArrayList<>();
         malls=new ArrayList<>();
        offices=new ArrayList<>();
         parks=new ArrayList<>();
         restaurants=new ArrayList<>();
         schools=new ArrayList<>();
         roadAreas=new ArrayList<>();
    }
    public void addAreas(List<AreaBase> areaBaseList){
        for (AreaBase areaBase:areaBaseList){
            addArea(areaBase);
        }
    }

    public void addArea(AreaBase areaBase){
        if(areaBase instanceof House){
            houses.add((House) areaBase);
        }else if(areaBase instanceof Hospital){
            hospitals.add((Hospital) areaBase);
        }else if(areaBase instanceof Mall){
            malls.add((Mall) areaBase);
        }else if(areaBase instanceof Office){
            offices.add((Office) areaBase);
        }else if(areaBase instanceof Park){
            parks.add((Park) areaBase);
        }else if(areaBase instanceof Restaurant){
            restaurants.add((Restaurant) areaBase);
        }else if(areaBase instanceof School){
            schools.add((School) areaBase);
        }else if(areaBase instanceof RoadArea){
            roadAreas.add((RoadArea) areaBase);
        }
    }

    public List<House> getHouses() {
        return houses;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public List<Mall> getMalls() {
        return malls;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public List<Park> getParks() {
        return parks;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public List<School> getSchools() {
        return schools;
    }

    public List<RoadArea> getRoadAreas() {
        return roadAreas;
    }

    @Override
    public ExecutorBase createExecutor() {
        return null;
    }
}
