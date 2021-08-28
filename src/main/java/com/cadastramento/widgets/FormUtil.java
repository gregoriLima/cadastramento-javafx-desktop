package com.cadastramento.widgets;

import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class FormUtil {

	public static Boolean isValid(Node... nodes) {
		Boolean isValid = true;
		for (Node node : nodes) {
			if (node instanceof TextField) {
				TextField textfield = (TextField) node;
				if (textfield.getText().isEmpty()
						|| textfield.getPseudoClassStates().contains(PseudoClass.getPseudoClass("error")))
					isValid = false;
			}
			if (node instanceof ChoiceBox) {
				ChoiceBox choicebox = (ChoiceBox) node;
				if (choicebox.getSelectionModel().isEmpty())
					isValid = false;
			}
		}
		return isValid;
	}

	public static void clear(Node... nodes) {

		for (Node node : nodes) {
			if (node instanceof TextField) {
				TextField textfield = (TextField) node;
				textfield.clear();
			}
			if (node instanceof ChoiceBox) {
				ChoiceBox choicebox = (ChoiceBox) node;
				choicebox.getSelectionModel().clearSelection();
				;
			}
		}
	}

	public static void changeStatus(Pane plnStatus, String status) {

		switch (status) {
		case "ok":
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("ok"), true);
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("advrt"), false);
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
			break;
		case "advrt":
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("ok"), false);
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("advrt"), true);
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
			break;
		case "error":
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("ok"), false);
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("advrt"), false);
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), true);
			break;
		default:
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("ok"), false);
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("advrt"), false);
			plnStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), false);
		}

	}
}