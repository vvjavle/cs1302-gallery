package cs1302.gallery;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GalleryAppController extends Application 
{
    boolean isPlaying = false;
    public void playButtonActionHandler(ActionEvent e)
    {
        ((Button) e.getSource()).setText((isPlaying = !isPlaying) ? "Pause" : "Play");
    }

    @Override
    public void start(Stage arg0) throws Exception
    {
        // TODO Auto-generated method stub
        
    }
}
