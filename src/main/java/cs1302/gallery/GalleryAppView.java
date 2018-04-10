package cs1302.gallery;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
    		    new ChangeListener() 
        		{
    		        int i = 0;
                    @Override
                    public void changed(ObservableValue arg0, Object arg1, Object arg2)
                    {
                        //System.out.println(i++ + ":" + arg0.getClass().toString() + "," + arg1.getClass().toString() + "," + arg2.getClass().toString());
                        setCenter(galleyAppController.getUpdatedTilePane());
                    } 
        		}	    
        );
	}
	
	public void buildTop()
    {
		VBox topBar = new VBox();
		topBar.getChildren().add(getMenuBar());
		topBar.getChildren().add(getToolBar());
		setTop(topBar);
	}

	private Node getToolBar() 
	{
		HBox hbox = new HBox(15);
		hbox.setAlignment(Pos.CENTER_LEFT);
		
		//Button
		hbox.getChildren().add(new Button("Play"){{setOnAction(e -> galleyAppController.slideShowEventHandler(e));}});
		hbox.getChildren().add(new Label("Search Query: "));
		
		//Text Field
		TextField queryInput = new TextField();
		hbox.getChildren().add(queryInput);
		
		//Button
		hbox.getChildren().add(new SearchButton("Update Images", this ,queryInput) {{setOnAction(e -> galleyAppController.updateImagesButtonHandler(e));}});
		
		return hbox;
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
