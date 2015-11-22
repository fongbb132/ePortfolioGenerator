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

    public ImageComponent(String c) {
        super(c);
    }

    public ImageComponent(String link, String c) {
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
}
