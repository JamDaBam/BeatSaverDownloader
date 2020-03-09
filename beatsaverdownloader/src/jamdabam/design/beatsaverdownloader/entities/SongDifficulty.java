package jamdabam.design.beatsaverdownloader.entities;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "iv")
public class SongDifficulty {
	private String ivName;
	private double ivDuration;
	private double ivLength;
	private int ivBombs;
	private int ivNotes;
	private int ivObstactles;
	private double ivNjs;
	private double ivNjsOffset;

	@SuppressWarnings("unused")
	private SongDifficulty() {
	}

	public SongDifficulty(String aName) {
		ivName = aName;
	}
}
