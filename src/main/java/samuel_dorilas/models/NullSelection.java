/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas.models;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;

/**
 *
 * @author sj_sc
 */
public class NullSelection extends SingleSelectionModel<Tab>{

    @Override
    public Tab getModelItem(int index) {
        return null;
    }

    @Override
    public int getItemCount() {
     return 0;
    }

}