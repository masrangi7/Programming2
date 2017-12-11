package parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

import model.AnimationFilm;

public class WikiAnimationFilmParser {

	private Scanner animationScanner;

	public void echoPage() {

		while (animationScanner.hasNextLine()) {
			System.out.println(animationScanner.nextLine());
		}

	}

	// hasNextline Returns true if the scanner has another line in its input;
	// false otherwise.
	// next matra vayo vane next euta input matra dinxha tara not fullsentence

	/**
	 * Top-Level Methode, die die Datei mit Hilfe eines Scanners parsed und den
	 * Inhalt in eine Liste von AnimationFilm-Objekten Ã¼berfÃ¼hrt.
	 * 
	 * @return Liste von AnimationFilm-Objekten
	 * @throws ParseException
	 *             Tritt u.a. auf, wenn beim Parsen von Date Objekten ein Fehler
	 *             erzeugt wird.
	 */

	// im Normalfall der Pukte im regülaren Ausdrücken nicht mit Zeilenbrüchen
	// matched .
	// Die Option Pattern.MULTILINE|Pattern.DOTALL bewirkt, dass auch
	// Zeilenumbrüche mit dem . matchen

	public List<AnimationFilm> contentToFilmList() throws ParseException {
		// TODO
		Pattern animationBegin = Pattern
				.compile("<h2><span id=\"1995–2004\"></span><span class=\"mw-headline\".*?");
		Pattern jahre = Pattern
				.compile(
						"<td colspan=\"6\" class=\"hintergrundfarbe6\">.*? href=\".*?_([0-9]{4})\".*?</td>.*?"
								+ "(<td colspan=\"6\"|</tbody><tfoot></tfoot></table>)",
						Pattern.MULTILINE | Pattern.DOTALL);
		Pattern animationEnum = Pattern.compile("<tr>.*?<b>(.*?)</b>.*?" // capturing group (month g1)
				+ "<td><a href=\".*?\" titel=\".*?\">(.*?)</a></td>.*?"          // Land g2
				+ "<td>(.*?)</td>*?" // deutscheTitel g3
				+ "<td>(.*?)</td>.*?" // original titel g4
				+ "<td>(.*?)</td>.*?" // AniminationStudio g5
				+ "<td>(.*?)</td>.*?</tr>", // kommentar g6
				Pattern.MULTILINE | Pattern.DOTALL);
		ArrayList<AnimationFilm> listeFilme = new ArrayList<AnimationFilm>();
		
		animationScanner.useDelimiter(animationBegin); // positioning Scanner before patern begins
		if (animationScanner.hasNext()) {
			
			animationScanner.next();

		}

		String alle_jahre_filmliste = animationScanner.next();
		
		Matcher einzelneJahr = jahre.matcher(alle_jahre_filmliste);

		while (einzelneJahr.find()) {
			System.out.println("hallo there");
			String film_jahr_detail = einzelneJahr.group(0);
			String film_jahr = einzelneJahr.group(1);
									
			Matcher film_Enum = animationEnum.matcher(film_jahr_detail);
			while (film_Enum.find()) {
			
              //  string monat =film_Enum(1)
				Date date = parseDate(film_Enum.group(1),film_jahr);
				String ver_Land = clean(film_Enum.group(2));
				String dt_Titel = clean(film_Enum.group(3));
				String org_Titel = clean(film_Enum.group(4));
				String anm_Studio = clean(film_Enum.group(5));
				String anmerkungen = clean(film_Enum.group(6));
				
				ArrayList<String> laender = new ArrayList<String>(Arrays.asList(ver_Land.split("\\s*,\\s*")));
				ArrayList<String> studios = new ArrayList<String>(Arrays.asList(anm_Studio.split("\\s*,\\s*")));
				
				listeFilme.add(new AnimationFilm(org_Titel,dt_Titel,date,laender,studios,anmerkungen));

			}
		}

		// lesen des Berich bis zum ende
		return listeFilme;
	}

	/**
	 * LÃ¶scht alle HTML-Tags aus der Zeichenkette und ersetzt HTML
	 * Sonderzeichen (z.B. &amp;) durch lesbare Zeichen
	 * 
	 * @param str
	 *            : Zeichenkette, die noch HTML Tags oder HTML Sonderzeichen
	 *            enthÃ¤lt
	 * @return Zeichenkette ohne HTML-Tags und mit lesbaren Varianten fÃ¼r die
	 *         Sonderzeichen
	 */
	private String clean(String str) {
		// TODO

		return str.replaceAll("&amp", "&").replaceAll("<.*?>|\n|&nbsp", "");
	}

	/**
	 * Erzeugt aus einer Zeichenkette, die Tag und Monatsangaben enthÃ¤lt, und
	 * einer Zeichenkette, die die Jahreszahl enthÃ¤lt, ein Datumsobjekt.
	 * 
	 * @param dayAndMonth
	 *            Zeichenkette, die Tag und Monatsangaben enthÃ¤lt. Zeichenkette
	 *            ist ggf. nicht konform zur Darstellung von deutschen Tag und
	 *            Monatsangaben in der Langform und muss daher hier in eine
	 *            gÃ¼ltige Form Ã¼berfÃ¼hrt werden.
	 * @param year
	 *            Zeichenkette, die die Jahresangabe als 4-stellige Ziffer
	 *            enthÃ¤lt. Zeichenkette ist ggf. nicht konform zur Darstellung
	 *            von 4-stelligen Jahresangaben und muss daher hier in eine
	 *            gÃ¼ltige Form Ã¼berfÃ¼hrt werden.
	 * @return Date - Objekt fÃ¼r die Datumsangaben in dayAndMonth und year
	 * @throws ParseException
	 */
	Date parseDate(String dayAndMonth, String year) throws ParseException {

		Pattern datumsangaben = Pattern
				.compile(".*?([0-9]{1,2}).*?"
						+ "(Januar|Februar|März|April|Mai|Juni|Juli|August|September|Oktober|November|Dezember");
		Matcher matcher = datumsangaben.matcher(dayAndMonth);
		while (matcher.find()) {

			String dayandmonth = matcher.group(1) + ". " + matcher.group(2);
			Date datum = DateFormat.getDateInstance(DateFormat.LONG,
					Locale.GERMANY).parse(
					String.format("%s%s", dayandmonth, year));
			return datum;
		}

		return null;
	}

	/**
	 * GEGEBEN
	 * 
	 * @param url
	 *            Zeichenkette, die die URL-Ressource korrekt beschreibt
	 * @throws MalformedURLException
	 *             URL im falschen Format
	 * @throws IOException
	 *             kein Zugriff auf die Ressource
	 */
	/**
	 * Konstruktor öffnet einen InputStream für eine URL und erzeugt einen
	 * Scanner für das Lesen der Quelle. Hier handelt es sich um eine HTML
	 * formatierte Seite. Das Encoding wird auf UTF-8 gesetzt. (Encodings können
	 * in dem Metainformationen einer HTML-Seite nachgesehen werden).
	 * 
	 * @param uri
	 *            eine URI als Zeichenkette dargestellt.
	 * @throws MalformedURLException
	 *             : uri ist eine ungültige Darstellung einer URL
	 * @throws IOException
	 *             : Beim Öffnen des InputStreams einer URL ist ein Fehler
	 *             aufgetreten
	 */
	public WikiAnimationFilmParser(String url) throws MalformedURLException,
			IOException {
		this.animationScanner = new Scanner(new URL(url).openStream(), "UTF-8");
	}

	// whenever an input or output operation is failed or interpreted.

	
}
