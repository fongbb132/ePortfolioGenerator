/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.model.Component;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ka Wing Fong
 */
public class ParagraphCompView extends VBox{
    Component comp;
    
    Label paragraph;
    Button editButton;
    public ParagraphCompView(Component comp) {
        this.comp = comp;
        paragraph = new Label(comp.getContent());
        editButton = new Button();
        editButton.setText("Edit");
        editButton.setOnAction(e->{
            
        });
        getChildren().add(paragraph);
        getChildren().add(editButton);
    }
    
}
