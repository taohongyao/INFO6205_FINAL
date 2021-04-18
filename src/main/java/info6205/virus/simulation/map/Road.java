package info6205.virus.simulation.map;

import info6205.virus.simulation.entity.RoadArea;

import java.util.*;

public class Road {

    protected List<RoadArea> roadAreas;



    public static List<RoadArea> findPath(RoadArea src, RoadArea des) throws Exception {
        List<RoadArea> path=new ArrayList<>();
        Queue<BFSNode> queue=new LinkedList<>();
        Map<String,Boolean> travelFlag=new HashMap<>();
        boolean finded=false;
        BFSNode currentNode=new BFSNode(src,null);

        queue.add(currentNode);
        while (queue.size()!=0){
            currentNode=queue.poll();
            RoadArea currentRoadArea=currentNode.getRoadArea();
            travelFlag.put(currentRoadArea.getId(),true);

            if(currentRoadArea.equals(des)){
                finded=true;
                break;
            }
            for(RoadArea roadArea:currentRoadArea.getAdjacentRoad()){
                if(travelFlag.get(roadArea.getId())==null){
                    queue.add(new BFSNode(roadArea,currentNode));
                }
            }
        }
        if(finded==false) throw new Exception("Can't find path.");

        return currentNode.getPath();
    }

    public List<RoadArea> getRoadAreas() {
        return roadAreas;
    }

    public void setRoadAreas(List<RoadArea> roadAreas) {
        this.roadAreas = roadAreas;
    }

}
class BFSNode{
    private RoadArea roadArea;
    private List<RoadArea> path;

    public BFSNode(RoadArea node,BFSNode parent) {
        this.roadArea = node;
        if(parent!=null){
            path=new ArrayList<>(parent.getPath());
        }else{
            path=new ArrayList<>();
        }
        path.add(node);
    }

    public RoadArea getRoadArea() {
        return roadArea;
    }

    public void setRoadArea(RoadArea roadArea) {
        this.roadArea = roadArea;
    }

    public List<RoadArea> getPath() {
        return path;
    }

    public void setPath(List<RoadArea> path) {
        this.path = path;
    }
}
