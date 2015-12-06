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
    String type = "l";
    int selected = -1;
    public ListComponent(String a) {
        super(a);
        list = new ArrayList<String>();
    }
    
    public ArrayList<String> getList(){
        return list;
    }
    
    public String getType(){
        return type;
    }
    public void addList(String newItem){
        list.add(newItem);
    }

    public void setSelected(int a){
        selected = a;
    }

    public void removeSelectedItem(){
        list.remove(selected);
        selected = -1;
    }
    public void removeList(String b, String text) {
        int index = list.indexOf(b);
        list.remove(index);
        list.add(index, text);
    }
    public void editItem(int pos, String text){
        list.remove(pos);
        list.add(pos, text);
    }

    public int getSelected() {
        return selected;
    }

    public void moveUp() {
        if(selected>0){
            String temp = list.remove(selected);
            selected = selected-1;
            list.add(selected,temp);
        }
    }

    public void moveDown() {
        if(selected<list.size()-1){
            String temp = list.remove(selected);
            selected+=1;
            list.add(selected,temp);
        }
    }

}
