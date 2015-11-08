/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author wing
 */
public class VideoComponent extends Component{
    
    private String src;
    private String type = "v";

    public VideoComponent(String c) {
        super(c);
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
    
}
