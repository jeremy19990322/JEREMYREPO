package com.b07.store;

import java.util.ArrayList;
import java.util.List;

public class SalesLogImpl implements SalesLog {

	private List<Sale> sales;
	
	public SalesLogImpl() {
		sales = new ArrayList<Sale>();
	}
	
	@Override
	public void addSale(Sale sale) {
		sales.add(sale);
	}

	@Override
	public List<Sale> getSales() {
		return sales;
	}

}

