package info6205.virus.simulation.map;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;

import java.util.*;

public class GridElement {
    private int mapXIndex;
    private int mapYIndex;
    private double realX;
    private double realY;
    private Map<String,PeopleBase> people;
    private List<AreaBase> areas;


    public GridElement(int mapXIndex, int mapYIndex, double realX, double realY) {
        this.mapXIndex = mapXIndex;
        this.mapYIndex = mapYIndex;
        this.realX = realX;
        this.realY = realY;
        people=new HashMap<>();
        areas=new ArrayList<>();
    }


    public void addPeople(PeopleBase peopleBase){
        people.put(peopleBase.getId(),peopleBase);
    }
    public void removePeople(PeopleBase peopleBase){
        people.remove(peopleBase.getId());
    }
    public void bindArea(AreaBase areaBase){
        areas.add(areaBase);
    }
    public void removeAreaBind(AreaBase areaBase){
        for(int i=0;i<areas.size();i++){
            if(areas.get(i).getId().equals(areaBase.getId())){
                areas.remove(i);
                break;
            }
        }
    }

    public int getMapXIndex() {
        return mapXIndex;
    }

    public int getMapYIndex() {
        return mapYIndex;
    }

    public double getRealX() {
        return realX;
    }

    public double getRealY() {
        return realY;
    }

    public List<AreaBase> getAreas() {
        List<AreaBase> areaList=new ArrayList<>(areas);
        return areaList;
    }

    public List<PeopleBase> getPeople() {
        List<PeopleBase> peopleList=new ArrayList<>();
        for(String id:people.keySet()){
            peopleList.add(people.get(id));
        }
        return peopleList;
    }

}
