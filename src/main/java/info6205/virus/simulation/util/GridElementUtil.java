package info6205.virus.simulation.util;

import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.map.GridElement;

import java.util.*;

public class GridElementUtil {

    public static Set<GridElement> getBFSAreaGrids(GridElement gridElement,int deep){
        Queue<BFSHelper> queue=new LinkedList<>();
        Set<GridElement> set=new HashSet<>();
        queue.add(new BFSHelper(gridElement,deep));
        while (queue.size()>0){
            BFSHelper bfsHelper=queue.poll();
            set.add(bfsHelper.gridElement);
            if(bfsHelper.deep>1){
                for (GridElement adj:bfsHelper.gridElement.getAdjacentElement()){
                    if(!set.contains(adj)){
                        queue.add(new BFSHelper(adj,bfsHelper.deep-1));
                    }
                }
            }
        }
        return set;
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
        Set<PeopleBase> peopleBases=new HashSet<>();
        for(GridElement element:list){
            for (PeopleBase peopleBase:element.getPeople()){
                peopleBases.add(peopleBase);
            }
        }
        return peopleBases;
    }

}
class BFSHelper{
    int deep;
    GridElement gridElement;
    public BFSHelper( GridElement gridElement,int deep) {
        this.deep = deep;
        this.gridElement = gridElement;
    }
}
