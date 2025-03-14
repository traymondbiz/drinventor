/*
 * ******************************************************************************************************
 * Dr. Inventor Text Mining Framework Java Library
 * 
 * This code has been developed by the Natural Language Processing Group of the
 * Universitat Pompeu Fabra in the context of the FP7 European Project Dr. Inventor
 * Call: FP7-ICT-2013.8.1 - Agreement No: 611383
 * 
 * Dr. Inventor Text Mining Framework Java Library is available under an open licence, GPLv3, for non-commercial applications.
 * ******************************************************************************************************
 */
package edu.upf.taln.dri.common.connector.pdfx;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/26580912/pdf-convert-to-black-and-white-pngs
 */
 
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfStream;
import com.itextpdf.text.pdf.parser.PdfImageObject;
 
/**
 * Utility class to replace images in PDF files in order to decrease the final size of the same file.
 * 
 *
 */
public class ReplaceImage {
	
	public static final String filename = "Full";
    public static final String SRC = "/home/francesco/Downloads/" + filename + ".pdf";
    public static final String DEST = "/home/francesco/Downloads/" + filename + "_NEW2.pdf";
 
    public static void main(String[] args) throws DocumentException, IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReplaceImage().manipulatePdf(SRC, DEST);
    }
 
    public void manipulatePdf(String src, String dest) throws DocumentException, IOException {
        PdfReader reader = new PdfReader(src);
        PdfDictionary page = reader.getPageN(1);
        PdfDictionary resources = page.getAsDict(PdfName.RESOURCES);
        PdfDictionary xobjects = resources.getAsDict(PdfName.XOBJECT);
        PdfName imgRef = xobjects.getKeys().iterator().next();
        PRStream stream = (PRStream) xobjects.getAsStream(imgRef);
        Image img = makeBlackAndWhitePng(new PdfImageObject(stream));
        PdfImage image = new PdfImage(makeBlackAndWhitePng(new PdfImageObject(stream)), "", null);
        replaceStream(stream, image);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        stamper.close();
        reader.close();
    }
 
    public static Image makeBlackAndWhitePng(PdfImageObject image) throws IOException, DocumentException {
        BufferedImage bi = image.getBufferedImage();
        BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        newBi.getGraphics().drawImage(bi, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBi, "png", baos);
        return Image.getInstance(baos.toByteArray());
    }
 
    public static void replaceStream(PRStream orig, PdfStream stream) throws IOException {
        orig.clear();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        stream.writeContent(baos);
        orig.setData(baos.toByteArray(), false);
        for (PdfName name : stream.getKeys()) {
            orig.put(name, stream.get(name));
        }
    }
}