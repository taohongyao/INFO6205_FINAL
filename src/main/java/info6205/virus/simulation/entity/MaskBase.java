package info6205.virus.simulation.entity;

import java.util.UUID;

public class MaskBase {
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
}
