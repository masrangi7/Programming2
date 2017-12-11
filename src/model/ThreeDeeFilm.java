package model;

import java.util.Date;
import java.util.List;

public class ThreeDeeFilm extends AbstraktFilmKlasse {

	private String bemerkungen;

	public ThreeDeeFilm(String deutscherTitel, String jahr, Date veroeffentlicht, String studios, String bemerkungen) {

		super(deutscherTitel, jahr);
		setSingleProperty(FilmSingleProperty.COMMENTS, bemerkungen);

	}

	@Override
	public String toSeparatedString(String sep1, String sep2) {
		// TODO Auto-generated method stub
		return null;
	}

}

