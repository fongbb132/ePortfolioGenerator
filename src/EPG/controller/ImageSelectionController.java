/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.controller;

import static EPG.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import EPG.model.Slide;
import EPG.view.SlideEditView;
import EPG.view.SlideShowEditDialog;
import java.io.File;
import javafx.stage.FileChooser;

/**
 *
 * @author wing
 */
public class ImageSelectionController {
SlideShowEditDialog ui;
    
    public ImageSelectionController(SlideShowEditDialog initUi) {   
	ui = initUi;
    }
    
    /**
     * This function provides the response to the user's request to
     * select an image.
     * 
     * @param slideToEdit - Slide for which the user is selecting an image.
     * 
     * @param view The user interface control group where the image
     * will appear after selection.
     */
    public void processSelectImage(Slide slideToEdit, SlideEditView view) {
	FileChooser imageFileChooser = new FileChooser();
	
	// SET THE STARTING DIRECTORY
	imageFileChooser.setInitialDirectory(new File(PATH_SLIDE_SHOW_IMAGES));
	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
	imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);
	
	// LET'S OPEN THE FILE CHOOSER
	File file = imageFileChooser.showOpenDialog(null);
	if (file != null) {
	    String path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
	    String fileName = file.getName();
	    slideToEdit.setImage(path, fileName);
	    view.updateSlideImage();
	    ui.updateFileToolbarControls(false);
	}
    }  
}
