module JavaProject_Ex3_322775123 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.media;
	requires java.desktop;
	requires javafx.swing;
	requires transitive java.compiler;
	
	opens controllers to javafx.graphics, javafx.fxml;
	opens application to javafx.fxml,javafx.graphics;
	exports application.model;
	exports Utils;
	exports Exceptions;
	exports application.util;
	
	
}
