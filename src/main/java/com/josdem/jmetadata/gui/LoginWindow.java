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

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.asmatron.messengine.annotations.EventMethod;
import org.asmatron.messengine.engines.support.ViewEngineConfigurator;
import com.josdem.jmetadata.ApplicationConstants;
import com.josdem.jmetadata.action.Actions;
import com.josdem.jmetadata.event.Events;
import com.josdem.jmetadata.gui.util.SelectedTextForeground;
import com.josdem.jmetadata.gui.util.SynthFonts;
import com.josdem.jmetadata.model.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @understands UI components for logging window
*/

public class LoginWindow {
	private JButton sendButton;
	private JTextField usernameTextfield;
	private JPasswordField passwordTextfield;
	private JFrame frame;
	private JPanel panel;
	private static final String SEND_BUTTON_LABEL = "Login";
	private static final Rectangle FRAME_BOUNDS = new Rectangle(300, 300, 390, 180);
	private static final int TEXT_COLUMNS = 10;
	private JLabel usernameLabel;
	private JLabel passwordLabel;

	private static final String USERNAME_TEXTFIELD_NAME = "loginTextField";
	private static final String PASSWORD_TEXTFIELD_NAME = "passwordLoginField";
	private static final Rectangle USERNAME_LABEL_BOUNDS = new Rectangle(20, 30, 120, 22);
	private static final Rectangle PASSWORD_LABEL_BOUNDS = new Rectangle(20, 70, 120, 22);
	private static final Rectangle USERNAME_TEXTFIELD_BOUNDS = new Rectangle(140, 27, 220, 34);
	private static final Rectangle PASSWORD_TEXTFIELD_BOUNDS = new Rectangle(140, 71, 220, 34);
	private static final String SIGN_UP_BUTTON_NAME = "buttonCenterLogin";
	private static final Rectangle SIGN_UP_BUTTON_BOUNDS = new Rectangle(140, 114, 220, 34);

	private static final Color COLOR_SELECTION_TEXTFIELDS = SelectedTextForeground.SELECTED_FOREGROUND_COLOR;
	private static final Icon BULLET_ICON = UIManager.getDefaults().getIcon("icons.createAccountBullet");
	private static final int REQUIRE_ICON_TEXT_GAP = 2;

	@Autowired
	private ViewEngineConfigurator configurator;

	public LoginWindow() {
		initialize();
	}

	private void initialize(){
		getFrame().add(getPanel());
		getFrame().setVisible(false);

		getSendButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configurator.getViewEngine().sendValueAction(Actions.LOGIN, new User(usernameTextfield.getText(), new String(passwordTextfield.getPassword())));
			}
		});

		getSendButton().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				configurator.getViewEngine().sendValueAction(Actions.LOGIN, new User(usernameTextfield.getText(), new String(passwordTextfield.getPassword())));
			}
		});

		getPasswordTextfield().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ENTER){
					configurator.getViewEngine().sendValueAction(Actions.LOGIN, new User(usernameTextfield.getText(), new String(passwordTextfield.getPassword())));
				}
			}
		});
	}

	@EventMethod(Events.USER_LOGGED)
	public void onUserLogged(){
		this.getFrame().dispose();
	}

	private JPanel getPanel() {
		if(panel == null){
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getUserNameLabel());
			panel.add(getUsernameTextfield());
			panel.add(getPasswordLabel());
			panel.add(getPasswordTextfield());
			panel.add(getSendButton());
		}
		return panel;
	}

	private JLabel getPasswordLabel() {
		if(passwordLabel == null){
			passwordLabel = new JLabel();
			passwordLabel.setText(ApplicationConstants.PASSWORD_LABEL);
			passwordLabel.setBounds(PASSWORD_LABEL_BOUNDS);
			passwordLabel.setName(SynthFonts.PLAIN_FONT16_GRAY100_100_100);
			passwordLabel.setIcon(BULLET_ICON);
			passwordLabel.setIconTextGap(REQUIRE_ICON_TEXT_GAP);
		}
		return passwordLabel;
	}

	private JLabel getUserNameLabel() {
		if(usernameLabel == null){
			usernameLabel = new JLabel();
			usernameLabel.setText(ApplicationConstants.USERNAME_LABEL);
			usernameLabel.setBounds(USERNAME_LABEL_BOUNDS);
			usernameLabel.setName(SynthFonts.PLAIN_FONT16_GRAY100_100_100);
			usernameLabel.setIcon(BULLET_ICON);
			usernameLabel.setIconTextGap(REQUIRE_ICON_TEXT_GAP);
		}
		return usernameLabel;
	}

	private JTextField getUsernameTextfield() {
		if(usernameTextfield == null){
			usernameTextfield = new JTextField(TEXT_COLUMNS);
			usernameTextfield.setBounds(USERNAME_TEXTFIELD_BOUNDS);
			usernameTextfield.setName(USERNAME_TEXTFIELD_NAME);
			usernameTextfield.setSelectionColor(COLOR_SELECTION_TEXTFIELDS);
		}
		return usernameTextfield;
	}

	private JTextField getPasswordTextfield() {
		if(passwordTextfield == null){
			passwordTextfield = new JPasswordField(TEXT_COLUMNS);
			passwordTextfield.setBounds(PASSWORD_TEXTFIELD_BOUNDS);
			passwordTextfield.setName(PASSWORD_TEXTFIELD_NAME);
			passwordTextfield.setSelectionColor(COLOR_SELECTION_TEXTFIELDS);
			passwordTextfield.setEchoChar('\u25CF');
		}
		return passwordTextfield;
	}

	public JFrame getFrame() {
		if(frame == null){
			frame = new JFrame();
			frame.setBounds(FRAME_BOUNDS);
			frame.setResizable(false);
		}
		return frame;
	}

	private JButton getSendButton() {
		if (sendButton == null) {
			sendButton = new JButton(SEND_BUTTON_LABEL);
			sendButton.setName(SIGN_UP_BUTTON_NAME);
			sendButton.setBounds(SIGN_UP_BUTTON_BOUNDS);
		}
		return sendButton;
	}
}
