package edu.upf.taln.dri.lib;

import edu.upf.taln.dri.lib.exception.DRIexception;
import edu.upf.taln.dri.lib.loader.JATSloader;
import edu.upf.taln.dri.lib.loader.PDFloader;
import edu.upf.taln.dri.lib.loader.PlainTextLoader;
import edu.upf.taln.dri.lib.model.Document;
import edu.upf.taln.dri.lib.util.ModuleConfig;
import edu.upf.taln.dri.lib.util.PDFtoTextConvMethod;
import edu.upf.taln.dri.module.importer.SourceENUM;

import java.net.URL;

public class Facade {

    private void setUpFactory(String propertyFilePath,
                              ModuleConfig moduleConfig) throws DRIexception {
        // 1) Set the full path to the Dr. Inventor Framework Property files
        Factory.setDRIPropertyFilePath(propertyFilePath);

        Factory.setModuleConfig(moduleConfig);

        Factory.initFramework();

    }

    private Document loadPDF(PDFtoTextConvMethod conversionType,
                             String propertyFilePath,
                             ModuleConfig moduleConfig,
                             String PDFFilePath) throws DRIexception {
        Factory.setPDFtoTextConverter(conversionType);
        setUpFactory(propertyFilePath, moduleConfig);
        Document document = Factory.getPDFloader()
                .parsePDF(PDFFilePath);
        document.preprocess();
        return document;
    }

    private Document loadPDF(PDFtoTextConvMethod conversionType,
                             String propertyFilePath,
                             ModuleConfig moduleConfig,
                             URL URLSource) throws DRIexception {
        Factory.setPDFtoTextConverter(conversionType);
        setUpFactory(propertyFilePath, moduleConfig);
        Document document = Factory.getPDFloader()
                .parsePDF(URLSource);
        document.preprocess();
        return document;
    }

    public Document loadLocalPDFUsingGrobid(String propertyFilePath,
                                            ModuleConfig moduleConfig,
                                            String PDFFilePath) throws DRIexception {
        return loadPDF(PDFtoTextConvMethod.GROBID, propertyFilePath, moduleConfig, PDFFilePath);
    }

    public Document loadLocalPDFUsingPdfx(String propertyFilePath,
                                          ModuleConfig moduleConfig,
                                          String PDFFilePath) throws DRIexception {
        return loadPDF(PDFtoTextConvMethod.PDFX, propertyFilePath, moduleConfig, PDFFilePath);
    }

    public Document loadLocalXMLJats(String propertyFilePath,
                                     ModuleConfig moduleConfig,
                                     String JATSFilePath) throws DRIexception {
        setUpFactory(propertyFilePath, moduleConfig);
        Document document = Factory.getJATSloader()
                .parseJATS(JATSFilePath);
        document.preprocess();
        return document;
    }

    public Document loadLocalPlainText(String textToParse,
                                       String documentName,
                                       String propertyFilePath,
                                       ModuleConfig moduleConfig) throws DRIexception {
        setUpFactory(propertyFilePath, moduleConfig);
        Document document = Factory.getPlainTextLoader()
                .parseString(textToParse, documentName);
        document.preprocess();
        return document;
    }

    public Document loadRemotePDFUsingGrobid(String propertyFilePath,
                                             ModuleConfig moduleConfig,
                                             URL URLSource) throws DRIexception {
        return loadPDF(PDFtoTextConvMethod.GROBID, propertyFilePath, moduleConfig, URLSource);
    }

    public Document loadRemotePDFUsingPdfx(String propertyFilePath,
                                           ModuleConfig moduleConfig,
                                           URL URLSource) throws DRIexception {
        return loadPDF(PDFtoTextConvMethod.PDFX, propertyFilePath, moduleConfig, URLSource);
    }

    public Document loadRemoteXMLJats(String propertyFilePath,
                                      ModuleConfig moduleConfig,
                                      URL URLSource) throws DRIexception {
        setUpFactory(propertyFilePath, moduleConfig);
        Document document = Factory.getJATSloader()
                .parseJATS(URLSource);
        document.preprocess();
        return document;
    }

}
