/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wing
 */
public class Page {
    ArrayList<Component> components;
    String title;
    Component selectedComp;
    boolean isSelected;
    private String name = "";
    private String banner = "";
    private String bannerUrl = "";
    private String footer = "";
    private String color = "";
    private String layout = "";
    private String font = "";

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Page(String title) {
        components = new ArrayList<Component>();
        this.title = title;
    }

    public void addComponent(Component c){
        components.add(c);
    }
    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Component> getComponents() {
        return components;//To change body of generated methods, choose Tools | Templates.
    }

    public void setSelectedComp(Component c){
        selectedComp = c;
    }
    
    public boolean isSelected(){
        return selectedComp!=null;
    }
    
    public Component getSelectedComp(){
        return selectedComp;
    }

    public void remove() {
        components.remove(selectedComp);
    }
}
