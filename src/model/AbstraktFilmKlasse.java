package model;
import java.util.stream.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import interfaces.FilmInterface;


public abstract class AbstraktFilmKlasse implements FilmInterface {
	// gemeinsamen Eigenschaften von Klasse AnimationFilm und ThreeDeeFilm
	protected String deutscherTitel;
	protected String jahr;
	protected Map<FilmSingleProperty, String> singleprop = new HashMap<FilmSingleProperty, String>(); ;
	protected Map<FilmMultiProperty, List<String>> multiprop=new HashMap<FilmMultiProperty, List<String>>(); ;
	
	
	public AbstraktFilmKlasse(String deutscherTitel, String jahr) {
	    
		this.jahr = jahr; // zwingend
		this.deutscherTitel = deutscherTitel; // zwingend
	}

	public AbstraktFilmKlasse(String deutscherTitel, Date datum) {
		this.jahr = jahrExtract(datum); // zwingend
		this.deutscherTitel = deutscherTitel; // zwingend
	}

	private String jahrExtract(Date datum) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(datum);
		return String.valueOf(cal.get(Calendar.YEAR));
	}

	public String getYear() {
		return jahr;
	}

	public String getGermanTitel() {
		return deutscherTitel;
	}

	public void setMultiProperty(FilmMultiProperty prop, List<String> multi) {
		this.multiprop.put(prop, multi);// put(k,v)
	}

	public List<String> getMultiProperty(FilmMultiProperty prop) {
		return this.multiprop.get(prop);
	}

	public boolean hasMultiProperty(FilmMultiProperty prop) {
		return this.multiprop.containsKey(prop);
	}

	public void setSingleProperty(FilmSingleProperty prop, String single) {
		this.singleprop.put(prop, single);
	}

	public String getSingleProperty(FilmSingleProperty prop) {
		return this.singleprop.get(prop);
	}

	public boolean hasSingleProperty(FilmSingleProperty prop) {
		return singleprop.containsKey(prop);
	}

	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getSimpleName());
		sb.append("|");
		sb.append(this.deutscherTitel);
		sb.append(",");
		sb.append(this.getYear());
		String singleProp = Stream.of(FilmSingleProperty.values())
                .filter(prop-> hasSingleProperty(prop))
                .map(prop -> getSingleProperty(prop))
                .collect(Collectors.joining(","));
        if (!singleProp.equals(""))
            sb.append(",");
        sb.append(singleProp);
        sb.append("|");

        return sb.toString();

	}
	

	public String toSeparatedString(String sep1, String sep2) {
		 StringBuffer sb = new StringBuffer();
	        sb.append(this.getClass().getSimpleName() + sep1);
	        sb.append(this.deutscherTitel);
	        sb.append(sep1);
	        sb.append(this.getYear());
	        String singleProp = Arrays.stream(FilmSingleProperty.values())
	                .filter(s->hasSingleProperty(s))
	                .map(s -> s.name() + sep2 + getSingleProperty(s))
	                .collect(Collectors.joining(sep1));

	        String multiProp = Stream.of(FilmMultiProperty.values())
	                .filter(s -> hasMultiProperty(s))
	                .map(s -> s.name() + sep2 + getMultiProperty(s).toString())
	                .collect(Collectors.joining(sep1));
	        if (!singleProp.equals(""))
	            sb.append(sep1);
	        sb.append(singleProp);
	        if (!multiProp.equals(""))
	            sb.append(sep1);
	        sb.append(multiProp);


	        return sb.toString();


	}

	public static void main(String[] args) {
	    List<String> liste = new ArrayList<String>();
	    liste.add("a");
	    liste.add("b");
	    liste.add("c");
		AnimationFilm film = new AnimationFilm("The Swam","Die Schwanenprinzessin", new Date(), liste,
				liste, "kommentar");
		System.out.println(film.toString());
	}
	
}
