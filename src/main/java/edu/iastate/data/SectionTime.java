package edu.iastate.data;



import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.Interval;


public class SectionTime implements Serializable {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("h:mm a");

    private int id;

    private Integer sectionSequenceNumber;

    private String instructionType;

    private Date startTimeVal;

    private Date stopTimeVal;

    private String meetDays;

    private String instrName;

    private String buildingName;

    private String roomNum;

    private Integer Monday;

    private Integer Tuesday;

    private Integer Wednesday;

    private Integer Thursday;

    private Integer Friday;

    private Integer Saturday;

    private List<Interval> meetTimeIntervals;


    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }

    public LocalTime getStartTime() {
        return getStartTimeVal() == null ? null : LocalTime.fromDateFields(getStartTimeVal());
    }

    @JsonGetter
    public String getFormattedStartTime()
    {
        return DATE_TIME_FORMATTER.print(startTimeVal.getTime());
    }

    @JsonGetter
    public String getFormattedStopTime()
    {
        return DATE_TIME_FORMATTER.print(stopTimeVal.getTime());
    }

    @JsonGetter
    public String getMeetTimeDisplay()
    {
        if ( (!StringUtils.equals(getFormattedStartTime(), getFormattedStopTime())) &&
                (!StringUtils.equals(getMeetDays(), ""))  )
        {
            return getFormattedStartTime() + " - " + getFormattedStopTime();
        }
        else
        {
            return "Arranged";
        }
    }

    public Time getStartTimeVal() {
        return new Time(startTimeVal.getTime());
    }

    public void setStartTimeVal(Time startTime) {
        this.startTimeVal = new Date(startTime.getTime());
    }

    public void setStartTimeVal(Date startTime) {
        this.startTimeVal = new Date(startTime.getTime());
    }

    public LocalTime getStopTime() {
        return getStopTimeVal() == null ? null : LocalTime.fromDateFields(getStopTimeVal());
    }

    public Time getStopTimeVal() {
        return new Time(stopTimeVal.getTime());
    }

    public void setStopTimeVal(Time stopTime) {
        this.stopTimeVal = new Date(stopTime.getTime());
    }

    public void setStopTimeVal(Date stopTime) {
        this.stopTimeVal = new Date(stopTime.getTime());
    }

    public String getMeetDays() {
        return meetDays;
    }

    public void setMeetDays(String meetDays) {
        this.meetDays = meetDays;
    }

    public Integer getSectionSequenceNumber() {
        return sectionSequenceNumber;
    }

    public void setSectionSequenceNumber(Integer sectionSequenceNumber) {
        this.sectionSequenceNumber = sectionSequenceNumber;
    }

    public String getInstrName() {
        return instrName;
    }

    public void setInstrName(String instrName) {
        this.instrName = instrName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<Interval> getMeetTimeIntervals()
    {
        if (meetTimeIntervals == null)
        {
            List<String> days = new ArrayList<String>();
            for (int x = 0; x<= 5; x++)
            {
                switch (x) {
                    case 0: {
                        if (this.getMonday() != null && this.getMonday().equals(1))
                        {
                            days.add("Monday");
                        }
                        break;
                    }
                    case 1: {
                        if (this.getTuesday() != null && this.getTuesday().equals(1))
                        {
                            days.add("Tuesday");
                        }
                        break;
                    }
                    case 2: {
                        if (this.getWednesday() != null && this.getWednesday().equals(1))
                        {
                            days.add("Wednesday");
                        }
                        break;
                    }
                    case 3: {
                        if (this.getThursday() != null && this.getThursday().equals(1))
                        {
                            days.add("Thursday");
                        }
                        break;
                    }
                    case 4: {
                        if (this.getFriday() != null && this.getFriday().equals(1))
                        {
                            days.add("Friday");
                        }
                        break;
                    }
                    case 5: {
                        if (this.getSaturday() != null && this.getSaturday().equals(1))
                        {
                            days.add("Saturday");
                        }
                        break;
                    }
                }
            }
            meetTimeIntervals = IntervalGenerator.getIntervals(days, getStartTimeVal(), getStopTimeVal());
        }
        return meetTimeIntervals;
    }

    @JsonGetter
    public String getMeetDaysDisplay()
    {
        String meetDays="";
        LocalDate mondayDate = LocalDate.parse("2014-10-20");
        for (int x = 0; x<= 5; x++)
        {
            LocalDate tmpStartDate = mondayDate.plusDays(x);
            String tmpDay = tmpStartDate.toString("EEEE");
            try {
                Method m = this.getClass().getMethod("get" + tmpDay);
                try {
                    Integer exists = (Integer)m.invoke(this);
                    if ( (exists != null) && (exists.equals(1)) )
                    {
                        meetDays+= tmpDay.substring(0,3) + " ";
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return meetDays;
    }

    @JsonIgnore
    public Integer getMonday() {
        return Monday;
    }

    public void setMonday(Integer monday) {
        Monday = monday;
    }

    @JsonIgnore
    public Integer getTuesday() {
        return Tuesday;
    }

    public void setTuesday(Integer tuesday) {
        Tuesday = tuesday;
    }

    @JsonIgnore
    public Integer getWednesday() {
        return Wednesday;
    }

    public void setWednesday(Integer wednesday) {
        Wednesday = wednesday;
    }

    @JsonIgnore
    public Integer getThursday() {
        return Thursday;
    }

    public void setThursday(Integer thursday) {
        Thursday = thursday;
    }

    @JsonIgnore
    public Integer getFriday() {
        return Friday;
    }

    public void setFriday(Integer friday) {
        Friday = friday;
    }

    @JsonIgnore
    public Integer getSaturday() {
        return Saturday;
    }

    public void setSaturday(Integer saturday) {
        Saturday = saturday;
    }
}
