package cs1302.gallery;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

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
    }

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