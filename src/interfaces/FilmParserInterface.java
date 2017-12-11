package interfaces;

import java.text.ParseException;
import java.util.List;

public interface FilmParserInterface {
	
	public List<FilmInterface> contentToFilmList() throws ParseException;

}
