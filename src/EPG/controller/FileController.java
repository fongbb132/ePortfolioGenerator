/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.controller;

import EPG.LanguagePropertyType;
import static EPG.LanguagePropertyType.LABEL_SAVE_UNSAVED_WORK;
import EPG.handler.ErrorHandler;
import EPG.manager.EPortfolioFileManager;
import EPG.model.EPortfolio;
import EPG.view.EPortfolioView;
import EPG.view.YesNoCancelDialog;
import java.io.IOException;
import properties_manager.PropertiesManager;

/**
 *
 * @author wing
 */
public class FileController {

    private boolean saved;
    private EPortfolioView ui; 
    private EPortfolioFileManager pageIO;
    public FileController(EPortfolioView aThis, EPortfolioFileManager fileManager) {
        this.ui = aThis;
        saved = true;
        pageIO = fileManager;
    }
    
    public void markAsEdited(){
        saved = false;
        ui.updateFileToolbarControls(saved);
    }
    
    public void handleNewSlideShowRequest(){
        try{
            boolean continueToMakeNew = true;
            if(!saved){
                continueToMakeNew = promptToSave();
                
            }
            if(continueToMakeNew){
                EPortfolio ePortfolio = ui.getEPortfolio();
                ePortfolio.reset();
                saved=false;
                ui.updateFileToolbarControls(saved);
                ui.reloadTitleControls();
                ui.reloadPagePane();
            }
        }catch(IOException ioe){
            ErrorHandler eh = ui.getErrorHandler();
            eh.processError(LanguagePropertyType.ERROR_UNEXPECTED);
        }
    }

    
    private boolean promptToSave() throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
	YesNoCancelDialog yesNoCancelDialog = new YesNoCancelDialog(ui.getWindow());
	PropertiesManager props = PropertiesManager.getPropertiesManager();
        yesNoCancelDialog.show(props.getProperty(LABEL_SAVE_UNSAVED_WORK));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();	
	boolean saveWork = selection.equals(YesNoCancelDialog.YES);

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (saveWork) {
            EPortfolio slideShow = ui.getEPortfolio();
            pageIO.saveSlideShow(slideShow);
            saved = true;
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (!true) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }
    
}
