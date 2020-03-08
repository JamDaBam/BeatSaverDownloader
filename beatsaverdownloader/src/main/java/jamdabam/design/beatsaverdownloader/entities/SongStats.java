package jamdabam.design.beatsaverdownloader.entities;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "iv")
public class SongStats {
	private int ivDownloads;
	private int ivPlays;
	private int ivDownVotes;
	private int ivUpVotes;
	private double ivHeat;
	private double ivRating;
}
