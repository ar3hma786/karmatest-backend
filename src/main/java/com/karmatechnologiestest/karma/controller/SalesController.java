package com.karmatechnologiestest.karma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.karmatechnologiestest.karma.dto.SalesRequest;
import com.karmatechnologiestest.karma.entities.Sales;
import com.karmatechnologiestest.karma.entities.Admin;
import com.karmatechnologiestest.karma.exception.AdminException;
import com.karmatechnologiestest.karma.exception.SalesException;
import com.karmatechnologiestest.karma.service.AdminService;
import com.karmatechnologiestest.karma.service.SalesService;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Sales>> findAllSales() throws SalesException{
        try {
            List<Sales> sales = salesService.findAllSales();
            return new ResponseEntity<>(sales, HttpStatus.OK);
        } catch (SalesException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Sales> createSale(@RequestBody SalesRequest salesRequest, @RequestHeader("Authorization") String jwt) {
        try {
            Admin admin = adminService.findUserProfileByJwt(jwt);
            if (admin == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Sales createdSale = salesService.createSale(salesRequest);
            return new ResponseEntity<>(createdSale, HttpStatus.CREATED);
        } catch (AdminException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (SalesException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sales> findSaleById(@PathVariable Long id, @RequestHeader("Authorization") String jwt) {
        try {
            Admin admin = adminService.findUserProfileByJwt(jwt);
            if (admin == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Sales sale = salesService.findSaleById(id);
            return new ResponseEntity<>(sale, HttpStatus.OK);
        } catch (AdminException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (SalesException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id, @RequestHeader("Authorization") String jwt) {
        try {
            Admin admin = adminService.findUserProfileByJwt(jwt);
            if (admin == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            salesService.deleteSale(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (AdminException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (SalesException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sales> updateSale(@PathVariable Long id, @RequestBody Sales sales, @RequestHeader("Authorization") String jwt) {
        try {
            Admin admin = adminService.findUserProfileByJwt(jwt);
            if (admin == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            Sales updatedSale = salesService.updateSale(id, sales);
            return new ResponseEntity<>(updatedSale, HttpStatus.OK);
        } catch (AdminException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (SalesException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
