package info6205.virus.simulation.util;

import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.map.GridElement;

import java.util.*;

public class Graph<E> {
    private Map<E, List<E>> map;
    private Queue<Node> queue;
    private Stack<Node> stack;

    public Graph(){
        queue=new LinkedList<>();
        stack=new Stack<>();
    }

    public Graph(Map<E, List<E>> map) {
        this.map = map;
        queue=new LinkedList<>();
    }
    void addVertex(E element){
        if(!map.keySet().contains(element)){
            map.put(element,new LinkedList<E>());
        }
    }
    void addEdge(E a,E b){
        addVertex(a);
        addVertex(b);
        List<E> adjList=map.get(a);
        adjList.add(b);
    }
    Set<E> getAllVertex(){
        return map.keySet();
    }

    void addBiEge(E a,E b){
        addEdge(a,b);
        addEdge(b,a);
    }

    List<E> findPathBFS(E src, E des) throws Exception {
        boolean finded=false;
        Node currentNode=new Node(src,0,null);
        Set<E> traveled=new HashSet<>();
        queue.add(currentNode);
        while (queue.size()!=0){
            currentNode=queue.poll();
            E element=currentNode.getElement();
            traveled.add(element);

            if(element.equals(des)){
                finded=true;
                break;
            }
            for(E adjElement:getAdjacentElement(element)){
                if(!traveled.contains(adjElement)){
                    queue.add(new Node(adjElement,currentNode.deep+1,currentNode));
                }
            }
        }
        if (finded==false){
            throw new Exception("Can'find a path");
        }
        return currentNode.getPath();
    }

    public List<E> getAdjacentElement(E element){
        return map.get(element);
    }

    public Set<E> getBFSNodes(E src,int deep){
        boolean finded=false;
        Node currentNode=new Node(src,0,null);
        Set<E> traveled=new HashSet<>();
        queue.add(currentNode);
        while (queue.size()!=0){
            currentNode=queue.poll();
            E element=currentNode.getElement();
            traveled.add(element);
            if(currentNode.deep<deep){
                for(E adjElement:getAdjacentElement(element)){
                    if(!traveled.contains(adjElement)){
                        queue.add(new Node(adjElement,currentNode.deep+1,currentNode));
                    }
                }
            }
        }
        return traveled;
    }

    class Node{
        int deep;
        private List<E> path;
        private E element;
        public Node( E element,int deep,Node parent) {
            this.element = element;
            this.deep = deep;
            path=new LinkedList<>();
            if(parent!=null){
                path.addAll(parent.getPath());
            }
            path.add(element);
        }

        public int getDeep() {
            return deep;
        }

        public void setDeep(int deep) {
            this.deep = deep;
        }

        public List<E> getPath() {
            return path;
        }

        public void setPath(List<E> path) {
            this.path = path;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }
    }
}


