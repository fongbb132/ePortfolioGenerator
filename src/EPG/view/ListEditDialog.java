/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.LanguagePropertyType;
import static EPG.LanguagePropertyType.TOOLTIP_ADD_LIST_ITEM;
import static EPG.LanguagePropertyType.TOOLTIP_MOVE_DOWN;
import static EPG.LanguagePropertyType.TOOLTIP_MOVE_UP;
import static EPG.LanguagePropertyType.TOOLTIP_REMOVE;
import static EPG.StartupConstants.CSS_CLASS_SLIDES_EDITOR_PANE;
import static EPG.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static EPG.StartupConstants.CSS_CLASS_TITLE_TEXT_FIELD;
import static EPG.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static EPG.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_PANE;
import static EPG.StartupConstants.ICON_ADD;
import static EPG.StartupConstants.ICON_MOVE_DOWN;
import static EPG.StartupConstants.ICON_MOVE_UP;
import static EPG.StartupConstants.ICON_REMOVE;
import static EPG.StartupConstants.PATH_ICONS;
import static EPG.StartupConstants.STYLE_SHEET_UI;
import EPG.model.Component;
import EPG.model.ListComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Ka Wing Fong
 */
public final class ListEditDialog extends Stage{
    BorderPane borderPane;
    VBox ListToolbar;
    VBox textFieldList;
    Button addListButton;
    Button removeItemButton;
    Button moveItemUpButton;
    Button moveItemDownButton;
    Button okButton;
    ListComponent list;
    ComponentEditView componentEditView;
    public ListEditDialog(Component c, ComponentEditView a){
        list = (ListComponent)c;
        componentEditView = a;
        borderPane = new BorderPane();
        ListToolbar = new VBox();
        textFieldList = new VBox();
        borderPane.setLeft(ListToolbar);
        borderPane.setCenter(textFieldList);
        reloadList();
        addListButton = initChildButton(ListToolbar,ICON_ADD,TOOLTIP_ADD_LIST_ITEM,CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,false);
        removeItemButton = initChildButton(ListToolbar,ICON_REMOVE,TOOLTIP_REMOVE,CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,false);
	moveItemUpButton = initChildButton(ListToolbar,ICON_MOVE_UP,TOOLTIP_MOVE_UP,CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,false);
	moveItemDownButton = initChildButton(ListToolbar,ICON_MOVE_DOWN,TOOLTIP_MOVE_DOWN,CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,false);
        okButton = new Button();
        okButton.setText("OK");
        borderPane.setBottom(okButton);
        
        initOnClick();
        Scene scene = new Scene(borderPane,800, 500);
        scene.getStylesheets().add(STYLE_SHEET_UI); 
        setScene(scene);
    }
    
    public void initOnClick(){
        addListButton.setOnAction(e->{
            list.addList("");
            reloadList();
        });
        
        okButton.setOnAction(e->{
            componentEditView.reloadComponents();
            this.hide();
        });
    }
    
    public Button initChildButton(
	    Pane toolbar, 
	    String iconFileName, 
	    LanguagePropertyType tooltip, 
	    String cssClass,
	    boolean disabled) {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String imagePath = "file:" + PATH_ICONS + iconFileName;
	Image buttonImage = new Image(imagePath);
	Button button = new Button();
	button.getStyleClass().add(cssClass);
	button.setDisable(disabled);
	button.setGraphic(new ImageView(buttonImage));
	Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
	button.setTooltip(buttonTooltip);
	toolbar.getChildren().add(button);
	return button;
    }
    
    public void reloadList(){
        textFieldList.getChildren().clear();
        for(int i = 0; i< list.getList().size();i++){
            String b= list.getList().get(i);
            TextField a = new TextField(b);
            a.getStyleClass().add("-fx-padding: 5 5 5 5");
            a.textProperty().addListener(e->{
                String text = a.getText();
                list.removeList(b,text);
            });
            a.getStyleClass().add(CSS_CLASS_TITLE_TEXT_FIELD);
            textFieldList.getChildren().add(a);
        }
    }
}
