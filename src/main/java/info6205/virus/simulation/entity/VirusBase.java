package info6205.virus.simulation.entity;

public abstract class VirusBase {
    private double kFactor;
    private double rFactor;

    abstract public VirusBase generate();


    public double getkFactor() {
        return kFactor;
    }

    public void setkFactor(double kFactor) {
        this.kFactor = kFactor;
    }

    public double getrFactor() {
        return rFactor;
    }

    public void setrFactor(double rFactor) {
        this.rFactor = rFactor;
    }
}
