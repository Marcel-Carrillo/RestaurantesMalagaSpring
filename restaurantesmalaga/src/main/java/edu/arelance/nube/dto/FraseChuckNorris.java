package edu.arelance.nube.dto;

/*
 * {
    "categories": [],
    "created_at": "2020-01-05 13:42:30.480041",
    "icon_url": "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
    "id": "UnkLlJd7QgqaqXeDUvvT4w",
    "updated_at": "2020-01-05 13:42:30.480041",
    "url": "https://api.chucknorris.io/jokes/UnkLlJd7QgqaqXeDUvvT4w",
    "value": "You, the reader of this message, have nothing better to do than read Chuck Norris memes all day. One day your fat, acme-covered ass will die of AID's and virginity and i'll stand over your grave in a yellow thong thrusting my man meat on your tombstone tea bagging you."
}
 */

public class FraseChuckNorris {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public FraseChuckNorris(String value) {
		super();
		this.value = value;
	}

	public FraseChuckNorris() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "FraseChuckNorris [value=" + value + "]";
	}
	
	

}
