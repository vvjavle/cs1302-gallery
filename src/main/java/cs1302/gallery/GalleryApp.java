package cs1302.gallery;

//Image Imports
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Menu Imports
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem; 

//Driver Class Imports
import javafx.application.Application; 
import javafx.stage.Stage;

//Event Handler Imports
import javafx.event.EventHandler;
import javafx.scene.Scene; 

//Layout Pane Imports
import javafx.scene.layout.TilePane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//Control Imports
import javafx.scene.control.Button;


//Miscellaneous Imports
import javafx.scene.input.MouseEvent; 
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;


public class GalleryApp extends Application 
{
    @Override
    public void start(Stage stage) 
    {
    		final double windowWidth = 640.0;
    		final double windowHeight = 480.0;
//  		final int noOfHorrizontalImages = 5;
//  		final int noOfVerticalImages = 1;
//    	
//    	double imageViewWidth = windowWidth / noOfHorrizontalImages;
//    	double imageViewHeight = windowHeight  / noOfVerticalImages;
//    	
//    	TilePane pane = new TilePane();
//    	pane.setPrefColumns(noOfVerticalImages);
//
//		for(String s : getImageFilesInCurrentFolder())
//		{
//    		ImageView iv = new ImageView(new Image("File:" + s));
//    		iv.setFitHeight(imageViewWidth);
//    		iv.setFitWidth(imageViewHeight);
//
//    		pane.getChildren().add(iv);
//		}
//		
    		VBox topBar = new VBox();
    		
    		//File Menu
    		Menu fileMenu = new Menu("File");
    		
    		//Menu Items
    		MenuItem exit = new MenuItem("Exit");
    		exit.setOnAction(e -> System.exit(0));
    		fileMenu.getItems().add(exit);
    		
    		//Main Menu Bar
    		MenuBar mainMenu = new MenuBar();
    		mainMenu.getMenus().addAll(fileMenu);
    		topBar.getChildren().add(mainMenu);
    		
    		HBox hbox = new HBox();
    		
    		//Button
    		hbox.getChildren().add(new Button("Test1"));
    		hbox.getChildren().add(new Button("Test2"));
    		topBar.getChildren().add(hbox);
    		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(topBar);
		
        Scene scene = new Scene(borderPane, windowWidth, windowHeight);
        stage.setTitle("[XYZ] Gallery!");
        stage.setScene(scene);
        //stage.sizeToScene();
        //stage.setResizable(false);
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