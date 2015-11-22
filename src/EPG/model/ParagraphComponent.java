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
public class ParagraphComponent extends Component {
    
    public String type = "t";

    public ParagraphComponent(String c) {
        super(c);
    }
    
    public String getType(){
        return type;
    }
    
}
