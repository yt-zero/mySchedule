package com.example.myschedule;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Period {
    WTime start;
    WTime end;
    String period;
    String teacher, room, subject;
    private static ArrayList<Period> periods = new ArrayList<Period>();
    //Whether print one day or the whole week
    static boolean printDay=false;



    public Period(WTime start, int periodLength, String period){
        this.start = start;
        this.end = new WTime(start, periodLength);
        this.period = period;
        subject = "";
        room = "";
        teacher = "";
    }
    public Period(Period a){
        start = a.start;
        end = a.end;
        period = a.period;
        teacher = a.teacher;
        room = a.room;
        subject = a.subject;
    }
    public Period (WTime start, WTime end, String period){
        this.start = start;
        this.end = end;
        this.period = period;
        subject = "";
        room = "";
        teacher = "";
    }
    public void setSubject(String g)
    {
        subject = g;
    }
    public String getSubject()
    {
        return subject;
    }
    public void setRoom(String g)
    {
        room = g;
    }
    public String getRoom()
    {
        return room;
    }
    public void setTeacher(String g)
    {
        teacher= g;
    }
    public String getTeacher()
    {
        return teacher;
    }
    public String getPeriod()
    {
        return period;
    }
    public static void loadPeriods() {
        periods.add(new Period(new WTime(1,8,00),15,"Chapel"));
        periods.add(new Period(new WTime(1, 8, 20), 70, "1"));
        periods.add(new Period(new WTime(1, 9, 35), 45, "2"));
        periods.add(new Period(new WTime(1, 10, 25), 70, "3"));
        periods.add(new Period(new WTime(1, 11, 45), 25, "Lunch"));
        periods.add(new Period(new WTime(1, 12, 30), 45, "4"));
        periods.add(new Period(new WTime(1, 13, 20), 45, "5"));
        periods.add(new Period(new WTime(1, 14, 10), 45, "6"));

        periods.add(new Period(new WTime(2,8,00),15,"Chapel"));
        periods.add(new Period(new WTime(2, 8, 20), 70, "7"));
        periods.add(new Period(new WTime(2, 9, 35), 45, "4"));
        periods.add(new Period(new WTime(2, 10, 25), 70, "2"));
        periods.add(new Period(new WTime(2, 11, 45), 25, "Lunch"));
        periods.add(new Period(new WTime(2, 12, 30), 45, "5"));
        periods.add(new Period(new WTime(2, 13, 20), 45, "1"));
        periods.add(new Period(new WTime(2, 14, 10), 45, "3"));

        periods.add(new Period(new WTime(3,8,00),15,"Class meeting"));
        periods.add(new Period(new WTime(3, 8, 20), 45, "5"));
        periods.add(new Period(new WTime(3, 9, 10), 45, "6"));
        periods.add(new Period(new WTime(3, 10, 0), 45, "1"));
        periods.add(new Period(new WTime(3, 10, 50), 45, "7"));

        periods.add(new Period(new WTime(4,8,00),15,"Chapel"));
        periods.add(new Period(new WTime(4, 8, 20), 70, "4"));
        periods.add(new Period(new WTime(4, 9, 35), 45, "7"));
        periods.add(new Period(new WTime(4, 10, 25), 70, "6"));
        periods.add(new Period(new WTime(4, 11, 45), 25, "Lunch"));
        periods.add(new Period(new WTime(4, 12, 30), 45, "2"));
        periods.add(new Period(new WTime(4, 13, 20), 45, "3"));
        periods.add(new Period(new WTime(4, 14, 10), 45, "1"));

        periods.add(new Period(new WTime(5, 8, 0), 45, "3"));
        periods.add(new Period(new WTime(5, 8, 50), 45, "2"));
        periods.add(new Period(new WTime(5, 9, 40), 70, "5"));
        periods.add(new Period(new WTime(5, 11, 0), 40, "Chapel"));
        periods.add(new Period(new WTime(5, 11, 45), 25, "Lunch"));
        periods.add(new Period(new WTime(5, 12, 30), 45, "6"));
        periods.add(new Period(new WTime(5, 13, 20), 45, "7"));
        periods.add(new Period(new WTime(5, 14, 10), 45, "4"));
    }
    public static void drawPeriods(Canvas c) {
        for (Period i :periods)
            i.draw(c);

    }
    public static void drawTodayP(Canvas c)
    {
        WTime today=new WTime();
        today.getDay();
        ArrayList<Period> dayPeriods = Period.getTodaysPeriods(today.getDay());
        printDay=true;
        for(Period i: dayPeriods)
            i.draw(c);
    }


    public static void setAllPeriodInfo(String num,String meets, String className, String room, String teacher){
        System.out.println("num "+num+" meets "+meets+" className "+className+" Room "+room+" Teacher "+teacher);
        int meetsSession = 1;
        int meetsNum = 0;
        for (Period p:periods){
            if (p.period.equals(num)){
                if (meets.length() < 1){
                    p.setSubject(className);
                    p.setRoom(room);
                    p.setTeacher(teacher);
                }
                else if (meets.length() > meetsNum && meets.substring(meetsNum,meetsNum+1).equals(""+meetsSession)){
                    meetsNum++;
                    meetsSession++;
                    p.setSubject(className);
                    p.setRoom(room);
                    p.setTeacher(teacher);
                }
            }
        }
    }
    public static void setAllPeriodInfo(String num, String className){
        setAllPeriodInfo(num,"",className,"","");
    }
    public static Period findContainingPeriod(WTime target){
        for (Period p:periods){
            if (p.contains(target)){
                return p;
            }
        }
        return null;
    }
    //Returns the Period before the current passing block or the previous Period to the current Period
    // if between last Period of the week and the first Period of the week return the last Period of the week
    public static Period findPreviousPeriod(WTime target){
        Period previous = null;
        Period current = null;
        current = findContainingPeriod(target);
        if (current != null){  //return the previous Period
            int index = periods.indexOf(current);
            if (index > 0)
                return periods.get(index-1);
            else
                return periods.get(periods.size()-1);
        }
        else{    //not a current Period ie a passing block
            current  = findNextPeriod(target);
            if (periods.indexOf(current) == 0)
                return periods.get(periods.size()-1);
            else
                return periods.get(periods.indexOf(current)-1);
        }
        // return null;
    }
    //Returns the Period after the current passing block or the Period after current Period
    // if between last Period of the week and the first Period of the week return the first Period of the week
    public static Period findNextPeriod(WTime target){
        Period previous = null;
        for (Period p:periods){
            if (!p.startsAfter(target))
                return p;
        }
        return periods.get(0);
    }

    public boolean startsAfter(WTime target)
    {
        return start.isBefore(target);
    }

    public boolean contains(WTime target)
    {
        if (start.isBefore(target) && end.isAfter(target))
            return true;
        return false;
    }

    public void setStart(WTime a)
    {
        start = a;
    }

    public void setPeriodNumber(String g)
    {
        period = g;
    }

    public void draw(Canvas canvas)
    {
        Paint paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(20);
        paint.setStrokeWidth(2);
        int day=start.getDay();
        WTime eight = new WTime(day,8,0);
        int gs =(start.ticks - eight.ticks)/200;
        int ge =(end.ticks- eight.ticks)/200;
       // Log.i("info",gs+" "+ge);
       // Log.i ("info",day+"");
        //System.out.println(day);
        paint.setTextSize(15);
        int para=day;
        if(printDay)
            para=0;
        Rect rectangle = new Rect(20+ para*110, gs, 120+para*110, ge);
        canvas.drawRect(rectangle,paint);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(start.getHourAMPM()+":"+start.getMinuteS(),20+para*110,gs+17,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(start.getHourAMPM()+":"+end.getMinuteS(),120+para*110,ge-3,paint);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(subject,40+para*110,(ge+gs)/2,paint);
        canvas.drawText(getPeriod(),75+para*110,(ge+gs)/2,paint);

        //getTodaysPeriods(day);
        //canvas.drawRect(20,gs,120,ge-gs);
        // g.drawString(""+start,30,gs+20);
        //g.drawString(start.getHourAMPM()+":"+start.getMinuteS(),30,gs+20);
        //g.drawString(end.getHourAMPM()+":"+end.getMinuteS(),105,ge-10);
        //g.drawString(subject,40,(ge+gs)/2+10);
        //g.drawString(getPeriod(),75,(ge+gs)/2);
    }
    public static ArrayList<Period> getTodaysPeriods(int dDay){
        ArrayList <Period> dayPeriods = new ArrayList<Period>();
        for(Period p:periods){
            if(p.start.getDay() == dDay)
                dayPeriods.add(p);
        }
        return dayPeriods;
    }
    public String toString()
    {
        return "Period :"+period+" starts "+start+" ends "+end;
    }
    // for testing
    public static void main(String[] args) {
        //Period test1 = new Period(new WTime(1, 8, 40), new WTime(1, 9, 30), "1");
        //System.out.println(test1);
        //setAllPeriodInfo("4", "Lunch");
        //setAllPeriodInfo("5", "App Development");
        WTime a = new WTime();
        // WTime a = new WTime(6,14,30,0);  //saturday after classes
        //  WTime a = new WTime(0,14,30,0);   // Sunday Morning
        //WTime a = new WTime(1,8,30,0);   // Monday Morning
        //WTime a = new WTime(1, 9, 32, 0);   // Monday Morning passing block
        System.out.println(a);
        System.out.println(Period.findContainingPeriod(a));
        System.out.println(Period.findNextPeriod(a));
        System.out.println(Period.findPreviousPeriod(a));
        //  for (Period a: periods){
        //    System.out.println(a);
        //  }
}
}
