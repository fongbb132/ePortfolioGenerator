/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.controller;

import EPG.model.EPortfolio;
import EPG.model.Page;
import EPG.view.EPortfolioView;
import properties_manager.PropertiesManager;

/**
 *
 * @author wing
 */
public class PortfolioController {
    private EPortfolioView ui;
    
    public PortfolioController(EPortfolioView initUI){
        this.ui = initUI;
    }
    
    public void processAddPageRequest(){
        EPortfolio ePortfolio = ui.getEPortfolio();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Page page = new Page("New Page");
        ePortfolio.addPages(page);
        ui.reloadPagePane();
    }
    
    public void processRemovePageRequest(){
        EPortfolio eport = ui.getEPortfolio();
        eport.removeSelectedPage();
        ui.reloadPagePane();
    }
    
    public void processMovePageUpRequest(){
        EPortfolio eport = ui.getEPortfolio();
        eport.moveSelectedPageUp();
        ui.reloadPagePane();
    }
    
    public void processMovePageDownRequest(){
        EPortfolio eport = ui.getEPortfolio();
        eport.moveSelectedPageDown();
        ui.reloadPagePane();
    }
}
