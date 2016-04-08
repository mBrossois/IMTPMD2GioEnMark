package com.example.gioenmark.myapplication.Models;

/**
 * Created by mark on 4/5/2016.
 */
import java.io.Serializable;

public class Course implements Serializable {           // WAAROM serializable ????

    public String name;
    public String ects;
    public String grade;
    public String period;


    public Course(String courseName, String ects, String grade, String period){
        this.name = courseName;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
    }

    public String getCourseName()
    {
        return name;
    }
    public String getEcts()
    {
        return ects;
    }
    public String getGrade()
    {
        return grade;
    }
    public String getPeriod()
    {
        return period;
    }

    public void setCourse(String courseName, String ects, String grade, String period)
    {
        this.name = courseName;
        this.ects = ects;
        this.grade = grade;
        this.period = period;
    }
    public void setCourseName(String courseName)
    {
        this.name = courseName;
    }
    public void setEcts(String ects)
    {
        this.ects = ects;

    }
    public void setGrade(String grade)
    {
        this.grade = grade;
    }
    public void setPeriod(String period)
    {
        this.period = period;
    }
}
