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
import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
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
    
    public void handleNewPortfolioRequest(){
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
                ui.clearEditPageView();
            }
        }catch(IOException ioe){
            ErrorHandler eh = ui.getErrorHandler();
            eh.processError(LanguagePropertyType.ERROR_UNEXPECTED);
        }
    }
    
    public boolean handSaveAsPortfolioRequest(String path){
        try {
	    // GET THE SLIDE SHOW TO SAVE
	    EPortfolio toSave = ui.getEPortfolio();
	    
            // SAVE IT TO A FILE
            pageIO.saveEPortfolio(toSave,path);

            // MARK IT AS SAVED
            saved = true;

            // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
            // THE APPROPRIATE CONTROLS
            ui.updateFileToolbarControls(saved);
	    return true;
        } catch (IOException ioe) {
            ErrorHandler eH = ui.getErrorHandler();
            eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
	    return false;
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

    public void handleLoadEportfolioRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave();
            }

            // IF THE USER REALLY WANTS TO OPEN A POSE
            if (continueToOpen) {
                // GO AHEAD AND PROCEED MAKING A NEW POSE
                promptToOpen();
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ui.getErrorHandler();
            eH.processError(LanguagePropertyType.ERROR_DATA_FILE_LOADING);
        }
    }

    public boolean handleSaveEPortfolioRequest() {
        try {
	    // GET THE SLIDE SHOW TO SAVE
	    EPortfolio toSave = ui.getEPortfolio();
	    
            // SAVE IT TO A FILE
            pageIO.saveEPortfolio(toSave);

            // MARK IT AS SAVED
            saved = true;

            // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
            // THE APPROPRIATE CONTROLS
            ui.updateFileToolbarControls(saved);
	    return true;
        } catch (IOException ioe) {
            ErrorHandler eH = ui.getErrorHandler();
            eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
	    return false;
        }
    }

    public void handleExitRequest() {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave();
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ui.getErrorHandler();
            eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
        }
    }
    
        private void promptToOpen() {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser slideShowFileChooser = new FileChooser();
        slideShowFileChooser.setInitialDirectory(new File("./data/EPortfolio"));
        File selectedFile = slideShowFileChooser.showOpenDialog(ui.getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
		EPortfolio portfolioToLoad = ui.getEPortfolio();
                pageIO.loadPortfolio(portfolioToLoad, selectedFile.getAbsoluteFile());
                System.out.println("FileController: promptoopen");
                ui.reloadPagePane();
                saved = true;
                ui.updateFileToolbarControls(saved);
                ui.clearEditPageView();
            } catch (Exception e) {
                ErrorHandler eH = ui.getErrorHandler();
		eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
            }
        }
    }
    
}
