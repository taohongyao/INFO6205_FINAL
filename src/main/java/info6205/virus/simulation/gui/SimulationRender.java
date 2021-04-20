package info6205.virus.simulation.gui;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.Direction;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.*;

import java.awt.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationRender {
    private int highCanvas;
    private int widthCanvas;
    private double xLeftTopRealWorld;
    private double yLeftTopRealWorld;
    private double zoom;

    private static Logger logger=Logger.getLogger(SimulationRender.class.getName());


    public SimulationRender(int highCanvas, int widthCanvas, double xLeftTopRealWorld, double yLeftTopRealWorld, double zoom) {
        this.highCanvas = highCanvas;
        this.widthCanvas = widthCanvas;
        this.xLeftTopRealWorld = xLeftTopRealWorld;
        this.yLeftTopRealWorld = yLeftTopRealWorld;
        this.zoom = zoom;
    }


    public void renderAreaBase(AreaBase areaBase,Graphics g,double highCanvasRealWorld,double widthCanvasRealWorld){
        double x=areaBase.getLeftUpX();
        double y=areaBase.getLeftUpY();
        logger.log(Level.INFO,String.join(" ","AreaBase ","x:"+x,",y:"+y));
        logger.log(Level.INFO,String.join(" ","Bounce ","X["+xLeftTopRealWorld,":"+(xLeftTopRealWorld+widthCanvasRealWorld)+"]","Y["+(yLeftTopRealWorld-highCanvasRealWorld),":"+yLeftTopRealWorld+"]"));
        if(x>=xLeftTopRealWorld&&x<xLeftTopRealWorld+widthCanvasRealWorld&&y<=yLeftTopRealWorld&&y>yLeftTopRealWorld-highCanvasRealWorld){
            logger.log(Level.INFO,"Start render.");
            if(areaBase instanceof RoadArea){
                renderRoad((RoadArea) areaBase,g);
            }else if(areaBase instanceof BuildingBase){
                renderBuilding((BuildingBase) areaBase,g);
            }else {
                logger.log(Level.INFO,"Nothing to render.");
            }
        }else {
            logger.log(Level.INFO,"Out of render scope.");
        }
    }

    public void renderAreaBase(List<? extends AreaBase> areaBase,Graphics g){
        double widthCanvasRealWorld = widthCanvas*zoom;
        double highCanvasRealWorld = highCanvas*zoom;
        logger.log(Level.INFO,String.join(" ","Render areaBase:","x:"+xLeftTopRealWorld,",y:"+yLeftTopRealWorld,",widthCanvasRealWorld:"+widthCanvasRealWorld,",highCanvasRealWorld:"+highCanvasRealWorld));
        for (AreaBase item:areaBase){
            renderAreaBase(item,g,highCanvasRealWorld,widthCanvasRealWorld);
        }
    }



    private int convert2CanvasX(double xRealWorld){
        return (int) (((xRealWorld-xLeftTopRealWorld)/zoom));
    }
    private int convert2CanvasY(double yRealWorld){
        return (int) (((yLeftTopRealWorld-yRealWorld)/zoom));
    }

    private int convert2CanvasLength(double lengthRealWorld){
        return (int) ((lengthRealWorld/zoom));
    }

    private void renderRoad(RoadArea roadArea, Graphics g){
        int x= convert2CanvasX(roadArea.getLeftUpX());
        int y= convert2CanvasY(roadArea.getLeftUpY());
        int width= convert2CanvasLength(roadArea.getWidth());
        int high= convert2CanvasLength(roadArea.getHight());
        logger.log(Level.INFO,String.join(" ","Render road:","x:"+x,",y:"+y,",width:"+width,",high:"+high));
        g.setColor(new Color(77, 208, 225));
        g.fillRect(x,y,width,high);
    }


    private void renderBuilding(BuildingBase buildingBase, Graphics g){
        // Fill Building Area
        int x= convert2CanvasX(buildingBase.getLeftUpX());
        int y= convert2CanvasY(buildingBase.getLeftUpY());
        int width= convert2CanvasLength(buildingBase.getWidth());
        int high= convert2CanvasLength(buildingBase.getHight());
        logger.log(Level.INFO,String.join(" ","Render road:","x:"+x,",y:"+y,",width:"+width,",high:"+high));

        //Draw public building area (Road)
        int xPublicRoadLU=convert2CanvasX(buildingBase.getLeftUpXPublicArea());
        int yPublicRoadLU=convert2CanvasY(buildingBase.getLeftUpYPublicArea());
        int roadWidth=convert2CanvasLength(buildingBase.getPublicRoadWidth());
        int roadHight=convert2CanvasLength(buildingBase.getPublicRoadHigh());


        //Draw private building area
        int xPrivateBuildingArea=convert2CanvasX(buildingBase.getLeftUpXBuildingWall());
        int yPrivateBuildingArea=convert2CanvasY(buildingBase.getLeftUpYBuildingWall());
        int privateWidth=convert2CanvasLength(buildingBase.getPrivateWallWidth());
        int privateHigh=convert2CanvasLength(buildingBase.getPrivateWallHigh());

        if (buildingBase instanceof Apartment){
            renderApartment(x,y,width,high,xPublicRoadLU,yPublicRoadLU,roadWidth,roadHight,xPrivateBuildingArea,yPrivateBuildingArea,privateWidth,privateHigh,g);
        }else if (buildingBase instanceof House){
            renderHouse(x,y,width,high,xPublicRoadLU,yPublicRoadLU,roadWidth,roadHight,xPrivateBuildingArea,yPrivateBuildingArea,privateWidth,privateHigh,g);
        }else if(buildingBase instanceof Hospital){
            renderHospital(x,y,width,high,xPublicRoadLU,yPublicRoadLU,roadWidth,roadHight,xPrivateBuildingArea,yPrivateBuildingArea,privateWidth,privateHigh,g);
        }else if(buildingBase instanceof Mall){
            renderMall(x,y,width,high,xPublicRoadLU,yPublicRoadLU,roadWidth,roadHight,xPrivateBuildingArea,yPrivateBuildingArea,privateWidth,privateHigh,g);
        }else if(buildingBase instanceof Office){
            renderOffice(x,y,width,high,xPublicRoadLU,yPublicRoadLU,roadWidth,roadHight,xPrivateBuildingArea,yPrivateBuildingArea,privateWidth,privateHigh,g);
        }else if(buildingBase instanceof Park){
            renderPark(x,y,width,high,xPublicRoadLU,yPublicRoadLU,roadWidth,roadHight,xPrivateBuildingArea,yPrivateBuildingArea,privateWidth,privateHigh,g);
        }else if(buildingBase instanceof Restaurant){
            renderRestaurant(x,y,width,high,xPublicRoadLU,yPublicRoadLU,roadWidth,roadHight,xPrivateBuildingArea,yPrivateBuildingArea,privateWidth,privateHigh,g);
        }else if(buildingBase instanceof School){
            renderSchool(x,y,width,high,xPublicRoadLU,yPublicRoadLU,roadWidth,roadHight,xPrivateBuildingArea,yPrivateBuildingArea,privateWidth,privateHigh,g);
        }else {

        }
    }

    private void renderStringWithinBuilding(int x,int y,int High, String string,Graphics g){
        g.drawString(string,x,y);
    }

    private void renderHouse(int x,int y,int buildingWidth,int buildingHigh,
                             int xBuildingRoad,int yBuildingRoad,int roadWidth,int roadHigh,
                             int xBuildingPrivate,int yBuildingPriavte,int buildingPrivateWidth,int buildingPrivateHigh,
                             Graphics g){

        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(77, 208, 225));
//        g2d.fillRect(x,y,buildingWidth,buildingHigh);
        g2d.setColor(new Color(224, 224, 224, 255));
        g2d.fillRect(xBuildingRoad,yBuildingRoad,roadWidth,roadHigh);
        g2d.setColor(new Color(149, 117, 205));
//        g2d.fillRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh);
        int arcWidth=(int)(buildingPrivateHigh*0.1);
        g2d.fillRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        g2d.setColor(new Color(69, 39, 160));
        g2d.drawRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        renderStringWithinBuilding(xBuildingPrivate,yBuildingPriavte,buildingPrivateHigh,"House",g);
    }

    private void renderApartment(int x,int y,int buildingWidth,int buildingHigh,
                             int xBuildingRoad,int yBuildingRoad,int roadWidth,int roadHigh,
                             int xBuildingPrivate,int yBuildingPriavte,int buildingPrivateWidth,int buildingPrivateHigh,
                             Graphics g){

        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(77, 208, 225));
//        g2d.fillRect(x,y,buildingWidth,buildingHigh);
        g2d.setColor(new Color(224, 224, 224, 255));
        g2d.fillRect(xBuildingRoad,yBuildingRoad,roadWidth,roadHigh);
        g2d.setColor(new Color(38, 166, 154));
//        g2d.fillRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh);
        int arcWidth=(int)(buildingPrivateHigh*0.1);
        g2d.fillRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        g2d.setColor(new Color(0, 105, 92));
        g2d.drawRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        renderStringWithinBuilding(xBuildingPrivate,yBuildingPriavte,buildingPrivateHigh,"Apartment",g);
    }

    private void renderHospital(int x,int y,int buildingWidth,int buildingHigh,
                             int xBuildingRoad,int yBuildingRoad,int roadWidth,int roadHigh,
                             int xBuildingPrivate,int yBuildingPriavte,int buildingPrivateWidth,int buildingPrivateHigh,
                             Graphics g){
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(77, 208, 225));
//        g2d.fillRect(x,y,buildingWidth,buildingHigh);
        g2d.setColor(new Color(224, 224, 224, 255));
        g2d.fillRect(xBuildingRoad,yBuildingRoad,roadWidth,roadHigh);
        g2d.setColor(new Color(233, 30, 99));
//        g2d.fillRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh);
        int arcWidth=(int)(buildingPrivateHigh*0.1);
        g2d.fillRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        g2d.setColor(new Color(136, 14, 79));
        g2d.drawRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        renderStringWithinBuilding(xBuildingPrivate,yBuildingPriavte,buildingPrivateHigh,"Hospital",g);
    }

    private void renderMall(int x,int y,int buildingWidth,int buildingHigh,
                                int xBuildingRoad,int yBuildingRoad,int roadWidth,int roadHigh,
                                int xBuildingPrivate,int yBuildingPriavte,int buildingPrivateWidth,int buildingPrivateHigh,
                                Graphics g){
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(77, 208, 225));
//        g2d.fillRect(x,y,buildingWidth,buildingHigh);
        g2d.setColor(new Color(224, 224, 224, 255));
        g2d.fillRect(xBuildingRoad,yBuildingRoad,roadWidth,roadHigh);
        g2d.setColor(new Color(156, 204, 101));
//        g2d.fillRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh);
        int arcWidth=(int)(buildingPrivateHigh*0.1);
        g2d.fillRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        g2d.setColor(new Color(51, 105, 30));
        g2d.drawRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        renderStringWithinBuilding(xBuildingPrivate,yBuildingPriavte,buildingPrivateHigh,"Mall",g);
    }

    private void renderOffice(int x,int y,int buildingWidth,int buildingHigh,
                            int xBuildingRoad,int yBuildingRoad,int roadWidth,int roadHigh,
                            int xBuildingPrivate,int yBuildingPriavte,int buildingPrivateWidth,int buildingPrivateHigh,
                            Graphics g){
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(77, 208, 225));
//        g2d.fillRect(x,y,buildingWidth,buildingHigh);
        g2d.setColor(new Color(224, 224, 224, 255));
        g2d.fillRect(xBuildingRoad,yBuildingRoad,roadWidth,roadHigh);
        g2d.setColor(new Color(255, 193, 7));
//        g2d.fillRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh);
        int arcWidth=(int)(buildingPrivateHigh*0.1);
        g2d.fillRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        g2d.setColor(new Color(255, 143, 0));
        g2d.drawRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        renderStringWithinBuilding(xBuildingPrivate,yBuildingPriavte,buildingPrivateHigh,"Office",g);
    }

    private void renderPark(int x,int y,int buildingWidth,int buildingHigh,
                              int xBuildingRoad,int yBuildingRoad,int roadWidth,int roadHigh,
                              int xBuildingPrivate,int yBuildingPriavte,int buildingPrivateWidth,int buildingPrivateHigh,
                              Graphics g){
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(77, 208, 225));
//        g2d.fillRect(x,y,buildingWidth,buildingHigh);
        g2d.setColor(new Color(224, 224, 224, 255));
        g2d.fillRect(xBuildingRoad,yBuildingRoad,roadWidth,roadHigh);
        g2d.setColor(new Color(255, 112, 67));
//        g2d.fillRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh);
        int arcWidth=(int)(buildingPrivateHigh*0.1);
        g2d.fillRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        g2d.setColor(new Color(216, 67, 21));
        g2d.drawRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        renderStringWithinBuilding(xBuildingPrivate,yBuildingPriavte,buildingPrivateHigh,"Park",g);
    }

    private void renderRestaurant(int x,int y,int buildingWidth,int buildingHigh,
                            int xBuildingRoad,int yBuildingRoad,int roadWidth,int roadHigh,
                            int xBuildingPrivate,int yBuildingPriavte,int buildingPrivateWidth,int buildingPrivateHigh,
                            Graphics g){
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(77, 208, 225));
//        g2d.fillRect(x,y,buildingWidth,buildingHigh);
        g2d.setColor(new Color(224, 224, 224, 255));
        g2d.fillRect(xBuildingRoad,yBuildingRoad,roadWidth,roadHigh);
        g2d.setColor(new Color(205, 220, 57));
//        g2d.fillRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh);
        int arcWidth=(int)(buildingPrivateHigh*0.1);
        g2d.fillRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        g2d.setColor(new Color(130, 119, 23));
        g2d.drawRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        renderStringWithinBuilding(xBuildingPrivate,yBuildingPriavte,buildingPrivateHigh,"Restaurant",g);
    }

    private void renderSchool(int x,int y,int buildingWidth,int buildingHigh,
                                  int xBuildingRoad,int yBuildingRoad,int roadWidth,int roadHigh,
                                  int xBuildingPrivate,int yBuildingPriavte,int buildingPrivateWidth,int buildingPrivateHigh,
                                  Graphics g){
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(77, 208, 225));
//        g2d.fillRect(x,y,buildingWidth,buildingHigh);
        g2d.setColor(new Color(224, 224, 224, 255));
        g2d.fillRect(xBuildingRoad,yBuildingRoad,roadWidth,roadHigh);
        g2d.setColor(new Color(63, 81, 181));
//        g2d.fillRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh);
        int arcWidth=(int)(buildingPrivateHigh*0.1);
        g2d.fillRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        g2d.setColor(new Color(40, 53, 147, 255));
        g2d.drawRoundRect(xBuildingPrivate,yBuildingPriavte,buildingPrivateWidth,buildingPrivateHigh,arcWidth,arcWidth);
        renderStringWithinBuilding(xBuildingPrivate,yBuildingPriavte,buildingPrivateHigh,"School",g);
    }


    public int getHighCanvas() {
        return highCanvas;
    }

    public void setHighCanvas(int highCanvas) {
        this.highCanvas = highCanvas;
    }

    public int getWidthCanvas() {
        return widthCanvas;
    }

    public void setWidthCanvas(int widthCanvas) {
        this.widthCanvas = widthCanvas;
    }

    public double getyLeftTopRealWorld() {
        return yLeftTopRealWorld;
    }

    public void setyLeftTopRealWorld(double yLeftTopRealWorld) {
        this.yLeftTopRealWorld = yLeftTopRealWorld;
    }

    public double getxLeftTopRealWorld() {
        return xLeftTopRealWorld;
    }

    public void setxLeftTopRealWorld(double xLeftTopRealWorld) {
        this.xLeftTopRealWorld = xLeftTopRealWorld;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
