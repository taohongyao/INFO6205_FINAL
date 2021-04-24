package info6205.virus.simulation.gui;

import info6205.virus.simulation.console.SimulationApplication;
import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.MaskBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.*;
import info6205.virus.simulation.entity.people.Adult;
import info6205.virus.simulation.entity.people.Elder;
import info6205.virus.simulation.entity.people.Teen;
import info6205.virus.simulation.logger.Log;
import info6205.virus.simulation.map.GridElement;
import info6205.virus.simulation.map.SimulationMap;
import info6205.virus.simulation.task.RandomWalkTask;
import info6205.virus.simulation.util.GridElementUtil;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class SimulationRender {
    private SimulationApplicationWindows simulationApplicationWindows;
    private double xLeftTopRealWorld;
    private double yLeftTopRealWorld;
    private double zoom;
    private Map<String,List<Integer>> record;
    private PeopleBase selectedPeople;
    private int selectedX;
    private int selectedY;
    private SimulationApplication simulationApplication;

    private static Logger logger=Logger.getLogger(SimulationRender.class.getName());


    public SimulationRender(SimulationApplicationWindows simulationApplicationWindows, double xLeftTopRealWorld, double yLeftTopRealWorld, double zoom,SimulationApplication simulationApplication) {
        this.simulationApplicationWindows = simulationApplicationWindows;
        this.xLeftTopRealWorld = xLeftTopRealWorld;
        this.yLeftTopRealWorld = yLeftTopRealWorld;
        this.zoom = zoom;
        record =new HashMap<>();
        this.simulationApplication=simulationApplication;
    }


    public void renderAreaBase(AreaBase areaBase,Graphics g,double highCanvasRealWorld,double widthCanvasRealWorld){
        double x=areaBase.getLeftUpX();
        double y=areaBase.getLeftUpY();
        double x2=areaBase.getRightDownX();
        double y2=areaBase.getRightDownY();
        logger.log(Log.DEBUG,String.join(" ","AreaBase ","x:"+x,",y:"+y));
        logger.log(Log.DEBUG,String.join(" ","Bounce ","X["+xLeftTopRealWorld,":"+(xLeftTopRealWorld+widthCanvasRealWorld)+"]","Y["+(yLeftTopRealWorld-highCanvasRealWorld),":"+yLeftTopRealWorld+"]"));
        if(x2>=xLeftTopRealWorld&&x<xLeftTopRealWorld+widthCanvasRealWorld&&y2<=yLeftTopRealWorld&&y>yLeftTopRealWorld-highCanvasRealWorld){
            logger.log(Log.DEBUG,"Start render.");
            if(areaBase instanceof RoadArea){
                renderRoad((RoadArea) areaBase,g);
            }else if(areaBase instanceof BuildingBase){
                renderBuilding((BuildingBase) areaBase,g);
            }else {
                logger.log(Log.DEBUG,"Nothing to render.");
            }
        }else {
            logger.log(Log.DEBUG,"Out of render scope.");
        }
    }

    public void renderAreaBase(List<? extends AreaBase> areaBase,Graphics g){
        double widthCanvasRealWorld = getWidthCanvas()*zoom;
        double highCanvasRealWorld = getHighCanvas()*zoom;
        logger.log(Log.DEBUG,String.join(" ","Render areaBase:","x:"+xLeftTopRealWorld,",y:"+yLeftTopRealWorld,",widthCanvasRealWorld:"+widthCanvasRealWorld,",highCanvasRealWorld:"+highCanvasRealWorld));
        for (AreaBase item:areaBase){
            renderAreaBase(item,g,highCanvasRealWorld,widthCanvasRealWorld);
        }
    }


    public double convertCanvas2RealWorldX(int x){
        return xLeftTopRealWorld+x*zoom;
    }
    public double convertCanvas2RealWorldY(int y){
        return yLeftTopRealWorld-y*zoom;
    }
    public double convert2RealWorldLength(double lengthRealWorld){
        return lengthRealWorld*zoom;
    }

    public int convert2CanvasX(double xRealWorld){
        return (int) (((xRealWorld-xLeftTopRealWorld)/zoom));
    }
    public int convert2CanvasY(double yRealWorld){
        return (int) (((yLeftTopRealWorld-yRealWorld)/zoom));
    }

    public int convert2CanvasLength(double lengthRealWorld){
        return (int) ((lengthRealWorld/zoom)+1);
    }

    private void renderRoad(RoadArea roadArea, Graphics g){
        int x= convert2CanvasX(roadArea.getLeftUpX());
        int y= convert2CanvasY(roadArea.getLeftUpY());
        int width= convert2CanvasLength(roadArea.getWidth());
        int high= convert2CanvasLength(roadArea.getHight());
        logger.log(Log.DEBUG,String.join(" ","Render road:","x:"+x,",y:"+y,",width:"+width,",high:"+high));
        g.setColor(new Color(77, 208, 225));
        g.fillRect(x,y,width,high);
    }


    private void renderBuilding(BuildingBase buildingBase, Graphics g){
        // Fill Building Area
        int x= convert2CanvasX(buildingBase.getLeftUpX());
        int y= convert2CanvasY(buildingBase.getLeftUpY());
        int width= convert2CanvasLength(buildingBase.getWidth());
        int high= convert2CanvasLength(buildingBase.getHight());
        logger.log(Log.DEBUG,String.join(" ","Render road:","x:"+x,",y:"+y,",width:"+width,",high:"+high));

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

//    public void cleanLastCrossLine(Graphics g){
//        List<Integer> crossXY=record.get("CROSS");
//        if(crossXY!=null){
//            int x=crossXY.get(0);
//            int y=crossXY.get(1);
//            Graphics2D g2d= (Graphics2D) g;
//            g2d.setColor(Color.WHITE);
//            g2d.drawLine(0,y,widthCanvas,y);
//            g2d.drawLine(x,0,x,highCanvas);
//            g2d.fillRect(x,y-10,50,20);
//        }
//    }
    public void updateCrossLineXY(int x,int y){
        List<Integer> crossXY=record.get("CROSS");
        if(crossXY!=null){
            crossXY.set(0,x);
            crossXY.set(1,y);
        }else {
            crossXY=new ArrayList<>();
            crossXY.add(x);
            crossXY.add(y);
            record.put("CROSS",crossXY);
        }
    }

    public void drawRecordLine(Graphics g){
        synchronized (record){
            List<Integer> crossXY=record.get("CROSS");
            if(crossXY!=null){
                int x=crossXY.get(0);
                int y=crossXY.get(1);
                Graphics2D g2d= (Graphics2D) g;
                g2d.setStroke(new BasicStroke((float) 1));
                g2d.setColor(new Color(158, 158, 158));
                g2d.drawLine(0,y,getWidthCanvas(),y);
                g2d.drawLine(x,0,x,getHighCanvas());
                g2d.drawString(String.format("(%d,%d)",(int)(convertCanvas2RealWorldX(x)),(int)convertCanvas2RealWorldY(y)),x,y);
            }
        }
    }

    public Color getGradatedColor(int count){
        int band1=5;
        int band2=5;
        if(count<=band1){
            int r= (int) (156+(count*61.0/band1));
            return new Color(r,26,217);
        }else if( count>band1 && count<=band2) {
            int b= (int) (217-(count-band1)*(188.0/band2));
            return new Color(217,26,b);
        }else {
            return new Color(217, 26, 29);
        }
    }



    public void renderInfectedPanel(int x,int y,int panelWidth,int teen, int adult,int elder,Graphics g){
        int width=panelWidth/4;

//        g.setColor(new Color(216, 27, 96));
        int sum=teen+elder+adult;
        g.setColor(getGradatedColor(sum));
        g.fillRoundRect(x,y,width*3+100,20,5,5);
        g.setColor(new Color(255, 255, 255, 255));
        y=y+13;
        x=x+3;
        g.drawString(String.format("Infected people: %4d",sum),x,y);
        g.drawString(String.format("Infected elder: %4d",elder),x+width,y);
        g.drawString(String.format("Infected adult: %4d",adult),x+width*2,y);
        g.drawString(String.format("Infected teen: %4d",teen),x+width*3,y);
    }

    public void renderMouseOperationInfo(int x,int y,Graphics g){
        int panelWidth=140;
        int panelHigh=36;

        g.setColor(new Color(27, 81, 216));
        g.fillRoundRect(x,y,panelWidth,panelHigh,5,5);
        g.setColor(new Color(255, 255, 255, 255));
        y=y+13;
        x=x+6;
        g.drawString(String.format("Zoom+/-: Middle Wheel"),x,y);
        g.drawString(String.format("Move   : Mouse Drag"),x,y+panelHigh/2);
    }


//    public void renderCrossLine(int x,int y,Graphics g){
////        cleanLastCrossLine(g);
//        recordLastCrossLine(x,y);
//    }

    private void renderStringWithinBuilding(int x,int y,int High, String string,Graphics g){
        g.drawString(string,x,y);
    }



    public void renderPeopleList(List<?extends PeopleBase> list,Graphics g){
        for (PeopleBase peopleBase:list){

            if(peopleBase.isInfected()){
                renderPeopleBase(peopleBase,g,new Color(245, 0, 87));
            }else if(peopleBase.isFeelSick()){
                renderPeopleBase(peopleBase,g,new Color(171, 71, 188));
            }else if(peopleBase.isVaccine()){
                renderPeopleBase(peopleBase,g,new Color(129, 199, 132));
            }else if(peopleBase instanceof Elder){
                renderPeopleBase(peopleBase,g,new Color(0, 0, 0));
            }else if(peopleBase instanceof Adult){
                renderPeopleBase(peopleBase,g,new Color(84, 110, 122));
            }else if(peopleBase instanceof Teen){
                renderPeopleBase(peopleBase,g,new Color(255, 255, 255));
            }else {
                renderPeopleBase(peopleBase,g,new Color(38, 50, 56));
            }

        }
    }

    private double convertTimeToDouble(){
        int time=simulationApplication.getWorldTimeUnit()%simulationApplication.getTimeUnitADay();
        return (0.0+time)/simulationApplication.getTimeUnitADay();
    }
    private Color getColorWithDayTime(double daytime){
        int R=219;
        int G=245;
        int B=255;
        double rate=0;
        if(daytime<=0.25){
            // 6.00~12.00
            rate=0.5+daytime*2;

        }else if(daytime<0.75) {
            // 12.00~ 24.00
            rate=1.5-daytime*2;
        }else {
            // 24.00~ 6.00
            rate=2*daytime-1.5;
        }
        R= (int) (R*rate);
        G= (int) (G*rate);
        B= (int) (B*rate);

        return new Color(R, G, B, 255);
    }

    public void cleanCanvas(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double daytime=convertTimeToDouble();

        g2d.setColor(getColorWithDayTime(daytime));
        g2d.fillRect(0, 0, getWidthCanvas(), getHighCanvas());
    }

    public void drawCoordinate(Graphics g){
        int xLabel=10;
        int yLabel=10;
        int canvasWidth=getWidthCanvas()/xLabel;
        int canvasHigh=getHighCanvas()/yLabel;
        double width=getWidthCanvas()/(1.0*xLabel)*zoom;
        double high=getHighCanvas()/(1.0*yLabel)*zoom;
        for(int i=0;i<xLabel;i++){
            g.drawString(String.format("|%.2f",xLeftTopRealWorld+i*width), (int) (i*canvasWidth),getHighCanvas()-10);
        }
        for(int i=0;i<yLabel;i++){
            g.drawString("-", 0,(int) (i*canvasHigh));
            g.drawString(String.format("%.2f",yLeftTopRealWorld-i*high), 0,(int) (i*canvasHigh)+10);
        }
    }


    private List<Integer> getRecord(PeopleBase peopleBase){
        return record.get(peopleBase.getId());
    }



    private void putRecord(PeopleBase peopleBase,int x, int y, int size){
        List<Integer> list=record.get(peopleBase.getId());
        if(list!=null){
            list.clear();
            list.add(x);
            list.add(y);
            list.add(size);
        }else {
            list=new ArrayList<>();
            list.add(x);
            list.add(y);
            list.add(size);
            record.put(peopleBase.getId(),list);
        }
    }

    public void selectPeople(int x,int y){
        double xRealWorld=convertCanvas2RealWorldX(x);
        double yRealWorld=convertCanvas2RealWorldY(y);
        try {
            GridElement gridElement=simulationApplication.getMap().getGridElimentByXY(xRealWorld,yRealWorld);
            if (gridElement!=null){
                Set<PeopleBase> set=GridElementUtil.getBFSAreaGridsPeople(gridElement,1.0);
                Set<PeopleBase> set2=GridElementUtil.getBFSAreaGridsDeadPeople(gridElement,1.0);
                set.addAll(set2);
                if(set.size()>0){
                    for(PeopleBase peopleBase:set){
                        selectedPeople=peopleBase;
                        selectedX=x;
                        selectedY=y;
                        return;
                    }
                }
            }
        } catch (Exception e) {
        }
        selectedPeople=null;
    }

    public void renderSelectedPeople(Graphics g){
        if(selectedPeople!=null){
            renderPeoplePanel(selectedPeople,selectedX,selectedY,g);
        }
    }

    private void renderPeoplePanel(PeopleBase peopleBase,int xPanel,int yPanel, Graphics g){
        Graphics2D g2d= (Graphics2D) g;
        int x= xPanel+2;
        int y= yPanel-10;
        int width=160;
        int high=120;
        int arcWidth=10;
        g2d.setColor(new Color(41, 67, 78 ,100));
        g2d.fillRoundRect(x,y-high,width,high,arcWidth,arcWidth);
        g2d.setColor(new Color(75, 99, 110));
        g2d.drawRoundRect(x,y-high,width,high,arcWidth,arcWidth);

        g2d.setColor(new Color(255, 255, 255));
        int line=9;
        int lineSpace=high/line;
        int xSting=x+5;
        int yString=y-high-2;

        g2d.setFont(new Font("TimesRoman",Font.PLAIN,12));
        g2d.drawString(String.format("Type: %s",peopleBase.getPeopleType()),xSting,yString+lineSpace);
        g2d.drawString(String.format("Alive: %s",peopleBase.isAlive()),xSting,yString+lineSpace*2);
        g2d.drawString(String.format("Mask: %s",peopleBase.getMaskBase()==null?"False":peopleBase.getMaskBase().isWare()),xSting,yString+3*lineSpace);
        g2d.drawString(String.format("Task: %s",peopleBase.getCurrentTask().getName()),xSting,yString+4*lineSpace);
        g2d.drawString(String.format("Infected: %s",peopleBase.isInfected()),xSting,yString+5*lineSpace);
        g2d.drawString(String.format("Speed: %s",peopleBase.getWalkSpeed()),xSting,yString+6*lineSpace);
        g2d.drawString(String.format("SocialDistance: %s",peopleBase.getSocialDistance()),xSting,yString+7*lineSpace);
        g2d.drawString(String.format("SocialDistance Rate: %s",peopleBase.getKeepSocialDistanceRate()),xSting,yString+8*lineSpace);
        if(peopleBase.getCurrentTask() instanceof RandomWalkTask){
            RandomWalkTask randomWalkTask=(RandomWalkTask)peopleBase.getCurrentTask();
            g2d.drawString(String.format("Keep SocialDistance: %s",randomWalkTask.isKeepDistance()),xSting,yString+9*lineSpace);
        }else {
            g2d.drawString(String.format("Keep SocialDistance: %s",true),xSting,yString+9*lineSpace);
        }

    }

    private void renderPeopleBase(PeopleBase peopleBase, Graphics g,Color color){
        // Fill Building Area
        int size=(int)(0.5/zoom);
        int x= (int)(convert2CanvasX(peopleBase.getX())-size/2.0);
        int y= (int)(convert2CanvasY(peopleBase.getY())-size/2.0);
//        cleanLastRender(peopleBase,g);
        putRecord(peopleBase,x,y,size);

        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(new Color(38, 50, 56));
        g2d.setColor(color);
        g2d.fillOval(x,y,size,size);

        if(!peopleBase.isAlive()){
            int xRD=x+size;
            int yRD=y+size;
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x,y,xRD,yRD);
            g2d.drawLine(x,yRD,xRD,y);
        }

        MaskBase maskBase=peopleBase.getMaskBase();
        if(maskBase!=null&&maskBase.isWare()){
            int maskSize=(int)(1/zoom);
            int xMask=(int)(convert2CanvasX(peopleBase.getX())-maskSize/2.0);
            int yMask= (int)(convert2CanvasY(peopleBase.getY())-maskSize/2.0);
            g2d.setColor(new Color(8, 127, 35));
            g2d.drawOval(xMask,yMask,maskSize,maskSize);
        }
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
        return simulationApplicationWindows.getCanvas().getHeight();
    }



    public int getWidthCanvas() {
        return simulationApplicationWindows.getCanvas().getWidth();
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
