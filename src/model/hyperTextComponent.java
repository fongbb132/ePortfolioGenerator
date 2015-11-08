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
public class hyperTextComponent extends Component{
    private String link;
    private String type = "hl";
    public hyperTextComponent(String c) {
        super(c);
    }
    
    public hyperTextComponent(String c, String l){
        super(c);
        link = l;
    }
    
    public String getLink(){
        return link;
    }
    
    public void setLink(String l){
        link = l;
    }
    
    public String getType(){
        return type;
    }
    
}
