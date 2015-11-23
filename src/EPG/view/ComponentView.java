/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.model.Component;
import EPG.model.ImageComponent;
import EPG.model.ListComponent;
import EPG.model.ParagraphComponent;
import EPG.model.SlideShowComponent;
import EPG.model.VideoComponent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ka Wing Fong
 */
public class ComponentView extends VBox{
    Component comp;
    ComponentEditView componentEditView;
    
    Label content;
    Button editButton;
    public ComponentView(Component comp, ComponentEditView compEdit) {
        this.comp = comp;
        content = new Label(comp.getContent());
        editButton = new Button();
        editButton.setText("Edit");
        getChildren().add(content);
        getChildren().add(editButton);
        this.componentEditView = compEdit;
        initEditButton();
    }
    
    public void initEditButton(){
        editButton.setOnAction(e->{
            if(comp instanceof ParagraphComponent){
                ParagraphEditDialog a = new ParagraphEditDialog((ParagraphComponent)comp, componentEditView);
                a.showAndWait();
            }
            if(comp instanceof ImageComponent){
                ImageEditDialog a = new ImageEditDialog((ImageComponent)comp,componentEditView);
                a.showAndWait();
            }
            if(comp instanceof VideoComponent){
                VideoEditDialog a = new VideoEditDialog((VideoComponent)comp,componentEditView);
                a.showAndWait();
            }
            if(comp instanceof ListComponent){
                ListEditDialog a = new ListEditDialog((ListComponent)comp, componentEditView);
                a.showAndWait();
            }
            if(comp instanceof SlideShowComponent){
                SlideShowEditDialog a = new SlideShowEditDialog((SlideShowComponent)comp, componentEditView);
                //a.showAndWait();
            }
        });
    }
    
}
