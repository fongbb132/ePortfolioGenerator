/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.LanguagePropertyType;
import static EPG.LanguagePropertyType.LABEL_STUDENT_NAME;
import static EPG.LanguagePropertyType.SITE_VIEWER;
import static EPG.LanguagePropertyType.TOOLTIP_ADD_PAGE;
import static EPG.LanguagePropertyType.TOOLTIP_EXIT;
import static EPG.LanguagePropertyType.TOOLTIP_LOAD_PORTFOLIO;
import static EPG.LanguagePropertyType.TOOLTIP_MOVE_DOWN;
import static EPG.LanguagePropertyType.TOOLTIP_MOVE_UP;
import static EPG.LanguagePropertyType.TOOLTIP_NEW_PORTFOLIO;
import static EPG.LanguagePropertyType.TOOLTIP_REMOVE_PAGE;
import static EPG.LanguagePropertyType.TOOLTIP_SAVE_PORTFOLIO;
import static EPG.StartupConstants.CSS_CLASS_BACKGROUND;
import static EPG.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON;
import static EPG.StartupConstants.CSS_CLASS_HORIZONTAL_TOOLBAR_PANE;
import static EPG.StartupConstants.CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW;
import static EPG.StartupConstants.CSS_CLASS_SLIDES_EDITOR_PANE;
import static EPG.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import static EPG.StartupConstants.CSS_CLASS_TITLE_PANE;
import static EPG.StartupConstants.CSS_CLASS_TITLE_PROMPT;
import static EPG.StartupConstants.CSS_CLASS_TITLE_TEXT_FIELD;
import static EPG.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_BUTTON;
import static EPG.StartupConstants.CSS_CLASS_VERTICAL_TOOLBAR_PANE;
import static EPG.StartupConstants.CSS_CLASS_WORKSPACE;
import static EPG.StartupConstants.ICON_ADD_PAGE;
import static EPG.StartupConstants.ICON_EXIT;
import static EPG.StartupConstants.ICON_LOAD_PORTFOLIO;
import static EPG.StartupConstants.ICON_MOVE_DOWN;
import static EPG.StartupConstants.ICON_MOVE_UP;
import static EPG.StartupConstants.ICON_NEW_PORTFOLIO;
import static EPG.StartupConstants.ICON_REMOVE_PAGE;
import static EPG.StartupConstants.ICON_SAVE_AS;
import static EPG.StartupConstants.ICON_SAVE_PORTFOLIO;
import static EPG.StartupConstants.ICON_VIEW_SLIDE_SHOW;
import static EPG.StartupConstants.PATH_ICONS;
import static EPG.StartupConstants.STYLE_SHEET_UI;
import EPG.controller.FileController;
import EPG.controller.PortfolioController;
import EPG.handler.ErrorHandler;
import EPG.manager.EPortfolioFileManager;
import EPG.manager.createNameSiteController;
import EPG.model.EPortfolio;
import EPG.model.Page;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author wing
 */
public class EPortfolioView {

    Stage primaryStage;
    Scene primaryScene;
    private EPortfolioFileManager fileManager;
    private EPortfolio ePortfolio;
    private ErrorHandler errorHandler;

    BorderPane totalPane;

    FlowPane fileToolbarPane;

    Button newPortfolioButton;
    Button loadPortfolioButton;
    Button savePortfolioButton;
    Button viewPortfolioButton;
    Button saveAsButton;
    Button exitButton;

    BorderPane workspace;
    FlowPane pageEditToolbar;
    Button addPageButton;
    Button removeButton;
    Button movePageUpButton;
    Button movePageDownButton;

    FlowPane studentPane;
    Label studentLabel;
    TextField studentTextField;

    ScrollPane pagesEditorScrollPane;
    VBox pagesEditorPane;
    HBox centralPane;
   
    VBox componentEditVBox;
    Page page;

    private FileController fileController;
    private PortfolioController editController;
    createNameSiteController createSiteController;
    
    public EPortfolioView(EPortfolioFileManager fileManager) {
        this.fileManager = fileManager;
        ePortfolio = new EPortfolio("Untitled ePortfolio",this);
        errorHandler = new ErrorHandler();
	studentTextField = new TextField();
        createSiteController = new createNameSiteController(this);
        studentTextField.setText(ePortfolio.getName());
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void startUI(Stage primaryStage, String title) {
        initFileToolbar();
        initWorkspace();
        initEventHandlers();
        this.primaryStage = primaryStage;
        initWindow(title);
    }

    private void initWindow(String title) {
        primaryStage.setTitle(title);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinX());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        totalPane = new BorderPane();
        totalPane.getStyleClass().add(CSS_CLASS_WORKSPACE);
        totalPane.setTop(fileToolbarPane);
        primaryScene = new Scene(totalPane);
        
        String css = this.getClass().getResource(STYLE_SHEET_UI).toExternalForm();
        primaryScene.getStylesheets().add(STYLE_SHEET_UI);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }

    private void initFileToolbar() {
        fileToolbarPane = new FlowPane();
        fileToolbarPane.getStyleClass().add(CSS_CLASS_HORIZONTAL_TOOLBAR_PANE);

        PropertiesManager props = PropertiesManager.getPropertiesManager();
        newPortfolioButton = initChildButton(fileToolbarPane, ICON_NEW_PORTFOLIO, TOOLTIP_NEW_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        loadPortfolioButton = initChildButton(fileToolbarPane, ICON_LOAD_PORTFOLIO, TOOLTIP_LOAD_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        savePortfolioButton = initChildButton(fileToolbarPane, ICON_SAVE_PORTFOLIO, TOOLTIP_SAVE_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
        saveAsButton = initChildButton(fileToolbarPane, ICON_SAVE_AS, TOOLTIP_SAVE_PORTFOLIO, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON,true);
        viewPortfolioButton = initChildButton(fileToolbarPane, ICON_VIEW_SLIDE_SHOW, SITE_VIEWER, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
        exitButton = initChildButton(fileToolbarPane, ICON_EXIT, TOOLTIP_EXIT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
    }

    private void initWorkspace() {
        workspace = new BorderPane();

        pageEditToolbar = new FlowPane();
        addPageButton = initChildButton(pageEditToolbar, ICON_ADD_PAGE, TOOLTIP_ADD_PAGE, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        removeButton = initChildButton(pageEditToolbar, ICON_REMOVE_PAGE, TOOLTIP_REMOVE_PAGE, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        //movePageUpButton = initChildButton(pageEditToolbar, ICON_MOVE_UP, TOOLTIP_MOVE_UP, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);
        //movePageDownButton = initChildButton(pageEditToolbar, ICON_MOVE_DOWN, TOOLTIP_MOVE_DOWN, CSS_CLASS_VERTICAL_TOOLBAR_BUTTON, true);

        pagesEditorPane = new VBox();
        pagesEditorScrollPane = new ScrollPane(pagesEditorPane);
        pagesEditorScrollPane.setFitToWidth(true);
        initTitleControls();
        
        centralPane = new HBox();
        centralPane.getChildren().add(pagesEditorScrollPane);
        componentEditVBox = new VBox();
        componentEditVBox.getStyleClass().add(CSS_CLASS_BACKGROUND);
        centralPane.getStyleClass().add(CSS_CLASS_BACKGROUND);
        centralPane.getChildren().add(componentEditVBox);
        workspace.setLeft(pageEditToolbar);
        workspace.setCenter(centralPane);

        workspace.getStyleClass().add(CSS_CLASS_WORKSPACE);
        pageEditToolbar.getStyleClass().add(CSS_CLASS_VERTICAL_TOOLBAR_PANE);
        pagesEditorPane.getStyleClass().add(CSS_CLASS_SLIDES_EDITOR_PANE);
        pagesEditorScrollPane.getStyleClass().add(CSS_CLASS_SLIDES_EDITOR_PANE);

    }

    private void initEventHandlers() {
        fileController = new FileController(this, fileManager);
        newPortfolioButton.setOnAction(e->{
            this.viewPortfolioButton.setDisable(false);
            this.saveAsButton.setDisable(false);
            this.savePortfolioButton.setDisable(false);
            fileController.handleNewPortfolioRequest();
        });
        loadPortfolioButton.setOnAction(e->{
            this.viewPortfolioButton.setDisable(false);
            this.saveAsButton.setDisable(false);
            this.savePortfolioButton.setDisable(false);
            fileController.handleLoadEportfolioRequest();
        });
        savePortfolioButton.setOnAction(e->{
            fileController.handleSaveEPortfolioRequest();
        });
        
        saveAsButton.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
  
              //Set extension filter
              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
              fileChooser.getExtensionFilters().add(extFilter);
              
              //Show save file dialog
              File file = fileChooser.showSaveDialog(primaryStage);
              
              if(file != null){
                  fileController.handSaveAsPortfolioRequest(file.toString());
              }
        });
        
        exitButton.setOnAction(e->{
            fileController.handleExitRequest();
        });
        viewPortfolioButton.setOnAction(e->{
            EPortfolioFileManager fileManager = new EPortfolioFileManager();
            try {
                fileManager.saveEPortfolio(ePortfolio);
                createSiteController.copySampleSite();
                System.out.println("SLIDE SHOW SAVED");
            }
            catch(IOException ioe) {
                ioe.printStackTrace();
                System.exit(-1);
            }
            SiteViewer siteView = new SiteViewer(this,-1);
            try {
                siteView.startSiteView();
            } catch (MalformedURLException ex) {
                Logger.getLogger(EPortfolioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        editController = new PortfolioController(this);
        addPageButton.setOnAction(e->{
            editController.processAddPageRequest();
        });
        removeButton.setOnAction(e->{
            editController.processRemovePageRequest();
            this.clearEditPageView();
        });
        
    }
    
    public void reloadPagePane(){
        pagesEditorPane.getChildren().clear();
        reloadTitleControls();
        for(Page page: ePortfolio.getPages()){
            PageEditView pageEditor = new PageEditView(this, page);
            if(ePortfolio.isSelectedPage(page)){
                reloadComponentWorkSpace(page);
                pageEditor.getStyleClass().add(CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW);
            }else{
                pageEditor.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
            }
	    pagesEditorPane.getChildren().add(pageEditor);
	    pageEditor.setOnMousePressed(e -> {
                removeButton.setDisable(false);
		ePortfolio.setSelectedPage(page);
		this.reloadPagePane();
	    });
        }
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

    private void initTitleControls() {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String labelPrompt = props.getProperty(LABEL_STUDENT_NAME);
	studentPane = new FlowPane();
	studentLabel = new Label(labelPrompt);
	HBox hBox = new HBox();
        
        hBox.getChildren().add(studentLabel);
        hBox.getChildren().add(studentTextField);
	studentPane.getChildren().add(hBox);
        
        studentTextField.setOnKeyReleased(e->{
            ePortfolio.setName(studentTextField.getText());
            updateFileToolbarControls(false);
        });
	
	studentPane.getStyleClass().add(CSS_CLASS_TITLE_PANE);
	studentLabel.getStyleClass().add(CSS_CLASS_TITLE_PROMPT);
	studentTextField.getStyleClass().add(CSS_CLASS_TITLE_TEXT_FIELD);
    }
    
    
    
    public void reloadTitleControls() {
	if (pagesEditorPane.getChildren().size() == 0)
	    pagesEditorPane.getChildren().add(studentPane);
	studentTextField.setText(ePortfolio.getName());
    }
    
    public void updateFileToolbarControls(boolean saved) {
	// FIRST MAKE SURE THE WORKSPACE IS THERE
	totalPane.setCenter(workspace);
	
	updatePortfolioEditToolbarControls();
    }
    
    public void updatePortfolioEditToolbarControls() {
	// AND THE SLIDESHOW EDIT TOOLBAR
	addPageButton.setDisable(false);
	boolean slideSelected = ePortfolio.isPageSelected();
	removeButton.setDisable(!slideSelected);	
    }

    public EPortfolio getEPortfolio() {
        return ePortfolio;
    }

    public Stage getWindow() {
        return primaryStage;
    }
    
    public void reloadComponentWorkSpace(Page page){
        componentEditVBox.getChildren().clear();
        ComponentEditView newEdit = new ComponentEditView(page,this);
        newEdit.getStylesheets().add(STYLE_SHEET_UI);
        newEdit.reloadComponents();
        componentEditVBox.getChildren().add(newEdit);
    }

    public void clearEditPageView() {
        componentEditVBox.getChildren().clear();
    }
    
     private void SaveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
             
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(JavaFX_Text.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
}
