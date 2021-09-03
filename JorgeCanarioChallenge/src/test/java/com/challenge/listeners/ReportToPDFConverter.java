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

public class ReportToPDFConverter implements IExecutionListener {

	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-hhmmssS");
		Date date = new Date();
		return dateFormat.format(date);
	}

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
	
	public void deletePreviousRunFiles() {

		File currentProjectDir = new File(System.getProperty("user.dir"));
		File reportDir = new File(System.getProperty("user.dir") + "/test-output");

		deleteFilesWithExtension(currentProjectDir, ".png");
		deleteFilesWithExtension(reportDir, ".pdf");
	}

	public void onExecutionStart() {
		deletePreviousRunFiles();
	}

	public void onExecutionFinish() {
		String htmlInputFile = System.getProperty("user.dir") + "/test-output/emailable-report.html";
		String pdfOutputFile = System.getProperty("user.dir") + "/test-output/emailable-report-" + getDate() + ".pdf";
		convertHTMLFileToA4PDF(htmlInputFile, pdfOutputFile);
		System.out.print("New PDF file created: " + pdfOutputFile);
	}

}
