package cs1302.gallery;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import javafx.scene.layout.TilePane;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.Modality;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.geometry.Pos;

public class GalleryAppController
{
	boolean isPlaying = false;
	TilePane tilePane;
	//String[] urls = null;
	
	GalleryAppModel galleryAppModel = new GalleryAppModel();
	
	public GalleryAppModel getGalleryAppModel()
	{
	    return galleryAppModel;
	}
	
	public int getSearchResultLength()
	{
	    return galleryAppModel.getUrlList().size();
	}
	
	public void keyFrameHandler(ActionEvent e)
	{
		if(isPlaying)
		{
			System.out.println(LocalTime.now());
			
			Random rand = new Random();

	        // Generate random integers in range 0 to 999
	        int rand_int1 = rand.nextInt(20);
	        
	        System.out.println(rand_int1);
	        galleryAppModel.getUrlList().set(rand_int1, "http://1millionlovemessages.com/wp-content/uploads/2013/12/Today%E2%80%99s-Love-Quote-21.jpg");
		}
	}
	public GalleryAppController()
	{

		KeyFrame keyFrame = new KeyFrame(Duration.seconds(2), e -> keyFrameHandler(e));
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
	
	
	public void exitMenuHandler(ActionEvent e)
	{
		System.exit(0);
	}
	
	public void slideShowEventHandler(ActionEvent e)
	{
		((Button) e.getSource()).setText((isPlaying = !isPlaying) ? "Pause" : "Play");
	}
	
	public void updateImagesButtonHandler(ActionEvent e)
	{
		SearchButton updateImgBtn = (SearchButton) e.getSource();
		
		if(updateImgBtn != null)
		{
	        getSearchResults(updateImgBtn.textField.getText());
	        if(galleryAppModel.getUrlList().size() < 20) displayPopUp();
	        //updateImgBtn.parentBorderPane.setCenter(getUpdatedTilePane());
		}
	}

	public TilePane getUpdatedTilePane()
	{
        TilePane tilePane = new TilePane();
        
        for(int i = 0; i < galleryAppModel.getUrlList().size(); i++)
        	if(galleryAppModel.getUrlList().get(i) != null && i < 20)
        		tilePane.getChildren().add
        		(
        		        new ImageView(new Image(galleryAppModel.getUrlList().get(i)))
        		        {
        		            {
        		                setFitWidth(100);
        		                setFitHeight(100);
        		            }
        		        }
        		);
        return tilePane;
	}

	private String[] parseResults(InputStreamReader reader) 
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
	
	public static void displayPopUp()
	{

        Stage window = new Stage()
        		        {
                		    {
                		        initModality(Modality.APPLICATION_MODAL);
                		        setTitle("Error");
                		        setWidth(300);
                		        setHeight(150);
                		        setResizable(false);
                		    }
        		        };

        window.setScene
        (
                new Scene
                (
                        new VBox()
                        {
                            {
                                getChildren().addAll
                                (
                                        new Label("Error: The search yields less than 20 results"),
                                        new Label("Enter a new search"),
                                        new Button("Close")
                                        {
                                            {
                                                setOnAction(e -> window.close());
                                            }
                                        }
                                );
                                setAlignment(Pos.CENTER);
                            }
                        } 
                )
        );
		window.showAndWait();
	}
	
	private InputStreamReader getQueryResults(String searchString)
	{
		InputStreamReader reader = null;
		
		try
		{
			if(searchString != null)
				searchString = searchString.replaceAll(" ", "+");
			
			URL url = new URL("https://itunes.apple.com/search?term=" + searchString + "&entity=album" /*+ "&limit=20"*/);
			reader = new InputStreamReader(url.openStream());
		}
		
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return reader;
	}
    public void buildBorderPaneCenter(String string)
    {

        
    }
    public void getSearchResults(String searchQuery)
    {
        List<String> o = galleryAppModel.getUrlList();
        if(o != null)
        {
            o.clear();
            for (String s : parseResults(getQueryResults(searchQuery)))
                    o.add(s);
 
        }
        
    }
}