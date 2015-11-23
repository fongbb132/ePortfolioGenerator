/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.model;

/**
 *
 * @author wing
 */
public class VideoComponent extends Component{
    
    private String src;
    private String type = "v";
    private String name;
    private String caption;
    private String alignment;
    private int width;
    private int height;

    public VideoComponent(String c) {
        super(c);
        caption = "NO CAPTION";
        name = "NO NAME";
        alignment = "middle";
        width = 800;
        height = 600;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public VideoComponent(String link, String c) {
        super(c);
        this.src = link;
    }
    
    public String getSrc(){
        return src;
    }
    
    public void setSrc(String s){
        src = s;
    }
    
    public String getType(){
        return type;
    }
    
    public void setName(String n ){
        name = n;
    }
    
    public void setCaption(String c){
        caption = c;
    }
    
    public void setAlignment(String a){
        alignment = a;
    }
    
    public String getAlignment(){
        return alignment;
    }
    
    public String getCaption(){
        return caption;
    }
    
    public String getName(){
        return name;
    }
}
