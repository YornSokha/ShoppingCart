package kh.edu.ckcc.assignment.classes;
import kh.edu.ckcc.assignment.drawtable.DrawingTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static Scanner scanner = new Scanner(System.in);
	public static List<Customer> customers = new ArrayList<Customer>();
	private static List<Product> products = new ArrayList<Product>();
	private static int purchaseNo = 1;
	
	public static void main(String[] args) {	
		String option;
		Cart cart = new Cart();
		initialDefaultProduct();
		
		
		boolean quit = false;
		while(!quit) {
			option = menu();
			switch(option) {
				case "1":
					boolean add = true;
					while(add) {
						createNewProduct();	
						System.out.println("------------------------------------------------------------");
						System.out.println("Do you want to add more product?(Y/N)");
						String isBuy = scanner.nextLine();
						if(!isBuy.equalsIgnoreCase("Y"))
							add = false;
					}
					break;
					
				case "2":
					showProductList();
					break;
					
				case "3":
					System.out.println("\n-----------------------Let's go shopping products you want---------------------------------");
					boolean buy = true;
					while(buy) {
						shoppingProduct(cart);	
						System.out.println("------------------------------------------------------------");
						System.out.println("Do you want to shop more product?(Y/N)");
						String isBuy = scanner.nextLine();
						if(!isBuy.equalsIgnoreCase("Y"))
							buy = false;
					}
					
					break;
				case "4":
					boolean back = false;
					if(!cart.isEmpty()) {
						do {
							showMyShoppingCart(cart);
							System.out.println("	1. Add more purchase");
							System.out.println("	2. Remove purchase");
							System.out.println("	3. Back");
							String key = scanner.nextLine();
							switch(key) {
							case "1":
								System.out.println("---------------------Shopping more product(s)---------------------");
								shoppingProduct(cart);
								break;
								
							case "2":
								removePurchase(cart);
								break;
							case "3":
								back = true;
								break;
							}
							
						}while(!back);
					}	
					else {
						System.out.println("Sorry, you haven't shopped any product yet\nPlease go to buy our product first");	
						scanner.nextLine();
					}
					break;
				case "5":
					String ch;	
					do {
						System.out.println("	1. Add new Customer to check out");
						System.out.println("	2. Go back to Main Menu");
						ch = scanner.nextLine();
						switch(ch) {
						case "1":
							if(addCustomer(cart))
								cart = new Cart();
							break;
						
						}
					}while(!ch.equals("2"));				
					break;				
				case "6":
					String st;
					Customer c;
					if(!customers.isEmpty()) {
						do {
							showShoppingHistory();
							System.out.println("1. Search Customer's invoice");
							System.out.println("2. Go back");
							st = scanner.nextLine();
							String cusID;
							switch(st) {
								case "1":
									System.out.println("Enter Customer ID : "); 
									cusID = scanner.nextLine();
									c = findCustomer(cusID);
									if(c != null)
										showCustomer(c);
									else
										System.out.println("Customer is not found!");
									scanner.nextLine();
									break;															 
							}
						}while(!st.equals("2"));
					}
					else {
						System.out.println("No any customers to view!!!");
					}
					break;
			}
			System.out.print("Do you want to continue to Main Menu? (Y/N)\n");
			if(!scanner.nextLine().equalsIgnoreCase("Y"))
				quit = true;
		}
	}
	

	private static String menu() {

		String option;
		DrawingTable drawingTable1 = new DrawingTable();
		
		drawingTable1.setHeaders("              Menu              ");
		drawingTable1.addRow("   1. Create New Product   ");
		drawingTable1.addRow("   2. List of Product(s)   ");
		drawingTable1.addRow("   3. Go to Shopping    ");
		drawingTable1.addRow("   4. My Shopping Cart   ");
		drawingTable1.addRow("   5. Check Out  ");
		drawingTable1.addRow("   6. View Shopping History");
	
		drawingTable1.print();
		System.out.print("Enter your choice : ");
		option = scanner.nextLine();
		return option;
	}
	private static void initialDefaultProduct() {
		// /*Initialize 3 products and add to list of Products */		 
		products.add(new Product("001","Laptop","Product of Cambodia", 500, 1000));
		products.add(new Product("002","Desktop","Product of Cambodia", 340, 1000));
		products.add(new Product("003","Mouse","Product of Cambodia", 10, 1000));
		
	}

	private static void createNewProduct() {
		 
             System.out.println("----------------- Create New Product --------------------\n");
             System.out.print("Enter your product code:\n");
             String code = scanner.nextLine();
             Product p = findProduct(code);
             if(p == null) {
            	 System.out.print("Enter your product name:\n");
                 String name = scanner.nextLine();
                 System.out.print("Enter your product description:\n");
                 String description = scanner.nextLine();
                 double price;
                 double qty;                                  	               
            	 try {
                	 System.out.print("Enter your product price:\n");
                     price = scanner.nextDouble();
                 }catch(Exception e) {
                	 System.out.println("Product price must be double!\n"
                	 		+ "Add product unsuccessful!");
                	 scanner.nextLine();
                	 return;
                 }
            	 try {
                	 System.out.print("Enter your product quantity:\n");
                     qty = scanner.nextDouble();
                 }catch(Exception e) {
                	 System.out.println("Product quantity must be double!\n"
                	 		+ "Add product unsuccessful!");
                	 scanner.nextLine();
                	 return;
                 }
                 addNewProduct(new Product(code, name, description, price, qty));
                 System.out.println("Product name : " + name + " with code " + code + " is added into stock.");
                 scanner.nextLine();
                 
             }
             else
                 System.out.println("Product code : " + code + " is already existed!");
      
	}
    private static void addNewProduct(Product product){
        products.add(product);
    }
	private static boolean addCustomer(Cart cart) {
		if(!cart.isEmpty()) {
			System.out.println("-------------------------------Before Check out, give me your information-------------------------------");
			System.out.println("Enter your identification Number : "); 
			String id = scanner.nextLine();
			System.out.println("Enter your name : "); 
			String name = scanner.nextLine();
			System.out.println("Enter your email : "); 
			String email = scanner.nextLine();
			System.out.println("Enter your shipping address : "); 
			String shippingAddress = scanner.nextLine();
			System.out.println("Enter your billing address :  "); 
			String billingAddress = scanner.nextLine();
			System.out.println("\n-------------------------------Do you have discount card?---------------------------------------------");
			double discountCard = 0D;
			try {
				System.out.println("\nEnter Pertage on your discount card : "); 
				discountCard = scanner.nextDouble();	
			}catch(Exception e) {
				System.out.println("Discount must be double!\n"
						+ "Checkout unsuccessful!");
				scanner.nextLine();
				return false;
			}
			
			cart.setDiscount(discountCard);
			Customer customer = new Customer(id, name, email, shippingAddress, billingAddress);
			customer.placeOrder(cart);
			System.out.println("Do you accept?(Y/N):");
			scanner.nextLine();
			String accept = scanner.nextLine();
			if(!accept.equalsIgnoreCase("Y")) {

				List<Purchase> cancelledPurchase = customer.getCart().getPurchasedItems();
                for(int j = 0; j < products.size(); j++) {				                        	
                    
                	Product product = products.get(j);
                	for(int k = 0; k < cancelledPurchase.size(); k++){
                        
                		if((product.getID()).equals(cancelledPurchase.get(k).getProduct().getID())){
                            product.setQtyInStock(product.getQtyInStock() + cancelledPurchase.get(k).getQty());
                        }
                    }	
                }
                customer.cancelOrder();   
				return true;
			}
			customers.add(customer);
			System.out.println("\n------------------------------------Your purchased Invoice----------------------------------------------");
			showCustomer(customer);
			
			// renew purchaseNo for new customer
			purchaseNo = 1;
			System.out.println("Arigato!!!!");
			scanner.nextLine();
			return true;
		}
		else {
			System.out.println("Please go to shopping frist!!!");
			scanner.nextLine();
			return false;
		}
	}

	private static void removePurchase(Cart cart) {
		System.out.println("Input purchaseNo to remove : "); 
		String purNo = scanner.nextLine();
		boolean exist = false;
		Purchase pur = findPurchase(purNo, cart.getPurchasedItems());
		if(pur != null) {
			Purchase cancelledPurchase = pur;
            for(int j = 0; j < products.size(); j++) {				                           
                    Product product = products.get(j);
                    if((product.getID()).equals(cancelledPurchase.getProduct().getID())){
                        products.set(j, new Product(
                                product.getID(), product.getName(),
                                product.getDescription(),
                                product.getQtyInStock() + cancelledPurchase.getQty(),
                                product.getPrice()));
                    }			                        
            cart.removeItem(pur);				         
            System.out.println("You products have been removed!");
            exist = true;
		}
			
	}																								
		if(!exist){
			System.out.println("Invalid product name or Order ID");
		}
		
	}

	
	private static Purchase findPurchase(String purNo, List<Purchase> purchased) {
		for(Purchase pu : purchased)
			if(pu.getOrderNo().equals(purNo))
				return pu;
		return null;
	}
	private static Product findProduct(String id) {
        for(Product product : products)
            if(product.getID().equalsIgnoreCase(id))
                return product;
        return null;
    }  
	private static Customer findCustomer(String customerID) {
	for(Customer customer : customers)
		if(customer.getCustomerID().equals(customerID))
			return customer;
	return null;
	}
    
	private static void showProductList() {
		DrawingTable drawingTable;
		System.out.println("----------------------Product List-------------------------\n");
		drawingTable = new DrawingTable();
		drawingTable.setHeaders(" ID ", " Name ", " Price ", " QTY ", " Description ");
		for(Product p : products) {
			drawingTable.addRow(p.getID() , p.getName(), "$ " +  p.getPrice() + "", p.getQtyInStock() + "", p.getDescription());		
			}
		drawingTable.print();
	}	
	private static void showShoppingHistory() {
		DrawingTable drawingTable = new DrawingTable();
		System.out.println("-----------------Shopping History------------------");
		
		drawingTable.setHeaders("Customer ID", "Customer Name", "Shipping Address", "Total");
		for(Customer cus : customers) {
			drawingTable.addRow(cus.getCustomerID(), cus.getCustomerName(), cus.getShippingAddress(),"$ " + cus.getCart().calculateTotal() + "");
		}
		
		drawingTable.print();					
	}	
	private static void showMyShoppingCart(Cart cart) {
		DrawingTable drawingTable = new DrawingTable();
		System.out.println("--------------------My Shopping Cart----------------------------\n");
		
		drawingTable.setHeaders(" NO ", " Name ", "Unit Price", " Qty ", " Discount ", " Price " );
		
		for(Purchase item : cart.getPurchasedItems()) {
			drawingTable.addRow(item.getOrderNo(), item.getProductName(),
					"$ " + item.getProductPrice(), 
					item.getQty() + "", (item.getDiscount() * 100) + "",
					"$ " + item.getPrice() + "");
		}
		drawingTable.print();
	}	
	private static void showCustomer(Customer customer) {
		
		System.out.println(customer.toString());
		showMyShoppingCart(customer.getCart());
		System.out.println("\nSubTotal : $" + customer.getCart().calculateSubTotal());
		System.out.println("Discount : " + (customer.getCart().getDiscount()*100) + "%");
		System.out.println("Total : $" + customer.getCart().calculateTotal());

		
	}
	private static void shoppingProduct(Cart cart) {
		System.out.print("Enter product code you want to buy : "); 
		String proCode = scanner.nextLine();
		System.out.println(proCode);
		Product product = findProduct(proCode);
		if(product != null) {
			double qty;
			try {
				System.out.print("Enter product qty you want to buy : "); 
				qty = scanner.nextDouble();	
			}catch(Exception e) {
				System.out.println("Qty must be type of double!");
				scanner.nextLine();
				return;
			}
			
			if(product.isValidStock(qty)) {
				
				product.setQtyInStock(product.getQtyInStock() - qty); //reduce product in stock	
				double discount;
				try {
					System.out.print("Enter discount for this product if you have: "); 
					discount = scanner.nextDouble();	
				}catch(Exception e) {
					System.out.println("discount must be decimal!");
					scanner.nextLine();
					return;
				}
				Purchase purchase = new Purchase("" + purchaseNo++, product, qty, discount);
				cart.addItem(purchase);	
				scanner.nextLine();
			}
			else {
			
				System.out.print("Not enough stock!!! Product with code " + product.getID() + " has only " + product.getQtyInStock() + " in stock");
				scanner.nextLine();		
			}
		}else{
			System.out.println("Product ID not found");
		}	

		
	}
		
}

