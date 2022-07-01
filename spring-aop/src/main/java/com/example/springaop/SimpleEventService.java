package com.rldnddl87.springaop;

import org.springframework.stereotype.Service;

@Service
public class SimpleEventService implements EventService {

    @PerfLogging
    @Override
    public void createEvent() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("crated an Event");

    }

    @PerfLogging
    @Override
    public void publishEvent() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("published an Event");

    }

    @Override
    public void deleteEvent() {
        System.out.println("deleted an Event");
    }
}
