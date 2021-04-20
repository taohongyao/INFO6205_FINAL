package info6205.virus.simulation.entity;

import java.util.UUID;

public abstract class MaskBase {
    protected String id;
    protected boolean usage;
    protected double effective;
    protected double decayRate;

    public MaskBase(boolean usage, double effective, double decayRate) {
        this.usage = usage;
        this.effective = effective;
        this.decayRate = decayRate;
        id = UUID.randomUUID().toString();
    }

    public boolean isWare() {
        return usage;
    }

    public void wareMask(){
        usage=true;
    }

    public void unWareMask(){
        usage=false;
    }

    public double getEffective() {
        return effective;
    }

    public double getDecayRate() {
        return decayRate;
    }


}
