package cs1302.gallery;

//Driver Imports
import javafx.application.Platform;

//Collections Imports
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

//Event Imports
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;

//Control Imports
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

//Image Imports
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Layout Pans Imports
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * GalleryAppView is responsible for displaying the functions that need to 
 * be carried out on the gallery GUI.
 * 
 * @author Ved Javle (811690870)
 */
public class GalleryAppView extends BorderPane
{
	private GalleryAppController galleryAppController;
	private ProgressBar progressBar = new ProgressBar();
	TextField queryTextField = new TextField();
	Button updateImagesButton = new Button("Update Images");

	/**
	 * Constructs the GalleryAppView
	 * 
	 * @param galleryAppController an instance of the GalleryAppController
	 * 							  class is passed through so that the methods
	 * 							  of GalleryAppController can be called
	 */
	public GalleryAppView(GalleryAppController galleryAppController)
	{
		this.galleryAppController = galleryAppController;
		buildTop();
		buildBottom();
	    buildCenter();

		this.galleryAppController.getGalleryAppModel().urlListProperty().addListener
		(
	        new ListChangeListener<String>()
	        {
                @Override
                public void onChanged(Change<? extends String> change)
                {
                    Runnable r = () ->
                    {
                        change.next();
                        int changeIndex = change.getFrom();
                        
                        TilePane currentTilePane = (TilePane)getCenter();
                        if(currentTilePane != null)
                        {
                            ObservableList<Node> nodes = currentTilePane.getChildren();
                            if(nodes != null)
                                if(change.wasReplaced() || change.wasAdded())
                                {
                                    ObservableList<? extends String> changedList = change.getList();
                                    if(changedList != null)
                                        nodes.set(changeIndex, 
                                        			new ImageView(new Image(changedList.get(changeIndex))) {{setFitWidth(100); setFitHeight(100);}});
                                }
                        }
                    };
                    
                    Platform.runLater(r);
                }
	        }
		);
		
		//After all screen elements initialization and wiring up their event handlers,
		//set the text field to default query and programatically fire action event of the button from View
		queryTextField.setText("rock");
		updateImagesButton.fireEvent(new ActionEvent());
	}
	
	/**
	 * Constructs the top of the border-pane.
	 */
	public void buildTop()
    {
		setTop(new VBox() {{getChildren().addAll(getMenuBar(),getToolBar());}});
	}

	/**
	 * Creates and returns a tool bar that holds the play/pause button, a label, a text-field, and the update
	 * images button.
	 * 
	 * @return returns a node (in the form of an HBox) containing the elements of the tool-bar
	 */
	private Node getToolBar() 
	{
		return new HBox(15)
        {{
            setAlignment(Pos.CENTER_LEFT);
            queryTextField.textProperty().bindBidirectional(galleryAppController.galleryAppModel.queryFieldProperty());
            updateImagesButton.setOnAction(e -> galleryAppController.updateImagesButtonHandler(e));
            getChildren().addAll
            (
                new Button("Play"){{setOnAction(e -> galleryAppController.slideShowEventHandler(e));}},
                new Label("Search Query: "),
                queryTextField,
                updateImagesButton
            );
        }};
	}
	
	/**
	 * Constructs and returns a menu-bar.
	 * 
	 * @return returns a menu-bar containing a file and help option
	 */
	private MenuBar getMenuBar()
	{
		return new MenuBar() //Main Menu Bar
		{{
		    getMenus().addAll(new Menu("File") {{ getItems().add(new MenuItem("Exit") {{setOnAction (e -> galleryAppController.exitMenuHandler(e));}});}}
		    					, new Menu("Help") {{getItems().add(new MenuItem("About") {{setOnAction(e -> galleryAppController.displayHelpPopUp());}});}});
		}};
	}
	
	/**
	 * Constructs the center of the border-pane.
	 */
	public void buildCenter()
	{	
        TilePane tilePane = new TilePane();
        for(int i = 0; i < galleryAppController.PANE_MAX_ELEMENTS; i++)
            tilePane.getChildren().add(new ImageView());
        setCenter(tilePane);
	}
	
	/**
	 * Constructs the bottom of the border-pane (which contains a progress bar and label).
	 */
	private void buildBottom()
    {		
		HBox hb = new HBox();
		
	    progressBar.progressProperty().bind(galleryAppController.galleryAppModel.progressProperty);
	    Label label = new Label("  Images provided courtesy of iTunes");
	    
	    hb.getChildren().addAll(progressBar, label);
	    
		setBottom(hb);
	}
}