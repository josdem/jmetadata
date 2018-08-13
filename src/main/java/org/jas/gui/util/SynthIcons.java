/*
   Copyright 2013 Jose Luis De la Cruz Morales joseluis.delacruz@gmail.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
