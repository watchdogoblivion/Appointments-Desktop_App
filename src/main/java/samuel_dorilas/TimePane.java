/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas;

import java.time.LocalDate;
import java.time.LocalTime;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sj_sc
 */
public class TimePane extends AnchorPane{
    private LocalTime time;
    private LocalDate date;


    public TimePane(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> System.out.println("This pane's time is: " + time+
        "and this pane's date is: " + date));
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
}
