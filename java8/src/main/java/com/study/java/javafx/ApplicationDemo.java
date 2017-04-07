package com.study.java.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author Jeffrey
 * @since 2017/03/22 16:26
 */
public class ApplicationDemo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = new Label("Hello,奔奔!");
        label.setFont(new Font(100));
        primaryStage.setScene(new Scene(label));
        primaryStage.setTitle("Hello");
        primaryStage.show();
    }
}
