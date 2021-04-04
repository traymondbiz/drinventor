package edu.upf.taln.dri.lib;

import edu.upf.taln.dri.lib.exception.DRIexception;
import edu.upf.taln.dri.lib.loader.PDFloader;
import edu.upf.taln.dri.lib.util.ModuleConfig;
import edu.upf.taln.dri.lib.util.PDFtoTextConvMethod;

public class Facade {

	private PDFloader setUpFactory(PDFtoTextConvMethod pdfConverterType,
								   String propertyFilePath,
								   ModuleConfig moduleConfig) throws DRIexception {
		// 1) Set the full path to the Dr. Inventor Framework Property files
		Factory.setDRIPropertyFilePath(propertyFilePath);

		// 2) Programmatically configure the PDF processing options (http://driframework.readthedocs.io/en/latest/Initialize/)
		Factory.setPDFtoTextConverter(pdfConverterType);

		Factory.setModuleConfig(moduleConfig);

		Factory.initFramework();

		return Factory.getPDFloader();

	}

	public PDFloader getGrobidPdfLoader(String propertyFilePath,
										 ModuleConfig moduleConfig) throws DRIexception {
		return setUpFactory(PDFtoTextConvMethod.GROBID, propertyFilePath, moduleConfig);
	}

	public PDFloader getPdfxPdfLoader(String propertyFilePath,
										ModuleConfig moduleConfig) throws DRIexception {
		return setUpFactory(PDFtoTextConvMethod.PDFX, propertyFilePath, moduleConfig);
	}


}
