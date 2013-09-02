package org.jas.action;

import static org.asmatron.messengine.action.ActionId.cm;

import java.util.List;

import org.asmatron.messengine.action.ActionId;
import org.asmatron.messengine.action.EmptyAction;
import org.asmatron.messengine.action.RequestAction;
import org.asmatron.messengine.action.ValueAction;
import org.jas.model.ExportPackage;
import org.jas.model.Metadata;
import org.jas.model.User;

public interface Actions {
	String LOGIN_ID = "login";
	ActionId<ValueAction<User>> LOGIN = cm(LOGIN_ID);
	
	String GET_METADATA = "getMetadata";
	ActionId<EmptyAction> METADATA = cm(GET_METADATA);
	
	String SEND_METADATA = "sendMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> SEND = cm(SEND_METADATA);
	
	String COMPLETE_ALBUM_METADATA = "completeAlbumMetadata";
	ActionId<RequestAction<Metadata, ActionResult>> COMPLETE_MUSICBRAINZ = cm(COMPLETE_ALBUM_METADATA);
	
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
