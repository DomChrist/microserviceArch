package de.dom.microservice.arch.eventsourcing.examples.logic.values;

import java.time.LocalDateTime;

public class TestStatus {

    private boolean open = false;
    private LocalDateTime openDate;
    private LocalDateTime closeDate;


    public TestStatus(boolean open, LocalDateTime openDate, LocalDateTime closeDate) {
        this.open = open;
        this.openDate = openDate;
        this.closeDate = closeDate;
    }

    public TestStatus() {
        this(false,null,null);
    }

    public static TestStatus defaultStatus(){
        return new TestStatus(false,null,null);
    }

    public TestStatus open(LocalDateTime time){
        return new TestStatus(true,time,null);
    }

    public TestStatus closed(LocalDateTime time){
        return new TestStatus(false,this.openDate,time);
    }

    public boolean isOpen() {
        return open;
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public boolean isTestClosed(){
        if( !open && closeDate != null ) return true;
        return false;
    }

}
