/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.LanguagePropertyType;
import static EPG.StartupConstants.DEFAULT_THUMBNAIL_WIDTH;
import EPG.handler.ErrorHandler;
import EPG.model.Component;
import EPG.model.ImageComponent;
import EPG.model.VideoComponent;
import java.io.File;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Ka Wing Fong
 */
public class VideoEditDialog extends EditDialog{
    VideoComponent videoComp;
    public VideoEditDialog(Component c, ComponentEditView a) {
        super(c, a);
        videoComp = (VideoComponent)c;
    }

    @Override
    public void initView() {
        Label image = new Label("Video");
        Button selectVideo = new Button();
        selectVideo.setText("Select Video");
        selectVideo.setOnAction(e->{
            
        });
        
        ObservableList<String> alignmentChoices = FXCollections.observableArrayList();
        alignmentChoices.add("Left");
        alignmentChoices.add("Middle");
        alignmentChoices.add("Right");
        
        ComboBox alignment = new ComboBox(alignmentChoices);
        Label alignmentText = new Label("Alignment");
        TextField caption = new TextField("Enter Caption");
        okButton = new Button();
        okButton.setText("OK");
        okButton.setOnAction(e->{
           editView.reloadComponents();
           this.hide();
        });
        Label Caption = new Label("Caption");
        gridPane.add(image, 0, 0);
        gridPane.add(selectVideo, 1,0);
        gridPane.add(alignmentText, 0, 1);
        gridPane.add(alignment, 1, 1);
        gridPane.add(Caption, 0,2);
        gridPane.add(caption, 1,2);
        gridPane.add(okButton, 1,3);
    }
}
