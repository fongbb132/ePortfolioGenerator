/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import EPG.model.ParagraphComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
        initView();
    }
    
    @Override
    public void initView() {
	hintLabel = new Label("Add Paragraph");
	gridPane.add(hintLabel, 0,0);
        TextArea a = new TextArea();
        
        Label fontLabel = new Label("Fonts: ");
        ObservableList<String> fontChoices = FXCollections.observableArrayList();
	fontChoices.add("Font 1");
	fontChoices.add("Font 2");
	fontChoices.add("Font 3");
	fontChoices.add("Font 4");
	fontChoices.add("Font 5");
        
        ComboBox fonts = new ComboBox(fontChoices);
        Button setHyperLink = new Button();
        setHyperLink.setText("set link");
        setHyperLink.setOnAction(e->{
            addLinkDialog link = new addLinkDialog(parag, this.editView);
            link.showAndWait();
        });
        gridPane.add(fontLabel, 0, 1);
        gridPane.add(fonts, 1, 1);
        gridPane.add(setHyperLink,2,1);
	gridPane.add(a,0,2,3,1);
        okButton.setOnAction(e->{
           parag.setContent(a.getText());
           editView.reloadComponents();
           this.hide();
        });
	gridPane.add(okButton, 0,4);
    }
    
}
