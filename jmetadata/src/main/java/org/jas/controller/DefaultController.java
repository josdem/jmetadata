package org.jas.controller;

import java.util.List;

import org.asmatron.messengine.annotations.RequestMethod;
import org.jas.action.ActionResult;
import org.jas.action.Actions;
import org.jas.model.Metadata;
import org.jas.service.DefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DefaultController {

	@Autowired
	private DefaultService defaultService;
	
	@RequestMethod(Actions.COMPLETE_DEFAULT_METADATA)
	public synchronized ActionResult complete(List<Metadata> metadatas) {
		if (defaultService.isCompletable(metadatas) == true){
			defaultService.complete(metadatas);
			return ActionResult.New;
		}
		return ActionResult.Complete;
	}

}
