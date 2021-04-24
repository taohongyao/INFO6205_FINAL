package info6205.virus.simulation.util;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.map.GridElement;

import java.util.*;

public class GridElementUtil {

    public static Set<GridElement> getBFSAreaGrids(GridElement gridElement,int deep){
        Graph<GridElement> roadAreaGraph=new Graph<>(){
            @Override
            public List<GridElement> getAdjacentElement(GridElement element){
                return element.getAdjacentElement();
            }
        };
        return roadAreaGraph.getBFSNodes(gridElement,deep);
    }

    public static boolean isKeepSocialDistance(GridElement gridElement, PeopleBase peopleBase, double distance){
        boolean isKeepSocialDistance=true;
        Set<PeopleBase> set= GridElementUtil.getBFSAreaGridsPeople(gridElement,distance);
        set.remove(peopleBase);
        if(set.size()>0){
            isKeepSocialDistance=false;
        }
        return isKeepSocialDistance;
    }



    public static boolean isConnected(GridElement src,GridElement des){
        if(src==null || des==null) return false;
        return src.getConnectedId().equals(des.getConnectedId());
    }


    public static Set<GridElement> getBFSAreaGrids(GridElement gridElement,Double radius){
        int deep= (int) (gridElement.getMap().getDivEveryMeter()*radius);
        return getBFSAreaGrids(gridElement,deep);
    }

    public static Set<PeopleBase> getBFSAreaGridsPeople(GridElement gridElement, Double radius){
        int deep= (int) (gridElement.getMap().getDivEveryMeter()*radius);
        return getBFSAreaGridsPeople(gridElement,deep);
    }

    public static Set<PeopleBase> getBFSAreaGridsPeople(GridElement gridElement, int deep){
        Set<GridElement> list=getBFSAreaGrids(gridElement,deep);
        Set peopleBases=Collections.synchronizedSet(new HashSet<>());
        list.stream().parallel().forEach(i->peopleBases.addAll(i.getPeople()));
//        for(GridElement element:list){
//            peopleBases.addAll(element.getPeople());
//        }
        return peopleBases;
    }

    public static Set<PeopleBase> getBFSAreaGridsDeadPeople(GridElement gridElement, Double radius){
        int deep= (int) (gridElement.getMap().getDivEveryMeter()*radius);
        return getBFSAreaGridsDeadPeople(gridElement,deep);
    }

    public static Set<PeopleBase> getBFSAreaGridsDeadPeople(GridElement gridElement, int deep){
        Set<GridElement> list=getBFSAreaGrids(gridElement,deep);
        Set peopleBases=Collections.synchronizedSet(new HashSet<>());
        list.stream().parallel().forEach(i->peopleBases.addAll(i.getDeadPeople()));
        return peopleBases;
    }

}
