/*
   Copyright 2025 Jose Morales contact@josdem.io

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

import com.josdem.jmetadata.collaborator.MetadataCollaborator;
import com.josdem.jmetadata.dnd.ImageDropListener;
import com.josdem.jmetadata.dnd.MainFrameDragOverListener;
import com.josdem.jmetadata.dnd.MultiLayerDropTargetListener;
import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.helper.MetadataHelper;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.MetadataAlbumValues;
import com.josdem.jmetadata.model.Model;
import com.josdem.jmetadata.observer.ObservValue;
import com.josdem.jmetadata.observer.Observer;
import com.josdem.jmetadata.util.ImageUtils;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.asmatron.messengine.engines.support.ControlEngineConfigurator;
import org.asmatron.messengine.event.ValueEvent;

public class MetadataDialog extends AllDialog {
  private static final long serialVersionUID = 4326045585716235724L;
  private static final int ONE_HUNDRED_FIFTY = 150;
  private static final int THREE_HUNDRED = 300;
  private static final Rectangle CONTENT_PANEL_BOUNDS = new Rectangle(0, 0, 388, 357);
  private static final Rectangle IMAGE_BOUNDS = new Rectangle(123, 10, 150, 150);
  private static final Rectangle ARTIST_TEXTFIELD_BOUNDS = new Rectangle(123, 170, 200, 22);
  private static final Rectangle ALBUM_TEXTFIELD_BOUNDS = new Rectangle(123, 190, 200, 22);
  private static final Rectangle GENRE_TEXTFIELD_BOUNDS = new Rectangle(123, 210, 200, 22);
  private static final Rectangle YEAR_TEXTFIELD_BOUNDS = new Rectangle(123, 230, 120, 22);
  private static final Rectangle TRACKS_TEXTFIELD_BOUNDS = new Rectangle(123, 250, 120, 22);
  private static final Rectangle CD_TEXTFIELD_BOUNDS = new Rectangle(123, 270, 120, 22);
  private static final Rectangle CDS_TEXTFIELD_BOUNDS = new Rectangle(123, 290, 120, 22);
  private static final Rectangle IMAGE_LABEL_BOUNDS = new Rectangle(24, 10, 226, 18);
  private static final Rectangle ARTIST_LABEL_BOUNDS = new Rectangle(24, 170, 226, 18);
  private static final Rectangle ALBUM_LABEL_BOUNDS = new Rectangle(24, 190, 226, 18);
  private static final Rectangle GENRE_LABEL_BOUNDS = new Rectangle(24, 210, 226, 18);
  private static final Rectangle YEAR_LABEL_BOUNDS = new Rectangle(24, 230, 226, 18);
  private static final Rectangle TRACKS_LABEL_BOUNDS = new Rectangle(24, 250, 226, 18);
  private static final Rectangle CD_LABEL_BOUNDS = new Rectangle(24, 270, 226, 18);
  private static final Rectangle CDS_LABEL_BOUNDS = new Rectangle(24, 290, 226, 18);
  private static final Rectangle SEND_BUTTON_BOUNDS = new Rectangle(200, 330, 80, 22);
  private static final Rectangle CANCEL_BUTTON_BOUNDS = new Rectangle(109, 330, 80, 22);

  private static final String ARTIST_TEXT_FIELD = "artistTextField";
  private static final String ALBUM_TEXT_FIELD = "albumTextField";
  private static final String GENRE_TEXT_FIELD = "genreTextField";
  private static final String YEAR_TEXT_FIELD = "yearTextField";
  private static final String TRACKS_TEXT_FIELD = "tracksTextField";
  private static final String CD_TEXT_FIELD = "cdTextField";
  private static final String CDS_TEXT_FIELD = "cdsTextField";

  private static final String IMAGE_LABEL = "imageLabel";
  private static final String IMAGE_NAME = "smallDnDImagePanel";
  private static final String ARTIST_LABEL = "artistLabel";
  private static final String ALBUM_LABEL = "albumLabel";
  private static final String GENRE_LABEL = "genreLabel";
  private static final String YEAR_LABEL = "yearLabel";
  private static final String TRACKS_LABEL = "tracksLabel";
  private static final String CD_LABEL = "cdLabel";
  private static final String CDS_LABEL = "cdsLabel";
  private static final String BUTTON_NAME = "buttonCancel";
  private static final String APPLY_BUTTON_NAME = "buttonApply";
  private static final String APPLY = "Apply";
  private static final String IMAGE = "Cover Art";
  private static final String ARTIST = "Artist";
  private static final String ALBUM = "Album";
  private static final String GENRE = "Genre";
  private static final String YEAR = "Year";
  private static final String TRACKS = "#Trks";
  private static final String CANCEL = "Cancel";
  private static final String CD = "#CD";
  private static final String CDS = "#CDs";
  private JPanel contentPanel;
  private JTextField artistTextField;
  private JTextField albumTextField;
  private JTextField genreTextField;
  private JTextField yearTextField;
  private JTextField tracksTextField;
  private JTextField cdTextField;
  private JTextField cdsTextField;
  private JButton applyButton;
  private JButton cancelButton;
  private final String message;
  private JLabel imageLabel;
  private JLabel artistLabel;
  private JLabel albumLabel;
  private JLabel genreLabel;
  private JLabel yearLabel;
  private JLabel tracksLabel;
  private JLabel cdLabel;
  private JLabel cdsLabel;
  private JPanel imagePanel;
  private Image coverArt;
  private ImageUtils imageUtils = new ImageUtils();
  private MetadataHelper metadataHelper = new MetadataHelper();
  private MetadataCollaborator metadataCollaborator = new MetadataCollaborator();
  private final ControlEngineConfigurator configurator;

  public MetadataDialog(
      JFrame frame, ControlEngineConfigurator controlEngineConfigurator, String message) {
    super(frame, true, message);
    this.configurator = controlEngineConfigurator;
    this.message = message;
    List<Metadata> metadatas = controlEngineConfigurator.getControlEngine().get(Model.METADATA);
    metadataCollaborator.setMetadatas(metadatas);
    initializeContentPane();
    getTitleLabel().setText(dialogTitle());
    MultiLayerDropTargetListener multiLayerDropTargetListener = new MultiLayerDropTargetListener();
    setDragAndDrop(multiLayerDropTargetListener);
  }

  private void setDragAndDrop(MultiLayerDropTargetListener multiLayerDropTargetListener) {
    setDropTarget(new DropTarget(this, multiLayerDropTargetListener));
    multiLayerDropTargetListener.addDragListener(this, new MainFrameDragOverListener(this));
    ImageDropListener listener = new ImageDropListener(new ImagePanel());
    listener
        .onDropped()
        .add(
            new Observer<ObservValue<ImagePanel>>() {

              public void observe(ObservValue<ImagePanel> t) {
                ImagePanel value = t.getValue();
                Image image = value.getImage();
                coverArt = imageUtils.resize(image, THREE_HUNDRED, THREE_HUNDRED);
                JLabel imageLabel =
                    new JLabel(
                        new ImageIcon(
                            imageUtils.resize(image, ONE_HUNDRED_FIFTY, ONE_HUNDRED_FIFTY)));
                getImagePanel().removeAll();
                getImagePanel().add(imageLabel);
                MetadataDialog.this.invalidate();
                MetadataDialog.this.validate();
              }
            });
    multiLayerDropTargetListener.addDropListener(imagePanel, listener);
  }

  String dialogTitle() {
    return message;
  }

  private JPanel getContentPanel() {
    if (contentPanel == null) {
      contentPanel = new JPanel();
      contentPanel.setLayout(null);
      contentPanel.setBounds(CONTENT_PANEL_BOUNDS);
      contentPanel.add(getImageLabel());
      contentPanel.add(getImagePanel());
      contentPanel.add(getArtistLabel());
      contentPanel.add(getArtistTextField());
      contentPanel.add(getAlbumLabel());
      contentPanel.add(getAlbumTextField());
      contentPanel.add(getGenreLabel());
      contentPanel.add(getGenreTextField());
      contentPanel.add(getYearLabel());
      contentPanel.add(getYearTextField());
      contentPanel.add(getTracksLabel());
      contentPanel.add(getTracksTextField());
      contentPanel.add(getCdLabel());
      contentPanel.add(getCdTextField());
      contentPanel.add(getCdsLabel());
      contentPanel.add(getCdsTextField());
      contentPanel.add(getApplyButton());
      contentPanel.add(getCancelButton());
    }
    return contentPanel;
  }

  private JLabel getArtistLabel() {
    if (artistLabel == null) {
      artistLabel = new JLabel();
      artistLabel.setBounds(ARTIST_LABEL_BOUNDS);
      artistLabel.setName(ARTIST_LABEL);
      artistLabel.setText(ARTIST);
      artistLabel.requestFocus();
    }
    return artistLabel;
  }

  private JTextField getArtistTextField() {
    if (artistTextField == null) {
      artistTextField = new JTextField();
      artistTextField.setBounds(ARTIST_TEXTFIELD_BOUNDS);
      artistTextField.setName(ARTIST_TEXT_FIELD);
      artistTextField.setText(metadataCollaborator.getArtist());
    }
    return artistTextField;
  }

  private JLabel getAlbumLabel() {
    if (albumLabel == null) {
      albumLabel = new JLabel();
      albumLabel.setBounds(ALBUM_LABEL_BOUNDS);
      albumLabel.setName(ALBUM_LABEL);
      albumLabel.setText(ALBUM);
      albumLabel.requestFocus();
    }
    return albumLabel;
  }

  private JTextField getAlbumTextField() {
    if (albumTextField == null) {
      albumTextField = new JTextField();
      albumTextField.setBounds(ALBUM_TEXTFIELD_BOUNDS);
      albumTextField.setName(ALBUM_TEXT_FIELD);
      albumTextField.setText(metadataCollaborator.getAlbum());
    }
    return albumTextField;
  }

  private JLabel getYearLabel() {
    if (yearLabel == null) {
      yearLabel = new JLabel();
      yearLabel.setBounds(YEAR_LABEL_BOUNDS);
      yearLabel.setName(YEAR_LABEL);
      yearLabel.setText(YEAR);
      yearLabel.requestFocus();
    }
    return yearLabel;
  }

  private JTextField getYearTextField() {
    if (yearTextField == null) {
      yearTextField = new JTextField();
      yearTextField.setBounds(YEAR_TEXTFIELD_BOUNDS);
      yearTextField.setName(YEAR_TEXT_FIELD);
      yearTextField.setText(metadataCollaborator.getYear());
    }
    return yearTextField;
  }

  private JLabel getImageLabel() {
    if (imageLabel == null) {
      imageLabel = new JLabel();
      imageLabel.setBounds(IMAGE_LABEL_BOUNDS);
      imageLabel.setName(IMAGE_LABEL);
      imageLabel.setText(IMAGE);
      imageLabel.requestFocus();
    }
    return imageLabel;
  }

  private JPanel getImagePanel() {
    if (imagePanel == null) {
      imagePanel = new JPanel();
      imagePanel.setName(IMAGE_NAME);
      imagePanel.setBounds(IMAGE_BOUNDS);
    }
    return imagePanel;
  }

  JComponent getContentComponent() {
    return getContentPanel();
  }

  private JLabel getCdsLabel() {
    if (cdsLabel == null) {
      cdsLabel = new JLabel();
      cdsLabel.setBounds(CDS_LABEL_BOUNDS);
      cdsLabel.setName(CDS_LABEL);
      cdsLabel.setText(CDS);
      cdsLabel.requestFocus();
    }
    return cdsLabel;
  }

  private JTextField getCdsTextField() {
    if (cdsTextField == null) {
      cdsTextField = new JTextField();
      cdsTextField.setBounds(CDS_TEXTFIELD_BOUNDS);
      cdsTextField.setName(CDS_TEXT_FIELD);
      cdsTextField.setText(metadataCollaborator.getTotalCds());
    }
    return cdsTextField;
  }

  private JLabel getCdLabel() {
    if (cdLabel == null) {
      cdLabel = new JLabel();
      cdLabel.setBounds(CD_LABEL_BOUNDS);
      cdLabel.setName(CD_LABEL);
      cdLabel.setText(CD);
      cdLabel.requestFocus();
    }
    return cdLabel;
  }

  private JTextField getCdTextField() {
    if (cdTextField == null) {
      cdTextField = new JTextField();
      cdTextField.setBounds(CD_TEXTFIELD_BOUNDS);
      cdTextField.setName(CD_TEXT_FIELD);
      cdTextField.setText(metadataCollaborator.getCdNumber());
    }
    return cdTextField;
  }

  private JLabel getTracksLabel() {
    if (tracksLabel == null) {
      tracksLabel = new JLabel();
      tracksLabel.setBounds(TRACKS_LABEL_BOUNDS);
      tracksLabel.setName(TRACKS_LABEL);
      tracksLabel.setText(TRACKS);
      tracksLabel.requestFocus();
    }
    return tracksLabel;
  }

  private JTextField getTracksTextField() {
    if (tracksTextField == null) {
      tracksTextField = new JTextField();
      tracksTextField.setBounds(TRACKS_TEXTFIELD_BOUNDS);
      tracksTextField.setName(TRACKS_TEXT_FIELD);
      tracksTextField.setText(metadataCollaborator.getTotalTracks());
    }
    return tracksTextField;
  }

  private JLabel getGenreLabel() {
    if (genreLabel == null) {
      genreLabel = new JLabel();
      genreLabel.setBounds(GENRE_LABEL_BOUNDS);
      genreLabel.setName(GENRE_LABEL);
      genreLabel.setText(GENRE);
      genreLabel.requestFocus();
    }
    return genreLabel;
  }

  private JTextField getGenreTextField() {
    if (genreTextField == null) {
      genreTextField = new JTextField();
      genreTextField.setBounds(GENRE_TEXTFIELD_BOUNDS);
      genreTextField.setName(GENRE_TEXT_FIELD);
      genreTextField.setText(metadataCollaborator.getGenre());
      genreTextField.addFocusListener(new FocusListener());
      genreTextField.addKeyListener(new KeyListener());
    }
    return genreTextField;
  }

  private JButton getApplyButton() {
    if (applyButton == null) {
      applyButton = new JButton();
      applyButton.setBounds(SEND_BUTTON_BOUNDS);
      applyButton.setName(APPLY_BUTTON_NAME);
      applyButton.setText(APPLY);
      applyButton.setMnemonic(KeyEvent.VK_A);
      applyButton.setEnabled(true);
      getRootPane().setDefaultButton(applyButton);
      applyButton.addActionListener(
          new ActionListener() {

            public void actionPerformed(ActionEvent e) {
              applyButton.setEnabled(false);
              MetadataAlbumValues metadataValues = metadataHelper.createMetadataAlbumValues();
              metadataValues.setCoverArt(coverArt);
              metadataValues.setArtist(getArtistTextField().getText());
              metadataValues.setAlbum(getAlbumTextField().getText());
              metadataValues.setGenre(getGenreTextField().getText());
              metadataValues.setYear(getYearTextField().getText());
              metadataValues.setTracks(getTracksTextField().getText());
              metadataValues.setCd(getCdTextField().getText());
              metadataValues.setCds(getCdsTextField().getText());
              configurator
                  .getControlEngine()
                  .fireEvent(
                      Events.READY_TO_APPLY, new ValueEvent<MetadataAlbumValues>(metadataValues));
              closeDialog();
            }
          });
    }
    return applyButton;
  }

  private JButton getCancelButton() {
    if (cancelButton == null) {
      cancelButton = new JButton();
      cancelButton.setBounds(CANCEL_BUTTON_BOUNDS);
      cancelButton.setName(BUTTON_NAME);
      cancelButton.setText(CANCEL);
      cancelButton.setMnemonic(KeyEvent.VK_C);
      cancelButton.addActionListener(new CloseListener());
    }
    return cancelButton;
  }

  private final class KeyListener extends KeyAdapter {

    public void keyReleased(KeyEvent e) {
      if (e.getSource().equals(genreTextField)) {
        if (genreTextField.getText().length() > 0) {
          getApplyButton().setEnabled(true);
        }
      }
      if (getApplyButton().isEnabled() && e.getKeyCode() == KeyEvent.VK_ENTER) {
        getApplyButton().doClick();
      }
    }
  }

  private final class FocusListener extends FocusAdapter {

    public void focusLost(FocusEvent e) {
      if (e.getSource().equals(genreTextField)) {
        if (!genreTextField.getText().equals("")) {
          getApplyButton().setEnabled(true);
        }
      }
    }
  }

  public void setMetadataHelper(MetadataHelper metadataHelper) {
    this.metadataHelper = metadataHelper;
  }
}
