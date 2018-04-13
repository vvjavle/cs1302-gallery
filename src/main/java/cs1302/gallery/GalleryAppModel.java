package cs1302.gallery;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GalleryAppModel
{
    ObservableList<String> observableList = FXCollections.observableArrayList(new ArrayList<String>());
    private ListProperty<String> urlList = new SimpleListProperty<String>(observableList);
    private final StringProperty queryText = new SimpleStringProperty();
    public final DoubleProperty progressProperty = new SimpleDoubleProperty();
    
    public final ObservableList<String> getUrlList() 
    {
    		return urlList.getValue();
    }
 
    public ListProperty<String> urlListProperty() 
    {
    		return urlList;
    	}

    public StringProperty queryFieldProperty() 
    {
    		return queryText;
    	}

    public final String getQueryText() 
    {
    		return queryFieldProperty().get();
    	}

    public final void setQueryText(String queryText) 
    {
    		queryFieldProperty().set(queryText);
    	} 
    
    public final void setProgressPropertyValue (double propertyValue) 
    { 
    		progressProperty.set(propertyValue);
    	}
}
