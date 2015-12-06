/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.view;

import static EPG.StartupConstants.CSS_CLASS_CAPTION_TEXT_FIELD;
import static EPG.StartupConstants.CSS_CLASS_SLIDE_EDIT_VIEW;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ka Wing Fong
 */
public class ListItemView extends VBox{
    TextField textField;
    String content;
    ListEditDialog view;
    int pos;

    ListItemView(ListEditDialog aThis, String b, int i) {
	this.getStyleClass().add("list_edit_view");
        view = aThis;
        content = b;
        textField = new TextField();
        textField.setText(b);
        textField.setOnKeyReleased(e->{
            aThis.list.editItem(i, textField.getText());
        });
        this.setOnMouseClicked(e->{
            aThis.list.setSelected(i);
            aThis.reloadList();
            aThis.reloadButtons();
        });
        this.getChildren().add(textField);
	textField.getStyleClass().add(CSS_CLASS_CAPTION_TEXT_FIELD);
    }
    
}
