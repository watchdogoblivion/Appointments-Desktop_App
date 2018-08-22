/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas;

import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

/**
 *
 * @author sj_sc
 */

public class MonthlyCalendarView {

    private ArrayList<DatePane> calendarDays = new ArrayList<>();
    private GridPane calendar;
    private Label appointment = new Label("");
    private int aptNumber = 0; 
    
    public MonthlyCalendarView() {

        calendar = new GridPane();
        calendar.setPrefSize(675, 450);
        calendar.setGridLinesVisible(true);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                DatePane dP = new DatePane();
                dP.setPrefSize(200,200);
                dP.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                calendar.add(dP,j,i);
                calendarDays.add(dP);
            }
        }    
    }

    public int getAptNumber(){
        return aptNumber;
    }
    
    public void setAptNumber( int aptNumber){
        this.aptNumber=aptNumber;
    }
    
    public Label getAppointment(){
        return appointment;
    }
    
    public void setAppointment( Label apt){
        appointment=apt;
    }
    public GridPane getCalendar() {
        return calendar;
    }

    public ArrayList<DatePane> getCalendarDays() {
        return calendarDays;
    }

    public void setCalendarDays(ArrayList<DatePane> CalendarDays) {
        this.calendarDays = CalendarDays;
    }
    
}
