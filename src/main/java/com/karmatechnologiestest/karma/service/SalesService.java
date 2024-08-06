package com.karmatechnologiestest.karma.service;

import java.util.List;


import com.karmatechnologiestest.karma.dto.SalesRequest;
import com.karmatechnologiestest.karma.entities.Sales;
import com.karmatechnologiestest.karma.exception.SalesException;
import com.karmatechnologiestest.karma.exception.AdminException;

public interface SalesService {
	
	public List<Sales> findAllSales() throws SalesException; 
	
	public Sales createSale(SalesRequest salesRequest) throws AdminException, SalesException;
    
    public Sales findSaleById(Long id) throws AdminException, SalesException;
    
    public void deleteSale(Long id) throws AdminException, SalesException;
    
    public List<Sales> findSalesByDate(String startDate, String endDate) throws AdminException, SalesException;
    
    public List<Sales> findSalesByStatus(String status) throws AdminException, SalesException;
    
    public List<Sales> findSalesByTotalAmount(Double minTotalAmount, Double maxTotalAmount) throws AdminException, SalesException;
    
    public List<Sales> findSalesByDateAndStatus(String startDate, String endDate, String status) throws AdminException, SalesException;
    
    public List<Sales> searchByQuery(String query);
    
	public Sales updateSale(SalesRequest salesRequest, Long salesId) throws SalesException;


    
}
