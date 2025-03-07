package com.abc.banking_backend.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.abc.banking_backend.dto.BankResponse;
import com.abc.banking_backend.dto.BankStatementRequest;
import com.abc.banking_backend.entity.Transaction;
import com.abc.banking_backend.entity.User;
import com.abc.banking_backend.repository.UserRepository;
import com.abc.banking_backend.utilities.AccountUtilities;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.mail.MessagingException;

@Service
public class BankStatementServiceImpl implements BankStatementService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public BankResponse sendBankStatement(BankStatementRequest bs) throws DocumentException, MessagingException, IOException {
		User user = userRepository.findById(bs.getUserID()).orElseThrow(()-> new RuntimeException("User Not Found"));
		 List<Transaction> transactionsFromSpecificRange = user.getTransactions()
					.stream()
					.filter((t)->t.getTransactionDate().compareTo(bs.getFromDate())<=0 
					&& t.getTransactionDate().compareTo(bs.getToDate())>=0)
					.toList();
		 String toEmail = user.getEmail();
	     String subject = "ABC Bank Statement";
	     String body="Please find the attachment of your bank statement.";
	     ByteArrayResource attachment = generateBankStatement(user, transactionsFromSpecificRange);
        mailService.sendStatement(toEmail, subject, body, attachment);
		
		
		
		return BankResponse.builder()
				.responseCode(AccountUtilities.STATEMENT_GENERATED_SUCCESS_CODE)
				.responseMessage(AccountUtilities.STATEMENT_GENERATED_SUCCESS_RESPONSE)
				.build();
	}
	
	public ByteArrayResource generateBankStatement(User user, List<Transaction> transactionsFromSpecificRange) throws DocumentException {
	    ByteArrayOutputStream bsi = new ByteArrayOutputStream();
	    Document document = new Document();
	    PdfWriter.getInstance(document, bsi);
	    document.open();

	    // Adding the bank name
	    Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLUE);
	    Paragraph title = new Paragraph("ABC Bank", titleFont);
	    title.setAlignment(Element.ALIGN_CENTER);
	    document.add(title);

	    // Adding some space after the title
	    document.add(Chunk.NEWLINE);

	    // Adding the bank statement title
	    Font subTitleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
	    Paragraph subTitle = new Paragraph("Bank Statement", subTitleFont);
	    subTitle.setAlignment(Element.ALIGN_CENTER);
	    document.add(subTitle);

	    // Adding some space after the subtitle
	    document.add(Chunk.NEWLINE);

	    // Adding user details
	    Font userDetailsFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
	    document.add(new Paragraph("Name : " + user.getFirstName() + " " + user.getLastName(), userDetailsFont));
	    document.add(new Paragraph("Email : " + user.getEmail(), userDetailsFont));
	    document.add(new Paragraph("Account Number : " + user.getAccountNumber(), userDetailsFont));
	    document.add(new Paragraph("Account Balance : " + user.getAccountBalance(), userDetailsFont));

	    // Adding some space after user details
	    document.add(Chunk.NEWLINE);

	    // Creating the table with 5 columns
	    PdfPTable table = new PdfPTable(5);
	    table.setWidthPercentage(100); // Full width
	    table.setSpacingBefore(10f); // Space before table
	    table.setSpacingAfter(10f); // Space after table

	    // Setting column widths
	    float[] columnWidths = {1f, 2f, 2f, 3f, 2f};
	    table.setWidths(columnWidths);

	    // Adding table header
	    Font headFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);

	    PdfPCell hcell;
	    hcell = new PdfPCell(new Phrase("ID", headFont));
	    hcell.setBackgroundColor(BaseColor.BLUE);
	    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(hcell);

	    hcell = new PdfPCell(new Phrase("Amount", headFont));
	    hcell.setBackgroundColor(BaseColor.BLUE);
	    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(hcell);

	    hcell = new PdfPCell(new Phrase("Type", headFont));
	    hcell.setBackgroundColor(BaseColor.BLUE);
	    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(hcell);

	    hcell = new PdfPCell(new Phrase("Date", headFont));
	    hcell.setBackgroundColor(BaseColor.BLUE);
	    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(hcell);

	    hcell = new PdfPCell(new Phrase("Status", headFont));
	    hcell.setBackgroundColor(BaseColor.BLUE);
	    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.addCell(hcell);

	    // Adding table rows
	    Font cellFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
	    for (Transaction t : transactionsFromSpecificRange) {
	        PdfPCell cell;

	        cell = new PdfPCell(new Phrase(t.getId().toString(), cellFont));
	        cell.setPaddingLeft(5);
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);

	        cell = new PdfPCell(new Phrase(t.getAmount().toString(), cellFont));
	        cell.setPaddingLeft(5);
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        table.addCell(cell);

	        cell = new PdfPCell(new Phrase(t.getType(), cellFont));
	        cell.setPaddingLeft(5);
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        table.addCell(cell);

	        cell = new PdfPCell(new Phrase(t.getTransactionDate().toString(), cellFont));
	        cell.setPaddingLeft(5);
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);

	        cell = new PdfPCell(new Phrase(t.getStatus(), cellFont));
	        cell.setPaddingLeft(5);
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);
	    }

	    document.add(table);
	    document.close();

	    return new ByteArrayResource(bsi.toByteArray());
	}
	

}
