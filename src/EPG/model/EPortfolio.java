/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.model;

import EPG.LanguagePropertyType;
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
    public EPortfolio(String ePortName){
        name = ePortName;
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
    
}
