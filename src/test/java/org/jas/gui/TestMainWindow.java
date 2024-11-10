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

package org.jas.gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.apache.commons.lang3.StringUtils;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.ViewEngine;
import org.asmatron.messengine.action.ResponseCallback;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.engines.support.ViewEngineConfigurator;
import org.fest.swing.fixture.FrameFixture;
import org.jas.ApplicationState;
import org.jas.action.ActionResult;
import org.jas.action.Actions;
import org.jas.helper.DialogHelper;
import org.jas.helper.FileChooserHelper;
import org.jas.model.CoverArt;
import org.jas.model.CoverArtType;
import org.jas.model.ExportPackage;
import org.jas.model.Metadata;
import org.jas.model.MetadataAlbumValues;
import org.jas.model.Model;
import org.jas.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
@Ignore
public class TestMainWindow {

	@InjectMocks
	private MainWindow mainWindow = new MainWindow();

	private static final String TRACK_TITLE = "I Don't Own You";
	private static final int FIRST_ROW = 0;
	private static final int SECOND_ROW = 1;
	private static final String OPEN_BUTTON_NAME = "openButton";
	private static final String SEND_BUTTON_NAME = "sendButton";
	private static final String COMPLETE_BUTTON_NAME = "completeMetadataButton";
	private static final String EXPORT_BUTTON_NAME = "exportButton";
	private static final String APPLY_BUTTON_NAME = "applyButton";
	private static final String ARTIST = "Armin Van Buuren";
	private static final String ALBUM = "Mirage";
	private static final String TITLE = "I Don't Own You";
	private static final String YEAR = "2010";
	private static final String CD_NUMBER = "1";
	private static final String TOTAL_CD_NUMBER = "1";
	private static final String GENRE = "Trance";
	private static final String TRACK_NUMBER = "5";
	private static final String TOTAL_TRACKS_NUMBER = "16";
	private static final String LOGIN_LABEL_NAME = "loginLabel";
	private static final String USERNAME = "josdem";
	private static final String PATH = "/User/josdem/music";
	private static final String STATUS_LABEL_NAME = "statusLabel";
	private static final String DIRECTORY_SELECTED_TEXTFIELD_NAME = "searchTextField";
	private static final String IMAGE_LABEL_NAME = "imageLabelName";
	private static final String LOGIN_MENU_ITEM = "loginMenuItem";

	private FrameFixture window;

	@Mock
	private ViewEngineConfigurator viewEngineConfigurator;
	@Mock
	private ControlEngineConfigurator controlEngineConfigurator;
	@Mock
	private ViewEngine viewEngine;
	@Mock
	private ControlEngine controlEngine;
	@Mock
	private Metadata metadata;
	@Mock
	private Set<Metadata> metadataWithAlbum;
	@Mock
	private User currentUser;
	@Mock
	private DialogHelper dialogHelper;
	@Mock
	private File file;
	@Mock
	private File anotherfile;
	@Mock
	private MetadataAlbumValues metadataAlbumValues;
	@Mock
	private LoginWindow loginWindow;
	@Mock
	private FileChooserHelper fileChooserHelper;
	@Mock
	private File root;

	@Captor
	private ArgumentCaptor<ResponseCallback<ActionResult>> responseCaptor;

	private List<Metadata> metadatas = new ArrayList<Metadata>();;
	private Set<Metadata> metadatasWaitingForMetadata = new HashSet<Metadata>();
	private Set<File> filesWithoutMinimumMetadata = new HashSet<File>();
	private ImageIcon imageIcon = new ImageIcon("src/test/resources/images/A Flock of Bleeps.png");
	private JFrame frame = new JFrame();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(viewEngineConfigurator.getViewEngine()).thenReturn(viewEngine);
		when(controlEngineConfigurator.getControlEngine()).thenReturn(controlEngine);
		window = new FrameFixture(mainWindow);
		metadatas.add(metadata);
		metadatasWaitingForMetadata.add(metadata);
		when(metadata.getTitle()).thenReturn(TRACK_TITLE);
	}

	@Test
	public void shouldOpen() throws Exception {
		window.button(OPEN_BUTTON_NAME).click();

		verify(viewEngine).send(Actions.METADATA);
		assertFalse(mainWindow.getSendButton().isEnabled());
		assertFalse(mainWindow.getCompleteMetadataButton().isEnabled());
		assertFalse(mainWindow.getApplyButton().isEnabled());
	}

	private void setSendExpectations() {
		mainWindow.getSendButton().setEnabled(true);
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);
	}

	private ResponseCallback<ActionResult> verifySendExpectations() {
		verify(viewEngine).request(eq(Actions.SEND), eq(metadata), responseCaptor.capture());
		ResponseCallback<ActionResult> callback = responseCaptor.getValue();
		return callback;
	}

	@Test
	public void shouldSend() throws Exception {
		setSendExpectations();

		window.button(SEND_BUTTON_NAME).click();

		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.Sent);
		assertEquals(ActionResult.Sent, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldSendAndGetALoggedOutActionResult() throws Exception {
		setSendExpectations();

		window.button(SEND_BUTTON_NAME).click();

		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.NotLogged);
		assertEquals(ActionResult.NotLogged, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldSendAndGetASessionlessActionResult() throws Exception {
		setSendExpectations();

		window.button(SEND_BUTTON_NAME).click();

		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.Sessionless);
		assertEquals(ActionResult.Sessionless, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldSendAndGetAErrorActionResult() throws Exception {
		setSendExpectations();

		window.button(SEND_BUTTON_NAME).click();

		ResponseCallback<ActionResult> callback = verifySendExpectations();
		callback.onResponse(ActionResult.Error);
		assertEquals(ActionResult.Error, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	private void setMetadataExpectations() {
		when(metadata.getArtist()).thenReturn(ARTIST);
		when(metadata.getTitle()).thenReturn(TITLE);
		when(metadata.getAlbum()).thenReturn(ALBUM);
		when(metadata.getTrackNumber()).thenReturn(TRACK_NUMBER);
		when(metadata.getTotalTracks()).thenReturn(TOTAL_TRACKS_NUMBER);
		when(metadata.getYear()).thenReturn(YEAR);
		when(metadata.getGenre()).thenReturn(GENRE);
		when(metadata.getCdNumber()).thenReturn(CD_NUMBER);
		when(metadata.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);
	}

	@Test
	public void shouldComplete() throws Exception {
		setMetadataExpectations();

		mainWindow.getCompleteMetadataButton().setEnabled(true);
		window.button(COMPLETE_BUTTON_NAME).click();

		verify(viewEngine).request(eq(Actions.COMPLETE_MUSICBRAINZ), eq(metadata), responseCaptor.capture());
		ResponseCallback<ActionResult> callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.New);

		verifyCompleteMusicbrainzAssertions();

		verify(viewEngine).request(eq(Actions.COMPLETE_FORMATTER), eq(metadata), responseCaptor.capture());
		callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.New);

		verifyCompleteFormatterAssertions();

		verify(viewEngine).request(eq(Actions.COMPLETE_LAST_FM), eq(metadata), responseCaptor.capture());
		callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.New);

		verifyCompleteLastfmAssertions();

		verify(viewEngine).request(eq(Actions.COMPLETE_DEFAULT), eq(metadatas), responseCaptor.capture());
		callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.New);

		verifyCompleteDefaultAssertions();

		verify(controlEngine).set(Model.METADATA_ARTIST, metadataWithAlbum, null);
		assertTrue(mainWindow.getApplyButton().isEnabled());
		verifyButtonsAssertions();
	}

	private void verifyCompleteDefaultAssertions() {
		assertEquals(TOTAL_TRACKS_NUMBER, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.TOTAL_TRACKS_NUMBER_COLUMN));
		assertEquals(CD_NUMBER, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.CD_NUMBER_COLUMN));
		assertEquals(TOTAL_CD_NUMBER, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.TOTAL_CDS_NUMBER_COLUMN));
		assertEquals(ActionResult.New, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	private void verifyCompleteLastfmAssertions() {
		assertEquals(YEAR, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.YEAR_COLUMN));
		assertEquals(GENRE, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.GENRE_COLUMN));
		assertEquals(ActionResult.New, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	private void verifyCompleteFormatterAssertions() {
		assertEquals(ARTIST, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.ARTIST_COLUMN));
		assertEquals(TITLE, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.TITLE_COLUMN));
		assertEquals(ALBUM, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.ALBUM_COLUMN));
		assertEquals(ActionResult.New, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	private void verifyCompleteMusicbrainzAssertions() {
		assertEquals(ALBUM, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.ALBUM_COLUMN));
		assertEquals(TRACK_NUMBER, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.TRACK_NUMBER_COLUMN));
		assertEquals(TOTAL_TRACKS_NUMBER, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.TOTAL_TRACKS_NUMBER_COLUMN));
		assertEquals(ActionResult.New, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	private void verifyButtonsAssertions() {
		assertEquals(ApplicationState.DONE, mainWindow.getLabel().getText());
		assertTrue(mainWindow.getCompleteMetadataButton().isEnabled());
		assertTrue(mainWindow.getOpenButton().isEnabled());
		assertTrue(mainWindow.getDescriptionTable().isEnabled());
	}

	@Test
	public void shouldRespondOnUserLogged() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(ApplicationState.LOGGED_AS);
		sb.append(USERNAME);

		when(currentUser.getUsername()).thenReturn(USERNAME);

		mainWindow.onUserLogged(currentUser);

		assertEquals(sb.toString(), window.label(LOGIN_LABEL_NAME).text());
		assertTrue(window.button(SEND_BUTTON_NAME).target.isEnabled());
	}

	@Test
	public void shouldShowDialogWhenCoverArtCorrupted() throws Exception {
		StringBuilder sb = createStringBuilder();
		sb.append(ApplicationState.CORRUPTED_METADATA_LABEL);
		mainWindow.onCovertArtFailed(TITLE);
		verify(dialogHelper).showMessageDialog(mainWindow, sb.toString());
	}

	@Test
	public void shouldRespondAMusicDirectorySelected() throws Exception {
		mainWindow.getDescriptionTable().setEnabled(true);
		mainWindow.getDescriptionTable().setValueAt(ALBUM, 0, ApplicationState.ARTIST_COLUMN);
		assertEquals(1, mainWindow.getDescriptionTable().getModel().getRowCount());

		mainWindow.onMusicDirectorySelected(PATH);

		assertEquals(ApplicationState.WORKING, window.label(STATUS_LABEL_NAME).text());
		assertEquals(0, mainWindow.getDescriptionTable().getModel().getRowCount());
		assertEquals(PATH, window.textBox(DIRECTORY_SELECTED_TEXTFIELD_NAME).text());
	}

	@Test
	public void shouldLetUserKnowLoginFailed() throws Exception {
		mainWindow.onUserLoginFailed();
		assertEquals(ApplicationState.LOGIN_FAIL, window.label(LOGIN_LABEL_NAME).text());
	}

	@Test
	public void shouldKnowWhenMusicDirectorySelectedCanceled() throws Exception {
		mainWindow.onMusicDirectorySelectedCancel();
		assertTrue(window.button(OPEN_BUTTON_NAME).target.isEnabled());
	}

	@Test
	public void shouldResetstatusWhenTrackLoaded() throws Exception {
		setControlAndViewEngineExpectations();

		mainWindow.onTracksLoaded();

		verifyResetStatus();
	}

	private void verifyResetStatus() {
		assertTrue(window.button(COMPLETE_BUTTON_NAME).target.isEnabled());
		assertTrue(window.button(EXPORT_BUTTON_NAME).target.isEnabled());
		assertTrue(window.button(OPEN_BUTTON_NAME).target.isEnabled());
		assertEquals(ApplicationState.DONE, window.label(STATUS_LABEL_NAME).text());
	}

	@Test
	public void shouldUpdateImageWhenCoverArtAddedFromDandD() throws Exception {
		CoverArt newCoverArt = new CoverArt(imageIcon.getImage(), CoverArtType.DRAG_AND_DROP);
		when(metadata.getNewCoverArt()).thenReturn(newCoverArt);
		setControlAndViewEngineExpectations();

		mainWindow.onTracksLoaded();
		Thread.sleep(500);

		assertEquals(ApplicationState.COVER_ART_FROM_DRAG_AND_DROP, window.label(IMAGE_LABEL_NAME).text());
	}

	@Test
	public void shouldUpdateImageWhenCoverArtAddedFromLastFM() throws Exception {
		CoverArt newCoverArt = new CoverArt(imageIcon.getImage(), CoverArtType.LAST_FM);
		when(metadata.getNewCoverArt()).thenReturn(newCoverArt);
		setControlAndViewEngineExpectations();

		mainWindow.onTracksLoaded();
		Thread.sleep(500);

		assertEquals(ApplicationState.COVER_ART_FROM_LASTFM, window.label(IMAGE_LABEL_NAME).text());
	}

	@Test
	public void shouldUpdateImageWhenCoverArtAddedFromFile() throws Exception {
		when(metadata.getCoverArt()).thenReturn(imageIcon.getImage());
		setControlAndViewEngineExpectations();

		mainWindow.onTracksLoaded();
		Thread.sleep(500);

		assertEquals(ApplicationState.COVER_ART_FROM_FILE, window.label(IMAGE_LABEL_NAME).text());
	}

	@Test
	public void shouldUpdateImageWhenCoverArtIsDefault() throws Exception {
		setControlAndViewEngineExpectations();

		mainWindow.onTracksLoaded();
		Thread.sleep(500);

		assertEquals(ApplicationState.COVER_ART_DEFAULT, window.label(IMAGE_LABEL_NAME).text());
	}

	@Test
	public void shouldAlertOnlyOneFileHasMetadataFromFilename() throws Exception {
		when(file.getName()).thenReturn(TITLE);
		filesWithoutMinimumMetadata.add(file);
		setControlAndViewEngineExpectations();
		StringBuilder sb = setStringBuilderExpectations();

		mainWindow.onTracksLoaded();

		verify(dialogHelper).showMessageDialog(mainWindow, sb.toString());
	}

	@Test
	public void shouldAlertTwoFileHasMetadataFromFilename() throws Exception {
		when(file.getName()).thenReturn(TITLE);
		when(anotherfile.getName()).thenReturn(TITLE);
		filesWithoutMinimumMetadata.add(file);
		filesWithoutMinimumMetadata.add(anotherfile);
		setControlAndViewEngineExpectations();
		StringBuilder sb = setSeveralFilesStringBuilderExpectations();

		mainWindow.onTracksLoaded();

		verify(dialogHelper).showMessageDialog(mainWindow, sb.toString());
	}

	private StringBuilder setStringBuilderExpectations() {
		StringBuilder sb = createStringBuilder();
		sb.append(ApplicationState.METADATA_FROM_FILE_LABEL);
		return sb;
	}

	private StringBuilder setSeveralFilesStringBuilderExpectations() {
		StringBuilder sb = createStringBuilder();
		sb.append(ApplicationState.AND_ANOTHER);
		sb.append(filesWithoutMinimumMetadata.size() - 1);
		sb.append(ApplicationState.METADATA_FROM_FILE_LABEL);
		return sb;
	}

	private StringBuilder createStringBuilder() {
		StringBuilder sb = new StringBuilder();
		sb.append(TITLE);
		return sb;
	}

	private void setControlAndViewEngineExpectations() {
		when(controlEngine.get(Model.FILES_WITHOUT_MINIMUM_METADATA)).thenReturn(filesWithoutMinimumMetadata);
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);
	}

	@Test
	public void shouldInformWhenADirectoryIsEmpty() throws Exception {
		mainWindow.onDirectoryEmpty();
		verify(dialogHelper).showMessageDialog(mainWindow, ApplicationState.DIRECTORY_EMPTY);
		verifyResetStatus();
	}

	@Test
	public void shouldKnowWhenLoadMetadata() throws Exception {
		when(metadata.getArtist()).thenReturn(ARTIST);
		when(metadata.getTitle()).thenReturn(TITLE);
		when(metadata.getAlbum()).thenReturn(ALBUM);
		when(metadata.getGenre()).thenReturn(GENRE);
		when(metadata.getYear()).thenReturn(YEAR);
		when(metadata.getTrackNumber()).thenReturn(TRACK_NUMBER);
		when(metadata.getTotalTracks()).thenReturn(TOTAL_TRACKS_NUMBER);
		when(metadata.getCdNumber()).thenReturn(CD_NUMBER);
		when(metadata.getTotalCds()).thenReturn(TOTAL_CD_NUMBER);

		mainWindow.onLoadMetadata(metadatas);

		assertEquals(ARTIST, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.ARTIST_COLUMN));
		assertEquals(TITLE, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.TITLE_COLUMN));
		assertEquals(ALBUM, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.ALBUM_COLUMN));
		assertEquals(GENRE, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.GENRE_COLUMN));
		assertEquals(YEAR, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.YEAR_COLUMN));
		assertEquals(TRACK_NUMBER, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.TRACK_NUMBER_COLUMN));
		assertEquals(TOTAL_TRACKS_NUMBER, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.TOTAL_TRACKS_NUMBER_COLUMN));
		assertEquals(CD_NUMBER, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.CD_NUMBER_COLUMN));
		assertEquals(TOTAL_CD_NUMBER, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.TOTAL_CDS_NUMBER_COLUMN));
		assertEquals(ApplicationState.READY, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.STATUS_COLUMN));
	}

	@Test
	public void shouldNotAssingValuesWhenEmptyOnReadyToApplyMetadata() throws Exception {
		when(controlEngine.get(Model.METADATA)).thenReturn(metadatas);
		mainWindow.onReadyToApplyMetadata(metadataAlbumValues);

		assertEquals(StringUtils.EMPTY, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.ARTIST_COLUMN));
		assertEquals(StringUtils.EMPTY, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.ALBUM_COLUMN));
	}

	@Test
	public void shouldAssignValuesOnReadyToApplyMetadata() throws Exception {
		when(metadataAlbumValues.getArtist()).thenReturn(ARTIST);
		when(metadataAlbumValues.getAlbum()).thenReturn(ALBUM);
		when(metadataAlbumValues.getGenre()).thenReturn(GENRE);
		when(metadataAlbumValues.getYear()).thenReturn(YEAR);
		when(metadataAlbumValues.getTracks()).thenReturn(TOTAL_TRACKS_NUMBER);
		when(metadataAlbumValues.getCd()).thenReturn(CD_NUMBER);
		when(metadataAlbumValues.getCds()).thenReturn(TOTAL_CD_NUMBER);

		when(controlEngine.get(Model.METADATA)).thenReturn(metadatas);
		mainWindow.onReadyToApplyMetadata(metadataAlbumValues);

		assertEquals(ARTIST, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.ARTIST_COLUMN));
		assertEquals(ALBUM, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.ALBUM_COLUMN));
		assertEquals(GENRE, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.GENRE_COLUMN));
		assertEquals(YEAR, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.YEAR_COLUMN));
		assertEquals(TOTAL_TRACKS_NUMBER, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.TOTAL_TRACKS_NUMBER_COLUMN));
		assertEquals(CD_NUMBER, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.CD_NUMBER_COLUMN));
		assertEquals(TOTAL_CD_NUMBER, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.TOTAL_CDS_NUMBER_COLUMN));
	}

	@Test
	public void shouldDetectWhenNewCoverArtOnReadyToApplyMetadata() throws Exception {
		when(metadataAlbumValues.getCoverArt()).thenReturn(imageIcon.getImage());
		when(controlEngine.get(Model.METADATA)).thenReturn(metadatas);
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);

		mainWindow.onReadyToApplyMetadata(metadataAlbumValues);

		verify(metadata).setNewCoverArt(isA(CoverArt.class));
		assertEquals(ActionResult.New, mainWindow.getDescriptionTable().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
		assertTrue(window.button(APPLY_BUTTON_NAME).target.isEnabled());
	}

	@Test
	public void shouldKnowWhenMetadataIsFromFile() throws Exception {
		when(metadata.isMetadataFromFile()).thenReturn(true);
		mainWindow.onLoadMetadata(metadatas);
		assertEquals(ApplicationState.NEW, mainWindow.getDescriptionTable().getValueAt(SECOND_ROW, ApplicationState.STATUS_COLUMN));
		assertTrue(window.button(APPLY_BUTTON_NAME).target.isEnabled());
	}

	@Test
	public void shouldKnowWhenErrorInOpen() throws Exception {
		mainWindow.onOpenError();
		assertEquals(ApplicationState.OPEN_ERROR, window.label(STATUS_LABEL_NAME).target.getText());
	}

	@Test
	public void shouldClickMenuItem() throws Exception {
		when(loginWindow.getFrame()).thenReturn(frame);

		assertFalse(frame.isVisible());

		window.menuItem(LOGIN_MENU_ITEM).click();

		assertTrue(frame.isVisible());
	}

	@Test
	public void shouldExport() throws Exception {
		window.button(EXPORT_BUTTON_NAME).target.setEnabled(true);
		when(fileChooserHelper.getDirectory()).thenReturn(root);
		when(viewEngine.get(Model.METADATA)).thenReturn(metadatas);

		window.button(EXPORT_BUTTON_NAME).click();



		verify(viewEngine).request(eq(Actions.EXPORT), isA(ExportPackage.class), responseCaptor.capture());
		ResponseCallback<ActionResult> callback = responseCaptor.getValue();
		callback.onResponse(ActionResult.Exported);
		assertEquals(ActionResult.Exported, mainWindow.getDescriptionTable().getModel().getValueAt(FIRST_ROW, ApplicationState.STATUS_COLUMN));
	}

	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}

}
