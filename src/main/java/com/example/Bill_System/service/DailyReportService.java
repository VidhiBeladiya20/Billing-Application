package com.example.Bill_System.service;

import com.example.Bill_System.model.Bill;
import com.example.Bill_System.repository.BillRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyReportService {
    @Autowired
    BillRepository billRepository;
    @Autowired
    EmailSendService emailSendService;

    @Value("${admin.email}") // Inject from properties
    private String adminEmail;

    public void sendDailyReportAsCSV() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        List<Bill> bills = billRepository.findByCreatedAtBetween(start, end);
        if (bills.isEmpty()) {
            System.out.println("No sales today.");
            return;
        }

        try {
            // CSV file path
            String filePath = "bills_report.csv"; // You can also use absolute path if needed if file is note available then it create file

            // Write CSV file using OpenCSV
            try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
                //FileWriter opens a file for writing (creates it if it doesn't exist).
                //CSVWriter wraps it to write comma-separated rows (and handles quotes, commas, newlines etc. for you).

                //Header
                writer.writeNext(new String[]{"Customer", "Product", "Qty", "Total", "GST", "Final"});

                for (Bill bill : bills) {
                    writer.writeNext(new String[]{
                            // writeNext() adds a new line in the CSV with that bill’s data.
                            bill.getCustomer().getName(),
                            bill.getProduct().getProductName(),
                            String.valueOf(bill.getQuantity()),
                            String.valueOf(bill.getTotalAmount()),
                            String.valueOf(bill.getGst()),
                            String.valueOf(bill.getFinalAmount())
                    });
                }
            }

            // Send email with the generated file
            emailSendService.sendCsvToAdmin(filePath, adminEmail);

        } catch (Exception e) {
            System.err.println("Error generating or sending CSV: " + e.getMessage());
        }
    }
}
