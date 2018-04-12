package cs1302.gallery;

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
                }
	        }
		);

        galleyAppController.updateSearchResultsModel("drake");
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
                new Button("Play"){{setOnAction(e -> galleyAppController.slideShowEventHandler(e));}},
                new Label("Search Query: "),
                new TextField() {{textProperty().bindBidirectional(galleyAppController.galleryAppModel.queryFieldProperty());}},
                new Button("Update Images") {{setOnAction(e -> galleyAppController.updateImagesButtonHandler(e));}}
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
        TilePane tilePane = new TilePane();
        for(int i=0; i < galleyAppController.PANEMAXELEMENTS;i++)
            tilePane.getChildren().add(new ImageView());
        setCenter(tilePane);
	}
	
	private void buildBottom()
    {		
		setBottom(progressBar);
	}
}
