package org.jas.helper;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import de.umass.lastfm.Session;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
*/

public class LastFMAuthenticator {
	private AuthenticatorHelper authenticatorHelper = new AuthenticatorHelper();

	public Session login(String username, String password) throws IOException {
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			return null;
		}
		return authenticatorHelper.getSession(username, password);
	}

}
