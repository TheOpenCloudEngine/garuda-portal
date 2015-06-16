package org.uengine.bss.metering;

import org.aspectj.lang.annotation.*;

import java.util.Hashtable;

@Aspect
public class MeteringAdvice {

    Hashtable<String, Integer> usagelog = new Hashtable<String, Integer>();


    @Before("@annotation(metered)")
    public void checkAvailable(Metered metered) throws NoServiceAvailableException {
        String serviceId = metered.value();

        Integer currentValue;
        if(usagelog.containsKey(serviceId))
            currentValue = usagelog.get(serviceId);
        else
            currentValue = new Integer(0);

        if(currentValue.intValue() > 5){
            throw new NoServiceAvailableException("Quota exceeded for service " +  serviceId);
        }
    }


    @AfterReturning("@annotation(metered)")
    public void meter(Metered metered) throws Exception {
        String serviceId = metered.value();

        Integer currentValue;
        if(usagelog.containsKey(serviceId))
            currentValue = usagelog.get(serviceId);
        else
            currentValue = new Integer(0);

        currentValue = currentValue.intValue() + 1;

        usagelog.put(serviceId, currentValue);

        System.out.println(metered.value() + " is " + currentValue + " times used.");


    }


}
