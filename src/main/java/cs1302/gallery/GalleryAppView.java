package cs1302.gallery;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class GalleryAppView extends BorderPane
{
	GalleryAppController galleyAppController;
	ProgressBar progressBar = new ProgressBar();

	public GalleryAppView(GalleryAppController galleryAppController)
	{
		this.galleyAppController = galleryAppController;
		buildTop();
		buildBottom();
		buildCenter();

		this.galleyAppController.getGalleryAppModel().urlListProperty().addListener
		(
	        new ListChangeListener<String>()
	        {
                @Override
                public void onChanged(Change<? extends String> change)
                {
                    change.next();
                    int changeIndex = change.getFrom();
                    ((TilePane)getCenter()).getChildren().set(changeIndex, new ImageView(new Image(change.getList().get(changeIndex))){{setFitWidth(100);setFitHeight(100);}} );
                }
	        }
		);
	}
	
	public void buildTop()
    {
		setTop(new VBox() {{getChildren().addAll(getMenuBar(),getToolBar());}});
	}

	private Node getToolBar() 
	{
	    TextField queryInput = new TextField();
	    SearchButton searchButton = new SearchButton("Update Images", this ,queryInput) {{setOnAction(e -> galleyAppController.updateImagesButtonHandler(e));}};
	    
		return new HBox(15)
		        {{
		            setAlignment(Pos.CENTER_LEFT);
		            getChildren().addAll
		            (
	                    new Button("Play"){{setOnAction(e -> galleyAppController.slideShowEventHandler(e));}},
	                    new Label("Search Query: "),
	                    queryInput,
	                    searchButton
		            );
		        }};
	}

	private MenuBar getMenuBar()
	{
		return new MenuBar() //Main Menu Bar
		{{
		    getMenus().addAll(new Menu("File"){{getItems().add(new MenuItem("Exit") {{setOnAction (e -> galleyAppController.exitMenuHandler(e));}});}});
		}};
	}
	
	public void buildCenter()
	{	
        galleyAppController.updateSearchResultsModel("drake");
        if(galleyAppController.getSearchResultLength() < 20) galleyAppController.displayPopUp();
        setCenter(galleyAppController.getUpdatedTilePane());
	}
	
	private void buildBottom()
    {		
		setBottom(progressBar);
	}
}
