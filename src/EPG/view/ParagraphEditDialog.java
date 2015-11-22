/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.model.ParagraphComponent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author Ka Wing Fong
 */
public class ParagraphEditDialog extends EditDialog {
    ParagraphComponent parag;
    public ParagraphEditDialog(ParagraphComponent c, ComponentEditView a) {
        super(c,a);
        parag = c;
    }
    
    @Override
    public void initView() {
	hintLabel = new Label("Add Paragraph");
	gridPane.add(hintLabel, 0,0);
        TextArea a = new TextArea();
	gridPane.add(a, 0,1);
        okButton.setOnAction(e->{
           parag.setContent(a.getText());
           editView.reloadComponents();
           this.hide();
        });
	gridPane.add(okButton, 0,2);
    }
    
}
