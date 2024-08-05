package com.karmatechnologiestest.karma.service;

import java.util.List;

import com.karmatechnologiestest.karma.dto.SalesRequest;
import com.karmatechnologiestest.karma.entities.Sales;
import com.karmatechnologiestest.karma.entities.User;
import com.karmatechnologiestest.karma.exception.SalesException;
import com.karmatechnologiestest.karma.exception.UserException;

public interface SalesService {
	
	public List<Sales> findAllSales() throws UserException,  SalesException; 
	
	public Sales createSale(SalesRequest salesRequest, User user) throws UserException, SalesException;
    
    public Sales findSaleById(Long id) throws UserException, SalesException;
    
    public void deleteSale(Long id) throws UserException, SalesException;
    
//    public List<Sales> findSalesByUserId(Long userId) throws UserException, SalesException;
//    
    public List<Sales> findSalesByDate(String startDate, String endDate) throws UserException, SalesException;
    
    public List<Sales> findSalesByStatus(String status) throws UserException, SalesException;
    
    public List<Sales> findSalesByTotalAmount(Double minTotalAmount, Double maxTotalAmount) throws UserException, SalesException;
    
    public List<Sales> findSalesByDateAndStatus(String startDate, String endDate, String status) throws UserException, SalesException;
    
    public List<User> searchByCustomerName(String query);
    
    public List<Sales> searchBySalesDetails(String query);

	public Sales updateSale(SalesRequest salesRequest, Long salesId, User user) throws UserException, SalesException;

    
}
