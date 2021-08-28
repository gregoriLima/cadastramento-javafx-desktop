package com.cadastramento.interfaces;

import javafx.collections.ObservableList;

// para troca de dados entre as telas
public interface OnChangeServerStatus {

	void onChangeServerStatus(boolean serverStatus);

	void onChangeEstadosStatus(ObservableList<String> estados);

}
