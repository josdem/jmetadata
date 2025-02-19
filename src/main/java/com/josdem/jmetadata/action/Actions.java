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

package com.josdem.jmetadata.action;

import static org.asmatron.messengine.action.ActionId.cm;

import com.josdem.jmetadata.model.ExportPackage;
import com.josdem.jmetadata.model.Metadata;
import com.josdem.jmetadata.model.User;
import java.util.List;
import org.asmatron.messengine.action.ActionId;
import org.asmatron.messengine.action.EmptyAction;
import org.asmatron.messengine.action.RequestAction;
import org.asmatron.messengine.action.ValueAction;

public final class Actions {

  private Actions() {}

  public static final String LOGIN_ID = "login";
  public static final ActionId<ValueAction<User>> LOGIN = cm(LOGIN_ID);

  public static final String GET_METADATA = "getMetadata";
  public static final ActionId<EmptyAction> METADATA = cm(GET_METADATA);

  public static final String SEND_METADATA = "sendMetadata";
  public static final ActionId<RequestAction<Metadata, ActionResult>> SEND = cm(SEND_METADATA);

  public static final String COMPLETE_ALBUM_METADATA = "completeAlbumMetadata";
  public static final ActionId<RequestAction<List<Metadata>, ActionResult>> COMPLETE_MUSICBRAINZ =
      cm(COMPLETE_ALBUM_METADATA);

  public static final String COMPLETE_LAST_FM_METADATA = "completeLastfmMetadata";
  public static final ActionId<RequestAction<List<Metadata>, ActionResult>> COMPLETE_LAST_FM =
      cm(COMPLETE_LAST_FM_METADATA);

  public static final String WRITE_METADATA = "writeMetadata";
  public static final ActionId<RequestAction<Metadata, ActionResult>> WRITE = cm(WRITE_METADATA);

  public static final String EXPORT_METADATA = "exportMetadata";
  public static final ActionId<RequestAction<ExportPackage, ActionResult>> EXPORT =
      cm(EXPORT_METADATA);

  public static final String COMPLETE_FORMATTER_METADATA = "completeFormatterMetadata";
  public static final ActionId<RequestAction<Metadata, ActionResult>> COMPLETE_FORMATTER =
      cm(COMPLETE_FORMATTER_METADATA);

  public static final String COMPLETE_DEFAULT_METADATA = "completeDefaultMetadata";
  public static final ActionId<RequestAction<List<Metadata>, ActionResult>> COMPLETE_DEFAULT =
      cm(COMPLETE_DEFAULT_METADATA);
}
