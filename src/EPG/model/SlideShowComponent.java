package EPG.model;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import EPG.view.SlideShowEditDialog;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author wing
 */
public class SlideShowComponent extends Component{
    SlideShowModel slideshowModel;
    String type = "s";
    
    public SlideShowComponent(String n,SlideShowEditDialog a){
        super(n);
        slideshowModel = new SlideShowModel(a);
    }

    public String getType(){
        return type;
    }
    
    public void setSlideShowEditDialog(SlideShowEditDialog a){
        slideshowModel.setSlideShowEditDialog(a);
    }
    
    public SlideShowModel getSlideShowModel() {
        return slideshowModel;
    }
    
    public void addSlide(String initImageFileName,
			 String initImagePath,
			 String initCaption) {
	slideshowModel.addSlide(initImageFileName, initImagePath, initCaption);
    }
    
    public String getTitle() {
        return slideshowModel.getTitle();
    }

    public void setTitle(String title) {
        slideshowModel.setTitle(title);
    }
    
    public ObservableList<Slide> getSlides(){
        return slideshowModel.getSlides();
    }
}
