package org.uengine.test;

import junit.framework.TestCase;
import org.uengine.bss.metering.NoServiceAvailableException;
import org.uengine.bss.monetization.SampleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MeteringTest extends TestCase{

    public ApplicationContext context;

    SampleService sampleService;

    @Override
    public void setUp(){
        context = new ClassPathXmlApplicationContext("/applicationContext.xml");
        sampleService = (SampleService) context.getBean("sampleService");

    }

    public void testQuotaExceed(){

        for(int i=0; i<10; i++){
            boolean noServiceAvailableExceptionIsRaised = false;
            try {
                sampleService.serviceAMethod();
            }catch (NoServiceAvailableException na){
                noServiceAvailableExceptionIsRaised = true;
            }catch (Exception e){

            }

            if(i>5 && !noServiceAvailableExceptionIsRaised){
                fail("NoServiceAvailableException should be raised");
            }

        }

    }


    public void testFailedServiceIsNotCountedForQuotaExceed(){

       for(int i=0; i<10; i++){
            boolean noServiceAvailableExceptionIsRaised = false;
            try {
                sampleService.serviceBMethod();
            }catch (NoServiceAvailableException na){
                noServiceAvailableExceptionIsRaised = true;
            }catch (Exception e){

            }

            if(i>5 && noServiceAvailableExceptionIsRaised){
                fail("Failed Service call should not be counted for quota exceeded exception - NoServiceAvailableException");
            }

        }

    }

}
