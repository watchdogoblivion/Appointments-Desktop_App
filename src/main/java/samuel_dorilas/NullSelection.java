/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samuel_dorilas;

import javafx.scene.control.SingleSelectionModel;

/**
 *
 * @author sj_sc
 */
public class NullSelection extends SingleSelectionModel{

    @Override
    protected Object getModelItem(int index) {
        return null;
    }

    @Override
    protected int getItemCount() {
     return 0;
    }

}