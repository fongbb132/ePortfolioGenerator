/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.LanguagePropertyType;
import static EPG.LanguagePropertyType.EDIT_COMPONENTS;
import static EPG.LanguagePropertyType.SITE_VIEWER;
import static EPG.LanguagePropertyType.TOOLTIP_ADD_SLIDE;
import static EPG.LanguagePropertyType.TOOLTIP_REMOVE;
import static EPG.StartupConstants.CSS_CLASS_BACKGROUND;
import static EPG.StartupConstants.CSS_CLASS_COMPONENT_SELECTED;
import static EPG.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON;
import static EPG.StartupConstants.CSS_CLASS_LANG_PROMPT;
import static EPG.StartupConstants.CSS_CLASS_SCROLLPANE;
import static EPG.StartupConstants.CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW;
import static EPG.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static EPG.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static EPG.StartupConstants.ICON_ADD;
import static EPG.StartupConstants.ICON_REMOVE;
import static EPG.StartupConstants.PATH_ICONS;
import static EPG.StartupConstants.STYLE_SHEET_UI;
import EPG.controller.createSiteController;
import EPG.manager.EPortfolioFileManager;
import EPG.manager.createNameSiteController;
import EPG.model.Component;
import EPG.model.Page;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import properties_manager.PropertiesManager;

/**
 *
 * @author Ka Wing Fong
 */
public class ComponentEditView extends TabPane{
    Tab editPage;
    Tab siteViewer;
    VBox components;
    WebView webview;
    BorderPane componentWorkspace;
    Button removeButton;
    Page page;
    Button addComponentButton;
    ScrollPane scrollPane ;
    EPortfolioView ui;

    ComponentEditView(Page page, EPortfolioView aThis) {
        ui = aThis;
        Label siteName = new Label(page.getTitle());
        scrollPane =new  ScrollPane(componentWorkspace);
        scrollPane.getStyleClass().add(CSS_CLASS_BACKGROUND);
        initCompEditView();
        components.getChildren().add(siteName);
        this.page = page;
        this.getStyleClass().add(CSS_CLASS_BACKGROUND);
        this.getStylesheets().add(STYLE_SHEET_UI);
    }
    
    public Tab getEditPage() {
        return editPage;
    }

    public void setEditPage(Tab editPage) {
        this.editPage = editPage;
    }

    public Tab getSiteViewer() {
        return siteViewer;
    }

    public void setSiteViewer(Tab siteViewer) {
        this.siteViewer = siteViewer;
    }

    public VBox getComponents() {
        return components;
    }

    public void setComponents(VBox components) {
        this.components = components;
    }
    
    public void initCompEditView(){
        componentWorkspace = new BorderPane();
        componentWorkspace.getStyleClass().add(CSS_CLASS_BACKGROUND);
        editPage = new Tab();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        editPage.setText(props.getProperty(EDIT_COMPONENTS));
        
        components = new VBox();
        components.getStyleClass().add(CSS_CLASS_BACKGROUND);
        scrollPane.setContent(components);
        HBox componentToolbar = new HBox();
        addComponentButton = initChildButton(componentToolbar,		ICON_ADD,	    "Add Component",	    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON,  false);
        
        removeButton = initChildButton(componentToolbar,		ICON_REMOVE,	    "Remove Component",	    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON,  false);
        
        addComponentButton.setOnAction(e->{
            ChooseComponentDialog chooseComponent = new ChooseComponentDialog(page, this);
            chooseComponent.show();
        });
        removeButton.setOnAction(e->{
            page.remove();
            reloadComponents();
        });
        
        componentWorkspace.setTop(componentToolbar);
        componentWorkspace.setCenter(scrollPane);
        componentWorkspace.getStyleClass().add("root");
        editPage.setContent(componentWorkspace);
        siteViewer = new Tab();
        webview = new WebView();
        webview.getStyleClass().add(CSS_CLASS_BACKGROUND);
        
	scrollPane = new ScrollPane(webview);
	scrollPane.setFitToWidth(true);
	scrollPane.setFitToHeight(true);
	scrollPane.getStyleClass().add(CSS_CLASS_BACKGROUND);
	// GET THE URL
        
        
	
	// SET THE WINDOW TITLE
	
        siteViewer.setOnSelectionChanged(e->{
            EPortfolioFileManager fileManager = new EPortfolioFileManager();
            try {
                fileManager.saveEPortfolio(ui.getEPortfolio());
            } catch (IOException ex) {
                Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
            }
            createNameSiteController createSiteController = new createNameSiteController(ui);
            try {
                createSiteController.copySampleSite();
            } catch (IOException ex) {
                Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("SLIDE SHOW SAVED");
            String indexPath;
            indexPath = "./sites/"+ui.getEPortfolio().getName()+"/"+ui.getEPortfolio().getPages().get(page.getPosition()).getTitle()+".html";

            File indexFile = new File(indexPath);
            URL indexURL=null;
            try {
                indexURL = indexFile.toURI().toURL();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
            }

            // SETUP THE WEB ENGINE AND LOAD THE URL
            WebEngine webEngine = webview.getEngine();
            webEngine.load(indexURL.toExternalForm());
            webEngine.setJavaScriptEnabled(true);
        });
        siteViewer.setContent(webview);
        siteViewer.setText(props.getProperty(SITE_VIEWER));
        this.getTabs().add(editPage);
        this.getTabs().add(siteViewer);     
    }

    public void reloadComponents() {
        components.getChildren().clear();
        Label siteName = new Label(page.getTitle());
        siteName.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        components.getChildren().add(siteName);
        StyleEditPane stylePane = new StyleEditPane(page);
        components.getChildren().add(stylePane);
        for(Component comp:page.getComponents()){
            ComponentView a = new ComponentView(comp,this);
            a.setOnMouseClicked(e->{
                page.setSelectedComp(comp);
                reloadComponents();
            });
            if(page.getSelectedComp()==comp){
                a.getStyleClass().add(STYLE_SHEET_UI);
                a.getStyleClass().add("component_selected");
            }else{
                a.getStyleClass().add("component_view");
            }
            components.getChildren().add(a);
        }
    }
    
    public Button initChildButton(
	    Pane toolbar, 
	    String iconFileName, 
	    String tooltip, 
	    String cssClass,
	    boolean disabled) {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String imagePath = "file:" + PATH_ICONS + iconFileName;
	Image buttonImage = new Image(imagePath);
	Button button = new Button();
	button.getStyleClass().add(cssClass);
	button.setDisable(disabled);
	button.setGraphic(new ImageView(buttonImage));
	Tooltip buttonTooltip = new Tooltip(tooltip);
	button.setTooltip(buttonTooltip);
	toolbar.getChildren().add(button);
	return button;
    }
}
