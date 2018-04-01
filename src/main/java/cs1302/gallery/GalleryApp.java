package cs1302.gallery;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.application.Application; 
import static javafx.application.Application.launch; 
import javafx.event.EventHandler;
 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.input.MouseEvent; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.Circle; 

import javafx.scene.text.Font; 
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text; 
import javafx.stage.Stage; 

import java.io.File;
import java.util.ArrayList;

import javax.xml.stream.EventFilter;

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
    	final double windowWidth = 1020.0;
    	final double windowHeight = 980.0;
    	
    	final int noOfHorrizontalImages = 16;
    	final int noOfVerticalImages = 16;
    	
    	double imageViewWidth = windowWidth / noOfHorrizontalImages;
    	double imageViewHeight = windowHeight  / noOfVerticalImages;
    	
		HBox pane = new HBox();
		
		
		for(String s : getImageFilesInCurrentFolder())
		{
    		ImageView iv = new ImageView(new Image("File:" + s));
    		iv.setFitHeight(imageViewWidth);
    		iv.setFitWidth(imageViewHeight);
    		
    		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>()
    		{
				@Override
    			public void handle(MouseEvent mouseEvent)
    			{
    				System.out.println("Mouse clicked" + mouseEvent.getSource().toString());
    			};
    		};
    		iv.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

    		pane.getChildren().add(iv);
		}
    		
        Scene scene = new Scene(pane);

        stage.setMaxWidth(windowWidth);
        stage.setMaxHeight(windowHeight);
        stage.setTitle("[XYZ] Gallery!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    } // start


    
    private ArrayList<String> getImageFilesInCurrentFolder()
    {	
    	ArrayList<String> fileList = new ArrayList();
    	
    	for(File file : new File(".").listFiles((dir, name) -> {return name.toLowerCase().endsWith(".jpg");}))
    		if(file.isFile())
    			fileList.add(file.getName());
    	
    	return fileList;
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

