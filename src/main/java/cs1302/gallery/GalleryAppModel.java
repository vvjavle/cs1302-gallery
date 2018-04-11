package cs1302.gallery;

import java.util.ArrayList;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GalleryAppModel
{
    ObservableList<String> observableList = FXCollections.observableArrayList(new ArrayList<String>());

    private ListProperty<String> urlList = new SimpleListProperty<String>(observableList);
    
    public final ObservableList<String> getUrlList() {return urlList.getValue();}
 
    public ListProperty<String> urlListProperty() {return urlList;}
}
