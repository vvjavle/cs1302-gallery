package cs1302.gallery;

//Image Imports
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

//Menu Imports
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem; 

//Driver Class Imports
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

//Event Handler Imports
import javafx.event.EventHandler;
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
	
	boolean isPlaying = false;
	final double windowWidth = 640.0;
	final double windowHeight = 480.0;
    @Override
    public void start(Stage stage) 
    {

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(getTopBar());		
		borderPane.setBottom(getBottomBar());
		
		//borderPane.setCenter(getCenter());

		
        Scene scene = new Scene(borderPane, windowWidth, windowHeight);
        stage.setTitle("[XYZ] Gallery!");
        stage.setScene(scene);
        //stage.sizeToScene();
        //stage.setResizable(false);
        stage.show();
    } // start
    
    private Node getCenter(String[] imageUrls)
    {

		final int noOfHorrizontalImages = 3;
		final int noOfVerticalImages = 3;
		
		double imageViewWidth = 100; //windowWidth / noOfHorrizontalImages;
		double imageViewHeight = 100; //windowHeight  / noOfVerticalImages;
		
		TilePane pane = new TilePane();
		pane.setPrefColumns(noOfVerticalImages);
	
		for(String s : imageUrls)
		{
			ImageView iv = new ImageView(new Image(s));
			iv.setFitHeight(imageViewWidth);
			iv.setFitWidth(imageViewHeight);
			pane.getChildren().add(iv);
		}
		return pane;
	}

	private Node getBottomBar()
    {
		ProgressBar progressBar = new ProgressBar();
		return progressBar;
	}

	private Node getTopBar()
    {
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
		
		HBox hbox = new HBox(15);
		hbox.setAlignment(Pos.CENTER_LEFT);

		//Button
		Button slideShowButton = new Button("Play");
		slideShowButton.setOnAction(e -> ((Button) e.getSource()).setText((isPlaying = !isPlaying) ? "Pause" : "Play"));

		hbox.getChildren().add(slideShowButton);

		//Label Field
		Label searchQueryLabel = new Label("Search Query: ");
		hbox.getChildren().add(searchQueryLabel);
		
		//Text Field
		TextField queryInput = new TextField();
		hbox.getChildren().add(queryInput);
		topBar.getChildren().add(hbox);
		
		//Button
		Button updateImagesButton = new Button("Update Images");
		hbox.getChildren().add(updateImagesButton);
		updateImagesButton.setOnAction(event -> 
		{	
			String searchStrinbgFromTestBox = "";


			Button updateImgBtn = (Button)event.getSource();

			HBox hb = (HBox)updateImgBtn.getParent();
			TextField tf = getTextFieldNode(hb.getChildren());
			
			String[] artworkURLs = parseResults(getSearchResults(tf.getText()));
			
			Node c = getCenter(artworkURLs);
			VBox vb = (VBox)hb.getParent();
			
			BorderPane bp = (BorderPane)vb.getParent();
			bp.setCenter(c);

		});
		
		return topBar;
	}
	
	private TextField getTextFieldNode(ObservableList<Node> children)
	{
		TextField tfReturned = null;
		for(Node n : children)
			if(n instanceof TextField)
			{
				tfReturned = (TextField)n;
				break;
			}
		return tfReturned;
	}

	String[] getParsedResults(String searchString)
	{
		return parseResults(getSearchResults(searchString));
	}
	
	private String[] parseResults(BufferedReader reader) 
	{
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(reader);
		
		JsonObject root = je.getAsJsonObject();                      // root of response
		JsonArray results = root.getAsJsonArray("results");          // "results" array
		int numResults = results.size();  							// "results" array size
		
		String[] parsedResults = new String[numResults];
		
		for (int i = 0; i < numResults; i++) 
		{                       
		    JsonObject result = results.get(i).getAsJsonObject();    // object i in array
		    JsonElement artworkUrl100 = result.get("artworkUrl100"); // artworkUrl100 member
		    if (artworkUrl100 != null) 
		    {                             							// member might not exist
		         String artUrl = artworkUrl100.getAsString();        // get member as string
                 parsedResults[i] = artUrl;
		    } // if
		} // for
		
		return parsedResults;
	}

	private BufferedReader getSearchResults(String searchString)
	{
		BufferedReader br = null;
		try
		{
			URL url = new URL("https://itunes.apple.com/search?term=" + searchString + "&limit=20");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) 
			{
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			 br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		}
		
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return br;
	}

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