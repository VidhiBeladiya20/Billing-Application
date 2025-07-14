package com.example.Bill_System.service;

import com.example.Bill_System.model.Bill;
import com.example.Bill_System.model.Customer;
import com.example.Bill_System.model.Product;
import com.example.Bill_System.repository.BillRepository;
import com.example.Bill_System.repository.CustomerRepository;
import com.example.Bill_System.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SmsService smsService;
    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private DailyReportService dailyReportService;
    private final Random random = new Random();
    private static final int threshold = 5;
    public Object saveCustomerDetails(Customer customer) {
        return customerRepository.save(customer);
    }

    public String saveProductDetails(Product product) {
        productRepository.save(product);
        return "product saved successfully";
    }

    public String generateBillforCustomer_id(Long customerId) {
        Customer customer = fetchCustomer(customerId);
        if (customer.isBillApplied()) {
            return "You already applied this bill for your purchase.";
        }

        Product product = validateProduct(customer.getProductName());
        int quantity = customer.getProductCount();
        if (product.getStockQuantity() <= 0) {
            return "Product Out Of Stock! We will restock soon";
        }

        Bill bill = createBill(customer, product, quantity);
        billRepository.save(bill);

        if (bill.isPaymentConfirmed()) {
            handleSuccessfulPayment(customer, product, quantity, bill);
            return "Bill generated and payment confirmed. WhatsApp notification sent.";
        } else {
            handleFailedPayment(customer, bill);
            return "Bill generated but payment failed";
        }
    }

    //helper methods to segregate main function -- to improve readability, reusability, and maintainability

    private Customer fetchCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("User not found with customer id " + customerId));
    }

    private Product validateProduct(String productName) {
        Product product = productRepository.findByProductName(productName);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return product;
    }

    private Bill createBill(Customer customer, Product product, int quantity) {
        double totalAmt = product.getPrice() * quantity;
        double gst = totalAmt * 0.18;
        double finalAmt = totalAmt + gst;
//        boolean paymentConfirmed = random.nextBoolean();
        boolean paymentConfirmed = true;

        return new Bill(customer, product, quantity, totalAmt, gst, finalAmt, paymentConfirmed, LocalDateTime.now());
    }

    private void handleSuccessfulPayment(Customer customer, Product product, int quantity, Bill bill) {
        int remainingStock = product.getStockQuantity() - quantity;

        if (remainingStock < threshold) {
            sendLowStockEmail(product);
        }

        product.setStockQuantity(remainingStock);
        productRepository.save(product);

        customer.setBillApplied(true);
        customerRepository.save(customer);

        sendWhatsAppMessage(customer.getMobileNumber(), "Your payment of ₹" + bill.getFinalAmount() + " is successful. Thank you!");
        dailyReportService.sendDailyReportAsCSV();
    }

    private void handleFailedPayment(Customer customer, Bill bill) {
        sendWhatsAppMessage(customer.getMobileNumber(), "Your payment of ₹" + bill.getFinalAmount() + " has failed. Please try again!");
    }

    private void sendLowStockEmail(Product product) {
        String body = "⚠️ Product '" + product.getProductName() + "' is below threshold.\n"
                + "Current stock: " + product.getStockQuantity() + "\n"
                + "Threshold: " + threshold + "\n"
                + "Please restock soon.";
        emailSendService.sendEmail("vidhibeladiya9@gmail.com", "Low Stock Alert", body);
    }

    private void sendWhatsAppMessage(String mobileNumber, String message) {
        try {
            System.out.println("Sending WhatsApp to: " + mobileNumber);
            smsService.sendWhatsApp(mobileNumber, message);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
