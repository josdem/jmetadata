jmetadata
=========

Complete metadata automatically with MusicBrainz, LastFM or manually.

JMetadata also kwnon as JAudioScobbler is based on last.fm API bindings for Java & MusicBrainz our goal is to create a stand alone application that keeps a good Metadata quality.

Metadata is the information about your music files such as genre, cover art, artist, title, track number, cd number, etc.

Open Button: Open a file chooser that enable you to select some folder with mp3 & mp4 audio files.

Complete Button: Searches for an album in MusicBrainz database, but only track files that contains artist & track name and has not album in its metadata. Also JMetadata will search for cover art, year, genre in LastFM, but only track files that contains album and artist. If JMetadata found some new data then will change those rows in the description table with a "New" label; if not, will change to "Complete" label.

Apply Button: When the results from MusicBrainz and LastFM are visible in the description table you are able to apply that changes to your audio files, then JMetadata will change those rows with an updated label.

You are able to do a double click in some row and manually enter custom metadata, that changes will apply to audio files as well.

Send Button: Sends ALL tracks metadata as playcounts to your last.fm account. There is no need to login in your last.fm account for using complete metadata functionality.

Export Button: Sends ALL metadata to files (cover art and text file) in a selected directory.

Username & Password are from your last.fm account, they are secure with JMetadata, because we only send a request to lastfm API. We will not store or send your last.fm account to another site. If you don't want to write your username and password you're able to use JMetadata with out signin anyway.

In FeaturesInDevelopment section you can take a look about what we're thinking as future features, also those which are currently in development.

It requires Java JRE 1.8. JMetadata has been developed with Windows 7, Linux Ubuntu 12.10 And Mac 10.13.6.

“As we enjoy great advantages from inventions of others, we should be glad of an opportunity to serve others by any invention of ours; and this we should do freely and generously.” Benjamin Franklin


Installers Download page: https://github.com/josdem/jmetadata/wiki/Downloads

Wiki page: https://github.com/josdem/jmetadata/wiki
