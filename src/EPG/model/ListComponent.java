/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.model;

import java.util.ArrayList;

/**
 *
 * @author Ka Wing Fong
 */
public class ListComponent extends Component{
    ArrayList<String> list;
    public ListComponent(String a) {
        super(a);
        list = new ArrayList<String>();
    }
    
    public ArrayList<String> getList(){
        return list;
    }
    
    public void addList(String newItem){
        list.add(newItem);
    }
}
