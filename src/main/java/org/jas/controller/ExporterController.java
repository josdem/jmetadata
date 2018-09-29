
package org.jas.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.asmatron.messengine.annotations.RequestMethod;

import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;

import org.jas.action.Actions;
import org.jas.action.ActionResult;
import org.jas.model.ExportPackage;
import org.jas.helper.ExporterHelper;
import org.jas.metadata.MetadataException;

/*
 * @understands A class who manage export metadata to file
*/

@Controller
public class ExporterController {
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private ExporterHelper exporterHelper;

	@RequestMethod(Actions.EXPORT_METADATA)
	public ActionResult sendMetadata(ExportPackage exportPackage) throws CannotReadException, TagException, ReadOnlyFileException, MetadataException {
		try {
			return exporterHelper.export(exportPackage);
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		}
		return ActionResult.Error;
	}

}
