/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.controller;

import static EPG.LanguagePropertyType.DEFAULT_IMAGE_CAPTION;
import static EPG.StartupConstants.DEFAULT_SLIDE_IMAGE;
import static EPG.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import EPG.model.SlideShowModel;
import EPG.view.SlideShowEditDialog;
import properties_manager.PropertiesManager;

/**
 *
 * @author Ka Wing Fong
 */
public class SlideShowEditController {
    SlideShowEditDialog ui;

    public SlideShowEditController(SlideShowEditDialog aThis) {
        ui = aThis;
    }

    public void processAddSlideRequest() {
        SlideShowModel slideShow = ui.getSlideShow();
	PropertiesManager props = PropertiesManager.getPropertiesManager();
	slideShow.addSlide(DEFAULT_SLIDE_IMAGE, PATH_SLIDE_SHOW_IMAGES, props.getProperty(DEFAULT_IMAGE_CAPTION));
	ui.updateFileToolbarControls(false);
    }

    public void processRemoveSlideRequest() {
        SlideShowModel slideShow = ui.getSlideShow();
	slideShow.removeSelectedSlide();
	ui.reloadSlideShowPane();
	ui.updateFileToolbarControls(false);
    
    }

    public void processMoveSlideUpRequest() {
        SlideShowModel slideShow = ui.getSlideShow();
	slideShow.moveSelectedSlideUp();	
	ui.updateFileToolbarControls(false);	
    }

    public void processMoveSlideDownRequest() {
        SlideShowModel slideShow = ui.getSlideShow();
	slideShow.moveSelectedSlideDown();	
	ui.updateFileToolbarControls(false);
    }
    
}
