package cs1302.gallery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.google.gson.*;

public class GalleryApp extends Application 
{
    @Override
    public void start(Stage stage) throws Exception
    {

        Parent root = FXMLLoader.load(getClass().getResource("GalleryApp.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("[XYZ] Gallery!");
        stage.setScene(scene);
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