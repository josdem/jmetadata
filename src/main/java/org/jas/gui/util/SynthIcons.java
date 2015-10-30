package org.jas.gui.util;

import javax.swing.Icon;
import javax.swing.UIManager;

public interface SynthIcons {

	Icon FOLDER_ICON = UIManager.getDefaults().getIcon("Tree.openIcon");

	Icon PLAYLIST_ICON = UIManager.getDefaults().getIcon("Tree.leafIcon");

	Icon NEW_FOLDER_ICON = UIManager.getDefaults().getIcon("Tree.newOpenIcon");

	Icon NEW_PLAYLIST_ICON = UIManager.getDefaults().getIcon("Tree.newLeafIcon");

	Icon HIGHLIGHT_ICON = UIManager.getDefaults().getIcon("Tree.highlight");

	Icon SORT_ASCENDING_ICON = UIManager.getIcon("Table.ascendingSortIcon");

	Icon SORT_DESCENDING_ICON = UIManager.getIcon("Table.descendingSortIcon");

	Icon SORT_NATURAL_ICON = UIManager.getIcon("Table.naturalSortIcon");

	Icon SPEAKER_ICON = UIManager.getDefaults().getIcon("Table.speakerIcon");

	Icon NEW_ICON = UIManager.getDefaults().getIcon("Table.newIcon");

	Icon DOWNLOAD_ICON = UIManager.getDefaults().getIcon("Table.downloadingTrackIcon");

	Icon DOWNLOAD_QUEUE_ICON = UIManager.getDefaults().getIcon("Table.downloadingQueueIcon");

	Icon DOWNLOAD_ERROR_ICON = UIManager.getDefaults().getIcon("Table.downloadingErrorIcon");

	Icon SPEAKER_INVISIBLE_ICON = UIManager.getDefaults().getIcon("Table.speakerInvisibleIcon");

	Icon OFFLINE_ICON = UIManager.getDefaults().getIcon("contactTree.leafOfflineIcon");

	Icon ONLLINE_ICON = UIManager.getDefaults().getIcon("contactTree.leafOnlineIcon");

	Icon PENDING_ICON = UIManager.getDefaults().getIcon("contactTree.leafPendingIcon");

	Icon FACEBOOK_ONLINE_ICON = UIManager.getDefaults().getIcon("contactTree.leafOnlineFacebokIcon");

	Icon FACEBOOK_OFFLINE_ICON = UIManager.getDefaults().getIcon("contactTree.leafOfflineFacebokIcon");

	Icon FACEBOOK_AWAY_ICON = UIManager.getDefaults().getIcon("contactTree.leafAwayFacebookIcon");

	Icon FEED_FOLDER_ICON = UIManager.getDefaults().getIcon("feed.folderIcon");

	Icon FEED_PLAYLIST_ICON = UIManager.getDefaults().getIcon("feed.playlistIcon");

	Icon FEED_MUSIC_ICON = UIManager.getDefaults().getIcon("feed.musicIcon");

	Icon YOUTUBE_ACTIVE_ICON = UIManager.getDefaults().getIcon("youtube.activeIcon");

	Icon YOUTUBE_INACTIVE_ICON = UIManager.getDefaults().getIcon("youtube.inactiveIcon");

}
