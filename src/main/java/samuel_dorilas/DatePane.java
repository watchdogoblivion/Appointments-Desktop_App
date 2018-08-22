/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas;

import java.time.LocalDate;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author sj_sc
 */
public class DatePane extends AnchorPane{
    private LocalDate date;


    public DatePane(Node... children) {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> System.out.println("This pane's date is: " + date));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
