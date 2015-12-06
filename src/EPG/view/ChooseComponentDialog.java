/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import static EPG.LanguagePropertyType.IMAGE;
import static EPG.LanguagePropertyType.LABEL_COMPONENT_SELECTION_PROMPT;
import static EPG.LanguagePropertyType.LIST;
import static EPG.LanguagePropertyType.PARAGRAPH;
import static EPG.LanguagePropertyType.SLIDESHOW;
import static EPG.LanguagePropertyType.VIDEO;
import static EPG.StartupConstants.CSS_CLASS_LANG_COMBO_BOX;
import static EPG.StartupConstants.CSS_CLASS_LANG_DIALOG_PANE;
import static EPG.StartupConstants.CSS_CLASS_LANG_OK_BUTTON;
import static EPG.StartupConstants.CSS_CLASS_LANG_PROMPT;
import static EPG.StartupConstants.DEFAULT_SLIDE_IMAGE;
import static EPG.StartupConstants.OK_BUTTON_TEXT;
import static EPG.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import static EPG.StartupConstants.STYLE_SHEET_UI;
import EPG.model.HeadingComponent;
import EPG.model.ImageComponent;
import EPG.model.ListComponent;
import EPG.model.Page;
import EPG.model.ParagraphComponent;
import EPG.model.SlideShowComponent;
import EPG.model.VideoComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Ka Wing Fong
 */
public class ChooseComponentDialog extends Stage{
    GridPane gridPane;
    Label languagePromptLabel;
    ComboBox languageComboBox;
    Button okButton;
    String selectedComponent = "";
    Page page;
    ComponentEditView compEditView;
    public ChooseComponentDialog(Page page, ComponentEditView c) {
        this.compEditView = c;
	// SET THE WINDOW TITLE
        this.page = page;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	this.setTitle(props.getProperty(LABEL_COMPONENT_SELECTION_PROMPT));
	
	// SETUP THE PROMPT
	languagePromptLabel = new Label(props.getProperty(LABEL_COMPONENT_SELECTION_PROMPT));
	
	// INIT THE LANGUAGE CHOICES
	ObservableList<String> languageChoices = FXCollections.observableArrayList();
	languageChoices.add("Heading");
	languageChoices.add(props.getProperty(PARAGRAPH));
	languageChoices.add(props.getProperty(IMAGE));
	languageChoices.add(props.getProperty(VIDEO));
	languageChoices.add(props.getProperty(LIST));
	languageChoices.add(props.getProperty(SLIDESHOW));
	languageComboBox = new ComboBox(languageChoices);
	languageComboBox.getSelectionModel().select("");
	okButton = new Button(OK_BUTTON_TEXT);
	
	gridPane = new GridPane();
	gridPane.add(languagePromptLabel, 0, 0, 2, 1);
	gridPane.add(languageComboBox, 0, 1, 1, 1);
	gridPane.add(okButton, 1, 1, 1, 1);
	
	okButton.setOnAction(e -> {
	    selectedComponent = languageComboBox.getSelectionModel().getSelectedItem().toString();
            addComponent(selectedComponent);
            compEditView.reloadComponents();
	    this.hide();
	});
	
	// SPECIFY STYLE CLASSES
	gridPane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
	languagePromptLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
	languageComboBox.getStyleClass().add(CSS_CLASS_LANG_COMBO_BOX);
	okButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
	
	// NOW SET THE SCENE IN THIS WINDOW
	Scene scene = new Scene(gridPane,250,150);
	scene.getStylesheets().add(STYLE_SHEET_UI);
	setScene(scene);
    }
    
    public String getSelectedComponent() {
	return selectedComponent;
    }
    
    public void addComponent(String type){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        switch(type){
            case "Heading":
                HeadingComponent hComp = new HeadingComponent("new Heading");
                page.addComponent(hComp);
                break;
            case "paragraph":
                ParagraphComponent pComp = new ParagraphComponent("new paragraph component");
                page.addComponent(pComp);
                break;
            case "list":
                ListComponent lComp = new ListComponent("new list component");
                page.addComponent(lComp);
                break;
            case "video":
                VideoComponent vComp = new VideoComponent("new video component");
                page.addComponent(vComp);
                break;
            case "image":
                ImageComponent iComp = new ImageComponent("new image component");
                iComp.setSrc(PATH_SLIDE_SHOW_IMAGES+"/"+DEFAULT_SLIDE_IMAGE);
                page.addComponent(iComp);
                break;
            case "slideshow":
                SlideShowComponent sComp = new SlideShowComponent("new slideshow component",null);
                page.addComponent(sComp);
                break;
            default:
        }
        
    }
}
