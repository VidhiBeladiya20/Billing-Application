package com.example.Bill_System.controller;

import com.example.Bill_System.model.Customer;
import com.example.Bill_System.model.Product;
import com.example.Bill_System.service.BillService;
import com.example.Bill_System.service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private DailyReportService dailyReportService;

    @PostMapping("saveCustomer")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer){
        return ResponseEntity.ok(billService.saveCustomerDetails(customer));
    }

    @PostMapping("saveProduct")
    public ResponseEntity<?> saveProduct(@RequestBody Product product){
        String res = billService.saveProductDetails(product);
        return ResponseEntity.ok(res);
    }

    @PostMapping("billGenerate/{customerId}")
    public  ResponseEntity<?> generateBill(@PathVariable Long customerId){
        String bill = billService.generateBillforCustomer_id(customerId );
        return ResponseEntity.ok(bill);
    }
}
