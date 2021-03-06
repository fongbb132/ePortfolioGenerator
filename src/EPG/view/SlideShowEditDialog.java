/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.LanguagePropertyType;
import static EPG.LanguagePropertyType.LABEL_SLIDESHOW_TITLE;
import static EPG.LanguagePropertyType.TOOLTIP_ADD_SLIDE;
import static EPG.LanguagePropertyType.TOOLTIP_EXIT;
import static EPG.LanguagePropertyType.TOOLTIP_LOAD_SLIDE_SHOW;
import static EPG.LanguagePropertyType.TOOLTIP_MOVE_DOWN;
import static EPG.LanguagePropertyType.TOOLTIP_MOVE_UP;
import static EPG.LanguagePropertyType.TOOLTIP_NEW_SLIDE_SHOW;
import static EPG.LanguagePropertyType.TOOLTIP_REMOVE;
import static EPG.LanguagePropertyType.TOOLTIP_SAVE_SLIDE_SHOW;
import static EPG.LanguagePropertyType.TOOLTIP_VIEW_SLIDE_SHOW;
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
import static EPG.StartupConstants.ICON_ADD;
import static EPG.StartupConstants.ICON_EXIT;
import static EPG.StartupConstants.ICON_LOAD_SLIDE_SHOW;
import static EPG.StartupConstants.ICON_MOVE_DOWN;
import static EPG.StartupConstants.ICON_MOVE_UP;
import static EPG.StartupConstants.ICON_NEW_SLIDE_SHOW;
import static EPG.StartupConstants.ICON_REMOVE;
import static EPG.StartupConstants.ICON_SAVE_SLIDE_SHOW;
import static EPG.StartupConstants.ICON_VIEW_SLIDE_SHOW;
import static EPG.StartupConstants.PATH_ICONS;
import static EPG.StartupConstants.STYLE_SHEET_UI;
import EPG.controller.FileController;
import EPG.controller.SlideShowEditController;
import EPG.controller.SlideShowFileController;
import EPG.handler.ErrorHandler;
import EPG.manager.SlideShowFileManager;
import EPG.model.Component;
import EPG.model.Slide;
import EPG.model.SlideShowComponent;
import EPG.model.SlideShowModel;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

/**
 *
 * @author Ka Wing Fong
 */
public class SlideShowEditDialog extends Stage{
    Scene primaryScene;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    BorderPane ssmPane;

    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPane;
    Button newSlideShowButton;
    Button loadSlideShowButton;
    Button saveSlideShowButton;
    Button viewSlideShowButton;
    Button exitButton;
    
    // WORKSPACE
    BorderPane workspace;

    // THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
    FlowPane slideEditToolbar;
    Button addSlideButton;
    Button removeSlideButton;
    Button moveSlideUpButton;
    Button moveSlideDownButton;
    
    // FOR THE SLIDE SHOW TITLE
    FlowPane titlePane;
    Label titleLabel;
    TextField titleTextField;
    
    // AND THIS WILL GO IN THE CENTER
    ScrollPane slidesEditorScrollPane;
    VBox slidesEditorPane;

    // THIS IS THE SLIDE SHOW WE'RE WORKING WITH
    SlideShowModel slideShow;

    // THIS IS FOR SAVING AND LOADING SLIDE SHOWS
    SlideShowFileManager fileManager;
    
    // THIS IS FOR EXPORTING THE SLIDESHOW SITE

    // THIS CLASS WILL HANDLE ALL ERRORS FOR THIS PROGRAM
    private ErrorHandler errorHandler;

    // THIS CONTROLLER WILL ROUTE THE PROPER RESPONSES
    // ASSOCIATED WITH THE FILE TOOLBAR
    private SlideShowFileController fileController;
    
    // THIS CONTROLLER RESPONDS TO SLIDE SHOW EDIT BUTTONS
    private SlideShowEditController editController;
    SlideShowComponent slideshow;
    ComponentEditView componentView;
    /**
     * Default constructor, it initializes the GUI for use, but does not yet
     * load all the language-dependent controls, that needs to be done via the
     * startUI method after the user has selected a language.
     */
    public SlideShowEditDialog(Component c, ComponentEditView a) {
	// FIRST HOLD ONTO THE FILE MANAGER
        slideshow = (SlideShowComponent)c;
        slideshow.setSlideShowEditDialog(this);
	componentView = a;
	// WE'LL USE THIS ERROR HANDLER WHEN SOMETHING GOES WRONG
	errorHandler = new ErrorHandler();
	
	// MAKE THE DATA MANAGING MODEL
        
	slideShow = slideshow.getSlideShowModel();
        
	fileController = new SlideShowFileController(this, fileManager);
        
        startUI(this, "Edit Slide Show");
        initWindow("Edit Slide Show");
        
	fileController.handleNewSlideShowRequest();
        updateSlideshowEditToolbarControls();
    }

    // ACCESSOR METHODS
    public SlideShowModel getSlideShow() {
	return slideShow;
    }

    public Stage getWindow() {
	return this;
    }

    public ErrorHandler getErrorHandler() {
	return errorHandler;
    }

    /**
     * Initializes the UI controls and gets it rolling.
     * 
     * @param initPrimaryStage The window for this application.
     * 
     * @param windowTitle The title for this window.
     */
    public void startUI(Stage initPrimaryStage, String windowTitle) {
	// THE TOOLBAR ALONG THE NORTH
	initFileToolbar();
        
        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
	// TO THE WINDOW YET
	initWorkspace();

	// NOW SETUP THE EVENT HANDLERS
	initEventHandlers();

	initWindow(windowTitle);
    }

    // UI SETUP HELPER METHODS
    private void initWorkspace() {
	// FIRST THE WORKSPACE ITSELF, WHICH WILL CONTAIN TWO REGIONS
	workspace = new BorderPane();
	
	// THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
	slideEditToolbar = new FlowPane();
	addSlideButton = this.initChildButton(slideEditToolbar,		ICON_ADD,	    TOOLTIP_ADD_SLIDE,	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	removeSlideButton = this.initChildButton(slideEditToolbar,	ICON_REMOVE,  TOOLTIP_REMOVE,   CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	moveSlideUpButton = this.initChildButton(slideEditToolbar,	ICON_MOVE_UP,	    TOOLTIP_MOVE_UP,	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	moveSlideDownButton = this.initChildButton(slideEditToolbar,	ICON_MOVE_DOWN,	    TOOLTIP_MOVE_DOWN,	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  true);
	
	// AND THIS WILL GO IN THE CENTER
	slidesEditorPane = new VBox();
	slidesEditorScrollPane = new ScrollPane(slidesEditorPane);
	slidesEditorScrollPane.setFitToWidth(true);
	slidesEditorScrollPane.setFitToHeight(true);
	initTitleControls();
	
	// NOW PUT THESE TWO IN THE WORKSPACE
	workspace.setLeft(slideEditToolbar);
	workspace.setCenter(slidesEditorScrollPane);

	// SETUP ALL THE STYLE CLASSES
	workspace.getStyleClass().add(CSS_CLASS_WORKSPACE);
	slideEditToolbar.getStyleClass().add(CSS_CLASS_VERTICAL_TOOLBAR_PANE);
	slidesEditorPane.getStyleClass().add(CSS_CLASS_SLIDES_EDITOR_PANE);
	slidesEditorScrollPane.getStyleClass().add(CSS_CLASS_SLIDES_EDITOR_PANE);
    }

    private void initEventHandlers() {
	// FIRST THE FILE CONTROLS
	newSlideShowButton.setOnAction(e -> {
	    fileController.handleNewSlideShowRequest();
            updateSlideshowEditToolbarControls();
	});
	saveSlideShowButton.setOnAction(e -> {
	    fileController.handleSaveSlideShowRequest();
	});
	viewSlideShowButton.setOnAction(e -> {
	    fileController.handleViewSlideShowRequest();
	});
	exitButton.setOnAction(e -> {
	    fileController.handleExitRequest();
	});
	
	// THEN THE SLIDE SHOW EDIT CONTROLS
	editController = new SlideShowEditController(this);
	addSlideButton.setOnAction(e -> {
	    editController.processAddSlideRequest();
	});
	removeSlideButton.setOnAction(e -> {
	    editController.processRemoveSlideRequest();
	});
	moveSlideUpButton.setOnAction(e -> {
	    editController.processMoveSlideUpRequest();
	});
	moveSlideDownButton.setOnAction(e -> {
	    editController.processMoveSlideDownRequest();
	});
    }

    /**
     * This function initializes all the buttons in the toolbar at the top of
     * the application window. These are related to file management.
     */
    private void initFileToolbar() {
	fileToolbarPane = new FlowPane();
	fileToolbarPane.getStyleClass().add(CSS_CLASS_HORIZONTAL_TOOLBAR_PANE);

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
	// START AS ENABLED (false), WHILE OTHERS DISABLED (true)
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	newSlideShowButton = initChildButton(fileToolbarPane, ICON_NEW_SLIDE_SHOW,	TOOLTIP_NEW_SLIDE_SHOW,	    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
        saveSlideShowButton = initChildButton(fileToolbarPane, ICON_SAVE_SLIDE_SHOW, TOOLTIP_SAVE_SLIDE_SHOW, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON,false);
	viewSlideShowButton = initChildButton(fileToolbarPane, ICON_VIEW_SLIDE_SHOW,	TOOLTIP_VIEW_SLIDE_SHOW,    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, true);
	exitButton = initChildButton(fileToolbarPane, ICON_EXIT, TOOLTIP_EXIT, CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON, false);
    }

    private void initWindow(String windowTitle) {
	// SET THE WINDOW TITLE
	this.setTitle(windowTitle);

	// GET THE SIZE OF THE SCREEN
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();

	// AND USE IT TO SIZE THE WINDOW
	this.setX(bounds.getMinX()+100);
	this.setY(bounds.getMinY()+100);
	this.setWidth(bounds.getWidth()-200);
	this.setHeight(bounds.getHeight()-200);

        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
	ssmPane = new BorderPane();
	ssmPane.getStyleClass().add(CSS_CLASS_WORKSPACE);
	primaryScene = new Scene(ssmPane);
	
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
	// WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
	primaryScene.getStylesheets().add(STYLE_SHEET_UI);
	this.setScene(primaryScene);
	this.show();
    }
    
    /**
     * This helps initialize buttons in a toolbar, constructing a custom button
     * with a customly provided icon and tooltip, adding it to the provided
     * toolbar pane, and then returning it.
     */
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
    
    /**
     * Updates the enabled/disabled status of all toolbar
     * buttons.
     * 
     * @param saved 
     */
    public void updateFileToolbarControls(boolean saved) {
	// FIRST MAKE SURE THE WORKSPACE IS THERE
	ssmPane.setCenter(workspace);
	
	// NEXT ENABLE/DISABLE BUTTONS AS NEEDED IN THE FILE TOOLBAR
	saveSlideShowButton.setDisable(saved);
	viewSlideShowButton.setDisable(false);
	
	updateSlideshowEditToolbarControls();
    }
    
    public void updateSlideshowEditToolbarControls() {
	// AND THE SLIDESHOW EDIT TOOLBAR
	addSlideButton.setDisable(false);
	boolean slideSelected = slideShow.isSlideSelected();
	removeSlideButton.setDisable(!slideSelected);
	moveSlideUpButton.setDisable(!slideSelected);
	moveSlideDownButton.setDisable(!slideSelected);	
    }

    /**
     * Uses the slide show data to reload all the components for
     * slide editing.
     * 
     * @param slideShowToLoad SLide show being reloaded.
     */
    public void reloadSlideShowPane() {
	slidesEditorPane.getChildren().clear();
	reloadTitleControls();
	for (Slide slide : slideShow.getSlides()) {
	    SlideEditView slideEditor = new SlideEditView(this, slide);
	    if (slideShow.isSelectedSlide(slide))
		slideEditor.getStyleClass().add(CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW);
	    else
		slideEditor.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
	    slidesEditorPane.getChildren().add(slideEditor);
	    slideEditor.setOnMousePressed(e -> {
		slideShow.setSelectedSlide(slide);
		this.reloadSlideShowPane();
	    });
	}
	updateSlideshowEditToolbarControls();
    }
    
    private void initTitleControls() {
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	String labelPrompt = props.getProperty(LABEL_SLIDESHOW_TITLE);
	titlePane = new FlowPane();
	titleLabel = new Label(labelPrompt);
	titleTextField = new TextField();
	
	titlePane.getChildren().add(titleLabel);
	titlePane.getChildren().add(titleTextField);
	
	String titlePrompt = props.getProperty(LanguagePropertyType.LABEL_SLIDESHOW_TITLE);
	titleTextField.setText(titlePrompt);
	
	titleTextField.textProperty().addListener(e -> {
	    slideShow.setTitle(titleTextField.getText());
            componentView.reloadComponents();
	    updateFileToolbarControls(false);
	});
	
	titlePane.getStyleClass().add(CSS_CLASS_TITLE_PANE);
	titleLabel.getStyleClass().add(CSS_CLASS_TITLE_PROMPT);
	titleTextField.getStyleClass().add(CSS_CLASS_TITLE_TEXT_FIELD);
    }
    
    public void reloadTitleControls() {
	if (slidesEditorPane.getChildren().size() == 0)
	    slidesEditorPane.getChildren().add(titlePane);
	titleTextField.setText(slideShow.getTitle());
    }
}
