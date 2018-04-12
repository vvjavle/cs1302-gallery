package cs1302.gallery;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javafx.scene.layout.TilePane;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
    final int PANEMAXCOLUMNSIZE = 5;
    final int PANEMAXROWSIZE = 4;
    public final int PANEMAXELEMENTS = PANEMAXCOLUMNSIZE * PANEMAXROWSIZE;
    final int MAXSEARCHRESULTS = 50;
    final String URLPart1 = "https://itunes.apple.com/search?term=";
    final String URLPart2 = "&entity=album&limit=";
	boolean isPlaying = false;
	Timeline timeline = null;
	GalleryAppModel galleryAppModel = new GalleryAppModel();
	String[] results = null;
	
	private int getResultsSize()
	{
	    return results == null?0:results.length;
	}
	
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
        Random randomGenerator = new Random();
        int indexOfImageToBeSwapped1 = randomGenerator.nextInt(PANEMAXELEMENTS -1);

        int indexOfImageToBeSwapped2 = 
                galleryAppModel.getUrlList().size() > PANEMAXELEMENTS?
                        randomGenerator.nextInt(getResultsSize()  - 1 - PANEMAXELEMENTS) + PANEMAXELEMENTS - 1:
                        randomGenerator.nextInt(PANEMAXELEMENTS -1);

        swapUrlsInDataModel(indexOfImageToBeSwapped1,indexOfImageToBeSwapped2);
	}
	private void swapUrlsInDataModel(int indexOfImageToBeSwapped1, int indexOfImageToBeSwapped2)
    {
	    String tempUrlString = galleryAppModel.urlListProperty().get(indexOfImageToBeSwapped1);
	    galleryAppModel.urlListProperty().set(indexOfImageToBeSwapped1,results[indexOfImageToBeSwapped2]);
	    results[indexOfImageToBeSwapped2] = tempUrlString;
    }

    public GalleryAppController()
	{
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), e -> keyFrameHandler(e)));
	}
	
	public void exitMenuHandler(ActionEvent e)
	{
		System.exit(0);
	}
	
	public void slideShowEventHandler(ActionEvent e)
	{
	    Button  slideShowButton = (Button)e.getSource();
	    if(isPlaying)
	    {
	        slideShowButton.setText("Play");
	        timeline.pause();
	    }
	    else
	    {
            slideShowButton.setText("Pause");
            timeline.play();
	    }
	    isPlaying = !isPlaying;
	}
	
	public void updateImagesButtonHandler(ActionEvent e)
	{
	    Thread t = new Thread(() -> 
	    {
	        updateSearchResultsModel(galleryAppModel.queryFieldProperty().get());
	    });
	    t.start();
	}

	public TilePane getUpdatedTilePane()
	{
        TilePane tilePane = new TilePane();
        
        for(int i = 0; i < galleryAppModel.getUrlList().size(); i++)
        	if(galleryAppModel.getUrlList().get(i) != null && i < PANEMAXELEMENTS)
        		tilePane.getChildren().add
        		(
    		        new ImageView(new Image(galleryAppModel.getUrlList().get(i)))
    		        {{
		                setFitWidth(100);
		                setFitHeight(100);
    		        }}
        		);
        return tilePane;
	}

	public String[] parseResults(InputStreamReader reader) 
	{
		JsonArray jsonResults = new JsonParser().parse(reader).getAsJsonObject().getAsJsonArray("results"); // "results" array
		
		int resultSize = jsonResults.size();
		
		String[] searchResults = new String[resultSize];
		
		for (int i = 0; i < resultSize; i++) 
		{                       
		    JsonElement artworkUrl100 = jsonResults.get(i).getAsJsonObject().get("artworkUrl100"); // artworkUrl100 member
		    // check member existence and assign if present
		    if (artworkUrl100 != null) searchResults[i] = artworkUrl100.getAsString();
		}
		return searchResults;
	}
	
	public void displayPopUp()
	{
        Stage window = new Stage()
        		        {{
            		        initModality(Modality.APPLICATION_MODAL);
            		        setTitle("Error");
            		        setWidth(300);
            		        setHeight(150);
            		        setResizable(false);
        		        }};

        window.setScene
        (
            new Scene
            (
                new VBox()
                {{
                    getChildren().addAll
                    (
                        new Label("Error: The search yields less than " + PANEMAXELEMENTS + " results"),
                        new Label("Enter a new search"),
                        new Button("Close") {{setOnAction(e -> window.close());}}
                    );
                    setAlignment(Pos.CENTER);
                }} 
            )
        );
		window.showAndWait();
	}
	
	public InputStreamReader getQueryResults(String searchString)
	{
		InputStreamReader reader = null;
		
		try
		{
			if(searchString != null)
    			reader = new InputStreamReader(new URL(URLPart1 + searchString.replaceAll(" ", "+") + URLPart2 + MAXSEARCHRESULTS).openStream());
		}
		
		catch(MalformedURLException e) {e.printStackTrace();}
		
		catch(IOException e) {e.printStackTrace();}
		
		return reader;
	}

    public void updateSearchResultsModel(String searchString)
    {
       String[] parsedSearchResults = parseResults(getQueryResults(searchString));
       if(parsedSearchResults != null)
       {
           int parsedSearchResultsLength = parsedSearchResults.length;
           if (parsedSearchResultsLength < PANEMAXELEMENTS) displayPopUp();
           else
           {
               galleryAppModel.urlListProperty().clear();
               results = null;
               results = new String[parsedSearchResultsLength];
               for(int i=0;i<parsedSearchResultsLength;i++)
               {
                   results[i] = parsedSearchResults[i];
                   if(i<PANEMAXELEMENTS)
                       galleryAppModel.urlListProperty().add(results[i]);
               }
           }
       }
    }
}