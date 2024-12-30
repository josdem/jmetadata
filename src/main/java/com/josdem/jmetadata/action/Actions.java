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

package com.josdem.jmetadata.action;

import static org.asmatron.messengine.action.ActionId.cm;

import java.util.List;

import org.asmatron.messengine.action.ActionId;
import org.asmatron.messengine.action.EmptyAction;
import org.asmatron.messengine.action.RequestAction;
import org.asmatron.messengine.action.ValueAction;
import com.josdem.jmetadata.model.ExportPackage;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.User;

/**
 * @author josdem (joseluis.delacruz@gmail.com)
 * @understands A class who has Publisher Subscriber label descriptors
 */

public interface Actions {
	String LOGIN_ID = "login";
	ActionId<ValueAction<User>> LOGIN = cm(LOGIN_ID);

	String GET_METADATA = "getMetadata";
	ActionId<EmptyAction> METADATA = cm(GET_METADATA);

	String SEND_METADATA = "sendMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> SEND = cm(SEND_METADATA);

	String COMPLETE_ALBUM_METADATA = "completeAlbumMetadata";
	ActionId<RequestAction<List<Metadata>, ActionResult>> COMPLETE_MUSICBRAINZ = cm(COMPLETE_ALBUM_METADATA);

	String COMPLETE_LAST_FM_METADATA = "completeLastfmMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> COMPLETE_LAST_FM = cm(COMPLETE_LAST_FM_METADATA);

	String WRITE_METADATA = "writeMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> WRITE = cm(WRITE_METADATA);

	String EXPORT_METADATA = "exportMetadata";
	ActionId<RequestAction<ExportPackage, ActionResult>> EXPORT = cm(EXPORT_METADATA);

	String COMPLETE_FORMATTER_METADATA = "completeFormatterMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> COMPLETE_FORMATTER = cm(COMPLETE_FORMATTER_METADATA);

	String COMPLETE_DEFAULT_METADATA = "completeDefaultMetadata";
	ActionId<RequestAction<List<Metadata>, ActionResult>> COMPLETE_DEFAULT = cm(COMPLETE_DEFAULT_METADATA);
}
