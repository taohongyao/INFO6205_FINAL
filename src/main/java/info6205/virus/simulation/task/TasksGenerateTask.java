package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.AreaBase;
import info6205.virus.simulation.entity.PeopleBase;
import info6205.virus.simulation.entity.RoadArea;
import info6205.virus.simulation.entity.building.*;
import info6205.virus.simulation.manager.AreaManger;
import info6205.virus.simulation.map.SimulationMap;
import info6205.virus.simulation.map.Time;

public class TasksGenerateTask extends TaskBase{
    private static double defaultSettingSpeed=0.1;
    private static double socialDistance=3;
    private static double socialDistanceKeepRate=0.8;
    private AreaManger areaManger;

    public TasksGenerateTask(AreaManger areaManger) {
        this.areaManger = areaManger;
    }

    @Override
    public void executeTask(PeopleBase peopleBase) {
        while (!isFinished()){
            int taskAssign=getRandom().nextInt(1000);
            if(taskAssign<800){
                dailyTask(peopleBase);
            }else {
                randomTask(peopleBase);
            }
        }
    }

    private void randomTask(PeopleBase peopleBase) {
        int taskAssign=getRandom().nextInt(1000);
        SimulationMap map=peopleBase.getMap();
        Time time=map.getCurrentTime();
        // To sleep
        if(taskAssign<200) {
            if (peopleBase.isNeedToSleep() && time == Time.NIGHT) {
                goHomeAndSleep(peopleBase);
                finish();
            }
            // To Mall
        }else if (taskAssign<400){
            goToPlace(peopleBase,areaManger.getRandomMall(),true);
            finish();
            // To Park
        }else if(taskAssign<600){
            if(time==Time.MORNING||time==Time.AFTERNOON){
                goToPlace(peopleBase,areaManger.getRandomPark(),true);
                finish();
            }
            // To Home
        }else if(taskAssign<800){
            goToPlace(peopleBase,areaManger.getRandomHouse(),false);
            finish();
            // To Restaurant
        }else if(taskAssign<1000){
            goToEating(peopleBase, areaManger.getRandomRestaurant(),null);
            finish();
        }
    }

    private void dailyTask(PeopleBase peopleBase){
        int taskAssign=getRandom().nextInt(1000);
        SimulationMap map=peopleBase.getMap();
        Time time=map.getCurrentTime();
        if(peopleBase.isNeedToEatBreakFast()&&time==Time.MORNING){
            //eat at home
            if(taskAssign<700){
                if(peopleBase.isAtHome()){
                    goToEating(peopleBase,EatingTask.Meal.BREAKFAST);
                }else {
                    goHome(peopleBase,peopleBase.getHome());
                }
            }else {
                goToEating(peopleBase, areaManger.getRandomRestaurant(), EatingTask.Meal.BREAKFAST);
            }
            finish();
        }else if(peopleBase.isNeedToEatLunch()&&time==Time.AFTERNOON){
            //eat at outside
            if(taskAssign<500){
                goToEating(peopleBase, areaManger.getRandomRestaurant(), EatingTask.Meal.LUNCH);
            }else {
                if(peopleBase.isAtHome()){
                    goToEating(peopleBase,EatingTask.Meal.LUNCH);
                }else {
                    goHome(peopleBase,peopleBase.getHome());
                }
            }
            finish();
        }else if(peopleBase.isNeedToEatDinner()&&time==Time.NIGHT){
            //eat at home
            if(taskAssign<800){
                if(peopleBase.isAtHome()){
                    goToEating(peopleBase, EatingTask.Meal.DINNER);
                }else {
                    goHome(peopleBase,peopleBase.getHome());
                }
            }else {
                goToEating(peopleBase, areaManger.getRandomRestaurant(),EatingTask.Meal.DINNER);
            }
            finish();

        }else if(peopleBase.isNeedToMorningWork()&&time==Time.MORNING){
            goToWork(peopleBase,peopleBase.getOffice(),Time.MORNING);
//            peopleBase.setNeedToMorningWork(false);
            finish();
        }else if(peopleBase.isNeedToAfternoonWork()&&time==Time.AFTERNOON){
            goToWork(peopleBase,peopleBase.getOffice(),Time.AFTERNOON);
//            peopleBase.setNeedToAfternoonWork(false);
            finish();
        }else if(peopleBase.isNeedToSchool()&&time==Time.MORNING){
            goToSchool(peopleBase,peopleBase.getSchool());
//            peopleBase.setNeedToSchool(false);
            finish();
            // To sleep
        }else if(time==Time.MIDNIGHT&&peopleBase.isNeedToSleep()){
            peopleBase.cleanAllTasks();
            goHomeAndSleep(peopleBase);
            finish();
            // To home at night
        }else if(time==Time.NIGHT||time==Time.MIDNIGHT) {
            goHome(peopleBase, peopleBase.getHome());
            finish();
        }
    }

    private void goHome(PeopleBase peopleBase, House house){
        try {
            MoveInRoadTask moveInRoadTask=new MoveInRoadTask(defaultSettingSpeed,defaultSettingSpeed,socialDistanceKeepRate, house,areaManger);
            MoveInAreaTask moveInAreaTask=new MoveInAreaTask(house,areaManger);
            RandomWalkTask randomWalkTask=new RandomWalkTask(0,defaultSettingSpeed,0,house.getTaskTime());
            // Task series
            peopleBase.addTask(new MaskOperationTask(true));
            peopleBase.addTask(new LeaveBuildingTask());
            peopleBase.addTask(moveInRoadTask);
            peopleBase.addTask(moveInAreaTask);
            peopleBase.addTask(new MaskOperationTask(false));
            peopleBase.addTask(randomWalkTask);
            peopleBase.addTask(new TasksGenerateTask(areaManger));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void goToEating(PeopleBase peopleBase, Restaurant restaurant, EatingTask.Meal type){
        MoveInRoadTask moveInRoadTask=new MoveInRoadTask(defaultSettingSpeed,defaultSettingSpeed,socialDistanceKeepRate, restaurant,areaManger);
        MoveInAreaTask moveInAreaTask=new MoveInAreaTask(restaurant,areaManger);

        peopleBase.addTask(new MaskOperationTask(true));
        peopleBase.addTask(new LeaveBuildingTask());
        peopleBase.addTask(moveInRoadTask);
        peopleBase.addTask(moveInAreaTask);
        peopleBase.addTask(new MaskOperationTask(false));
        EatingTask eatingTask=new EatingTask(peopleBase.getEatingTimeDuration(),type);
        peopleBase.addTask(eatingTask);
        peopleBase.addTask(new MaskOperationTask(true));
        peopleBase.addTask(new TasksGenerateTask(areaManger));

    }

    private void goToEating(PeopleBase peopleBase, EatingTask.Meal type){
        EatingTask eatingTask=new EatingTask(peopleBase.getEatingTimeDuration(),type);
        peopleBase.addTask(new MaskOperationTask(false));
        peopleBase.addTask(eatingTask);
        peopleBase.addTask(new TasksGenerateTask(areaManger));
    }

    private void goHomeAndSleep(PeopleBase peopleBase){
        SleepTask sleepTask=new SleepTask(peopleBase.getSleepTimeDuration());
        MoveInRoadTask moveInRoadTask=new MoveInRoadTask(defaultSettingSpeed,defaultSettingSpeed,socialDistanceKeepRate, peopleBase.getHome(),areaManger);
        MoveInAreaTask moveInAreaTask=new MoveInAreaTask(peopleBase.getHome(),areaManger);
        peopleBase.addTask(new MaskOperationTask(true));
        peopleBase.addTask(new LeaveBuildingTask());
        peopleBase.addTask(moveInRoadTask);
        peopleBase.addTask(moveInAreaTask);
        peopleBase.addTask(new MaskOperationTask(false));
        peopleBase.addTask(sleepTask);
        peopleBase.addTask(new TasksGenerateTask(areaManger));
    }

    private void goToSchool(PeopleBase peopleBase, School school){
        try {
            MoveInRoadTask moveInRoadTask=new MoveInRoadTask(defaultSettingSpeed,defaultSettingSpeed,socialDistanceKeepRate, school,areaManger);
            MoveInAreaTask moveInAreaTask=new MoveInAreaTask(school,areaManger);
            RandomWalkTask randomWalkTask=new RandomWalkTask(socialDistance,defaultSettingSpeed,socialDistanceKeepRate,school.getTaskTime());
            // Task series
            peopleBase.addTask(new MaskOperationTask(true));
            peopleBase.addTask(new LeaveBuildingTask());
            peopleBase.addTask(moveInRoadTask);
            peopleBase.addTask(moveInAreaTask);
            peopleBase.addTask(randomWalkTask);
            peopleBase.addTask(new UpdateSchoolStateTask(false));
            peopleBase.addTask(new TasksGenerateTask(areaManger));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToWork(PeopleBase peopleBase, BuildingBase buildingBase, Time workingTime){
        try {
            MoveInRoadTask moveInRoadTask=new MoveInRoadTask(defaultSettingSpeed,defaultSettingSpeed,socialDistanceKeepRate, buildingBase,areaManger);
            MoveInAreaTask moveInAreaTask=new MoveInAreaTask(buildingBase,areaManger);
            RandomWalkTask randomWalkTask=new RandomWalkTask(socialDistance,defaultSettingSpeed,socialDistanceKeepRate,buildingBase.getTaskTime());
            // Task series
            peopleBase.addTask(new MaskOperationTask(true));
            peopleBase.addTask(new LeaveBuildingTask());
            peopleBase.addTask(moveInRoadTask);
            peopleBase.addTask(moveInAreaTask);
            peopleBase.addTask(randomWalkTask);
            peopleBase.addTask(new UpdateWorkingStateTask(workingTime,false));
            peopleBase.addTask(new TasksGenerateTask(areaManger));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToPlace(PeopleBase peopleBase, BuildingBase buildingBase,boolean applySocialDistance){
        try {
            MoveInRoadTask moveInRoadTask=new MoveInRoadTask(defaultSettingSpeed,defaultSettingSpeed,socialDistanceKeepRate, buildingBase,areaManger);
            MoveInAreaTask moveInAreaTask=new MoveInAreaTask(buildingBase,areaManger);
            RandomWalkTask randomWalkTask=null;
            if(!applySocialDistance){
                randomWalkTask=new RandomWalkTask(0,defaultSettingSpeed,0,buildingBase.getTaskTime());
            }else {
                randomWalkTask=new RandomWalkTask(socialDistance,defaultSettingSpeed,socialDistanceKeepRate,buildingBase.getTaskTime());
            }

            // Task series
            peopleBase.addTask(new MaskOperationTask(true));
            peopleBase.addTask(new LeaveBuildingTask());
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
