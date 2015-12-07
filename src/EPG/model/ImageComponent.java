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
public class ImageComponent extends Component{
    
    private String src;
    private String type = "p";
    private String name;
    private String caption;
    private String Alignment;
    private int width;
    private int height;

    public ImageComponent(String c) {
        super(c);
        name = "NO NAME";
        caption = "NO CAPTION";
        Alignment = "Middle";
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

    public ImageComponent(String link, String c) {
        super(c);
        this.src = link;
    }

    public String getAlignment() {
        return Alignment;
    }

    public void setAlignment(String Alignment) {
        this.Alignment = Alignment;
    }

    public String getName() {
        String[] temp = src.split("/");
        name = temp[temp.length-1];
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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
}
