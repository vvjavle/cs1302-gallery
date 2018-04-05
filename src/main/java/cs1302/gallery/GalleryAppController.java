package cs1302.gallery;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GalleryAppController  
{
    boolean isPlaying = false;
    
    public Button playbackButton;
    
    @FXML
    public void playButtonActionHandler(ActionEvent e)
    {
        playbackButton.setText((isPlaying = !isPlaying) ? "Pause" : "Play");
    }
}
