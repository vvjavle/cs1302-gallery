package cs1302.gallery;

//Driver Imports
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class GalleryApp extends Application 
{
	private final double windowWidth = 500.0;
	private final double windowHeight = 480.0;
	
    @Override
    public void start(Stage stage) 
    {
        stage.setTitle("[XYZ] Gallery!");
        stage.setScene(new Scene(new GalleryAppView(new GalleryAppController()), windowWidth, windowHeight));
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