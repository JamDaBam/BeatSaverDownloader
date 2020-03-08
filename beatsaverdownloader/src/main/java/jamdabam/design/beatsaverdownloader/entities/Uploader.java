package jamdabam.design.beatsaverdownloader.entities;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "iv")
public class Uploader {
	private String ivId;
	private String ivUsername;

	@SuppressWarnings("unused")
	private Uploader() {
	}

	public Uploader(String aId, String aUsername) {
		ivId = aId;
		ivUsername = aUsername;
	}
}
