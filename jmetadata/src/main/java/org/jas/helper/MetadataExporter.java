package org.jas.helper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jas.ApplicationState;
import org.jas.model.ExportPackage;
import org.jas.model.Metadata;
import org.jas.service.FormatterService;
import org.jas.service.MetadataService;
import org.jas.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataExporter {
	private static final String NEW_LINE = "\n";
	private static final String DASH = " - ";
	private static final String DOT = ". ";
	private static final String PAR_OPEN = " (";
	private static final String PAR_CLOSE = ")";
	private static final String BY = " by ";
	private FileUtils fileUtils = new FileUtils();
	private OutStreamWriter  outputStreamWriter = new OutStreamWriter();
	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private MetadataService metadataService;
	@Autowired
	private FormatterService formatter;

	public void export(ExportPackage exportPackage) throws IOException {
		File file = fileUtils.createFile(exportPackage.getRoot(), StringUtils.EMPTY, ApplicationState.FILE_EXT);
		log.info("Exporting metadata to: " + file.getAbsolutePath());
		OutputStream writer = outputStreamWriter.getWriter(file);
		int counter = 1;
		List<Metadata> metadatas = exportPackage.getMetadataList();
		if(metadataService.isSameAlbum(metadatas)){
			writer.write(metadatas.get(0).getAlbum().getBytes());
			writer.write(BY.getBytes());
			writer.write(metadatas.get(0).getArtist().getBytes());
			writer.write(NEW_LINE.getBytes());
			writer.write(NEW_LINE.getBytes());
		}
		for (Metadata metadata : metadatas) {
			writer.write(Integer.toString(counter++).getBytes());
			writer.write(DOT.getBytes());
			writer.write(metadata.getArtist().getBytes());
			writer.write(DASH.getBytes());
			writer.write(metadata.getTitle().getBytes());
			writer.write(PAR_OPEN.getBytes());
			writer.write(formatter.getDuration(metadata.getLength()).getBytes());
			writer.write(PAR_CLOSE.getBytes());
			writer.write(NEW_LINE.getBytes());
		}
		writer.flush();
		writer.close();
	}

}
