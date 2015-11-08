/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wing
 */
public class Page {
    ArrayList<Component> components;
    String title;

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
    
    
}