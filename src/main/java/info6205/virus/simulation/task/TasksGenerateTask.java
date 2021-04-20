package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.BuildingBase;
import info6205.virus.simulation.entity.building.House;
import info6205.virus.simulation.entity.building.Restaurant;

public class TasksGenerateTask extends TaskBase{
    private static double defaultSettingSpeed=0.5;
    private static double socialDistance;
    private static double socialDistanceKeepRate;

    @Override
    public void executeTask(PeopleBase peopleBase) {


        finish();
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
            peopleBase.addTask(new TasksGenerateTask());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void goToEating(PeopleBase peopleBase, Restaurant restaurant){

    }

    private void goToEating(PeopleBase peopleBase){
        EatingTask eatingTask=new EatingTask(peopleBase.getEatingTimeDuration());
        peopleBase.addTask(new MaskOperationTask(false));
        peopleBase.addTask(eatingTask);
        peopleBase.addTask(new TasksGenerateTask());
    }

    private void sleep(PeopleBase peopleBase){
        SleepTask sleepTask=new SleepTask(peopleBase.getSleepTimeDuration());
        peopleBase.addTask(new MaskOperationTask(false));
        peopleBase.addTask(sleepTask);
        peopleBase.addTask(new TasksGenerateTask());
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
            peopleBase.addTask(new TasksGenerateTask());

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
