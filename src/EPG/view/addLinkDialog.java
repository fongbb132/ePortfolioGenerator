/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.model.Component;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Ka Wing Fong
 */
public class addLinkDialog extends EditDialog{

    public addLinkDialog(Component c, ComponentEditView a) {
        super(c, a);
        initView();
    }

    @Override
    public void initView() {
        Label word = new Label("word: ");
        TextField wordTextField = new TextField();
        Label link = new Label("Hyperlink: ");
        TextField linkTextField = new TextField();
        Button okButton =new Button();
        okButton.setText("OK");
        okButton.setOnAction(e->{
            this.hide();
        });
        gridPane.add(word, 0, 0);
        gridPane.add(wordTextField,1,0);
        gridPane.add(link, 0,1);
        gridPane.add(linkTextField, 1,1);
        gridPane.add(okButton, 0, 2);
        

    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
