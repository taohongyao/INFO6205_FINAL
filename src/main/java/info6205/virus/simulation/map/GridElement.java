package info6205.virus.simulation.map;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.VirusBase;

import java.util.*;

public class GridElement {
    private boolean walkAble;
    private int mapXIndex;
    private int mapYIndex;
    private double realX;
    private double realY;
    private SimulationMap map;

    private Map<String,PeopleBase> people;
    private List<AreaBase> areas;
    private List<VirusBase> virus;


    public GridElement(int mapXIndex, int mapYIndex, double realX, double realY,boolean walkAble,SimulationMap map) {
        this.mapXIndex = mapXIndex;
        this.mapYIndex = mapYIndex;
        this.realX = realX;
        this.realY = realY;
        walkAble=false;
        people=new HashMap<>();
        areas=new ArrayList<>();
        virus=new ArrayList<>();
        this.map=map;
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

    public void attachVirus(VirusBase virusBase){
        virus.add(virusBase);
    }

    public void removeVirus(VirusBase virusBase){
        for(int i=0;i<virus.size();i++){
            if(virus.get(i).getId().equals(virusBase.getId())){
                virus.remove(i);
                break;
            }
        }
    }

    public boolean isWalkAble() {
        return walkAble;
    }

    public void setWalkAble(boolean walkAble) {
        this.walkAble = walkAble;
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

    public SimulationMap getMap() {
        return map;
    }

    public List<AreaBase> getAreas() {
        List<AreaBase> areaList=new ArrayList<>(areas);
        return areaList;
    }

    public boolean isArea(AreaBase base){
        for(AreaBase areaBase:areas){
            if(areaBase.equals(base)){
                return true;
            }
        }
        return false;
    }

    public boolean havePeople(){
        if(people.size()==0) return false;
        return true;
    }

    public List<PeopleBase> getPeople() {
        List<PeopleBase> peopleList=new ArrayList<>();
        for(String id:people.keySet()){
            peopleList.add(people.get(id));
        }
        return peopleList;
    }

}
