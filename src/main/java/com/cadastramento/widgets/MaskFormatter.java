package com.cadastramento.widgets;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class MaskFormatter {

	/* Declara��o dos componentes. */
	private TextField textField;
	private DatePicker datePicker;
	/*
	 * Variavel que vai contem a mascara selecionada para caso seja exibido o
	 * formato da mascara.
	 */
	private int maskSelecionada;
	/* Variavel para verifica��o se passou no construtor um DatePicker */
	private boolean usarDatePicker;
	private int genericLength;

	public void setGenericLength(int genericLength) {
		this.genericLength = genericLength;
	}

	/* Declara��o de constantes que v�o representar os tipos de mascaras */
	/**
	 * M�scara de telefone com 8 digitos. ex: (61) 9340-6012.
	 */
	public static final int TEL_8DIG = 0;
	/**
	 * M�scara de telefone com 9 digitos. Ex: (61) 99340-6012
	 */
	public static final int TEL_9DIG = 1;
	/**
	 * M�scara de CPF. Ex: 000.000.000-00
	 */
	public static final int CPF = 2;
	/**
	 * M�scara de RG. EX: 00.000.000-0
	 */
	public static final int RG = 3;
	/**
	 * M�scara de DATA com barras. Ex: 01/02/2016
	 */
	public static final int DATA_BARRA = 4;
	/**
	 * M�scara de DATA com tra�os. Ex: 01-02-2016
	 */
	public static final int DATA_TRACO = 5;

	/**
	 * M�scara de GENERIC com length
	 */
	public static final int GENERIC = 5;

	/**
	 * Passe o TextField que ter� a mascara.
	 *
	 * @param textField
	 */
	public MaskFormatter(TextField textField) {
		this.textField = textField;

	}

	/**
	 * Passe um DatePicker que ter� a mascara.
	 *
	 * @param datePicker
	 */
	public MaskFormatter(DatePicker datePicker) {
		this.datePicker = datePicker;
		/* Informa que voi passado um DatePicker */
		this.usarDatePicker = true;
	}

	/**
	 * Passe o tipo da Mascara. Ex: setMask(MaskFormatter.TEL_8DIG);
	 *
	 * @param maskType
	 */
	public void setMask(int maskType) {
		this.maskSelecionada = maskType;
		if (!usarDatePicker) {

			switch (maskType) {
			case TEL_8DIG:
				maskTel8Dig();
				break;
			case TEL_9DIG:
				maskTel9Dig();
				break;
			case CPF:
				maskCpf();
				break;
			case RG:
				maskRg();
				break;
			case GENERIC:
				maskGeneric();
				break;
			default:
				break;
			}
		} else {
			switch (maskType) {
			case DATA_BARRA:
				maskDataBarra();
				break;
			case DATA_TRACO:
				maskDataTraco();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * M�todo que iniciar a mascara.
	 */
	private void maskTel8Dig() {
		/* evento que captura os dados digitados */
		textField.setOnKeyTyped((KeyEvent evento) -> {

			/* verifico se o que est� sendo digitado � um numero */
			if (!"0123456789".contains(evento.getCharacter())) {
				/*
				 * usando o m�todo consume,e como se o que o usuario digitou n�o tivesse efeito,
				 * ex ele digitou uma letra, mas essa letra n�o apare�e pq bloqueamos o evento
				 * que faz ela aperecer na tela.
				 */
				evento.consume();
			}
			/*
			 * verificamos se o caracter foi digitado ou apagado se caso apagado ele � igual
			 * a zero.
			 */
			if (evento.getCharacter().trim().length() == 0) {

				switch (textField.getText().length()) {
				case 9:
					/*
					 * subString so retornar os caracteres entre aquelas posi��es fazemos isso para
					 * apagar o caractere - que colocamos na mascara.
					 */
					textField.setText(textField.getText().substring(0, 8));
					textField.positionCaret(textField.getText().length());
					break;
				case 3:
					textField.setText(textField.getText().substring(0, 2));
					textField.positionCaret(textField.getText().length());
					break;
				case 1:
					textField.setText("");
					textField.positionCaret(textField.getText().length());
					break;
				default:
					break;
				}

			} else if (textField.getText().length() == 14) {
				/*
				 * verificamos se j� chegou o limite de numeros digitados lembrando que estamos
				 * contando todos os caracteres que est�o visiveis
				 */

				evento.consume();
			}
			switch (textField.getText().length()) {
			case 1:
				/*
				 * Adicionamos o parentese no primeiro caracter.Obs: lembrando que cada
				 * caractere digitado e como se estivesse em um array ent�o o primeiro caracter
				 * fica na posi��o 0
				 */
				textField.setText("(" + textField.getText());
				/*
				 * movemos sempre a letra para o ultimo lugar, sem isso a letra voltaria para o
				 * primeiro
				 */
				textField.positionCaret(textField.getText().length());
				break;
			case 3:
				/*
				 * como adicionamos um caractere parentese, aumenta mais um, ent�o o segundo
				 * numero digitado � o 3
				 */
				textField.setText(textField.getText() + ") ");
				textField.positionCaret(textField.getText().length());
				break;
			case 9:
				textField.setText(textField.getText() + "-");
				textField.positionCaret(textField.getText().length());
				break;
			}

		});
	}

	private void maskTel9Dig() {
		textField.setOnKeyTyped((KeyEvent evento) -> {
			if (!"0123456789".contains(evento.getCharacter())) {
				evento.consume();
			}

			if (evento.getCharacter().trim().length() == 0) {

				switch (textField.getText().length()) {
				case 1:
					textField.setText("");
					textField.positionCaret(textField.getText().length());
					break;
				case 3:
					textField.setText(textField.getText().substring(0, 2));
					textField.positionCaret(textField.getText().length());
					break;
				case 10:
					textField.setText(textField.getText().substring(0, 9));
					textField.positionCaret(textField.getText().length());
					break;
				}
			} else if (textField.getText().length() == 15) {
				evento.consume();
			}
			switch (textField.getText().length()) {
			case 1:
				textField.setText("(" + textField.getText());
				textField.positionCaret(textField.getText().length());
				break;
			case 3:
				textField.setText(textField.getText() + ") ");
				textField.positionCaret(textField.getText().length());
				break;
			case 10:
				textField.setText(textField.getText() + "-");
				textField.positionCaret(textField.getText().length());
				break;
			}

		});
	}

	// generic mask
	private void maskGeneric() {
		textField.setOnKeyTyped((KeyEvent evento) -> {
			if (!evento.getCharacter().equals("")) {
				if (textField.getText().length() > this.genericLength) {
					textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
					textField.positionCaret(textField.getText().length());
				}
			}
		});

	}

	private void maskCpf() {

		textField.setOnKeyTyped((KeyEvent evento) -> {

			if (!evento.getCharacter().equals("") && !evento.getCharacter().isEmpty()
					&& !evento.getCharacter().isBlank()) {
				// System.out.println(evento.getCharacter());

				if (textField.getText().length() < 4)
					if (!textField.getText().matches("\\d+")) {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
						textField.positionCaret(textField.getText().length());
					}
				if (textField.getText().length() > 4 && textField.getText().length() < 8)
					if (!textField.getText().substring(4).matches("\\d+")) {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
						textField.positionCaret(textField.getText().length());
					}
				if (textField.getText().length() > 8 && textField.getText().length() < 12)
					if (!textField.getText().substring(8).matches("\\d+")) {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
						textField.positionCaret(textField.getText().length());
					}
				if (textField.getText().length() > 12 && textField.getText().length() < 15)
					if (!textField.getText().substring(12).matches("\\d+")) {
						textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
						textField.positionCaret(textField.getText().length());
					}
				if (textField.getText().length() >= 15) {
					textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
					textField.positionCaret(textField.getText().length());
				}

				if (!"0123456789".contains(evento.getCharacter())) {
					evento.consume();
				}

				if (evento.getCharacter().trim().length() == 0) {

					switch (textField.getText().length()) {
					case 11:
						textField.setText(textField.getText().substring(0, 9));
						textField.positionCaret(textField.getText().length());
						break;
					case 7:
						textField.setText(textField.getText().substring(0, 6));
						textField.positionCaret(textField.getText().length());
						break;
					case 3:
						textField.setText(textField.getText().substring(0, 2));
						textField.positionCaret(textField.getText().length());
						break;
					}

				} else if (textField.getText().length() == 14) {
					evento.consume();
				}
				switch (textField.getText().length()) {
				case 3:
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
					break;
				case 7:
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
					break;
				case 11:
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
					break;
				}

			}
		});
	}

	private void maskRg() {
		textField.setOnKeyTyped((KeyEvent evento) -> {
			if (!"0123456789".contains(evento.getCharacter())) {
				evento.consume();
			}

			if (evento.getCharacter().trim().length() == 0) {

				switch (textField.getText().length()) {
				case 2:
					textField.setText(textField.getText().substring(0, 1));
					textField.positionCaret(textField.getText().length());
					break;
				case 6:
					textField.setText(textField.getText().substring(0, 5));
					textField.positionCaret(textField.getText().length());
					break;
				case 10:
					textField.setText(textField.getText().substring(0, 9));
					textField.positionCaret(textField.getText().length());
					break;
				}

			} else if (textField.getText().length() == 12) {
				evento.consume();
			}
			switch (textField.getText().length()) {
			case 2:
				textField.setText(textField.getText() + ".");
				textField.positionCaret(textField.getText().length());
				break;
			case 6:
				textField.setText(textField.getText() + ".");
				textField.positionCaret(textField.getText().length());
				break;
			case 10:
				textField.setText(textField.getText() + "-");
				textField.positionCaret(textField.getText().length());
				break;
			}

		});

	}

	private void maskDataBarra() {
		datePicker.getEditor().setOnKeyTyped((KeyEvent evento) -> {

			if (!"0123456789".contains(evento.getCharacter())) {
				evento.consume();
			}

			if (evento.getCharacter().trim().length() == 0) {
				switch (datePicker.getEditor().getText().length()) {
				case 2:
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 1));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
					break;
				case 5:
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 4));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
					break;
				}
			} else if (datePicker.getEditor().getText().length() == 10) {
				evento.consume();
			}
			switch (datePicker.getEditor().getText().length()) {
			case 2:
				datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
				datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				break;
			case 5:
				datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
				datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				break;
			}

		});
	}

	private void maskDataTraco() {
		datePicker.getEditor().setOnKeyTyped((KeyEvent evento) -> {

			if (!"0123456789".contains(evento.getCharacter())) {
				evento.consume();
			}

			if (evento.getCharacter().trim().length() == 0) {
				switch (datePicker.getEditor().getText().length()) {
				case 2:
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 1));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
					break;
				case 5:
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 4));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
					break;
				}
			} else if (datePicker.getEditor().getText().length() == 10) {
				evento.consume();
			}
			switch (datePicker.getEditor().getText().length()) {
			case 2:
				datePicker.getEditor().setText(datePicker.getEditor().getText() + "-");
				datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				break;
			case 5:
				datePicker.getEditor().setText(datePicker.getEditor().getText() + "-");
				datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				break;
			}

		});
	}

	/**
	 * Exibi no componente o formato de m�scara selecionado. Obs: Utilizar somente
	 * depois do m�todo setMask()
	 */
	public void showMask() {
		switch (this.maskSelecionada) {
		case TEL_8DIG:
			textField.setPromptText("(__) ____-____");
			break;
		case TEL_9DIG:
			textField.setPromptText("(__) _____-____");
			break;
		case CPF:
			textField.setPromptText("_ _ _ . _ _ _ . _ _ _ - _ _");
			break;
		case RG:
			textField.setPromptText("_ _ . _ _ _ . _ _ _ - _");
			break;
		case DATA_BARRA:
			datePicker.setPromptText("__/__/____");
			break;
		case DATA_TRACO:
			datePicker.setPromptText("__-__-____");
			break;
		default:
			break;
		}
	}

	/**
	 * Adicione um TextField, forne�a a mascara e se deseja que seja exibida a
	 * mascara.
	 *
	 * @param field
	 * @param maskType
	 * @param showMask
	 */
	public void addComponente(TextField field, int maskType, boolean showMask) {
		MaskFormatter formatter = new MaskFormatter(textField);
		formatter.setMask(maskType);
		if (showMask) {
			formatter.showMask();
		}
	}

	/**
	 * Adicione um DatePicker, forne�a a mascara e se deseja que seja exibida a
	 * mascara.
	 *
	 * @param datePicker
	 * @param maskType
	 * @param showMask
	 */
	public void addComponente(DatePicker datePicker, int maskType, boolean showMask) {
		MaskFormatter formatter = new MaskFormatter(datePicker);
		formatter.setMask(maskType);
		if (showMask) {
			formatter.showMask();
		}
	}
}