package cs1302.gallery;

//Image Imports
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

//Layout Pane Imports
import javafx.scene.layout.TilePane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Control Imports
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

//Miscellaneous Imports
import javafx.scene.input.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import com.google.gson.*;

public class GalleryApp extends Application 
{
	
	final double windowWidth = 500.0;
	final double windowHeight = 480.0;
	
    @Override
    public void start(Stage stage) 
    {
		GalleryAppController galleryAppController = new GalleryAppController();
        Scene scene = new Scene(new GalleryAppView(galleryAppController), windowWidth, windowHeight);
        stage.setTitle("[XYZ] Gallery!");
        stage.setScene(scene);
        stage.setResizable(false);
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