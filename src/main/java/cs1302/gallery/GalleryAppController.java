package cs1302.gallery;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GalleryAppController
{
	boolean isPlaying = false;
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
			String textFieldText = updateImgBtn.textField.getText();
			String[] artworkURLs = parseResults(getSearchResults(textFieldText));
			
			TilePane tilePane = new TilePane();
			
			for(String s: artworkURLs)
			{
				System.out.println(s);
				ImageView imageView = new ImageView(new Image(s));
				imageView.setFitWidth(100);
				imageView.setFitHeight(100);
				tilePane.getChildren().add(imageView);
			}		
			updateImgBtn.parentBorderPane.setCenter(tilePane);
		}
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
			if(searchString != null)
				searchString = searchString.replaceAll(" ", "+");
			
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
	
	String[] getParsedResults(String searchString)
	{
		return parseResults(getSearchResults(searchString));
	}


}