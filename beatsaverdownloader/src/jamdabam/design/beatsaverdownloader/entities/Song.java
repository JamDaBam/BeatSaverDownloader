package jamdabam.design.beatsaverdownloader.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jamdabam.design.beatsaverdownloader.configuration.Constants;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "iv")
public class Song {
	private static final String ILLEGAL_CHARACTERS = "[<>/*\"\\:?|]";

	private Map<String, Boolean> ivDifficulties = new HashMap<>();
	private double ivDuration;
	private List<SongDifficulty> ivCharacteristics = new ArrayList<>();
	private String ivSongName;
	private String ivSongSubName;
	private String ivSongAuthorName;
	private String ivLevelAuthorName;
	private double ivBpm;
	private SongStats ivStats = new SongStats();
	private String ivDescription;
	private String ivId;
	private String ivKey;
	private String ivName;
	private Uploader ivUploader;
	private Date ivUploaded;
	private String ivHash;
	private String ivDirectDownload;
	private String ivDownloadURL;
	private String ivCoverURL;

	public String getDirectDownload() {
		return Constants.BASE_URL + ivDirectDownload;
	}

	public String getCoverURL() {
		return Constants.BASE_URL + ivCoverURL;
	}

	public String getDownloadName() {
		String name = ivSongAuthorName == null ? ""
				: (ivSongAuthorName + " - ") + ivSongName + " (" + ivLevelAuthorName + " " + " ("
						+ getDifficultiesAsString() + "))";
		name = name.replaceAll(ILLEGAL_CHARACTERS, "_");

		return ivKey + " - " + name + ".zip";
	}

	public String getDifficultiesAsString() {
		String res = "";

		for (Iterator<Entry<String, Boolean>> iterator = ivDifficulties.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Boolean> entry = iterator.next();
			String difficulty = entry.getKey();
			Boolean active = entry.getValue();

			if (!res.isEmpty() && Boolean.TRUE.equals(active)) {
				res += ", ";
			}

			if (Boolean.TRUE.equals(active)) {
				res += difficulty;
			}
		}

		return res;
	}

}
