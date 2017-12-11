package parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.AnimationFilm;
import interfaces.FilmInterface;
import interfaces.FilmParserInterface;

public class WikiAnimationFilmParser implements FilmParserInterface {

	private Scanner animationScanner;

	/**
	 * Top-Level Methode, die die Datei mit Hilfe eines Scanners parsed und den
	 * Inhalt in eine Liste von AnimationFilm-Objekten √ºberf√ºhrt.
	 * 
	 * @return Liste von AnimationFilm-Objekten
	 * @throws ParseException
	 *             Tritt u.a. auf, wenn beim Parsen von Date Objekten ein Fehler
	 *             erzeugt wird.
	 */
	
	public void echoPage() {
		// TODO
		while (animationScanner.hasNextLine()) {
			System.out.println(animationScanner.nextLine());
		}
	}
	public List<FilmInterface> contentToFilmList() throws ParseException {
		//TODO
		String begin = "<h2><span id=\"1995ñ2004\"></span><span class=\"mw-headline\".*?";
		Pattern jahre = Pattern.compile("class=\"hintergrundfarbe6\"><b><a href=.*?_(.*?)"
						+ "\".*?</a></b></td>.*?(<td colspan=\"6\"|</tbody><tfoot></tfoot></table>)",
						Pattern.MULTILINE | Pattern.DOTALL);
		Pattern filmEnum = Pattern.compile("<tr>.*?<b>(.*?)</b>.*?"
				+ "<td><a href=\".*?\" title=\".*?\">(.*?)</a></td.*?"
				+ "<td>(.*?)</td>.*?"
				+ "<td>(.*?)</td>.*?"
				+ "<td>(.*?)</td>.*?" 
				+ "<td>(.*?)</td>.*?</tr>",
				Pattern.MULTILINE | Pattern.DOTALL);
		List<FilmInterface> listFilme = new ArrayList<FilmInterface>();

		animationScanner.useDelimiter(begin);
		if (animationScanner.hasNext()) {
			animationScanner.next();
		}
		
		String alle_jahre = animationScanner.next();
		Matcher einzelnt_jahre = jahre.matcher(alle_jahre);
		while (einzelnt_jahre.find()) {
			String strEinJahr = einzelnt_jahre.group(0);
			String jahreszahl = einzelnt_jahre.group(1);
			Matcher matchEnum = filmEnum.matcher(strEinJahr);
			while (matchEnum.find()) {
				Date date = parseDate(matchEnum.group(1), jahreszahl);
				String land = clean(matchEnum.group(2));
				String deutitel = clean(matchEnum.group(3));
				String ogtitel = clean(matchEnum.group(4));
				String studio = clean(matchEnum.group(5));
				String anmerkung = clean(matchEnum.group(6));

			List<String> laenderlist = new ArrayList<String>(Arrays.asList(land));
			List<String> studiolist = new ArrayList<String>(Arrays.asList(studio));

				listFilme.add(new AnimationFilm(ogtitel, deutitel, date, laenderlist, studiolist, anmerkung));
			}
		}
		return listFilme;
	
	}
	
	Date parseDate(String dayAndMonth, String year) throws ParseException {

		Pattern format = Pattern.compile(".*?(\\d{1,2}).*?(Januar|Februar|M‰rz|April|Mai|Juni|Juli|August|September|"
				+ "Oktober|November|Dezember).*?");
		Matcher matdatum = format.matcher(dayAndMonth);

		if (matdatum.find()) {
			String dayAndMonth2 = matdatum.group(1) + ". " + matdatum.group(2);
			Date datum = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMANY)
					.parse(String.format("%s %s", dayAndMonth2, year));
			return datum;
		}
		return null;
	}
	
	private String clean(String str) {
		return str.replaceAll("&amp;", "&").replaceAll("<.*?>|\n|\r", "").replaceAll("&nbsp;", " ");
	}

	/**
	 * Gegeben
	 * 
	 * @param url
	 *            Zeichenkette, die die URL-Ressource korrekt beschreibt
	 * @throws MalformedURLException
	 *             URL im falschen Format
	 * @throws IOException
	 *             kein Zugriff auf die Ressource
	 */
	public WikiAnimationFilmParser(String url) throws MalformedURLException, IOException {
		this.animationScanner = new Scanner(new URL(url).openStream(), "UTF-8");
	}

}
