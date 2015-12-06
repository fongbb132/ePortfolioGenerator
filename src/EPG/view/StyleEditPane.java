/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import static EPG.StartupConstants.CSS_CLASS_LANG_COMBO_BOX;
import static EPG.StartupConstants.CSS_CLASS_LANG_PROMPT;
import EPG.controller.FileSelectionController;
import EPG.controller.ImageChooser;
import EPG.model.EPortfolio;
import EPG.model.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Ka Wing Fong
 */
public class StyleEditPane extends GridPane{
    Page page;

    public StyleEditPane(Page p) {
        this.page = p;
        initInterface();
        this.setVgap(10);
        this.setHgap(10);
    }
    
    public void initInterface(){
        Label bannerImage = new Label("Banner Image: ");
        bannerImage.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        Button selectImage = new Button();
        this.add(bannerImage, 0, 0);
        if(page.getBanner().equals("")){
            selectImage.setText("Choose Image");
        }else{
            selectImage.setText(page.getBanner());
        }
        
        selectImage.setOnAction(e->{
            ImageChooser iChooser = new ImageChooser();
            String[] temp = iChooser.processSelectImage();
            System.out.println(temp[0]);
            if(temp[0]!=null){
                page.setBannerUrl(temp[0]);
                page.setBanner(temp[1]);
                selectImage.setText(page.getBanner());
            }
        });
        this.add(selectImage, 1,0);
        Label footer = new Label("Footer");
        footer.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        TextField footerField = new TextField();
        footerField.getStyleClass().add("footer");
        footerField.setText(page.getFooter());
        footerField.setOnKeyReleased(e->{
            page.setFooter(footerField.getText());
        });
        add(footer, 0,1);
        add(footerField, 1,1);
        
        Label layoutSelLabel = new Label("Select Layout: ");
        ObservableList<String> layoutChoices = FXCollections.observableArrayList();
	layoutChoices.add("Layout 1");
	layoutChoices.add("Layout 2");
	layoutChoices.add("Layout 3");
	layoutChoices.add("Layout 4");
	layoutChoices.add("Layout 5");
        ComboBox layoutComboBox =  new ComboBox(layoutChoices);
        if(page.getLayout().equals("")){
            layoutComboBox.getSelectionModel().select(layoutChoices.get(0));
        }else{
            layoutComboBox.getSelectionModel().select(page.getLayout());
        }
        layoutSelLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        layoutComboBox.getStyleClass().add(CSS_CLASS_LANG_COMBO_BOX);
        layoutComboBox.setOnAction(e->{
            page.setLayout(layoutComboBox.getSelectionModel().getSelectedItem().toString());
        });
        
        add(layoutSelLabel, 0,2);
        add(layoutComboBox, 1,2);
	
        Label colorSelLabel = new Label("Select color: ");
        ObservableList<String> colorChoices = FXCollections.observableArrayList();
	colorChoices.add("Color 1");
	colorChoices.add("Color 2");
	colorChoices.add("Color 3");
	colorChoices.add("Color 4");
	colorChoices.add("Color 5");
        ComboBox colorComboBox =  new ComboBox(colorChoices);
        add(colorSelLabel, 0,3);
        add(colorComboBox, 1,3);
        if(page.getColor().equals("")){
            colorComboBox.getSelectionModel().select(colorChoices.get(0));
        }else{
            colorComboBox.getSelectionModel().select(page.getColor());
        }
        colorComboBox.setOnAction(e->{
            page.setColor(colorComboBox.getSelectionModel().getSelectedItem().toString());
        });
        
        colorSelLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        colorComboBox.getStyleClass().add(CSS_CLASS_LANG_COMBO_BOX);
        Label fontSelLabel = new Label("Select font: ");
        ObservableList<String> fontChoices = FXCollections.observableArrayList();
	fontChoices.add("Font 1");
	fontChoices.add("Font 2");
	fontChoices.add("Font 3");
	fontChoices.add("Font 4");
	fontChoices.add("Font 5");
        ComboBox fontComboBox =  new ComboBox(fontChoices);
        fontComboBox.setOnAction(e->{
            page.setFont(fontComboBox.getSelectionModel().getSelectedItem().toString());
        });
        
        if(page.getFont().equals("")){
            fontComboBox.getSelectionModel().select(fontChoices.get(0));
        }else{
            fontComboBox.getSelectionModel().select(page.getFont());
        }
        
        fontSelLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        fontComboBox.getStyleClass().add(CSS_CLASS_LANG_COMBO_BOX);
        add(fontSelLabel, 0,4);
        add(fontComboBox, 1,4);
            
    }
}
