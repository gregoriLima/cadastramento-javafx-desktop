package application.widgets;

import javafx.css.PseudoClass;
import javafx.scene.control.TextInputControl;

public class TextFieldValidator {

	public static void addListener(TextInputControl textField, String regex) {

		// validando o TextField de acordo com a regex passada no focusOut
		textField.focusedProperty().addListener((obs, oldValue, newValue) -> {

			if (obs.getValue()) { // focus in

				textField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);

			}
			if (!newValue) { // focus lost

				textField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"),
						!textField.getText().isEmpty() && !textField.getText().matches(regex)

				);
			}
		});
	}

}
