package parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import factories.FilmParserFactory;
import interfaces.FilmInterface;
import interfaces.FilmParserInterface;

public class MainMain {
	
	private static final String sep1 = "##";
	private static final String sep2 = ":";
	

	public static void main(String[] args) throws MalformedURLException, IOException, ParseException {

		// TODO
		FilmParserInterface pi11 = FilmParserFactory.getAnimationFilmParserInstance("animation.html");
		FilmParserInterface pi21 = FilmParserFactory.getFilmDataSeparatedParserInstance("animationDataSeparated", sep1,sep2);
		FilmParserInterface pi22 = FilmParserFactory.getFilmDataSeparatedParserInstance("threeDeeDataSeparated", sep1,sep2);
		FilmParserInterface pi23 = FilmParserFactory.getFilmDataSeparatedParserInstance("mixedDataSeparated", sep1,sep2);
		
		ppList(pi11.contentToFilmList());
//		ppList(pi21.contentToFilmList());
//		ppList(pi22.contentToFilmList());
//		ppList(pi23.contentToFilmList());
	}

	private static void ppList(List<FilmInterface> al) {
		Collections.sort(al, new Comparator<FilmInterface>() {

			@Override
			public int compare(FilmInterface o1, FilmInterface o2) {
				return o1.getYear().compareTo(o2.getYear());
			}

		});
		for (FilmInterface film : al) {
			System.out.println(film);
		}
	}
	
	/**
	 * Untersuchen der Systemumgebung
	 */
	private static void showProps() {
		Properties props = System.getProperties();
		for (Object key : props.keySet()) {
			System.out.println(key + ":" + props.getProperty((String) key));
		}
	}
	
	private static void showProp(String prop) {
		System.out.println(System.getProperty(prop));
	}

}
