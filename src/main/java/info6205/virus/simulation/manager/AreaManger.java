package info6205.virus.simulation.manager;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.*;
import info6205.virus.simulation.executor.ExecutorBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AreaManger extends ManagerBase {
    protected List<House> houses;
    protected List<Hospital> hospitals;
    protected List<Mall> malls;
    protected List<Office> offices;
    protected List<Park> parks;
    protected List<Restaurant> restaurants;
    protected List<School> schools;
    protected List<RoadArea> roadAreas;
    protected List<Apartment> apartments;
    protected List<AreaBase> others;
    protected static Random random=new Random();

    public AreaManger() {
        houses=new ArrayList<>();
        hospitals=new ArrayList<>();
         malls=new ArrayList<>();
        offices=new ArrayList<>();
         parks=new ArrayList<>();
         restaurants=new ArrayList<>();
         schools=new ArrayList<>();
         roadAreas=new ArrayList<>();
        apartments=new ArrayList<>();
        others=new ArrayList<>();
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
        }else if(areaBase instanceof Apartment){
            apartments.add((Apartment) areaBase);
        }else {
            others.add(areaBase);
        }
    }

    public House getRandomHouse(){
        int i=houses.size();
        i=random.nextInt(i);
        return houses.get(i);
    }

    public Hospital getRandomHospital(){
        int i=hospitals.size();
        i=random.nextInt(i);
        return hospitals.get(i);
    }

    public Mall getRandomMall(){
        int i=malls.size();
        i=random.nextInt(i);
        return malls.get(i);
    }

    public Office getRandomOffice(){
        int i=offices.size();
        i=random.nextInt(i);
        return offices.get(i);
    }

    public Park getRandomPark(){
        int i=parks.size();
        i=random.nextInt(i);
        return parks.get(i);
    }
    public Restaurant getRandomRestaurant(){
        int i=restaurants.size();
        i=random.nextInt(i);
        return restaurants.get(i);
    }
    public School getRandomSchool(){
        int i=schools.size();
        i=random.nextInt(i);
        return schools.get(i);
    }

    public Apartment getRandomApartment(){
        int i=apartments.size();
        i=random.nextInt(i);
        return apartments.get(i);
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public List<AreaBase> getOthers() {
        return others;
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
