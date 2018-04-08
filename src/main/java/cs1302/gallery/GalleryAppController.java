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
import javafx.scene.control.ProgressBar;

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
			updateImgBtn.parentBorderPane.setCenter
			(
					getUpdatedTilePane(updateImgBtn.textField.getText(), updateImgBtn.progressBar)
			);
	}

	
	public TilePane getUpdatedTilePane(String textFieldText, ProgressBar progressBar)
	{
		TilePane tilePane = new TilePane();
		
		progressBar.setProgress(0);
		
		String[] urls = parseResults(getSearchResults(textFieldText));	
		
		for(int i = 0, j = 0; i < urls.length; i++,j=j+5)
		{
			if(urls[i] != null && i < 20)
			{
				ImageView imageView = new ImageView(new Image(urls[i]));
				imageView.setFitWidth(100);
				imageView.setFitHeight(100);
				tilePane.getChildren().add(imageView);
				progressBar.setProgress(j);
					
				System.out.println(urls[i]);
			}
		}
		
		System.out.println("LENGTH:" + urls.length);
		
		return tilePane;
	}

	private String[] parseResults(/*BufferedReader*/ InputStreamReader reader) 
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
	
	private InputStreamReader getSearchResults(String searchString)
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
	
	private String[] getParsedResults(String searchString)
	{
		return parseResults(getSearchResults(searchString));
	}
}