package com.karmatechnologiestest.karma.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.karmatechnologiestest.karma.dto.SalesRequest;
import com.karmatechnologiestest.karma.entities.Sales;
import com.karmatechnologiestest.karma.entities.User;
import com.karmatechnologiestest.karma.enums.ROLE;
import com.karmatechnologiestest.karma.exception.SalesException;
import com.karmatechnologiestest.karma.exception.UserException;
import com.karmatechnologiestest.karma.repository.SalesRepository;
import com.karmatechnologiestest.karma.repository.UserRepository;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Sales> findAllSales() throws SalesException {
        List<Sales> salesList = salesRepository.findAll();
        if (salesList.isEmpty()) {
            throw new SalesException("No sales found.");
        }
        return salesList;
    }

    @Override
    public Sales createSale(SalesRequest salesRequest, User user) throws UserException, SalesException {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new UserException("User not found."));

        if (!existingUser.getRole().equals(ROLE.ADMIN)) {
            throw new UserException("Only admin users can create sales.");
        }

        Sales newSale = new Sales();
        newSale.setReferenceId(salesRequest.getReferenceId());
        newSale.setLocalDateTime(salesRequest.getLocalDateTime());
        newSale.setStatus(salesRequest.getStatus());
        newSale.setGrandTotal(salesRequest.getGrandTotal());
        newSale.setPaid(salesRequest.getPaid());
        newSale.setDue(salesRequest.getDue());
        newSale.setPaymentStatus(salesRequest.getPaymentStatus());
        newSale.setBiller("Admin");
        newSale.setAction(salesRequest.getAction());
        newSale.setUser(existingUser);

        return salesRepository.save(newSale);
    }

    @Override
    public Sales updateSale(SalesRequest salesRequest, Long salesId, User user) throws UserException, SalesException {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new UserException("User not found."));

        if (!existingUser.getRole().equals(ROLE.ADMIN)) {
            throw new UserException("Only admin users can update sales.");
        }

        Sales sale = salesRepository.findById(salesId)
                .orElseThrow(() -> new SalesException("Sale not found."));

        sale.setLocalDateTime(salesRequest.getLocalDateTime());
        sale.setStatus(salesRequest.getStatus());
        sale.setGrandTotal(salesRequest.getGrandTotal());
        sale.setPaid(salesRequest.getPaid());
        sale.setDue(salesRequest.getDue());
        sale.setPaymentStatus(salesRequest.getPaymentStatus());
        sale.setBiller("Admin");
        sale.setAction(salesRequest.getAction());
        sale.setUser(existingUser);

        return salesRepository.save(sale);
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

//    @Override
//    public List<Sales> findSalesByUserId(Long userId) throws UserException, SalesException {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserException("User not found."));
//
//        List<Sales> salesList = salesRepository.findByUserId(userId);
//        if (salesList.isEmpty()) {
//            throw new SalesException("No sales found for this user.");
//        }
//
//        return salesList;
//    }

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
    public List<User> searchByCustomerName(String query) {
        return userRepository.findAll().stream()
                .filter(user -> user.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                                user.getLastName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Sales> searchBySalesDetails(String query) {
        return salesRepository.findBySalesDetails(query);
    }
}
