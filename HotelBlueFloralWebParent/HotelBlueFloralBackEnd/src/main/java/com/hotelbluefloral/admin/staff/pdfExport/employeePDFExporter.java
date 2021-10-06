package com.hotelbluefloral.admin.staff.pdfExport;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.hotelbluefloral.comman.entity.Employee;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class employeePDFExporter {
	
	private List<Employee> listEmployee;

	public employeePDFExporter(List<Employee> listEmployee) {
		this.listEmployee = listEmployee;
	}
	
	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell= new PdfPCell();
		cell.setBackgroundColor(Color.BLACK);
		cell.setPadding(5);
		Font font= FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("Employee ID", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Employee Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Employee Role", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Employee ContactNo", font));
		table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table) {
		for(Employee employee : listEmployee) {
			table.addCell(String.valueOf(employee.getEid()));
			table.addCell(employee.getFname());
			table.addCell(employee.getRolename());
			table.addCell(employee.getContactNo());
		}
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document= new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		Font logofont= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		logofont.setColor(Color.BLACK);
		logofont.setSize(28);
		Paragraph logotitle =new Paragraph("HotelBlueFloral", logofont);
		document.add(logotitle);
		
		
		Font font= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.BLACK);
		font.setSize(18);
		Paragraph title =new Paragraph("List of Employees", font);
		document.add(title);
		
		PdfPTable table= new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		
		writeTableHeader(table);
		writeTableData(table);
		
		document.add(table);
		document.close();
	}
	
	
	
	
}

