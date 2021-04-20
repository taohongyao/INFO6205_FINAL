package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.entity.building.House;
import info6205.virus.simulation.entity.building.Restaurant;
import info6205.virus.simulation.manager.AreaManger;
import info6205.virus.simulation.map.SimulationMap;
import info6205.virus.simulation.map.Time;

public class TasksGenerateTask extends TaskBase{
    private static double defaultSettingSpeed=0.5;
    private static double socialDistance;
    private static double socialDistanceKeepRate;
    private AreaManger areaManger;

    public TasksGenerateTask(AreaManger areaManger) {
        this.areaManger = areaManger;
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        int taskAssign=getRandom().nextInt(1000);
        SimulationMap map=peopleBase.getMap();
        Time time=map.getCurrentTime();
        while (isFinished()){
            if(peopleBase.isNeedToMorningWork()&&time==Time.MORNING){
                goToPlace(peopleBase,peopleBase.getOffice());
                finish();
            }else if(peopleBase.isNeedToAfternoonWork()&&time==Time.AFTERNOON){
                goToPlace(peopleBase,peopleBase.getOffice());
                finish();
            }else if(peopleBase.isNeedToSchool()&&time==Time.MORNING){
                goToPlace(peopleBase,peopleBase.getSchool());
                finish();
            // To sleep
            }else if(time==Time.MIDNIGHT&&peopleBase.isNeedToSleep()){
                peopleBase.cleanAllTasks();
                goToPlace(peopleBase,areaManger.getRandomHouse());
                goHomeAndSleep(peopleBase);
                finish();
            // To home at night
            }else if(time==Time.NIGHT){
                goHome(peopleBase,areaManger.getRandomHouse());
                finish();
            }
            // To sleep
            if(taskAssign<200) {
                if (peopleBase.isNeedToSleep() && time == Time.NIGHT) {
                    goHomeAndSleep(peopleBase);
                    finish();
                }
            // To Mall
            }if (taskAssign<400){
                goToPlace(peopleBase,areaManger.getRandomMall());
                finish();
            // To Park
            }else if(taskAssign<600){
                if(time==Time.MORNING||time==Time.AFTERNOON){
                    goToPlace(peopleBase,areaManger.getRandomPark());
                    finish();
                }
            // To Home
            }else if(taskAssign<700){
                goToPlace(peopleBase,areaManger.getRandomHouse());
                finish();
            // To Restaurant
            }else if(taskAssign<800){
                // TODO: 4/20/2021 eating task
//                goToPlace(peopleBase,areaManger.getRandomRestaurant());
//                finish();
            }else {

            }

        }
    }

    private void goHome(PeopleBase peopleBase, House house){
        RoadArea des=house.getPublicArea();
        try {
            MoveInRoadTask moveInRoadTask=new MoveInRoadTask(defaultSettingSpeed, house);
            MoveInAreaTask moveInAreaTask=new MoveInAreaTask(house);
            RandomWalkTask randomWalkTask=new RandomWalkTask(socialDistance,defaultSettingSpeed,socialDistanceKeepRate,house.getTaskTime());
            // Task series
            peopleBase.addTask(moveInRoadTask);
            peopleBase.addTask(moveInAreaTask);
            peopleBase.addTask(new MaskOperationTask(false));
            peopleBase.addTask(randomWalkTask);
            peopleBase.addTask(new TasksGenerateTask(areaManger));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void goToEating(PeopleBase peopleBase, Restaurant restaurant){
        // TODO: 4/20/2021 eating task
    }

    private void goToEating(PeopleBase peopleBase){
        EatingTask eatingTask=new EatingTask(peopleBase.getEatingTimeDuration());
        peopleBase.addTask(new MaskOperationTask(false));
        peopleBase.addTask(eatingTask);
        peopleBase.addTask(new TasksGenerateTask(areaManger));
    }

    private void goHomeAndSleep(PeopleBase peopleBase){
        SleepTask sleepTask=new SleepTask(peopleBase.getSleepTimeDuration());
        MoveInRoadTask moveInRoadTask=new MoveInRoadTask(defaultSettingSpeed, peopleBase.getHome());
        MoveInAreaTask moveInAreaTask=new MoveInAreaTask(peopleBase.getHome());
        peopleBase.addTask(moveInRoadTask);
        peopleBase.addTask(moveInAreaTask);
        peopleBase.addTask(new MaskOperationTask(false));
        peopleBase.addTask(sleepTask);
        peopleBase.addTask(new TasksGenerateTask(areaManger));
    }

    private void goToPlace(PeopleBase peopleBase, BuildingBase buildingBase){
        RoadArea des=buildingBase.getPublicArea();
        try {
            MoveInRoadTask moveInRoadTask=new MoveInRoadTask(defaultSettingSpeed, buildingBase);
            MoveInAreaTask moveInAreaTask=new MoveInAreaTask(buildingBase);
            RandomWalkTask randomWalkTask=new RandomWalkTask(socialDistance,defaultSettingSpeed,socialDistanceKeepRate,buildingBase.getTaskTime());
            // Task series
            peopleBase.addTask(new MaskOperationTask(true));
            peopleBase.addTask(moveInRoadTask);
            peopleBase.addTask(moveInAreaTask);
            peopleBase.addTask(randomWalkTask);
            peopleBase.addTask(new TasksGenerateTask(areaManger));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getDefaultSettingSpeed() {
        return defaultSettingSpeed;
    }

    public static void setDefaultSettingSpeed(double defaultSettingSpeed) {
        TasksGenerateTask.defaultSettingSpeed = defaultSettingSpeed;
    }

    public static double getSocialDistance() {
        return socialDistance;
    }

    public static void setSocialDistance(double socialDistance) {
        TasksGenerateTask.socialDistance = socialDistance;
    }

    public static double getSocialDistanceKeepRate() {
        return socialDistanceKeepRate;
    }

    public static void setSocialDistanceKeepRate(double socialDistanceKeepRate) {
        TasksGenerateTask.socialDistanceKeepRate = socialDistanceKeepRate;
    }
}
