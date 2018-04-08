package cs1302.gallery;
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

	public GalleryAppView(GalleryAppController galleryAppController)
	{
		this.galleyAppController = galleryAppController;
		buildTop();
		buildCenter();
		buildBottom();
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
		Button slideShowButton = new Button("Play");
		slideShowButton.setOnAction(e -> galleyAppController.slideShowEventHandler(e));
		hbox.getChildren().add(slideShowButton);

		//Label Field
		Label searchQueryLabel = new Label("Search Query: ");
		hbox.getChildren().add(searchQueryLabel);
		
		//Text Field
		TextField queryInput = new TextField();
		hbox.getChildren().add(queryInput);
		
		//Button
		SearchButton updateImagesButton = new SearchButton("Update Images",this,queryInput);
		updateImagesButton.setOnAction(e -> galleyAppController.updateImagesButtonHandler(e));
		hbox.getChildren().add(updateImagesButton);
		return hbox;
	}

	private MenuBar getMenuBar()
	{
		//File Menu
		Menu fileMenu = new Menu("File");
		
		//Menu Items
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction (e -> galleyAppController.exitMenuHandler(e));
		fileMenu.getItems().add(exit);
		
		//Main Menu Bar
		MenuBar mainMenu = new MenuBar();
		mainMenu.getMenus().addAll(fileMenu);
		
		return mainMenu;
	}
	
	public void buildCenter()
	{	
		TilePane pane = new TilePane();
		setCenter(pane);
	}
	
	private void buildBottom()
    {
		ProgressBar progressBar = new ProgressBar();
		
		setBottom(progressBar);
	}
}
