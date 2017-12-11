package model;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnimationFilm {

	private String originalTitel;
	private String deutscherTitel;
	private Date veroeffentlicht;
	private List<String> land;
	private List<String> studios;
	private String bemerkungen;

	public AnimationFilm(String originalTitel, String deutscherTitel, Date veroeffentlicht, List<String> land,
			List<String> studios, String bemerkungen) {
		this.originalTitel = originalTitel;
		this.deutscherTitel = deutscherTitel;
		this.veroeffentlicht = veroeffentlicht;
		this.land = land;
		this.studios = studios;
		this.bemerkungen = bemerkungen;
	}

	public String getOriginalTitel() {
		return originalTitel;
	}

	public String getDeutscherTitel() {
		return deutscherTitel;
	}

	public Date getVeroeffentlicht() {
		return veroeffentlicht;
	}

	public List<String> getLand() {
		return land;
	}

	public List<String> getStudios() {
		return studios;
	}

	public String getBemerkungen() {
		return bemerkungen;
	}

	@Override
	public String toString() {
		return  String.format("%s: %s: %s: %s: %s: %s",DateFormat.getDateInstance(DateFormat.LONG,Locale.GERMANY).format(veroeffentlicht), land, deutscherTitel, originalTitel, studios,bemerkungen);
	}

	/**
	 * wurde generiert
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bemerkungen == null) ? 0 : bemerkungen.hashCode());
		result = prime * result + ((deutscherTitel == null) ? 0 : deutscherTitel.hashCode());
		result = prime * result + ((land == null) ? 0 : land.hashCode());
		result = prime * result + ((originalTitel == null) ? 0 : originalTitel.hashCode());
		result = prime * result + ((studios == null) ? 0 : studios.hashCode());
		result = prime * result + ((veroeffentlicht == null) ? 0 : veroeffentlicht.hashCode());
		return result;
	}

	/**
	 * wurde generiert
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnimationFilm other = (AnimationFilm) obj;
		if (bemerkungen == null) {
			if (other.bemerkungen != null)
				return false;
		} else if (!bemerkungen.equals(other.bemerkungen))
			return false;
		if (deutscherTitel == null) {
			if (other.deutscherTitel != null)
				return false;
		} else if (!deutscherTitel.equals(other.deutscherTitel))
			return false;
		if (land == null) {
			if (other.land != null)
				return false;
		} else if (!land.equals(other.land))
			return false;
		if (originalTitel == null) {
			if (other.originalTitel != null)
				return false;
		} else if (!originalTitel.equals(other.originalTitel))
			return false;
		if (studios == null) {
			if (other.studios != null)
				return false;
		} else if (!studios.equals(other.studios))
			return false;
		if (veroeffentlicht == null) {
			if (other.veroeffentlicht != null)
				return false;
		} else if (!veroeffentlicht.equals(other.veroeffentlicht))
			return false;
		return true;
	}

}
