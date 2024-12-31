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

package com.josdem.jmetadata.gui;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.asmatron.messengine.action.ResponseCallback;
import org.asmatron.messengine.annotations.EventMethod;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.engines.support.ViewEngineConfigurator;

import org.springframework.beans.factory.annotation.Autowired;

import com.josdem.jmetadata.ApplicationConstants;
import com.josdem.jmetadata.action.ActionResult;
import com.josdem.jmetadata.action.Actions;
import com.josdem.jmetadata.dnd.ImageDropListener;
import com.josdem.jmetadata.dnd.MainFrameDragOverListener;
import com.josdem.jmetadata.dnd.MultiLayerDropTargetListener;
import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.gui.table.DescriptionTable;
import com.josdem.jmetadata.helper.DialogHelper;
import com.josdem.jmetadata.helper.FileChooserHelper;
import com.josdem.jmetadata.helper.MetadataAdapter;
import com.josdem.jmetadata.model.CoverArt;
import com.josdem.jmetadata.model.CoverArtType;
import com.josdem.jmetadata.model.ExportPackage;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.MetadataAlbumValues;
import com.josdem.jmetadata.model.Model;
import com.josdem.jmetadata.model.User;
import com.josdem.jmetadata.observer.ObservValue;
import com.josdem.jmetadata.observer.Observer;
import com.josdem.jmetadata.util.ImageUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j

/**
 * @understands A principal JAudioScrobbler principal window
 */

@SuppressWarnings("unused")
public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1311230231101552007L;
	private static final String JMENU_ITEM_LABEL = "Sign in Last.fm";
	private static final String JMENU_EXIT_LABEL = "Exit";
	private static final String JMENU_LABEL = "File";
	private static final int DIRECTORY_SELECTED_LENGHT = 20;
	private static final String STATUS_LABEL = "Status";
	private static final String CTRL_O = "CTRL+O";
	private static final String ENTER = "ENTER";
	private static final String LOGIN_MENU_ITEM = "loginMenuItem";
	private static final String EXIT_MENU_ITEM = "exitMenuItem";
	private static final String SEND_SCROBBLINGS = "Send";
	private static final String LOAD_FILES = "Open";
	private static final String LOG_OUT = "logged out";
	private static final String LOGIN_LABEL_NAME = "loginLabel";
	private static final String STATUS_LABEL_NAME = "statusLabel";
	private static final String IMAGE_LABEL_NAME = "imageLabelName";
	private static final String MAIN_MENU_NAME = "mainMenuName";
	private static final String IMAGE_PANEL_NAME = "bigDnDImagePanel";

	/**
	 * 	Default button style
	 *	private static final String SEND_BUTTON_NAME = "button[A-Za-z0-9]+";
	 */
	private static final String TEXTFIELD_NAME = "searchTextField";
	private static final String OPEN_BUTTON_NAME = "openButton";
	private static final String SEND_BUTTON_NAME = "sendButton";
	private static final String APPLY_BUTTON_NAME = "applyButton";
	private static final String COMPLETE_BUTTON_NAME = "completeMetadataButton";
	private static final String EXPORT_BUTTON_NAME = "exportButton";
	private static final String PROGRESS_BAR_NAME = "bigProgressBar";
	private static final int PROGRESS_BAR_DEFAULT_VALUE = 0;
	private static final int PROGRESS_BAR_MAXIMUM_VALUE = 100;

	/**
	 * Bottom panel bounds
	 */
	private static final Rectangle BOTTOM_PANEL_BOUNDS = new Rectangle(0, 500, 1024, 100);
	private static final Rectangle LABEL_BOUNDS = new Rectangle(10, 10, 50, 20);
	private static final Rectangle DIRECTORY_BOUNDS = new Rectangle(60, 10, 195, 20);
	private static final Rectangle PROGRESS_BAR_BOUNDS = new Rectangle(265, 10, 150, 20);
	private static final Rectangle OPEN_BUTTON_BOUNDS = new Rectangle(425, 10, 110, 28);
	private static final Rectangle SEND_BUTTON_BOUNDS = new Rectangle(545, 10, 110, 28);
	private static final Rectangle COMPLETE_BUTTON_BOUNDS = new Rectangle(665, 10, 110, 28);
	private static final Rectangle APPLY_BUTTON_BOUNDS = new Rectangle(785, 10, 110, 28);
	private static final Rectangle EXPORT_BUTTON_BOUNDS = new Rectangle(905, 10, 110, 28);

	/**
	 * Top panel bounds
	 */
	private static final Rectangle TOP_PANEL_BOUNDS = new Rectangle(0, 0, 1024, 40);
	private static final Rectangle LOGIN_LABEL_BOUNDS = new Rectangle(450, 10, 200, 20);

	/**
	 * Middle panel bounds
	 */
	private static final Rectangle MIDDLE_PANEL_BOUNDS = new Rectangle(0, 50, 1024, 400);
	private static final Rectangle IMAGE_PANEL_BOUNDS = new Rectangle(10, 10, 300, 300);
	private static final Rectangle IMAGE_LABEL_BOUNDS = new Rectangle(100, 320, 200, 20);
	private static final Rectangle SCROLL_PANE_BOUNDS = new Rectangle(320, 10, 693, 390);

	private JPanel panel;
	private JButton openButton;
	private JButton sendButton;
	private JButton applyButton;
	private JButton exportButton;
	private JButton completeMetadataButton;
	private JTextField directorySelected;
	private JPanel bottomPanel;
	private JTable descriptionTable;
	private JSlider progressBar;
	private JLabel label;
	private JLabel imageLabel;
	private JLabel loginLabel;
	private JPanel middlePanel;
	private JPanel topPanel;
	private JPanel imagePanel;
	private JMenuBar menuBar;
	private JMenu mainMenu;
	private JMenuItem lastFmMenuItem;
	private JMenuItem exitMenuItem;
	private InputMap inputMap;
	private JScrollPane scrollPane;
	private ImageUtils imageUtils = new ImageUtils();
	private Set<Metadata> metadataWithAlbum = new HashSet<Metadata>();
	private MetadataAdapter metadataAdapter = new MetadataAdapter();
	private DialogHelper dialogHelper = new DialogHelper();
	private FileChooserHelper fileChooserHelper = new FileChooserHelper();
	boolean tableLoaded;
	boolean working;
	private int counter = 0;
	private int selectedRow;

	@Autowired
	private LoginWindow loginWindow;
	@Autowired
	private ViewEngineConfigurator viewEngineConfigurator;
	@Autowired
	private ControlEngineConfigurator controlEngineConfigurator;


	public MainWindow() {
		super(ApplicationConstants.APPLICATION_NAME);
		initialize();
		getDescriptionTable().getModel().addTableModelListener(new DescriptionTableModelListener());
	}

	private void initialize() {
		registerKeyStrokeAction();
		this.setBounds(0, 0, ApplicationConstants.WIDTH, ApplicationConstants.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setJMenuBar(getMenubar());
		this.add(getPanel());
		this.setVisible(true);
	}

	@PostConstruct
	private void setDragAndDrop() {
		MultiLayerDropTargetListener multiLayerDropTargetListener = new MultiLayerDropTargetListener();
		setDropTarget(new DropTarget(this, multiLayerDropTargetListener));
		multiLayerDropTargetListener.addDragListener(this, new MainFrameDragOverListener(this));
		ImageDropListener listener = new ImageDropListener(new ImagePanel());
		listener.onDropped().add(new Observer<ObservValue<ImagePanel>>() {

			public void observe(ObservValue<ImagePanel> t) {
				ImagePanel value = t.getValue();
				Image image = value.getImage();
				List<Metadata> metadatas = controlEngineConfigurator.getControlEngine().get(Model.METADATA);
				Metadata metadata = metadatas.get(selectedRow);
				CoverArt coverArt = new CoverArt(imageUtils.resize(image, ApplicationConstants.THREE_HUNDRED, ApplicationConstants.THREE_HUNDRED), CoverArtType.DRAG_AND_DROP);
				metadata.setNewCoverArt(coverArt);
				log.info("sertting image to the row: " + selectedRow);
				updateImage(selectedRow);
				metadataWithAlbum.add(metadata);
				getDescriptionTable().getModel().setValueAt(ActionResult.New, selectedRow, ApplicationConstants.STATUS_COLUMN);
				getApplyButton().setEnabled(!working);
				log.info("setting applyButton to : " + !working);
			}
		});
		multiLayerDropTargetListener.addDropListener(imagePanel, listener);
	}

	@EventMethod(Events.USER_LOGGED)
	void onUserLogged(User currentUser) {
		StringBuilder sb = new StringBuilder();
		sb.append(ApplicationConstants.LOGGED_AS);
		sb.append(currentUser.getUsername());
		getLoginLabel().setText(sb.toString());
		getSendButton().setEnabled(true);
	}

	@EventMethod(Events.COVER_ART_FAILED)
	void onCovertArtFailed(String title) {
		StringBuilder sb = new StringBuilder();
		sb.append(title);
		sb.append(ApplicationConstants.CORRUPTED_METADATA_LABEL);
		dialogHelper.showMessageDialog(this, sb.toString());
	}

	@EventMethod(Events.LOAD_FILE_FAILED)
	void onLoadFileFailed(String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(ApplicationConstants.FILE_NOT_FOUND);
		sb.append(fileName);
		dialogHelper.showMessageDialog(this, sb.toString());
	}

	@EventMethod(Events.USER_LOGIN_FAILED)
	void onUserLoginFailed() {
		getLoginLabel().setText(ApplicationConstants.LOGIN_FAIL);
	}

	@EventMethod(Events.MUSIC_DIRECTORY_SELECTED)
	void onMusicDirectorySelected(String path) {
		tableLoaded = false;
		deleteALLRows(descriptionTable);
		metadataWithAlbum.clear();
		getDirectoryField().setText(path);
		getLabel().setText(ApplicationConstants.WORKING);
	}

	@EventMethod(Events.MUSIC_DIRECTORY_NOT_EXIST)
	void onMusicDirectoryNotExist(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append(ApplicationConstants.DIRECTORY_NOT_FOUND);
		sb.append(path);
		dialogHelper.showMessageDialog(this, sb.toString());
		getOpenButton().setEnabled(true);
	}

	@EventMethod(Events.TOO_MUCH_FILES_LOADED)
	void onTooMuchFilesLoaded(String maxFiles) {
		StringBuilder sb = new StringBuilder();
		sb.append(ApplicationConstants.TOO_MUCH_FILES_LOADED);
		sb.append(maxFiles);
		dialogHelper.showMessageDialog(this, sb.toString());
		getOpenButton().setEnabled(true);
	}

	@EventMethod(Events.MUSIC_DIRECTORY_SELECTED_CANCEL)
	void onMusicDirectorySelectedCancel() {
		getOpenButton().setEnabled(true);
		if(tableLoaded){
			getCompleteMetadataButton().setEnabled(true);
			getExportButton().setEnabled(true);
		}
	}

	private void resetStatus() {
		log.info("Trying to reset to the default image");
		getCompleteMetadataButton().setEnabled(true);
		getExportButton().setEnabled(true);
		getOpenButton().setEnabled(true);
		getLabel().setText(ApplicationConstants.DONE);
	}

	@EventMethod(Events.TRACKS_LOADED)
	void onTracksLoaded() {
		Set<File> filesWithoutMinimumMetadata = controlEngineConfigurator.getControlEngine().get(Model.FILES_WITHOUT_MINIMUM_METADATA);
		List<Metadata> metadatas = controlEngineConfigurator.getControlEngine().get(Model.METADATA);
		if (!filesWithoutMinimumMetadata.isEmpty()) {
			File file = new File("Music File");
			Iterator<File> iterator = filesWithoutMinimumMetadata.iterator();
			while (iterator.hasNext()) {
				file = iterator.next();
			}
			if (filesWithoutMinimumMetadata.size() == 1) {
				StringBuilder sb = new StringBuilder();
				sb.append(file.getName());
				sb.append(ApplicationConstants.METADATA_FROM_FILE_LABEL);
				dialogHelper.showMessageDialog(this, sb.toString());
			} else {
				int otherFiles = filesWithoutMinimumMetadata.size() - 1;
				StringBuilder sb = new StringBuilder();
				sb.append(file.getName());
				sb.append(ApplicationConstants.AND_ANOTHER);
				sb.append(otherFiles);
				sb.append(ApplicationConstants.METADATA_FROM_FILE_LABEL);
				dialogHelper.showMessageDialog(this, sb.toString());
			}
		}
		resetStatus();
		ListSelectionModel listSelectionModel = getDescriptionTable().getSelectionModel();
		listSelectionModel.setSelectionInterval(0, 0);
		selectedRow = 0;
		updateImage(selectedRow);
		tableLoaded = true;
	}

	@EventMethod(Events.DIRECTORY_EMPTY_EVENT)
	void onDirectoryEmpty() {
		dialogHelper.showMessageDialog(this, ApplicationConstants.DIRECTORY_EMPTY);
		resetStatus();
	}

	@EventMethod(Events.LOAD_METADATA)
	void onLoadMetadata(List<Metadata> metadatas) {
		JTable descriptionTable = getDescriptionTable();
		DefaultTableModel model = (DefaultTableModel) descriptionTable.getModel();
		for (Metadata metadata : metadatas) {
			int row = descriptionTable.getRowCount();
			model.addRow(new Object[] { "", "", "", "", "", "", "", "" });
			descriptionTable.setValueAt(metadata.getArtist(), row, ApplicationConstants.ARTIST_COLUMN);
			descriptionTable.setValueAt(metadata.getTitle(), row, ApplicationConstants.TITLE_COLUMN);
			descriptionTable.setValueAt(metadata.getAlbum(), row, ApplicationConstants.ALBUM_COLUMN);
			descriptionTable.setValueAt(metadata.getGenre(), row, ApplicationConstants.GENRE_COLUMN);
			descriptionTable.setValueAt(metadata.getYear(), row, ApplicationConstants.YEAR_COLUMN);
			descriptionTable.setValueAt(metadata.getTrackNumber(), row, ApplicationConstants.TRACK_NUMBER_COLUMN);
			descriptionTable.setValueAt(metadata.getTotalTracks(), row, ApplicationConstants.TOTAL_TRACKS_NUMBER_COLUMN);
			descriptionTable.setValueAt(metadata.getCdNumber(), row, ApplicationConstants.CD_NUMBER_COLUMN);
			descriptionTable.setValueAt(metadata.getTotalCds(), row, ApplicationConstants.TOTAL_CDS_NUMBER_COLUMN);
			if(metadata.isMetadataFromFile()){
				descriptionTable.setValueAt(ApplicationConstants.NEW , row, ApplicationConstants.STATUS_COLUMN);
				metadataWithAlbum.add(metadata);
				getApplyButton().setEnabled(true);
			} else {
				descriptionTable.setValueAt(ApplicationConstants.READY, row, ApplicationConstants.STATUS_COLUMN);
			}
		}
	}

	@EventMethod(Events.APPLY_METADATA)
	void onReadyToApplyMetadata(MetadataAlbumValues metadataValues) {
		String artist = metadataValues.getArtist();
		String album = metadataValues.getAlbum();
		String genre = metadataValues.getGenre();
		String year = metadataValues.getYear();
		String tracks = metadataValues.getTracks();
		String cd = metadataValues.getCd();
		String cds = metadataValues.getCds();
		DefaultTableModel model = (DefaultTableModel) descriptionTable.getModel();
		List<Metadata> metadatas = controlEngineConfigurator.getControlEngine().get(Model.METADATA);
		log.info("Album size: " + metadataWithAlbum.size());
		for (int i = 0; i < model.getRowCount(); i++) {
			Metadata metadata = metadatas.get(i);
			if (!StringUtils.isEmpty(artist)) {
				getDescriptionTable().getModel().setValueAt(artist, i, ApplicationConstants.ARTIST_COLUMN);
			}
			if (!StringUtils.isEmpty(album)) {
				getDescriptionTable().getModel().setValueAt(album, i, ApplicationConstants.ALBUM_COLUMN);
			}
			if (!StringUtils.isEmpty(genre)) {
				getDescriptionTable().getModel().setValueAt(genre, i, ApplicationConstants.GENRE_COLUMN);
			}
			if (!StringUtils.isEmpty(year)) {
				getDescriptionTable().getModel().setValueAt(year, i, ApplicationConstants.YEAR_COLUMN);
			}
			if (!StringUtils.isEmpty(tracks)) {
				getDescriptionTable().getModel().setValueAt(tracks, i, ApplicationConstants.TOTAL_TRACKS_NUMBER_COLUMN);
			}
			if (!StringUtils.isEmpty(cd)) {
				getDescriptionTable().getModel().setValueAt(cd, i, ApplicationConstants.CD_NUMBER_COLUMN);
			}
			if (!StringUtils.isEmpty(cds)) {
				getDescriptionTable().getModel().setValueAt(cds, i, ApplicationConstants.TOTAL_CDS_NUMBER_COLUMN);
			}
			if (metadataValues.getCoverArt() != null) {
				log.info("coverArt detected for: " + metadata.getTitle());
				CoverArt coverArt = new CoverArt(metadataValues.getCoverArt(), CoverArtType.DRAG_AND_DROP);
				metadata.setNewCoverArt(coverArt);
				metadataWithAlbum.add(metadata);
				getDescriptionTable().getModel().setValueAt(ActionResult.New, i, ApplicationConstants.STATUS_COLUMN);
				if (i == selectedRow) {
					updateImage(i);
				}
				getApplyButton().setEnabled(!working);
				log.info("setting applyButton to : " + !working);
			}
		}
	}

	private void deleteALLRows(JTable descriptionTable) {
		DefaultTableModel model = (DefaultTableModel) descriptionTable.getModel();
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	@EventMethod(Events.OPEN_ERROR)
	void onOpenError() {
		getLabel().setText(ApplicationConstants.OPEN_ERROR);
	}

	private JMenuBar getMenubar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getLastFmMenu());
		}
		return menuBar;
	}

	private JMenu getLastFmMenu() {
		if (mainMenu == null) {
			mainMenu = new JMenu(JMENU_LABEL);
			mainMenu.setName(MAIN_MENU_NAME);
			mainMenu.setMnemonic(KeyEvent.VK_F);
			mainMenu.add(getLastFmMenuItem());
			mainMenu.add(getExitMenuItem());
		}
		return mainMenu;
	}

	private JMenuItem getLastFmMenuItem() {
		if (lastFmMenuItem == null) {
			lastFmMenuItem = new JMenuItem(JMENU_ITEM_LABEL);
			lastFmMenuItem.setName(LOGIN_MENU_ITEM);
			lastFmMenuItem.setMnemonic(KeyEvent.VK_S);

			lastFmMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					loginWindow.getFrame().setLocationRelativeTo(MainWindow.this);
					loginWindow.getFrame().setVisible(true);
				}
			});
		}
		return lastFmMenuItem;
	}

	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem(JMENU_EXIT_LABEL);
			exitMenuItem.setName(EXIT_MENU_ITEM);
			exitMenuItem.setMnemonic(KeyEvent.VK_E);

			exitMenuItem.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	private JPanel getBottomPanel() {
		if (bottomPanel == null) {
			bottomPanel = new JPanel();
			bottomPanel.setLayout(null);
			bottomPanel.setBounds(BOTTOM_PANEL_BOUNDS);
			bottomPanel.add(getLabel());
			bottomPanel.add(getDirectoryField());
			bottomPanel.add(getProgressBar());
			bottomPanel.add(getOpenButton());
			bottomPanel.add(getSendButton());
			bottomPanel.add(getCompleteMetadataButton());
			bottomPanel.add(getApplyButton());
			bottomPanel.add(getExportButton());
		}
		return bottomPanel;
	}

	private void registerKeyStrokeAction() {
		KeyStroke ctrlo = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		inputMap = getOpenButton().getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(ctrlo, CTRL_O);
		inputMap.put(enter, ENTER);
		getOpenButton().getActionMap().put(CTRL_O, new ClickAction(getOpenButton()));
		getOpenButton().getActionMap().put(ENTER, new ClickAction(getOpenButton()));
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getTopPanel());
			panel.add(getMiddlePanel());
			panel.add(getBottomPanel());
		}
		return panel;
	}

	private JPanel getMiddlePanel() {
		if (middlePanel == null) {
			middlePanel = new JPanel();
			middlePanel.setLayout(null);
			middlePanel.setBounds(MIDDLE_PANEL_BOUNDS);
			middlePanel.add(getImageLabel());
			middlePanel.add(getImagePanel());
			middlePanel.add(getScrollPane());
		}
		return middlePanel;
	}

	private JPanel getImagePanel() {
		if (imagePanel == null) {
			imagePanel = new JPanel();
			imagePanel.setName(IMAGE_PANEL_NAME);
			imagePanel.setBounds(IMAGE_PANEL_BOUNDS);
		}
		return imagePanel;
	}

	private JLabel getImageLabel() {
		if (imageLabel == null) {
			imageLabel = new JLabel();
			imageLabel.setName(IMAGE_LABEL_NAME);
			imageLabel.setBounds(IMAGE_LABEL_BOUNDS);
		}
		return imageLabel;
	}

	private JPanel getTopPanel() {
		if (topPanel == null) {
			topPanel = new JPanel();
			topPanel.setLayout(null);
			topPanel.setBounds(TOP_PANEL_BOUNDS);
			topPanel.add(getLoginLabel());
		}
		return topPanel;
	}

	private JTextField getDirectoryField() {
		if (directorySelected == null) {
			directorySelected = new JTextField(DIRECTORY_SELECTED_LENGHT);
			directorySelected.setName(TEXTFIELD_NAME);
			directorySelected.setBounds(DIRECTORY_BOUNDS);
			directorySelected.setEnabled(false);
		}
		return directorySelected;
	}

	private JSlider getProgressBar() {
		if (progressBar == null) {
			progressBar = new JSlider();
			progressBar.setName(PROGRESS_BAR_NAME);
			progressBar.setBounds(PROGRESS_BAR_BOUNDS);
			progressBar.setMaximum(PROGRESS_BAR_MAXIMUM_VALUE);
			progressBar.setRequestFocusEnabled(false);
			progressBar.setValue(PROGRESS_BAR_DEFAULT_VALUE);
			progressBar.setPaintLabels(false);
			progressBar.setOpaque(false);
			progressBar.setFocusable(false);
			progressBar.setEnabled(false);
			progressBar.setVisible(true);
		}
		return progressBar;
	}

	public JLabel getLabel() {
		if (label == null) {
			label = new JLabel(STATUS_LABEL);
			label.setName(STATUS_LABEL_NAME);
			label.setBounds(LABEL_BOUNDS);
		}
		return label;
	}

	private JLabel getLoginLabel() {
		if (loginLabel == null) {
			loginLabel = new JLabel(LOG_OUT);
			loginLabel.setName(LOGIN_LABEL_NAME);
			loginLabel.setBounds(LOGIN_LABEL_BOUNDS);
		}
		return loginLabel;
	}

	public JButton getCompleteMetadataButton() {
		if (completeMetadataButton == null) {
			completeMetadataButton = new JButton(ActionResult.Complete.toString());
			completeMetadataButton.setName(COMPLETE_BUTTON_NAME);
			completeMetadataButton.setEnabled(false);
			completeMetadataButton.setBounds(COMPLETE_BUTTON_BOUNDS);

			completeMetadataButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					resetStatus();
					new CompleteWorker();
				}

				private void resetStatus() {
					metadataWithAlbum.clear();
					for (int i = 0; i < getDescriptionTable().getModel().getRowCount(); i++) {
						getDescriptionTable().getModel().setValueAt(ApplicationConstants.READY, i, ApplicationConstants.STATUS_COLUMN);
					}
				}
			});
		}
		return completeMetadataButton;
	}

	public JButton getApplyButton() {
		if (applyButton == null) {
			applyButton = new JButton(ApplicationConstants.APPLY);
			applyButton.setName(APPLY_BUTTON_NAME);
			applyButton.setEnabled(false);
			applyButton.setBounds(APPLY_BUTTON_BOUNDS);

			applyButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new WriteWorker();
				}

			});
		}
		return applyButton;
	}

	public JButton getExportButton() {
		if (exportButton == null) {
			exportButton = new JButton(ApplicationConstants.EXPORT);
			exportButton.setName(EXPORT_BUTTON_NAME);
			exportButton.setEnabled(false);
			exportButton.setBounds(EXPORT_BUTTON_BOUNDS);

			exportButton.addActionListener(new ActionListener() {


				public void actionPerformed(ActionEvent e) {
					log.info("Exporting");
					File root = fileChooserHelper.getDirectory();
					if (root != null) {
						new ExportWorker(root);
					}
				}

			});
		}
		return exportButton;
	}

	public JButton getSendButton() {
		if (sendButton == null) {
			sendButton = new JButton(SEND_SCROBBLINGS);
			sendButton.setName(SEND_BUTTON_NAME);
			sendButton.setEnabled(false);
			sendButton.setBounds(SEND_BUTTON_BOUNDS);

			sendButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					new SendWorker();
				}
			});
		}
		return sendButton;
	}

	public JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton(LOAD_FILES);
			openButton.setName(OPEN_BUTTON_NAME);
			openButton.setBounds(OPEN_BUTTON_BOUNDS);

			openButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					openButton.setEnabled(false);
					getCompleteMetadataButton().setEnabled(false);
					getExportButton().setEnabled(false);
					viewEngineConfigurator.getViewEngine().send(Actions.METADATA);
				}
			});
		}
		return openButton;
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(getDescriptionTable());
			scrollPane.setBounds(SCROLL_PANE_BOUNDS);
		}
		return scrollPane;
	}

	public JTable getDescriptionTable() {
		if (descriptionTable == null) {
			descriptionTable = new DescriptionTable();
			descriptionTable.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						MetadataDialog metadataDialog = new MetadataDialog(MainWindow.this, controlEngineConfigurator, "Alter ALL rows");
						metadataDialog.setVisible(true);
					}
				}
			});

			descriptionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {
					if (tableLoaded) {
						selectedRow = descriptionTable.getSelectedRow();
						log.info("SETTING SELECTED ROW to : " + selectedRow);
						updateImage(selectedRow);
					}
				}

			});

			descriptionTable.getModel().addTableModelListener(new TableModelListener() {

				public void tableChanged(TableModelEvent e) {
					if (e.getType() == TableModelEvent.UPDATE && tableLoaded && e.getColumn() != ApplicationConstants.STATUS_COLUMN) {
						int column = e.getColumn();
						int row = e.getFirstRow();
						String value = getDescriptionTable().getModel().getValueAt(row, column).toString();
						log.info("value changed: " + value);
						List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);
						Metadata metadata = metadataList.get(row);
						metadataAdapter.update(metadata, column, value);
						metadataWithAlbum.add(metadata);

						getDescriptionTable().getModel().setValueAt(ActionResult.New, row, ApplicationConstants.STATUS_COLUMN);

						controlEngineConfigurator.getControlEngine().set(Model.METADATA_ARTIST, metadataWithAlbum, null);
						getApplyButton().setEnabled(!working);
						log.info("setting applyButton to : " + !working);
					}
				}
			});

		}

		return descriptionTable;
	}

	private void updateImage(int selectedRow) {
		TableModel model = getDescriptionTable().getModel();
		List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);

		Metadata metadata = metadataList.get(selectedRow);
		log.info("metadata: " + ToStringBuilder.reflectionToString(metadata));

		imagePanel.removeAll();
		if (metadata.getNewCoverArt() != null) {
			CoverArt coverArt = metadata.getNewCoverArt();
			log.info("setting coverArt to: " + coverArt.getType());
			ImageIcon imageIcon = new ImageIcon(coverArt.getImage());
			imagePanel.add(new JLabel(imageIcon));
			String label = ApplicationConstants.COVER_ART_FROM_LASTFM;
			if (coverArt.getType().equals(CoverArtType.DRAG_AND_DROP)) {
				label = ApplicationConstants.COVER_ART_FROM_DRAG_AND_DROP;
			}
			imageLabel.setText(label);
		} else if (metadata.getCoverArt() != null) {
			if(imageUtils.is300Image(metadata.getCoverArt())){
				imagePanel.add(new JLabel(new ImageIcon(metadata.getCoverArt())));
			} else {
				Image resize = imageUtils.resize(metadata.getCoverArt(), ApplicationConstants.THREE_HUNDRED, ApplicationConstants.THREE_HUNDRED);
				imagePanel.add(new JLabel(new ImageIcon(resize)));
			}
			imageLabel.setText(ApplicationConstants.COVER_ART_FROM_FILE);
		} else {
			imageLabel.setText(ApplicationConstants.COVER_ART_DEFAULT);
		}
		imagePanel.invalidate();
		imagePanel.revalidate();
		imagePanel.repaint();
	}

	private class WriteWorker {

		public WriteWorker() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				protected Boolean doInBackground() throws Exception {
					getLabel().setText(ApplicationConstants.WRITING_METADATA);
					counter = 0;
					working = true;
					log.info("Starting to write...");
					getApplyButton().setEnabled(!working);
					getExportButton().setEnabled(!working);
					log.info("setting applyButton to : " + !working);
					log.info("Ready for write " + metadataWithAlbum.size() + " files");
					for (final Metadata metadata : metadataWithAlbum) {
						viewEngineConfigurator.getViewEngine().request(Actions.WRITE, metadata, new ResponseCallback<ActionResult>() {

							public void onResponse(ActionResult result) {
								log.info("Writing metadata to " + metadata.getTitle() + " w/result: " + result);
								updateStatus(counter++, metadataWithAlbum.size());
								getDescriptionTable().getModel().setValueAt(result, getRow(metadata), ApplicationConstants.STATUS_COLUMN);
								if (metadata.getCoverArt() != null && selectedRow == getRow(metadata)) {
									log.info("setting image to row: " + selectedRow);
									updateImage(selectedRow);
								}
								if (counter >= metadataWithAlbum.size()) {
									resetButtonsState();
									finishingWorker();
								}
							}

							private void finishingWorker() {
								log.info("I'm done, ALL rows have been written");
								working = false;
								getApplyButton().setEnabled(working);
								getExportButton().setEnabled(true);
								log.info("setting applyButton to : " + working);
								metadataWithAlbum.clear();
							}
						});
					}
					return true;
				}

			};
			MainWindow.this.getCompleteMetadataButton().setEnabled(false);
			getOpenButton().setEnabled(false);
			swingWorker.execute();
		}

	}

	private class CompleteWorker {

		public CompleteWorker() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				protected Boolean doInBackground() throws Exception {
					final List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);
					getLabel().setText(ApplicationConstants.GETTING_ALBUM);
					counter = 0;
					working = true;
					log.info("Start to work...");
					getApplyButton().setEnabled(!working);
					getExportButton().setEnabled(!working);
					log.info("setting applyButton to : " + !working);
					for (final Metadata metadata : metadataList) {
						final int i = metadataList.indexOf(metadata);
						MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.COMPLETE_MUSICBRAINZ, metadataList, new ResponseCallback<ActionResult>() {

							public void onResponse(ActionResult response) {
								updateStatus(counter++, metadataList.size());
								log.info("response in getting from MusicBrainz album " + metadata.getTitle() + ": " + response);
								if (response.equals(ActionResult.New)) {
									metadataWithAlbum.add(metadata);
									getDescriptionTable().getModel().setValueAt(metadata.getAlbum(), i, ApplicationConstants.ALBUM_COLUMN);
									getDescriptionTable().getModel().setValueAt(metadata.getTrackNumber(), i, ApplicationConstants.TRACK_NUMBER_COLUMN);
									getDescriptionTable().getModel().setValueAt(metadata.getTotalTracks(), i, ApplicationConstants.TOTAL_TRACKS_NUMBER_COLUMN);
									getDescriptionTable().getModel().setValueAt(metadata.getCdNumber(), i, ApplicationConstants.CD_NUMBER_COLUMN);
									getDescriptionTable().getModel().setValueAt(metadata.getTotalCds(), i, ApplicationConstants.TOTAL_CDS_NUMBER_COLUMN);
								}
								getDescriptionTable().getModel().setValueAt(response, i, ApplicationConstants.STATUS_COLUMN);
								if (counter >= metadataList.size()) {
									getFormatterData();
								}
							}

							private void getFormatterData() {
								getLabel().setText(ApplicationConstants.GETTING_FORMATTER);
								counter = 0;
								log.info("Formating for " + metadataList.size() + " files");
								for (final Metadata metadata : metadataList) {
									final int i = metadataList.indexOf(metadata);
									MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.COMPLETE_FORMATTER, metadata, new ResponseCallback<ActionResult>() {

										@Override
										public void onResponse(ActionResult response) {
											updateStatus(counter++, metadataList.size());
											log.info("response in getFormatterData for " + metadata.getTitle() + ": " + response);
											if (response.equals(ActionResult.New)) {
												metadataWithAlbum.add(metadata);
												getDescriptionTable().getModel().setValueAt(metadata.getArtist(), i, ApplicationConstants.ARTIST_COLUMN);
												getDescriptionTable().getModel().setValueAt(metadata.getTitle(), i, ApplicationConstants.TITLE_COLUMN);
												getDescriptionTable().getModel().setValueAt(metadata.getAlbum(), i, ApplicationConstants.ALBUM_COLUMN);
											} else if (!getDescriptionTable().getModel().getValueAt(i, ApplicationConstants.STATUS_COLUMN).equals(ActionResult.New)) {
												getDescriptionTable().getModel().setValueAt(response, i, ApplicationConstants.STATUS_COLUMN);
											}
											if (counter >= metadataList.size()) {
												getLastfmData();
											}
										}

									});
								}
							}

							private void completeValuesForAlbum() {
								getLabel().setText(ApplicationConstants.COMPLETE_DEFAULT_VALUES);
								counter = 0;
								log.info("Completing album for " + metadataList.size() + " files");
								MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.COMPLETE_DEFAULT, metadataList, new ResponseCallback<ActionResult>() {

									@Override
									public void onResponse(ActionResult response) {
										log.info("response in completeValuesForAlbum for " + metadata.getTitle() + ": " + response);
										if (response.equals(ActionResult.New)) {
											for (Metadata metadata : metadataList) {
												int i = metadataList.indexOf(metadata);
												updateStatus(counter++, metadataList.size());
												getDescriptionTable().getModel().setValueAt(metadata.getTotalTracks(), i, ApplicationConstants.TOTAL_TRACKS_NUMBER_COLUMN);
												getDescriptionTable().getModel().setValueAt(metadata.getCdNumber(), i, ApplicationConstants.CD_NUMBER_COLUMN);
												getDescriptionTable().getModel().setValueAt(metadata.getTotalCds(), i, ApplicationConstants.TOTAL_CDS_NUMBER_COLUMN);
											}
										}else if (!getDescriptionTable().getModel().getValueAt(i, ApplicationConstants.STATUS_COLUMN).equals(ActionResult.New)) {
											getDescriptionTable().getModel().setValueAt(response, i, ApplicationConstants.STATUS_COLUMN);
										}
										afterComplete(metadataWithAlbum);
									}

								});
							}

							private void getLastfmData() {
								getLabel().setText(ApplicationConstants.GETTING_LAST_FM);
								counter = 0;
								log.info("Searching in lastfm for " + metadataList.size() + " files");
								for (final Metadata metadata : metadataList) {
									final int i = metadataList.indexOf(metadata);
									MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.COMPLETE_LAST_FM, metadata, new ResponseCallback<ActionResult>() {

										public void onResponse(ActionResult response) {
											updateStatus(counter++, metadataList.size());
											log.info("response in getLastfmData for " + metadata.getTitle() + ": " + response);
											if (response.equals(ActionResult.New)) {
												metadataWithAlbum.add(metadata);
												getDescriptionTable().getModel().setValueAt(metadata.getArtist(), i, ApplicationConstants.ARTIST_COLUMN);
												getDescriptionTable().getModel().setValueAt(metadata.getTitle(), i, ApplicationConstants.TITLE_COLUMN);
												getDescriptionTable().getModel().setValueAt(metadata.getAlbum(), i, ApplicationConstants.ALBUM_COLUMN);
												getDescriptionTable().getModel().setValueAt(metadata.getYear(), i, ApplicationConstants.YEAR_COLUMN);
												getDescriptionTable().getModel().setValueAt(metadata.getGenre(), i, ApplicationConstants.GENRE_COLUMN);
												getDescriptionTable().getModel().setValueAt(ActionResult.New, i, ApplicationConstants.STATUS_COLUMN);
												if (metadata.getNewCoverArt() != null && i == selectedRow) {
													updateImage(i);
												}
											} else if (!getDescriptionTable().getModel().getValueAt(i, ApplicationConstants.STATUS_COLUMN).equals(ActionResult.New)) {
												getDescriptionTable().getModel().setValueAt(response, i, ApplicationConstants.STATUS_COLUMN);
											}
											if (counter >= metadataList.size()) {
												completeValuesForAlbum();
											}
										}
									});
								}

							}
						});
					}
					return true;
				}
			};

			MainWindow.this.getCompleteMetadataButton().setEnabled(false);
			getOpenButton().setEnabled(false);
			swingWorker.execute();
		}
	}

	private void afterComplete(Set<Metadata> metadataWithOutArtist) {
		if (!metadataWithOutArtist.isEmpty()) {
			controlEngineConfigurator.getControlEngine().set(Model.METADATA_ARTIST, metadataWithOutArtist, null);
			getApplyButton().setEnabled(true);
			log.info("setting applyButton to : true");
		}
		resetButtonsState();
		working = false;
		log.info("setting working to : " + false);
		log.info("I already worked");
	}

	private void resetButtonsState() {
		getLabel().setText(ApplicationConstants.DONE);
		getCompleteMetadataButton().setEnabled(true);
		getOpenButton().setEnabled(true);
		getExportButton().setEnabled(true);
		getDescriptionTable().setEnabled(true);
	}

	private void updateStatus(final int i, int size) {
		int progress = ((i + 1) * 100) / size;
		getProgressBar().setValue(progress);
	};

	private class SendWorker {
		public SendWorker() {
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				protected Boolean doInBackground() throws Exception {
					final List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);
					counter = 0;
					for (final Metadata metadata : metadataList) {
						MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.SEND, metadata, new ResponseCallback<ActionResult>() {

							public void onResponse(ActionResult response) {
								log.info("response on sending " + metadata.getTitle() + ": " + response);
								updateStatus(counter++, metadataList.size());
								getDescriptionTable().getModel().setValueAt(response, getRow(metadata), ApplicationConstants.STATUS_COLUMN);
							}

						});
					}
					return true;
				}

				public void done() {
					getLabel().setText(ApplicationConstants.DONE);
					getCompleteMetadataButton().setEnabled(true);
					getSendButton().setEnabled(true);
					getOpenButton().setEnabled(true);
				}
			};
			swingWorker.execute();
			getCompleteMetadataButton().setEnabled(false);
			getSendButton().setEnabled(false);
			getOpenButton().setEnabled(false);
			getProgressBar().setVisible(true);
			getLabel().setText(ApplicationConstants.WORKING);
		}
	}

	private class ExportWorker {
		private final List<Metadata> metadataList;
		private final ExportPackage exportPackage;

		public ExportWorker(File root) {
			metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);
			exportPackage = new ExportPackage(root, metadataList);
			work();
		}

		private void work() {
			SwingWorker<Boolean, Integer> swingWorker = new SwingWorker<Boolean, Integer>() {

				protected Boolean doInBackground() throws Exception {
					MainWindow.this.viewEngineConfigurator.getViewEngine().request(Actions.EXPORT, exportPackage, new ResponseCallback<ActionResult>() {

						public void onResponse(ActionResult response) {
							for (Metadata metadata : metadataList) {
								getDescriptionTable().getModel().setValueAt(response, getRow(metadata), ApplicationConstants.STATUS_COLUMN);
							}
						}

					});
					return true;
				}
			};
			swingWorker.execute();
		}
	}

	private int getRow(Metadata metadataTarget) {
		TableModel model = getDescriptionTable().getModel();
		List<Metadata> metadataList = viewEngineConfigurator.getViewEngine().get(Model.METADATA);

		for (int i = 0; i < model.getRowCount(); i++) {
			String artist = (String) model.getValueAt(i, 0);
			String title = (String) model.getValueAt(i, 1);
			if (artist.equals(metadataTarget.getArtist()) && title.equals(metadataTarget.getTitle())) {
				return i;
			}
		}

		return 0;
	}

	private class ClickAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private JButton button;

		public ClickAction(JButton button) {
			this.button = button;
		}

		public void actionPerformed(ActionEvent e) {
			button.doClick();
		}
	}

	private class DescriptionTableModelListener implements TableModelListener {

		public void tableChanged(TableModelEvent e) {
			if (MainWindow.this.getCompleteMetadataButton().getText().equals(ApplicationConstants.APPLY) && e.getColumn() != ApplicationConstants.STATUS_COLUMN) {
				int lastRow = e.getLastRow();
				DefaultTableModel model = (DefaultTableModel) e.getSource();
				String artist = (String) model.getValueAt(lastRow, 0);
				String trackName = (String) model.getValueAt(lastRow, 1);
				String album = (String) model.getValueAt(lastRow, 2);
			}
		}
	}

}
