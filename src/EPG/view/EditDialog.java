/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import static EPG.StartupConstants.CSS_CLASS_LANG_DIALOG_PANE;
import static EPG.StartupConstants.CSS_CLASS_LANG_OK_BUTTON;
import static EPG.StartupConstants.OK_BUTTON_TEXT;
import static EPG.StartupConstants.STYLE_SHEET_UI;
import EPG.model.Component;
import EPG.model.ParagraphComponent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Ka Wing Fong
 */
public abstract class EditDialog extends Stage{
    
    GridPane gridPane;
    Label hintLabel;
    Button okButton;
    TextField textField;
    ComponentEditView editView; 
    
    public EditDialog(Component c, ComponentEditView a) {
	// SET THE WINDOW TITLE
        editView = a;
	this.setTitle("Edit Dialog");
	textField = new TextField();
	// SETUP THE PROMPT
	// INIT THE LANGUAGE CHOICES
        
	okButton = new Button(OK_BUTTON_TEXT);
	
	gridPane = new GridPane();
	
	// SPECIFY STYLE CLASSES
	gridPane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
	okButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
	//initView();
	// NOW SET THE SCENE IN THIS WINDOW
	Scene scene = new Scene(gridPane);
	scene.getStylesheets().add(STYLE_SHEET_UI);
	setScene(scene);
    }
    public abstract void initView();
    
}
