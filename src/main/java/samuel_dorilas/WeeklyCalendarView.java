/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas;

import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

/**
 *
 * @author sj_sc
 */

public class WeeklyCalendarView {

    private ArrayList<DatePane> calendarDays = new ArrayList<>();
    private ArrayList<TimePane> calendarHours = new ArrayList<>();
    private ArrayList<GridPane> weekList = new ArrayList<>();
    private GridPane calendar;

    public WeeklyCalendarView() {

        calendar = new GridPane();
        calendar.setPrefSize(675, 450);
        calendar.setGridLinesVisible(true);
        
        for (int j = 0; j < 7; j++) {
            DatePane dP = new DatePane();
            dP.setPrefSize(200,30);
            dP.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
            calendarDays.add(dP);
            
            GridPane weekGrid = new GridPane();
            weekGrid.setPrefSize(200, 420);
            weekList.add(weekGrid);
            weekGrid.add(dP, 0, 0);
            
            calendar.add(weekGrid, j, 0);
            
        }
            
        for (GridPane gP: weekList) {
            for (int i = 1; i < 17; i++) {
                
                TimePane tP = new TimePane();
                tP.setPrefSize(200,30);
                tP.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                gP.add(tP,0,i);
                calendarHours.add(tP);  
            }   
        } 
    }

    public GridPane getCalendar() {
        return calendar;
    }

    public ArrayList<DatePane> getCalendarDays() {
        return calendarDays;
    }

    public void setCalendarDays(ArrayList<DatePane> calendarDays) {
        this.calendarDays = calendarDays;
    }
    
    public ArrayList<TimePane> getCalendarHours() {
        return calendarHours;
    }

    public void setCalendarHours(ArrayList<TimePane> calendarHours) {
        this.calendarHours = calendarHours;
    }
    
    public ArrayList<GridPane> getWeekList() {
        return weekList;
    }

    public void setWeekList(ArrayList<GridPane> weekList) {
        this.weekList = weekList;
    }
    
}

