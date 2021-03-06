/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.controller;

import static EPG.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import EPG.view.EPortfolioView;
import java.io.File;
import javafx.stage.FileChooser;

/**
 *
 * @author Ka Wing Fong
 */
public class ImageChooser {
    
    /**
     * This function provides the response to the user's request to
     * select an image.
     * 
     * @param slideToEdit - Slide for which the user is selecting an image.
     * 
     * @param view The user interface control group where the image
     * will appear after selection.
     */
    public String[] processSelectImage() {
	FileChooser imageFileChooser = new FileChooser();
	String[] imageInfo = new String[2];
	// SET THE STARTING DIRECTORY
	imageFileChooser.setInitialDirectory(new File(PATH_SLIDE_SHOW_IMAGES));
	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
	FileChooser.ExtensionFilter mp4Filter = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.mp4");
	imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter,mp4Filter);
	
	// LET'S OPEN THE FILE CHOOSER
	File file = imageFileChooser.showOpenDialog(null);
	if (file != null) {
	    String path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
	    String fileName = file.getName();
            imageInfo[0] = path;
            imageInfo[1] = fileName;
	}
        return imageInfo;
    }
}
