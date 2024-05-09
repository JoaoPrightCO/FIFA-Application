package com.aroska.fifa.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class PDFTest {
	
	public static void main(String args[]) {
		try {
            PDDocument pDDocument = Loader.loadPDF(new File("../temp/tmp/pdftest.pdf"));
            PDAcroForm pDAcroForm = pDDocument.getDocumentCatalog().getAcroForm();
            PDField field = pDAcroForm.getField("nome");
            field.setValue("Exemplo de texto 1 no campo nome");
            field = pDAcroForm.getField("nome2");
            field.setValue("Exemplo de texto 2 no campo morada");
//            field = pDAcroForm.getField("codpostal");
//            field.setValue("9999-999");
//            PDCheckBox checkbox = new PDCheckBox(pDAcroForm);
//            testPDF(pDAcroForm);
            pDDocument.save("../temp/tmp/elearning_tests-output.pdf");
            pDDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	public static void testPDF(PDAcroForm pdAcroForm) {
		PDCheckBox checkBox = new PDCheckBox(pdAcroForm);
		try {
			checkBox.setValue(0);;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}
