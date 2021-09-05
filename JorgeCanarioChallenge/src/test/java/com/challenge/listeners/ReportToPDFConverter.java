package com.challenge.listeners;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.IExecutionListener;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

/**
 * This class prepares the outcome of the test suite in a way that can be read by the people that needs to read it
 * 
 * @author Jorge Canario
 *
 */
public class ReportToPDFConverter implements IExecutionListener {

	/**
	 * This method gets the current date and formats it as follows: yy-MM-dd-hhmmssS
	 * @return a string with the current date in the following format: yy-MM-dd-hhmmssS
	 * @author Jorge Canario
	 */
	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-hhmmssS");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * This method transform a html file into a A4 page PDF format
	 * 
	 * @param htmlFileName a String specifying the path of the html file (its absolute name)
	 * @param pdfFileName a String specifying the path of the pdf file to create (its absolute name)
	 * @author Jorge Canario
	 */
	private void convertHTMLFileToA4PDF(String htmlFileName, String pdfFileName) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outStream));
		pdfDocument.setDefaultPageSize(PageSize.A3);
		try {
			HtmlConverter.convertToPdf(new FileInputStream(htmlFileName), pdfDocument);
			PdfDocument resultantDocument = new PdfDocument(new PdfWriter(pdfFileName));
			resultantDocument.setDefaultPageSize(PageSize.A4);
			pdfDocument = new PdfDocument(new PdfReader(new ByteArrayInputStream(outStream.toByteArray())));
			for (int i = 1; i <= pdfDocument.getNumberOfPages(); i++) {
				PdfPage page = pdfDocument.getPage(i);
				PdfFormXObject formXObject = page.copyAsFormXObject(resultantDocument);
				PdfCanvas pdfCanvas = new PdfCanvas(resultantDocument.addNewPage());
				pdfCanvas.addXObject(formXObject, 0.4f, 0, 0, 0.4f, 6, 350);
			}
			resultantDocument.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pdfDocument.close();

	}

	/**
	 * This method deletes files with certain extension at the end of the name of the file from certain location
	 * @param input the directory from where the files wants to be deleted
	 * @param extension a String specifying the extension ending of the files that wants to be deleted
	 * @author Jorge Canario
	 */
	public void deleteFilesWithExtension(File input, String extension) {
		if (input.exists()) {
			for (File file : input.listFiles()) {
				if (file.getName().endsWith(extension)) {
					file.delete();
					System.out.print("Deleting file: "+file.getName() + "\n");
				}
			}
		}
	}
	
	/**
	 * This method deletes the resulted files used in previous run of the code 
	 * @author Jorge Canario
	 */
	public void deletePreviousRunFiles() {

		File currentProjectDir = new File(System.getProperty("user.dir"));
		File reportDir = new File(System.getProperty("user.dir") + "/test-output");

		deleteFilesWithExtension(currentProjectDir, ".png");
		deleteFilesWithExtension(reportDir, ".pdf");
		
		deleteFilesWithExtension(currentProjectDir, ".jpg");
	}

	/**
	 * This method run before the execution of the suite. Here the previous run files are deleted before the start of the next 
	 * execution
	 * @author Jorge Canario
	 */
	public void onExecutionStart() {
		deletePreviousRunFiles();
	}

	/**
	 * This method run after the suite has been ran completed and reports have been created. Here the report is converted to PDF and stored in the same 
	 * path that the html was saved
	 * @author Jorge Canario
	 */
	public void onExecutionFinish() {
		String htmlInputFile = System.getProperty("user.dir") + "/test-output/emailable-report.html";
		String pdfOutputFile = System.getProperty("user.dir") + "/test-output/emailable-report-" + getDate() + ".pdf";
		convertHTMLFileToA4PDF(htmlInputFile, pdfOutputFile);
		System.out.print("New PDF file created: " + pdfOutputFile);
	}

}
