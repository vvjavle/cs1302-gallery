package cs1302.gallery;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class SearchButton extends Button
{
	BorderPane parentBorderPane = null;
	TextField textField = null;
	public SearchButton(String title , BorderPane parentBorderPane, TextField textField)
	{
		super(title);
		this.parentBorderPane = parentBorderPane;
		this.textField = textField;
	}
}
