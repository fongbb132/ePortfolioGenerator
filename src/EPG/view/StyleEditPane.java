/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import static EPG.StartupConstants.ENGLISH_LANG;
import EPG.model.EPortfolio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Ka Wing Fong
 */
public class StyleEditPane extends GridPane{
    EPortfolio portfolio;

    public StyleEditPane(EPortfolio portfolio) {
        this.portfolio = portfolio;
        initInterface();
    }
    
    public void initInterface(){
        Label bannerImage = new Label("Banner Image: ");
        Button selectImage = new Button();
        this.add(bannerImage, 0, 0);
        selectImage.setText("Choose Image");
        this.add(selectImage, 1,0);
        Label footer = new Label("Footer");
        TextArea footerField = new TextArea();
        footerField.setOnKeyReleased(e->{
            portfolio.setFooter(footerField.getText());
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
        layoutComboBox.setOnAction(e->{
            portfolio.setLayout(layoutComboBox.getSelectionModel().getSelectedItem().toString());
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
        colorComboBox.setOnAction(e->{
            portfolio.setColor(colorComboBox.getSelectionModel().getSelectedItem().toString());
        });
        
        Label fontSelLabel = new Label("Select font: ");
        ObservableList<String> fontChoices = FXCollections.observableArrayList();
	fontChoices.add("Font 1");
	fontChoices.add("Font 2");
	fontChoices.add("Font 3");
	fontChoices.add("Font 4");
	fontChoices.add("Font 5");
        ComboBox fontComboBox =  new ComboBox(fontChoices);
        fontComboBox.setOnAction(e->{
            portfolio.setFont(fontComboBox.getSelectionModel().getSelectedItem().toString());
        });
        add(fontSelLabel, 0,4);
        add(fontComboBox, 1,4);
        
            
    }
}
