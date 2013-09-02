package org.jas.gui.table;

public enum DescriptionTableColumns {
	
	ARTIST("Artist", 68, 68), TRACK("Track", 180, 180), ALBUM("Album", 68, 68), GENRE("Genre", 67, 67), 
	YEAR("Year", 50, 50), N_TRACK("# Trk", 50, 50), N_TRACKS("# Trks", 50, 50), N_CD("# CD", 50, 50), 
	N_CDS("# CDS", 50, 50), STATUS("Status", 60, 60);
	
	private final String name;
	private final int minWidth;
	private final int maxWidth;
	
	private DescriptionTableColumns(String name, int minWidth, int maxWidth) {
		this.name = name;
		this.minWidth = minWidth;
		this.maxWidth = maxWidth;
	}
	
	public String label() {
		return getName() == null ? "" : getName();
	}

	public int maxWidth() {
		return maxWidth;
	}

	public int minWidth() {
		return minWidth;
	}

	public String getName() {
		return name;
	}
	
}
