/*
   Copyright 2024 Jose Morales contact@josdem.io

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

package org.jas.helper;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Session;
import org.jas.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.jas.config.LastFMConfig;

@Component
public class AuthenticatorHelper {

    @Autowired
    private LastFMConfig lastFMConfig;

    public Session getSession(String username, String password) {
        String apiKey = lastFMConfig.getLastFMKey();
        String apiSecret = lastFMConfig.getLastFMSecret();
        if(!apiKey.equals(Auth.KEY) || !apiSecret.equals(Auth.SECRET)){
            throw new IllegalStateException("Environment variable 'LASTFM_API_KEY' or 'LASTFM_API_SECRET' are not valid");
        }
        return Authenticator.getMobileSession(username, password, apiKey, apiSecret);
    }
}
