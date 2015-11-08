/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author wing
 */
public class ePortfolio {
    private ArrayList<Page> pages = new ArrayList<Page>();
    private String name = "";
    private String banner = "";
    private String footer = "";
    private String color = "";
    private String layout = "";
    public ePortfolio(String ePortName){
        name = ePortName;
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void addPages(Page p){
        pages.add(p);
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

    
}
