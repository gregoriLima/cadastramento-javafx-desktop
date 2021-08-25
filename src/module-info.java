module cadastramento_desktop {
	
	opens application to javafx.graphics, javafx.fxml, com.google.gson;
	opens application.controller;
	opens application.model;
	opens application.enumeration;
	
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	requires okhttp3;
	requires com.google.gson;
	requires javafx.base;
	requires spring.data.commons;
	requires java.base;
	
	

}
