/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import static EPG.LanguagePropertyType.EDIT_COMPONENTS;
import static EPG.LanguagePropertyType.SITE_VIEWER;
import static EPG.StartupConstants.CSS_CLASS_COMPONENT_SELECTED;
import static EPG.StartupConstants.CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW;
import static EPG.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static EPG.StartupConstants.STYLE_SHEET_UI;
import EPG.model.Component;
import EPG.model.Page;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    ScrollPane scrollPane =new  ScrollPane(components);
    private Object webView;

    public ComponentEditView(Page page) {
        Label siteName = new Label(page.getTitle());
        initCompEditView();
        components.getChildren().add(siteName);
        this.page = page;
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
        editPage = new Tab();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        editPage.setText(props.getProperty(EDIT_COMPONENTS));
        components = new VBox();
        scrollPane.setContent(components);
        addComponentButton = new Button();
        
        addComponentButton.setText("add Component");
        addComponentButton.setOnAction(e->{
            ChooseComponentDialog chooseComponent = new ChooseComponentDialog(page, this);
            chooseComponent.show();
        });
        removeButton = new Button();
        removeButton.setText("Remove");
        Button saveButton = new Button();
        saveButton.setText("Save site");
        saveButton.setOnAction(e->{
            
        });
        HBox componentToolbar = new HBox();
        componentToolbar.getChildren().add(addComponentButton);
        componentToolbar.getChildren().add(removeButton);
        componentToolbar.getChildren().add(saveButton);
        componentWorkspace.setTop(componentToolbar);
        componentWorkspace.setCenter(scrollPane);
        editPage.setContent(componentWorkspace);
        siteViewer = new Tab();
        webview = new WebView();
        
	scrollPane = new ScrollPane(webview);
	
	// GET THE URL
	String indexPath = "./sites/public_html/Site 2.html";
        System.out.println(indexPath);
	File indexFile = new File(indexPath);
	URL indexURL = null;
        try {
            indexURL = indexFile.toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
        }
	
	// SETUP THE WEB ENGINE AND LOAD THE URL
	WebEngine webEngine = webview.getEngine();
	webEngine.load(indexURL.toExternalForm());
	webEngine.setJavaScriptEnabled(true);
	
        
        removeButton.setOnAction(e->{
            page.remove();
            reloadComponents();
        });
        siteViewer.setOnSelectionChanged(e->{
            
        });
        siteViewer.setContent(webview);
        siteViewer.setText(props.getProperty(SITE_VIEWER));
        this.getTabs().add(editPage);
        this.getTabs().add(siteViewer);     
    }

    public void reloadComponents() {
        components.getChildren().clear();
        Label siteName = new Label(page.getTitle());
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
                a.getStyleClass().add(CSS_CLASS_COMPONENT_SELECTED);
            }else{
                a.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
            }
            components.getChildren().add(a);
        }
    }
}
