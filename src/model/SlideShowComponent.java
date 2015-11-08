package model;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author wing
 */
public class SlideShowComponent extends Component{
    ArrayList<Slide> slides;
    String title;
    String type = "s";
    
    public SlideShowComponent(String n){
        super(n);
        title = n;
        slides = new ArrayList<Slide>();
    }

    public String getType(){
        return type;
    }
    public ArrayList<Slide> getSlides() {
        return slides;
    }
    
    public void addSlide(String initImageFileName,
			 String initImagePath,
			 String initCaption) {
	Slide slideToAdd = new Slide(initImageFileName, initImagePath, initCaption);
	slides.add(slideToAdd);
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
