/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.model;

import EPG.LanguagePropertyType;
import EPG.view.EPortfolioView;
import java.util.ArrayList;
import properties_manager.PropertiesManager;

/**
 *
 * @author wing
 */
public class EPortfolio {
    private ArrayList<Page> pages = new ArrayList<Page>();
    private Page selectedPage;
    private EPortfolioView ui;
    private String name;

    
    public EPortfolio(String ePortName,EPortfolioView a){
        name = ePortName;
        ui = a;
    }


    public ArrayList<Page> getPages() {
        return pages;
    }

    public void addPages(Page p){
        pages.add(p);
    }
    
    public void setName(String name){
        if(name!=null){
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }

    public boolean isPageSelected() {
        return selectedPage != null;
    }
    
    public boolean isSelectedPage(Page testPage){
        return selectedPage == testPage;
    }

    public void reset() {
        pages.clear();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        name = "untitle ePortfolio";
        selectedPage = null;
    }

    public void setSelectedPage(Page page){
        this.selectedPage = page;
    }
    
    public void moveSelectedPageUp(){
        if(isPageSelected()){
            movePageUp(selectedPage);
            ui.reloadPagePane();
        }
    }
    
    public void moveSelectedPageDown(){
        if(isPageSelected()){
            movePageDown(selectedPage);
            ui.reloadPagePane();
        }
    }
    
    private void movePageUp(Page pageToMove){
        int index = pages.indexOf(pageToMove);
        if(index>0){
            Page temp = pages.get(index);
            pages.set(index, pages.get(index-1));
            pages.set(index-1, temp);
        }
    }
    public void removeSelectedPage() {
	if (isPageSelected()) {
	    pages.remove(selectedPage);
	    selectedPage = null;
	    ui.reloadPagePane();
	}
    }
    
    private void movePageDown(Page pageToMove) {
	int index = pages.indexOf(pageToMove);
	if (index < (pages.size()-1)) {
	    Page temp = pages.get(index);
	    pages.set(index, pages.get(index+1));
	    pages.set(index+1, temp);
	}
    }
    
}
