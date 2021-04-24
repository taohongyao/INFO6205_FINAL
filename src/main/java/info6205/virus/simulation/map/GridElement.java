package info6205.virus.simulation.map;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.VirusBase;

import java.util.*;
import java.util.stream.IntStream;

public class GridElement {
    private String connectedId;
    private String id;
    private boolean walkAble;
    private int mapXIndex;
    private int mapYIndex;
    private double realX;
    private double realY;
    private SimulationMap map;

    private Map<String,PeopleBase> people;
    private Map<String,PeopleBase> deadPeople;
    private List<AreaBase> areas;
    private List<VirusBase> virus;


    public GridElement(int mapXIndex, int mapYIndex, double realX, double realY,boolean walkAble,SimulationMap map) {
        this.mapXIndex = mapXIndex;
        this.mapYIndex = mapYIndex;
        this.realX = realX;
        this.realY = realY;
        this.walkAble=walkAble;
        people=new HashMap<>();
        deadPeople=new HashMap<>();
        areas=new ArrayList<>();
        virus=new ArrayList<>();
        this.map=map;
        id=UUID.randomUUID().toString();
    }


    public void addPeople(PeopleBase peopleBase){
        people.put(peopleBase.getId(),peopleBase);
    }
    public void removePeople(PeopleBase peopleBase){
        people.remove(peopleBase.getId());
    }
    public void addDeadPeople(PeopleBase peopleBase){
        deadPeople.put(peopleBase.getId(),peopleBase);
    }
    public void removeDeadPeople(PeopleBase peopleBase){
        deadPeople.remove(peopleBase.getId());
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

    public List<GridElement> getAdjacentElement(){
        return map.getAdjacentElements(this);
    }

    public boolean isWalkAble() {
        return walkAble;
    }

    public String getId() {
        return id;
    }




    public String getConnectedId() {
        return connectedId;
    }

    public void setConnectedId(String connectedId) {
        this.connectedId = connectedId;
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
        AreaBase finded=areas.stream().parallel().filter(areaBase -> areaBase.equals(base)).findFirst().orElse(null);
        return finded==null?false:true;
//        for(AreaBase areaBase:areas){
//            if(areaBase.equals(base)){
//                return true;
//            }
//        }
//        return false;
    }

    public boolean havePeople(){
        if(people.size()==0) return false;
        return true;
    }


    public int getPeopleSize(){
        return people.size();
    }
    public List<PeopleBase> getPeople() {
        List<PeopleBase> peopleList=new ArrayList<>(people.values());
        return peopleList;
    }

    public List<PeopleBase> getDeadPeople() {
        List<PeopleBase> peopleList=new ArrayList<>(deadPeople.values());
        return peopleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridElement that = (GridElement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
