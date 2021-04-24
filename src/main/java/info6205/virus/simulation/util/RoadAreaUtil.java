package info6205.virus.simulation.util;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.map.GridElement;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RoadAreaUtil {

    public static List<RoadArea> findPath(RoadArea src, RoadArea des) throws Exception {

        Graph<RoadArea> roadAreaGraph=new Graph<>(){
          @Override
          public List<RoadArea> getAdjacentElement(RoadArea roadArea){
              return roadArea.getAdjacentRoad();
          }
        };
        return roadAreaGraph.findPathBFS(src,des);
    }

    public static List<RoadArea> compactPath(List<RoadArea> path){
        LinkedList<RoadArea> copy=new LinkedList<>(path);
        Direction direction=null;
        int corner=0;
        int sameLength=1;
        for (int i=0;i<copy.size();i++){
            if(i==copy.size()-1){
                RoadArea currentArea=copy.get(i);
                Direction currentDirection=currentArea.getDirectionOfRoadArea(copy.get(i-1));
                if(currentDirection==direction){
                    sameLength++;
                    int sub_start=corner+1;
                    int sub_end=corner+sameLength;
                    if(sub_start<sub_end){
                        copy.subList(sub_start,sub_end).clear();
                        i=i-(sameLength-1);
                    }
                }
            }
            if(i==1){
                RoadArea currentArea=copy.get(i);
                Direction currentDirection=currentArea.getDirectionOfRoadArea(copy.get(i-1));
                direction=currentDirection;
            }else if(i>1){
                    RoadArea currentArea=copy.get(i);
                    Direction currentDirection=currentArea.getDirectionOfRoadArea(copy.get(i-1));
                    if(currentDirection!=direction){
                        int sub_start=corner+1;
                        int sub_end=corner+sameLength;
                        if(sub_start<sub_end){
                            copy.subList(sub_start,sub_end).clear();
                            i=i-(sameLength-1);
                        }
                        corner=i-1;
                        sameLength=1;
                        direction=currentDirection;
                        continue;
                    }else {
                        sameLength++;
                    }
                }
        }
        return  copy;
    }


    public static RoadArea getRoadAreaByGrid(GridElement gridElement){
        for (AreaBase areaBase:gridElement.getAreas()){
            if(areaBase instanceof RoadArea){
                return (RoadArea) areaBase;
            }
        }
        return null;
    }

    public static List<RoadArea> getRoadAreasByGrid(GridElement gridElement){
        List<RoadArea> areas=new ArrayList<>();
        for (AreaBase areaBase:gridElement.getAreas()){
            if(areaBase instanceof RoadArea){
                areas.add((RoadArea) areaBase);
            }
        }
        return areas;
    }

    public static int getRoadAreasPeople(RoadArea roadArea){
        AtomicInteger count= new AtomicInteger();
        roadArea.getArea().stream().parallel().forEach(i->i.stream().parallel().forEach(j-> count.addAndGet(j.getPeopleSize())));
        return count.get();
    }

}