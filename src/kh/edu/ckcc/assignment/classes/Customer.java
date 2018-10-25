package kh.edu.ckcc.assignment.classes;

public class Customer {
	private String customerID;
	private String customerName;
	private String email;
	private String shippingAddress;
	private Cart shoppingCart;
	private String billingAddress;
	
	public Customer(String customerID, String customerName, String email, String shippingAddress, String billingAddress) {
		this.customerID = customerID;
		this.customerName = customerName;
		this.email = email;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		shoppingCart = new Cart();
	}
	
	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public void placeOrder(Cart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
	
	public String getCustomerID() {
		return customerID;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public String getShippingAddress() {
		return shippingAddress;
	}
	
	public Cart getCart() {
		return shoppingCart;
	}
	
	public void cancelOrder() {
		this.shoppingCart = null;
	}
	
	public String checkOut() {	
		return "";
	}
	
	public String toString() {
		return "[ ID : " + customerID + " ]" + "\n[ Name : " + customerName + " ]"
				+ "\n[ Email : " + email + " ]\n" + "[ Ship to : " + shippingAddress + " ]"
				+ "\n[ Bill to : " + billingAddress + " ]";
	}
	
	public String print() {
		return customerID + "\t" + customerName + "\t" + "\t" + shoppingCart.calculateTotal(); 
	}
	
}
