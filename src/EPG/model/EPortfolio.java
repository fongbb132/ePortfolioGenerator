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
    private String name = "";
    private String banner = "";
    private String footer = "";
    private String color = "";
    private String layout = "";
    private Page selectedPage;
    private EPortfolioView ui;
    private String header = "";
    private String font="";

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
    public EPortfolio(String ePortName,EPortfolioView a){
        name = ePortName;
        ui = a;
        
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void addPages(Page p){
        pages.add(p);
    }
    
    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
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
        name = props.getProperty(LanguagePropertyType.DEFAULT_SLIDE_SHOW_TITLE);
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
