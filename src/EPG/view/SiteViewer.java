/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import static EPG.StartupConstants.PATH_SITES_WEB;
import static EPG.manager.EPortfolioFileManager.SLASH;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author wing
 */
public class SiteViewer extends Stage{
    
    // HERE ARE OUR UI CONTROLS
    ScrollPane scrollPane;
    WebView webView;
    WebEngine webEngine;
    
    EPortfolioView ePortfolioView;

    public SiteViewer(EPortfolioView ePortfolioView) {
        this.ePortfolioView = ePortfolioView;
    }
   
    public void startSiteView() throws MalformedURLException {
	// SETUP THE UI
	webView = new WebView();
	scrollPane = new ScrollPane(webView);
	
	// GET THE URL
	String indexPath = "./sites/public_html/Site 2.html";
	File indexFile = new File(indexPath);
	URL indexURL = indexFile.toURI().toURL();
	
	// SETUP THE WEB ENGINE AND LOAD THE URL
	webEngine = webView.getEngine();
	webEngine.load(indexURL.toExternalForm());
	webEngine.setJavaScriptEnabled(true);
	
	// SET THE WINDOW TITLE
	this.setTitle(ePortfolioView.getEPortfolio().getName());

	// NOW PUT STUFF IN THE STAGE'S SCENE
	Scene scene = new Scene(webView, 1100, 650);
	setScene(scene);
	this.showAndWait();
    }
}
