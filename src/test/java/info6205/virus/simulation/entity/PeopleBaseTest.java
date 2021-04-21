package info6205.virus.simulation.entity;

import info6205.virus.simulation.entity.virus.Covid19;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeopleBaseTest {

    @Test
    public void objectTest(){
        Object a=new Covid19();
        Covid19 b=new Covid19();
        System.out.println(a.getClass().isInstance(b));
    }
}