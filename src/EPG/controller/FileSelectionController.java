/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.controller;

import static EPG.StartupConstants.PATH_SLIDE_SHOW_IMAGES;
import java.io.File;
import javafx.stage.FileChooser;

/**
 *
 * @author Ka Wing Fong
 */
public class FileSelectionController {

    public FileSelectionController() {
    }
    
    
    public void processFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(PATH_SLIDE_SHOW_IMAGES));
	File file = fileChooser.showOpenDialog(null);
    }
    public String processFileToString(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(PATH_SLIDE_SHOW_IMAGES));
	File file = fileChooser.showOpenDialog(null);
        return file.getName();
    }
}
