package com.karmatechnologiestest.karma.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karmatechnologiestest.karma.dto.SalesRequest;
import com.karmatechnologiestest.karma.entities.Sales;
import com.karmatechnologiestest.karma.exception.SalesException;
import com.karmatechnologiestest.karma.repository.SalesRepository;


@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;


    @Override
    public List<Sales> findAllSales() throws SalesException {
        List<Sales> salesList = salesRepository.findAll();
        if (salesList.isEmpty()) {
            throw new SalesException("No sales found.");
        }
        return salesList;
    }

    @Override
    public Sales createSale(SalesRequest salesRequest) throws SalesException {
        // Check if a sale with the same reference ID already exists
        Sales existingSale = salesRepository.findByReferenceId(salesRequest.getReferenceId());
        if (existingSale != null) {
            throw new SalesException("Sale with this reference ID already exists.");
        }

        Sales newSale = new Sales();
        newSale.setCustomerName(salesRequest.getCustomerName());
                                      
        String referenceId = "SL00" + salesRequest.getReferenceId(); 
        newSale.setReferenceId(referenceId);
        newSale.setLocalDateTime(salesRequest.getLocalDateTime());
        newSale.setStatus(salesRequest.getStatus());
        newSale.setGrandTotal(salesRequest.getGrandTotal());
        newSale.setPaid(salesRequest.getPaid());
        newSale.setDue(salesRequest.getDue());
        newSale.setPaymentStatus(salesRequest.getPaymentStatus());
        newSale.setBiller(salesRequest.getBiller());
        newSale.setAction(salesRequest.getAction());

        return salesRepository.save(newSale);
    }
    
    public Sales updateSale(Long id, Sales newSale) throws SalesException {
        System.out.println("Attempting to update Sale with ID: " + id);
        Optional<Sales> optionalSale = salesRepository.findById(id);
        if (optionalSale.isPresent()) {
            Sales existingSale = optionalSale.get();

            if (newSale.getAction() != null) {
                existingSale.setAction(newSale.getAction());
            } else {
                throw new SalesException("Action cannot be null");
            }

            existingSale.setBiller(newSale.getBiller());
            existingSale.setCustomerName(newSale.getCustomerName());
            existingSale.setDue(newSale.getDue());
            existingSale.setGrandTotal(newSale.getGrandTotal());
            existingSale.setPaid(newSale.getPaid());
            existingSale.setPaymentStatus(newSale.getPaymentStatus());
            existingSale.setReferenceId(newSale.getReferenceId());
            existingSale.setLocalDateTime(newSale.getLocalDateTime());
            existingSale.setStatus(newSale.getStatus());

            System.out.println("Updated Sale: " + existingSale);

            try {
                Sales updatedSale = salesRepository.save(existingSale);
                System.out.println("Sale updated successfully: " + updatedSale);
                return updatedSale;
            } catch (Exception e) {
                System.err.println("Error while saving updated sale: " + e.getMessage());
                throw new SalesException("Error while saving updated sale");
            }
        } else {
            System.out.println("Sale with ID: " + id + " not found.");
            throw new SalesException("Sale not found with id " + id);
        }
    }




    @Override
    public Sales findSaleById(Long id) throws SalesException {
        return salesRepository.findById(id)
                .orElseThrow(() -> new SalesException("Sale not found."));
    }

    @Override
    public void deleteSale(Long id) throws SalesException {
        Sales sale = findSaleById(id);
        salesRepository.delete(sale);
    }

    @Override
    public List<Sales> findSalesByDate(String startDate, String endDate) throws SalesException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        List<Sales> salesList = salesRepository.findAll().stream()
                .filter(sale -> !sale.getLocalDateTime().toLocalDate().isBefore(start) &&
                                !sale.getLocalDateTime().toLocalDate().isAfter(end))
                .collect(Collectors.toList());

        if (salesList.isEmpty()) {
            throw new SalesException("No sales found in the specified date range.");
        }

        return salesList;
    }

    @Override
    public List<Sales> findSalesByStatus(String status) throws SalesException {
        List<Sales> salesList = salesRepository.findAll().stream()
                .filter(sale -> sale.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        if (salesList.isEmpty()) {
            throw new SalesException("No sales found with the specified status.");
        }

        return salesList;
    }

    @Override
    public List<Sales> findSalesByTotalAmount(Double minTotalAmount, Double maxTotalAmount) throws SalesException {
        List<Sales> salesList = salesRepository.findAll().stream()
                .filter(sale -> sale.getGrandTotal() >= minTotalAmount && sale.getGrandTotal() <= maxTotalAmount)
                .collect(Collectors.toList());

        if (salesList.isEmpty()) {
            throw new SalesException("No sales found within the specified amount range.");
        }

        return salesList;
    }

    @Override
    public List<Sales> findSalesByDateAndStatus(String startDate, String endDate, String status) throws SalesException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        List<Sales> salesList = salesRepository.findAll().stream()
                .filter(sale -> !sale.getLocalDateTime().toLocalDate().isBefore(start) &&
                                !sale.getLocalDateTime().toLocalDate().isAfter(end) &&
                                sale.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());

        if (salesList.isEmpty()) {
            throw new SalesException("No sales found with the specified criteria.");
        }

        return salesList;
    }

    @Override
    public List<Sales> searchByQuery(String query) {
        // Implement the search logic based on the query parameter
        // For example, you might search by customer name or reference ID
        return salesRepository.findAll().stream()
                .filter(sale -> sale.getCustomerName().contains(query) || 
                                sale.getReferenceId().contains(query))
                .collect(Collectors.toList());
    }
}
