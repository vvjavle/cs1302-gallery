package cs1302.gallery;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GalleryAppController  
{
    boolean isPlaying = false;
    
    public Button playbackButton;
    public TextField searchTextFIeld;
    
    @FXML
    public void playButtonActionHandler(ActionEvent e)
    {
        playbackButton.setText((isPlaying = !isPlaying) ? "Pause" : "Play");
    }
    
    @FXML
    public void updateImagesButtonHandler(ActionEvent e)
    {
        try
        {
            String[] imageURLs = parseResults(getSearchResults(searchTextFIeld.getText()));
            for(String url: imageURLs) System.out.println(url);
        }
        catch(Exception exception)
        {
            
        }
    }
    
    
    private String[] parseResults(BufferedReader reader) 
    {
        JsonArray results = new JsonParser().parse(reader).getAsJsonObject().getAsJsonArray("results");
        int numResults = results.size();
        
        String[] parsedResults = new String[numResults];
        
        for (int i = 0; i < numResults; i++) 
        {                       
            JsonElement artworkUrl100 = results.get(i).getAsJsonObject().get("artworkUrl100"); // artworkUrl100 member
            if (artworkUrl100 != null) // member might not exist                                 
                 parsedResults[i] = artworkUrl100.getAsString();
        }
        
        return parsedResults;
    }

    private BufferedReader getSearchResults(String searchString) throws Exception
    {
        if(searchString != null)
            searchString = searchString.replaceAll(" ", "+");
        
        HttpURLConnection conn = (HttpURLConnection) new URL("https://itunes.apple.com/search?term=" + searchString + "&limit=20").openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        if (conn.getResponseCode() != 200) 
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        else
           return new BufferedReader(new InputStreamReader((conn.getInputStream())));
    }
}
