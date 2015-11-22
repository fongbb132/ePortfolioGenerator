package EPG;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static EPG.LanguagePropertyType.TITLE_WINDOW;
import static EPG.StartupConstants.ICON_WINDOW_LOGO;
import static EPG.StartupConstants.PATH_DATA;
import static EPG.StartupConstants.PATH_IMAGES;
import static EPG.StartupConstants.PROPERTIES_SCHEMA_FILE_NAME;
import EPG.handler.ErrorHandler;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import EPG.manager.EPortfolioFileManager;
import EPG.view.EPortfolioView;
import java.io.File;
import java.net.URL;
import javafx.scene.image.Image;
import properties_manager.PropertiesManager;
import xml_utilities.InvalidXMLFileFormatException;

/**
 *
 * @author Ka Wing Fong
 */
public class EPortfolioGenerator extends Application {
    EPortfolioFileManager fileManager = new EPortfolioFileManager();
    
    EPortfolioView ui = new EPortfolioView(fileManager);
    
    @Override
    public void start(Stage primaryStage) {
        
        String imagePath = PATH_IMAGES + ICON_WINDOW_LOGO;
        File file = new File(imagePath);
        try{
            URL fileURL = file.toURI().toURL();
            Image windowIcon = new Image(fileURL.toExternalForm());
            primaryStage.getIcons().add(windowIcon);
        }catch(Exception e){
            System.out.println("Can't load the windowIcon");
        }
        
        boolean success = loadProperties("En");
        if (success) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String title = props.getProperty(TITLE_WINDOW);
            
            ui.startUI(primaryStage, title);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public boolean loadProperties(String languageCode) {
        try {
	    // FIGURE OUT THE PROPER FILE NAME
	    String propertiesFileName = "properties_" + languageCode + ".xml";

	    // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
	    props.loadProperties(propertiesFileName, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
       } catch (Exception e) {
            // SOMETHING WENT WRONG INITIALIZING THE XML FILE
            ErrorHandler eH = ui.getErrorHandler();
            eH.processError(LanguagePropertyType.ERROR_PROPERTIES_FILE_LOADING);
            return false;
        }        
    }
}
