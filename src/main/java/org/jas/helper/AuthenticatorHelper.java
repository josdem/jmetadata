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
import org.springframework.stereotype.Component;

@Component
public class AuthenticatorHelper {

    public Session getSession(String username, String password) {
        return Authenticator.getMobileSession(username, password, Auth.KEY, Auth.SECRET);
    }
}
