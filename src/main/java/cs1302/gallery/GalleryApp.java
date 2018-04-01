package cs1302.gallery;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Application; 
import javafx.event.EventHandler;
import javafx.scene.Scene; 
import javafx.scene.input.MouseEvent; 
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;

import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.layout.HBox;



public class GalleryApp extends Application 
{
    @Override
    public void start(Stage stage) 
    {
    	final double windowWidth = 640.0;
    	final double windowHeight = 480.0;
    	
    	final int noOfHorrizontalImages = 3;
    	final int noOfVerticalImages = 3;
    	
    	double imageViewWidth = windowWidth / noOfHorrizontalImages;
    	double imageViewHeight = windowHeight  / noOfVerticalImages;
    	
    	TilePane pane = new TilePane();
    	pane.setVgap(4);
    	pane.setHgap(4);
    	pane.setPrefColumns(3);

		
		
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