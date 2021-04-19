package info6205.virus.simulation.task;

import info6205.virus.simulation.entity.PeopleBase;

import java.util.Random;

public abstract class MoveTask extends TaskBase{
    protected double socialDistance;
    protected double keepSocialDistanceRate;
    protected double speed;
    protected static Random random;

    public MoveTask(double socialDistance, double speed, double keepSocialDistanceRate) {
        this.socialDistance = socialDistance;
        this.speed = speed;
        this.keepSocialDistanceRate=keepSocialDistanceRate;
        random=new Random();
    }

    public MoveTask(double socialDistance, double speed,Long walkSeed, double keepSocialDistanceRate) {
        this.socialDistance = socialDistance;
        this.speed = speed;
        this.keepSocialDistanceRate=keepSocialDistanceRate;
        random=new Random(walkSeed);
    }

    public static Random getRandom() {
        return random;
    }

    public double getKeepSocialDistanceRate() {
        return keepSocialDistanceRate;
    }

    public void setKeepSocialDistanceRate(double keepSocialDistanceRate) {
        this.keepSocialDistanceRate = keepSocialDistanceRate;
    }

    public static void setRandom(Random random) {
        MoveTask.random = random;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSocialDistance() {
        return socialDistance;
    }

    public void setSocialDistance(double socialDistance) {
        this.socialDistance = socialDistance;
    }
}
