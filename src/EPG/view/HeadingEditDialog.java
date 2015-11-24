/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.model.Component;
import EPG.model.HeadingComponent;
import EPG.model.Page;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Ka Wing Fong
 */
public class HeadingEditDialog extends EditDialog{
    HeadingComponent head; 
    public HeadingEditDialog(Component c, ComponentEditView a) {
        super(c, a);
        head = (HeadingComponent)c;
        initView();
        
    }

    @Override
    public void initView() {
        Label headingHint = new Label("Enter heading: ");
        gridPane.add(headingHint, 0 , 0);
        TextField heading = new TextField();
        okButton.setOnAction(e->{
           head.setContent(heading.getText());
           editView.reloadComponents();
           this.hide();
        });
        gridPane.add(heading, 1, 0);
        gridPane.add(okButton, 1, 1);
    }

}
