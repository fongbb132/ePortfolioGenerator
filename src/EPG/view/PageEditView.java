/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.LanguagePropertyType;
import static EPG.StartupConstants.CSS_CLASS_CAPTION_PROMPT;
import static EPG.StartupConstants.CSS_CLASS_CAPTION_TEXT_FIELD;
import static EPG.StartupConstants.CSS_CLASS_PAGE_EDIT_VIEW;
import EPG.model.Page;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author wing
 */
public class PageEditView extends HBox{
    EPortfolioView ui; 
    Page page;
    ComponentEditView componentEditView;
    Label siteLabel;
    
    VBox captionVBox;
    TextField captionTextField;
    public PageEditView(EPortfolioView initUI, Page initPage){
        ui = initUI;
        page = initPage;
        this.getStyleClass().add(CSS_CLASS_PAGE_EDIT_VIEW);
	captionVBox = new VBox();
       
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	siteLabel = new Label(props.getProperty(LanguagePropertyType.LABEL_CAPTION));
	captionTextField = new TextField();
	captionTextField.setText(page.getTitle());
	captionVBox.getChildren().add(siteLabel);
	captionVBox.getChildren().add(captionTextField);

	// LAY EVERYTHING OUT INSIDE THIS COMPONENT
	getChildren().add(captionVBox);

	// SETUP THE EVENT HANDLERS
	captionTextField.textProperty().addListener(e -> {
	    String text = captionTextField.getText();
	    page.setTitle(text);	 
	    ui.updateFileToolbarControls(false);
	});
	
	// CHOOSE THE STYLE
	siteLabel.getStyleClass().add(CSS_CLASS_CAPTION_PROMPT);
	captionTextField.getStyleClass().add(CSS_CLASS_CAPTION_TEXT_FIELD);
        this.setOnMouseClicked(e->{
            ui.reloadComponentWorkSpace(page);
        });
    }
}
