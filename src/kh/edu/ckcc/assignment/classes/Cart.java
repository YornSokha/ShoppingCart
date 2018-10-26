package kh.edu.ckcc.assignment.classes;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<Purchase> purchasedItems = new ArrayList<Purchase>();
	private double discount;
	
	public Cart(double discount) {
		this.discount = discount;
	}

	public Cart() {
		// TODO Auto-generated constructor stub
	}

	public void addItem(Purchase item) {
		//item = new Purchase();
		purchasedItems.add(item);
	}
	
	public boolean isEmpty() {
		if(purchasedItems.isEmpty())
			return true;
		return false;
	}
	
	public void removeItem(Purchase item) {
		purchasedItems.remove(item);
	}
	
	
	public List<Purchase> getPurchasedItems() {
		return purchasedItems;
	}
	
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public double getDiscount() {
		return discount;
	}
	
	public double calculateSubTotal() {
		double subTotal = 0;
		for(Purchase item : purchasedItems) {
			subTotal += item.getPrice();
		}
		
		return subTotal;
	}
	
	public int getPurchasedItemAmount() {
		return purchasedItems.size();
	}
	
	public double calculateTotal() {
		return calculateSubTotal() - discount * calculateSubTotal();
	}
}
