package cs1302.gallery;

//IO Imports
import java.io.IOException;
import java.io.InputStreamReader;

//Net Imports
import java.net.MalformedURLException;
import java.net.URL;

//Util Imports
import java.util.Random;
import javafx.util.Duration;

//GSON Imports
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

//Scene Imports
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

//Driver Imports
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Modality;

//Geometry Imports
import javafx.geometry.Pos;

/**
 * GalleryAppController class is responsible for holding all the functions of 
 * the gallery GUI.
 * 
 * @author Ved Javle (811690870)
 *
 */
public class GalleryAppController
{
    private final int PANE_MAX_COLUMN_SIZE = 5;
    private final int PANE_MAX_ROW_SIZE = 4;
    public final int PANE_MAX_ELEMENTS = PANE_MAX_COLUMN_SIZE * PANE_MAX_ROW_SIZE;
    public final int MAX_SEARCH_RESULTS = 50;
    
    private final String URL_Part_1 = "https://itunes.apple.com/search?term=";
    private final String URL_Part_2 = "&entity=album&limit=";
    
	private boolean isPlaying = false;
	private Timeline timeline = null;
	public GalleryAppModel galleryAppModel = new GalleryAppModel();
	private String[] results = null;
	
	/**
	 * Get the size of the array containing the image url's.
	 * Checks to see if the array is null before returning the
	 * size of the array.
	 * 
	 * @returns returns 0 if the array is null, and the number
	 * 			elements in the array if it is not
	 */
	private int getResultsSize()
	{
	    return results == null? 0 : results.length;
	}
	
	/**
	 * Returns a reference of the GalleryAppModel class.
	 * 
	 * @return returns reference to the GalleryAppModel class
	 */
	public GalleryAppModel getGalleryAppModel()
	{
	    return galleryAppModel;
	}
	
	/**
	 * Returns length of the string in text field.
	 * 
	 * @return returns the length of the query in the 
	 * 		   text field
	 */
	public int getSearchResultLength()
	{
	    return galleryAppModel.getUrlList().size();
	}
	
	/**
	 * Finds two random indices within the url array. The first
	 * random index resides within the first 20 elements and the
	 * second random index from the 21st element onwards. If the
	 * array is only 20 elements long, it swaps to images. 
	 * 
	 * @param e ActionEvent of the pause/play button being pressed
	 */
	public void keyFrameHandler(ActionEvent e)
	{
        Random randomGenerator = new Random();
 
        int indexOfImageToBeSwapped1 = randomGenerator.nextInt(PANE_MAX_ELEMENTS - 1);

        int indexOfImageToBeSwapped2 = 
                galleryAppModel.getUrlList().size() > PANE_MAX_ELEMENTS?
                        randomGenerator.nextInt(getResultsSize() - 1 - PANE_MAX_ELEMENTS) + PANE_MAX_ELEMENTS - 1:
                        randomGenerator.nextInt(getResultsSize());

        swapUrlsInDataModel(indexOfImageToBeSwapped1, indexOfImageToBeSwapped2);
	}
	
	/**
	 * Swaps two indices within the array of url's.
	 * 
	 * @param indexOfImageToBeSwapped1 index within the first 20 elements to be swapped
	 * @param indexOfImageToBeSwapped2 index from the 21st element onward to be swap
	 */
	private void swapUrlsInDataModel(int indexOfImageToBeSwapped1, int indexOfImageToBeSwapped2)
    {
	    String tempUrlString = galleryAppModel.urlListProperty().get(indexOfImageToBeSwapped1);
	    galleryAppModel.urlListProperty().set(indexOfImageToBeSwapped1, results[indexOfImageToBeSwapped2]);
	    results[indexOfImageToBeSwapped2] = tempUrlString;
    }
	
	/**
	 * Constructs the GalleryAppController.
	 */
    public GalleryAppController()
	{
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), e -> keyFrameHandler(e)));
	}
	
    /**
     * Handles the exiting of the gallery program.
     * 
     * @param e An ActionEvent representing the pressing of the exit option
     */
	public void exitMenuHandler(ActionEvent e)
	{
		System.exit(0);
	}
	
	/**
	 * Handles the slide-show function of the gallery program.
	 * 
	 * @param e An ActionEvent representing the pressing of the play/pause button
	 */
	public void slideShowEventHandler(ActionEvent e)
	{
	    Button  slideShowButton = (Button) e.getSource();
	    
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
	
	/**
	 * Handles the updating of images after a query is entered in the gallery program.
	 * 
	 * @param e An ActionEvent representing the update images button being pressed
	 */
	public void updateImagesButtonHandler(ActionEvent e)
	{
	    Thread t = new Thread(() -> 
	    {
	        if(isPlaying) timeline.pause();
	        updateSearchResultsModel(galleryAppModel.queryFieldProperty().get());
	        if(isPlaying) timeline.play();
	    });
	    
	    t.start();
	}
	
	/**
	 * Stores the urls of images into a string array
	 * 
	 * @param reader An InputStreamReader from gson
	 * @return searchResults returns an array of image url's corresponding to the search query
	 */
	public String[] parseResults(InputStreamReader reader) 
	{
		JsonArray jsonResults = new JsonParser().parse(reader)
												.getAsJsonObject()
												.getAsJsonArray("results"); // "results" array
		
		int resultSize = jsonResults.size();
		
		String[] searchResults = new String[resultSize];
		
		for (int i = 0; i < resultSize; i++) 
		{                       
		    JsonElement artworkUrl100 = jsonResults.get(i).getAsJsonObject().get("artworkUrl100");
		    // check member existence and assign if present
		    if (artworkUrl100 != null) searchResults[i] = artworkUrl100.getAsString();
		}
		return searchResults;
	}
	
	/**
	 * Constructs and displays a pop up window that is application modal, when a search query produces
	 * less than 20 results.
	 */
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
        (new Scene
            (new VBox()
                {{
                    getChildren().addAll
                    (
                        new Label("Error: The search yields less than " + PANE_MAX_ELEMENTS + " results"),
                        new Label("Enter a new search"),
                        new Label(" "),
                        new Button("Close") {{setOnAction(e -> window.close());}}
                    );
                    
                    setAlignment(Pos.CENTER);
                }} 
            ));
        
		window.showAndWait();
	}
	
	/**
	 * Constructs and displays a pop up window that is application modal to display information about the
	 * author and the program version.
	 */
	public void displayHelpPopUp()
	{
        Stage window = new Stage()
        		        {{
            		        initModality(Modality.APPLICATION_MODAL);
            		        setTitle("About VED-JAVLE");
            		        setWidth(400);
            		        setHeight(400);
            		        setResizable(false);
        		        }};

        window.setScene
        (new Scene
            (new VBox()
                {{
                    getChildren().addAll
                    (
                    		new ImageView(new Image("https://i.imgur.com/De6Hprm.jpg"))
            		        {{
        		                setFitWidth(180);
        		                setFitHeight(200);
            		        }},
            		        new Label(" "),
                    		new Label("Name: Ved Javle"),
                        new Label("Email: vvj82676@uga.edu"),
                        new Label("Version 1.1.1"),
                        new Label(" "),
                        
                        new Button("Close") {{setOnAction(e -> window.close());}}
                    );
                    
                    setAlignment(Pos.CENTER);
                }} 
            ));
        
		window.showAndWait();
	}
	
	/**
	 * Returns a InputStreamField from the gson file.
	 * 
	 * @param searchString A search query that is passed in from the text field
	 * @return Returns a InputStreamField from the gson file
	 */
	public InputStreamReader getQueryResults(String searchString)
	{
		InputStreamReader reader = null;
		
		try
		{
			if(searchString != null)
    				reader = new InputStreamReader(new URL(URL_Part_1 + searchString.replaceAll(" ", "+") + 
    						URL_Part_2 + MAX_SEARCH_RESULTS).openStream());
		}
		
		catch(MalformedURLException e) {e.printStackTrace();}
		
		catch(IOException e) {e.printStackTrace();}
		
		return reader;
	}
	
	/**
	 * Nested class that implements Runnable to assist in the delay of progress bar
	 * in tandem to the displaying of the images.
	 * 
	 * @author Ved Javle (811690870)
	 *
	 */
	private class MyRunnable implements Runnable
	{
	    private int progressIndicator;
	    
	    /**
	     * Constructs the MyRunnable class
	     * 
	     * @param progressIndicator progressIndicator is assigned to the one passed in
	     */
	    public MyRunnable(int progressIndicator)
	    {
	        this.progressIndicator = progressIndicator;
	    }
	    
	    /**
	     * {@inheritDoc}
	     */
        @Override
        public void run()
        {
            galleryAppModel.progressProperty
            				   .set(progressIndicator / ((PANE_MAX_ELEMENTS - 1) / 1.0));
            try
            {
                Thread.sleep(200);
            } 
            catch (InterruptedException e)
            {
                e.printStackTrace();
            };
        }
	}
	
	/**
	 * Updates the images displayed on the grid when a new search query is entered.
	 * If the query produces less than 20 results a pop-up window is displayed and the
	 * previous query's images remain on the screen. If the query produces 20 or more 
	 * results, the new images corresponding to the query are displayed.
	 * 
	 * @param searchString A search query from the text field
	 */
	public void updateSearchResultsModel(String searchString)
	{
	    String[] parsedSearchResults = parseResults(getQueryResults(searchString));
         
	    if(parsedSearchResults != null)
       	{
           	int parsedSearchResultsLength = parsedSearchResults.length;

       		if (parsedSearchResultsLength < PANE_MAX_ELEMENTS)
       		{
       		    Runnable r = () -> displayPopUp();
       		    Platform.runLater(r);
       		}
           	else
           	{
               	galleryAppModel.urlListProperty().clear();
               	results = null;
               	results = new String[parsedSearchResultsLength];

               	for(int i = 0; i < parsedSearchResultsLength; i++)
               	{
                   	results[i] = parsedSearchResults[i];
                   
                   	if(i < PANE_MAX_ELEMENTS)
                   	{
                   	    new MyRunnable(i).run();
                   	    galleryAppModel.urlListProperty().add(results[i]); 
                   	}
               	}
           	} // else
       	}
	} // updateSearchResults 	
}