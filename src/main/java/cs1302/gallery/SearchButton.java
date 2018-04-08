package cs1302.gallery;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class SearchButton extends Button
{
	BorderPane parentBorderPane = null;
	TextField textField = null;
	ProgressBar progressBar = null;
	public SearchButton(String title , BorderPane parentBorderPane, TextField textField, ProgressBar progressBar)
	{
		super(title);
		this.parentBorderPane = parentBorderPane;
		this.textField = textField;
		this.progressBar = progressBar;
	}
}
