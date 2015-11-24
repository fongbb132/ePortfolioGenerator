/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.LanguagePropertyType;
import static EPG.StartupConstants.DEFAULT_THUMBNAIL_WIDTH;
import static EPG.StartupConstants.STYLE_SHEET_UI;
import EPG.handler.ErrorHandler;
import EPG.model.Component;
import EPG.model.HeadingComponent;
import EPG.model.ImageComponent;
import EPG.model.ListComponent;
import EPG.model.ParagraphComponent;
import EPG.model.SlideShowComponent;
import EPG.model.VideoComponent;
import java.io.File;
import java.net.URL;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
        this.getStylesheets().add(STYLE_SHEET_UI);
        this.comp = comp;
        if(comp instanceof ParagraphComponent){
            content = new Label(comp.getContent());
            getChildren().add(content);
        }
        if(comp instanceof ImageComponent){
            Label imageLabel = new Label("Image");
            ImageView a = new ImageView();
            updateImage((ImageComponent)comp, a);
            Label captionLabel = new Label(((ImageComponent)comp).getCaption());
            Label alignmentLabel = new Label("Alignment: "+((ImageComponent)comp).getAlignment());
            getChildren().add(imageLabel);
            getChildren().add(a);
            getChildren().add(captionLabel);
            getChildren().add(alignmentLabel);
        }
        if(comp instanceof VideoComponent){
            Label imageLabel = new Label("Video");
            Label videoName = new Label("Video Name: "+ ((VideoComponent)comp).getName());
            Label captionLabel = new Label(((VideoComponent)comp).getCaption());
            Label alignmentLabel = new Label("Alignment: "+((VideoComponent)comp).getAlignment());
            getChildren().add(imageLabel);
            getChildren().add(videoName);
            getChildren().add(captionLabel);
            getChildren().add(alignmentLabel);
            
        }
        if(comp instanceof ListComponent){
            Label listLabel = new Label("List");
            ListComponent list = (ListComponent)comp;
            getChildren().add(listLabel);
            for(String a: list.getList()){
                Label b = new Label("* "+a);
                getChildren().add(b);
            }
        }
        if(comp instanceof SlideShowComponent){
            Label ssLabel = new Label("Slideshow");
            SlideShowComponent a = (SlideShowComponent)comp;
            Label titleLabel = new Label(a.getTitle());
            getChildren().add(ssLabel);
            getChildren().add(titleLabel);
        }
        if(comp instanceof HeadingComponent){
            Label hLabel = new Label("Heading: ");
            Label heading = new Label(((HeadingComponent)comp).getContent());
            getChildren().add(hLabel);
            getChildren().add(heading);
        }
        editButton = new Button();
        editButton.setText("Edit");
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
            if(comp instanceof HeadingComponent){
                HeadingEditDialog a = new HeadingEditDialog((HeadingComponent)comp,componentEditView);
                a.showAndWait();
            }
        });
    }
    
    public void updateImage(ImageComponent slide, ImageView imageSelectionView ){
	String imagePath = slide.getSrc();
	File file = new File(imagePath);
	try {
	    // GET AND SET THE IMAGE
	    URL fileURL = file.toURI().toURL();
	    Image slideImage = new Image(fileURL.toExternalForm());
	    imageSelectionView.setImage(slideImage);
	    
	    // AND RESIZE IT
	    double scaledWidth = DEFAULT_THUMBNAIL_WIDTH;
	    double perc = scaledWidth / slideImage.getWidth();
	    double scaledHeight = slideImage.getHeight() * perc;
	    imageSelectionView.setFitWidth(scaledWidth);
	    imageSelectionView.setFitHeight(scaledHeight);
	} catch (Exception e) {
	    ErrorHandler eH = new ErrorHandler();
            eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
	}
        
    }
}
