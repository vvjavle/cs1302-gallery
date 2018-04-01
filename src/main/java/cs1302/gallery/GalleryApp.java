package cs1302.gallery;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GalleryApp extends Application 
{
    @Override
    public void start(Stage stage) 
    {
    		HBox pane = new HBox();
    		
    		double dimension = Math.sqrt((640.0 * 380.0) / 16.0);
    		
    		Image image1 = new Image("File:wavepattern.jpg");
    		ImageView iv1 = new ImageView(image1);
    		iv1.setFitHeight(dimension);
    		iv1.setFitWidth(dimension);
    		pane.getChildren().add(iv1);
    		
    		Image image2 = new Image("File:pattern.jpg");
    		ImageView iv2 = new ImageView(image2);
    		iv2.setFitHeight(dimension);
    		iv2.setFitWidth(dimension);
    		pane.getChildren().add(iv2);
    		
    		Image image3 = new Image("File:edgepattern.jpg");
    		ImageView iv3 = new ImageView(image3);
    		iv3.setFitHeight(dimension);
    		iv3.setFitWidth(dimension);
    		pane.getChildren().add(iv3);
    		
    		Image image4 = new Image("File:flowerpattern.jpg");
    		ImageView iv4 = new ImageView(image4);
    		iv4.setFitHeight(dimension);
    		iv4.setFitWidth(dimension);
    		pane.getChildren().add(iv4);
    		
    		Image image5 = new Image("File:curvepattern.jpg");
    		ImageView iv5 = new ImageView(image5);
    		iv5.setFitHeight(dimension);
    		iv5.setFitWidth(dimension);
    		pane.getChildren().add(iv5);
    		
        Scene scene = new Scene(pane);
        stage.setMaxWidth(640);
        stage.setMaxHeight(480);
        stage.setTitle("[XYZ] Gallery!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    } // start

    public static void main(String[] args) 
    {
    		try 
    		{
    			Application.launch(args);
    		} 
    		catch (UnsupportedOperationException e) 
    		{
    			System.out.println(e);
    			System.err.println("If this is a DISPLAY problem, then your X server connection");
    			System.err.println("has likely timed out. This can generally be fixed by logging");
    			System.err.println("out and logging back in.");
    			System.exit(1);
    		} // try
    } // main

} // GalleryApp

