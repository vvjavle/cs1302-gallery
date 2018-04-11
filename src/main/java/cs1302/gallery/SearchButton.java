package cs1302.gallery;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchButton extends Button
{
	TextField textField = null;

	public SearchButton(String title ,TextField textField)
	{
		super(title);
		this.textField = textField;
	}
}