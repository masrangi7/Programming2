package interfaces;

import java.util.List;

import model.FilmMultiProperty;
import model.FilmSingleProperty;

public interface FilmInterface {

	public String getGermanTitel();
	public String getYear();
	public void setMultiProperty(FilmMultiProperty prop, List<String> multi);
	public List<String> getMultiProperty(FilmMultiProperty prop);
	public boolean hasMultiProperty(FilmMultiProperty prop);
	public void setSingleProperty(FilmSingleProperty prop, String single);
	public String getSingleProperty(FilmSingleProperty prop);
	public boolean hasSingleProperty(FilmSingleProperty prop);
	public String toSeparatedString(String sep1,String sep2);
	
}
