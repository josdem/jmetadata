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

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @understands
 * This class decorates the dialog borders avoiding code duplication in every
 * dialog</br> </br> <b>Note:</b> Call {@code initializeContentPane()} in the
 * contructor of every subclass but before setting the dialog visible
 */

public abstract class AllDialog extends JDialog {
	private static final long serialVersionUID = -9135593375722062618L;
	private static final Insets TITLE_LABEL_INSETS = new Insets(1, 0, 0, 0);
	private static final Insets EXIT_BUTTON_INSETS = new Insets(2, 0, 0, 0);
	private static final Dimension TITLE_PANEL_DEFAULT_SIZE = new Dimension(560, 24);
	private static final Dimension EXIT_BUTTON_SIZE = new Dimension(15, 15);
	protected static final Insets TITLE_PANEL_INSETS = new Insets(0, 5, 0, 5);
	protected static final Insets CONTENT_PANEL_INSETS = new Insets(0, 5, 4, 5);
	private static final String EXIT_BUTTON_NAME = "closeDialogButton";
	private static final String ROOT_PANE_NAME = "RootPane";
	protected static final int TITLE_HEIGHT = 24;
	protected static final int BORDER = 5;
	private JPanel titlePanel;
	private JLabel titleLabel;
	private JButton exitButton;

	/**
	 * Set the title of the dialog
	 *
	 * @param titleLabel
	 */
	abstract String dialogTitle();

	/**
	 * Obtains the panel with the components for the dialog
	 *
	 * @return JComponent dialog contents
	 */
	abstract JComponent getContentComponent();

	public AllDialog(JFrame frame, boolean modal, String message) {
		super(frame, modal);
		initialize();
	}

	private void initialize() {
		setUndecorated(true);
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		getRootPane().setName(ROOT_PANE_NAME);
	}

	protected void changeSize(int width, int height) {
		Dimension preferredSize = new Dimension(width, height);
		this.setPreferredSize(preferredSize);
		this.setMinimumSize(preferredSize);
		this.setMaximumSize(preferredSize);
		this.setSize(preferredSize);
		this.validate();
	}

	/**
	 * Call this method from the classes which extends this, after initialize
	 * all their components and before setting it visible.
	 */
	protected void initializeContentPane() {
		// setting the layout of the dialog
		GridBagConstraints titlePanelConstraints = new GridBagConstraints();
		titlePanelConstraints.gridx = 0;
		titlePanelConstraints.gridy = 0;
		titlePanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		titlePanelConstraints.weightx = 1;
		titlePanelConstraints.insets = TITLE_PANEL_INSETS;
		GridBagConstraints contentPanelConstraints = new GridBagConstraints();
		contentPanelConstraints.gridx = 0;
		contentPanelConstraints.gridy = 1;
		contentPanelConstraints.fill = GridBagConstraints.BOTH;
		contentPanelConstraints.weightx = 1;
		contentPanelConstraints.weighty = 1;
		contentPanelConstraints.insets = CONTENT_PANEL_INSETS;

		getContentPane().setLayout(new GridBagLayout());
		getContentPane().add(getTitlePanel(), titlePanelConstraints);
		getContentPane().add(getContentComponent(), contentPanelConstraints);

		// setting the size according to the content component
		Dimension contentSize = getContentComponent().getPreferredSize();
		int w = (int) contentSize.getWidth() + BORDER * 2;
		int h = (int) contentSize.getHeight() + BORDER + TITLE_HEIGHT;
		changeSize(w, h);
		setLocationRelativeTo(getParent());
		pack();
	}

	protected JPanel getTitlePanel() {
		if (titlePanel == null) {
			GridBagConstraints titleLabelConstraints = new GridBagConstraints();
			titleLabelConstraints.gridx = 0;
			titleLabelConstraints.gridy = 0;
			titleLabelConstraints.insets = TITLE_LABEL_INSETS;
			titleLabelConstraints.weightx = 1;
			titleLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints exitButtonConstraints = new GridBagConstraints();
			exitButtonConstraints.gridx = 1;
			exitButtonConstraints.gridy = 0;
			exitButtonConstraints.insets = EXIT_BUTTON_INSETS;
			exitButtonConstraints.anchor = GridBagConstraints.CENTER;

			titlePanel = new JPanel();
			titlePanel.setLayout(new GridBagLayout());
			titlePanel.setPreferredSize(TITLE_PANEL_DEFAULT_SIZE);
			titlePanel.setMinimumSize(TITLE_PANEL_DEFAULT_SIZE);
			titlePanel.setMaximumSize(TITLE_PANEL_DEFAULT_SIZE);
			titlePanel.add(getTitleLabel(), titleLabelConstraints);
			titlePanel.add(getExitButton(), exitButtonConstraints);
		}
		return titlePanel;
	}

	protected JLabel getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new JLabel();
		}
		return titleLabel;
	}

	protected JButton getExitButton() {
		if (exitButton == null) {
			exitButton = new JButton();
			exitButton.setName(EXIT_BUTTON_NAME);
			exitButton.setPreferredSize(EXIT_BUTTON_SIZE);
			exitButton.setMinimumSize(EXIT_BUTTON_SIZE);
			exitButton.setMaximumSize(EXIT_BUTTON_SIZE);
			exitButton.setMnemonic(KeyEvent.VK_UNDEFINED);
			exitButton.addActionListener(new CloseListener());
		}
		return exitButton;
	}

	protected void closeDialog() {
		Window window = AllDialog.this;
		window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
	}

	class CloseListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			closeDialog();
		}
	}
}
