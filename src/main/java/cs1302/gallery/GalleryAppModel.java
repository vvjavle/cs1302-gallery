package cs1302.gallery;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GalleryAppModel
{
    ObservableList<String> observableList = FXCollections.observableArrayList(new ArrayList<String>());

    private ListProperty<String> urlList = new SimpleListProperty<String>(observableList);
    
    // Define a getter for the property's value
    public final List<String> getUrlList() {return urlList;}
 
     // Define a getter for the property itself
    public ListProperty<String> urlListProperty() {return urlList;}
}
