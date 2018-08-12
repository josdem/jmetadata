
package org.jas.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asmatron.messengine.annotations.RequestMethod;
import org.jas.action.ActionResult;
import org.jas.action.Actions;
import org.jas.helper.ExporterHelper;
import org.jas.model.ExportPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/*
 * @understands A class who manage export metadata to file
*/

@Controller
public class ExporterController {
	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private ExporterHelper exporterHelper;

	@RequestMethod(Actions.EXPORT_METADATA)
	public ActionResult sendMetadata(ExportPackage exportPackage) {
		try {
			return exporterHelper.export(exportPackage);
		} catch (IOException ioe) {
			log.error(ioe, ioe);
		}
		return ActionResult.Error;
	}

}
