/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.LanguagePropertyType;
import static EPG.StartupConstants.DEFAULT_THUMBNAIL_WIDTH;
import EPG.controller.FileSelectionController;
import EPG.controller.ImageChooser;
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
        initView();
    }

    @Override
    public void initView() {
        Label image = new Label("Video");
        Button selectVideo = new Button();
        
        if(videoComp.getName().equals("")){
            selectVideo.setText("Select Video");
        }else{
            selectVideo.setText(videoComp.getName());
        }
        
        selectVideo.setOnAction(e->{
            ImageChooser temp = new ImageChooser();
            String[] a = temp.processSelectImage();
            if(a[0]!=null){
                videoComp.setSrc(a[0]);
                videoComp.setName(a[1]);
                selectVideo.setText(a[1]);
            }
        });
        
        TextField width = new TextField();
        width.setText(videoComp.getWidth()+"");
        width.setOnKeyTyped(e->{
            
        });
        TextField height = new TextField();
        height.setText(videoComp.getHeight()+"");
        
        ObservableList<String> alignmentChoices = FXCollections.observableArrayList();
        alignmentChoices.add("Left");
        alignmentChoices.add("center");
        alignmentChoices.add("Right");
        ComboBox alignment = new ComboBox(alignmentChoices);
        alignment.getSelectionModel().select(alignmentChoices.get(0));
        Label alignmentText = new Label("Alignment");
        TextField caption = new TextField("Enter Caption");
        caption.setText(videoComp.getCaption());
        okButton = new Button();
        okButton.setText("OK");
        Label Caption = new Label("Caption");
        gridPane.add(image, 0, 0);
        gridPane.add(selectVideo, 1,0);
        gridPane.add(alignmentText, 0, 1);
        gridPane.add(alignment, 1, 1);
        gridPane.add(Caption, 0,2);
        gridPane.add(caption, 1,2);
        gridPane.add(new Label("Width: "), 0, 3);
        gridPane.add(new Label("Height: "), 0,4);
        gridPane.add(width, 1, 3);
        gridPane.add(height, 1,4);
        gridPane.add(okButton, 1,5);
        
        okButton.setOnAction(e->{
           videoComp.setAlignment(alignment.getSelectionModel().getSelectedItem().toString());
           videoComp.setCaption(caption.getText());
           videoComp.setWidth(Integer.parseInt(width.getText()));
           videoComp.setHeight(Integer.parseInt(height.getText()));
           editView.reloadComponents();
           this.hide();
        });
    }
}
