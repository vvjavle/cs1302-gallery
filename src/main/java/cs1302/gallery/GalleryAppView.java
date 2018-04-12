package cs1302.gallery;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
	GalleryAppController galleryAppController;
	ProgressBar progressBar = new ProgressBar();

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
                                        nodes.set(changeIndex, new ImageView(new Image(changedList.get(changeIndex))) {{setFitWidth(100);setFitHeight(100);}});
                                }
                        }
                    };
                    Platform.runLater(r);
                }
	        }
		);

        galleryAppController.updateSearchResultsModel("drake");
	}
	
	public void buildTop()
    {
		setTop(new VBox() {{getChildren().addAll(getMenuBar(),getToolBar());}});
	}

	private Node getToolBar() 
	{
		return new HBox(15)
        {{
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll
            (
                new Button("Play"){{setOnAction(e -> galleryAppController.slideShowEventHandler(e));}},
                new Label("Search Query: "),
                new TextField() {{textProperty().bindBidirectional(galleryAppController.galleryAppModel.queryFieldProperty());}},
                new Button("Update Images") {{setOnAction(e -> galleryAppController.updateImagesButtonHandler(e));}}
            );
        }};
	}

	private MenuBar getMenuBar()
	{
		return new MenuBar() //Main Menu Bar
		{{
		    getMenus().addAll(new Menu("File") {{ getItems().add(new MenuItem("Exit") {{setOnAction (e -> galleryAppController.exitMenuHandler(e));}});}}
		    					, new Menu("Help") {{getItems().add(new MenuItem("About") {{setOnAction(e -> galleryAppController.displayHelpPopUp());}});}});
		}};
	}
	
	public void buildCenter()
	{	
        TilePane tilePane = new TilePane();
        for(int i = 0; i < galleryAppController.PANE_MAX_ELEMENTS; i++)
            tilePane.getChildren().add(new ImageView());
        setCenter(tilePane);
	}
	
	private void buildBottom()
    {		
		setBottom(progressBar);
	}
}