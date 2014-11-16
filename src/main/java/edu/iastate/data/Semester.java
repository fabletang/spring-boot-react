package edu.iastate.data;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Semester implements Serializable {
    @Id
    private int id;

    private String code;

    private String year;

    private String preliminary;

    private String current;

    private Date lastChanged;

    @JsonGetter
    public String getSemesterName()
    {
        if (StringUtils.equals(this.code, "F"))
        {
            return "Fall";
        }
        else if (StringUtils.equals(this.code, "S"))
        {
            return "Spring";
        }
        else
        {
            return "Summer";
        }
    }

    public String getYear() {
        return year;
    }

    public String getShortYear()
    {
        return this.getYear().substring(2,4);
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPreliminary() {
        return preliminary;
    }

    public void setPreliminary(String preliminary) {
        this.preliminary = preliminary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public void addCourse(Course course)
    {
        if (this.courses == null)
        {
            this.courses = new ArrayList<Course>();
        }

        this.courses.add(course);
    }*/

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }
}
