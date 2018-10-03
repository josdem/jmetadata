
package org.jas.controller;

import java.io.IOException;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * @understands A class who manage export metadata to file
*/

@Controller
public class ExporterController {

	@Autowired
	private ExporterHelper exporterHelper;

  private Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMethod(Actions.EXPORT_METADATA)
	public ActionResult sendMetadata(ExportPackage exportPackage) throws CannotReadException, TagException, ReadOnlyFileException, MetadataException {
		try {
			return exporterHelper.export(exportPackage);
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
		}
		return ActionResult.Error;
	}

}
