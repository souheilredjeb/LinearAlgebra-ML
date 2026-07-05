package com.linsolve.illustrated.wirebond;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class WireBondRegressionApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlUrl = getClass().getResource("/com/linsolve/illustrated/wirebond/regression-view.fxml");
        if (fxmlUrl == null) {
            throw new RuntimeException("FXML file not found!");
        }
        
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        VBox root = loader.load();
        
        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(
            getClass().getResource("/com/linsolve/illustrated/wirebond/style.css").toExternalForm()
        );
        
        primaryStage.setTitle("Wire Bond Regression Analysis");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}