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
public class ImageEditDialog extends EditDialog {
    ImageComponent imgComp;
    ImageView imageView ;
    public ImageEditDialog(Component c, ComponentEditView a) {
        super(c, a);
        imgComp = (ImageComponent)c;
        updateImage();
    }

    @Override
    public void initView() {
        Label image = new Label("Image");
        imageView = new ImageView();
        TextField caption = new TextField("Enter Caption");
        okButton = new Button();
        okButton.setText("OK");
        
        
        ObservableList<String> alignmentChoices = FXCollections.observableArrayList();
        alignmentChoices.add("Left");
        alignmentChoices.add("Middle");
        alignmentChoices.add("Right");
        
        ComboBox alignment = new ComboBox(alignmentChoices);
        Label alignmentText = new Label("Alignment");
        Label Caption = new Label("Caption");
        okButton.setOnAction(e->{
           editView.reloadComponents();
           this.hide();
        });
        gridPane.add(image, 0, 0);
        gridPane.add(imageView, 0,1);
        gridPane.add(Caption, 0, 2);
        gridPane.add(caption, 1,2);
        gridPane.add(alignment, 1, 3);
        gridPane.add(alignmentText, 0, 3);
        gridPane.add(okButton, 1,4);
    }
    public void updateImage(){
        String imagePath = imgComp.getSrc();
        File file = new File(imagePath);
        try{
            URL fileURL = file.toURI().toURL();
            Image image = new Image(fileURL.toExternalForm());
            imageView.setImage(image);
            
	    double scaledWidth = DEFAULT_THUMBNAIL_WIDTH;
	    double perc = scaledWidth / image.getWidth();
	    double scaledHeight = image.getHeight() * perc;
	    imageView.setFitWidth(scaledWidth);
	    imageView.setFitHeight(scaledHeight);
        }catch(Exception e){
            ErrorHandler eh = new ErrorHandler();
            eh.processError(LanguagePropertyType.ERROR_UNEXPECTED);
        }
    }
}
