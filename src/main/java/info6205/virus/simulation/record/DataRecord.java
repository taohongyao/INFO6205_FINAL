package info6205.virus.simulation.record;

import java.util.ArrayList;
import java.util.List;

public class DataRecord {

    protected static List<List<Double>> kFactor=new ArrayList<>();

    public static void addRFactorRecord(double x, double y){
        List<Double> point=new ArrayList<>();
        point.add(x);
        point.add(y);
        kFactor.add(point);
    }

    public static void addKFactorRecord(double y){
        addRFactorRecord(kFactor.size()+1,y);
    }

    public static List<List<Double>> getkFactor() {
        return kFactor;
    }
}
