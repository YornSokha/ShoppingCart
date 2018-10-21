package kh.edu.ckcc.assignment.classes;
import kh.edu.ckcc.assignment.drawtable.DrawingTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	public static Scanner in = new Scanner(System.in);

	public static List<Customer> customers = new ArrayList<Customer>();
	
	public static void main(String[] args) {	
		String option;
		
		List<Product> products = new ArrayList<Product>();
		Cart cart = new Cart();;
		/*Initialize 3 products and add to list of Products */		 
		products.add(new Product("001","Laptop","Product of Cambodia", 10, 1000));
		products.add(new Product("002","Desktop","Product of Cambodia", 100, 1100));
		products.add(new Product("003","Mouse","Product of Cambodia", 100, 10));
		
		
		do {
			option = menu();
			switch(option) {
			case "1":
					createNewProduct(products);
				break;
				
			case "2":
					showProductList(products);
				break;
				
			case "3":
					shoppingProduct(products, cart);
				break;
			case "4":
				String key;
				if(!cart.getPurchasedItems().isEmpty()) {
					do {
						showMyShoppingCart(cart);
						System.out.println("	1. Remove item");
						System.out.println("	2. Add more item(s)");
						System.out.println("	3. Back to Main Menu");
						key = in.nextLine();
						switch(key) {
						case "1":
							System.out.println("Input product ID or Name to remove : "); String x = in.nextLine();
								for(Purchase pur : cart.getPurchasedItems()) {
									if(pur.getProductName().equalsIgnoreCase(x) || pur.getOrderNo().equalsIgnoreCase(x)) {
											//cart.removeItem(pur);		
										if(cart.getPurchasedItemAmount() > 1) {
											pur.getProduct().setQtyInStock(pur.getProduct().getQtyInStock() + pur.getQty());
											cart.removeItem(pur);
										}
										else
											pur.getProduct().setQtyInStock(pur.getProduct().getQtyInStock() + pur.getQty());
											cart = new Cart();
										System.out.println("Product was successfully removed!!");
									}
									else {
										System.out.println("Invalid product name or Order ID");
									}
								}

							
							break;
						case "2":
							System.out.println("---------------------Shopping more product(s)---------------------");
								shoppingProduct(products, cart);
							break;
						}
					}while(!key.equalsIgnoreCase("3"));
				}	
				else {
					System.out.println("Sorry, you haven't shopped any product yet\nPlease go to buy our product first");	
					in.nextLine();
				}
				break;
			case "5":
				String ch;
				Customer customer = null;	
				do {
					System.out.println("	1. Add new Customer to check out");
					System.out.println("	2. Cancel Order");
					System.out.println("	3. View Shopping History");
					System.out.println("	4. Go back to Main Menu");
					ch = in.nextLine();
					switch(ch) {
					case "1":
						if(!cart.getPurchasedItems().isEmpty()) {
							System.out.println("-------------------------------Before Check out, give me your information-------------------------------");
							System.out.println("Enter your identification Number : "); String id = in.nextLine();
							System.out.println("Enter your name : "); String name = in.nextLine();
							System.out.println("Enter your email : "); String email = in.nextLine();
							System.out.println("Enter your shipping address : "); String shippingAddress = in.nextLine();
							System.out.println("Enter your billing address :  "); String billingAddress = in.nextLine();
							System.out.println("\n-------------------------------Do you have discount card?---------------------------------------------");
							System.out.println("\nEnter Pertage on your discount card : "); double discountCard = in.nextDouble();
							//
							customer = new Customer(id, name, email, shippingAddress, billingAddress);
							customer.placeOrder(cart);
							customer.getCart().setDiscount(discountCard);
							customers.add(customer);
							
							System.out.println("\n------------------------------------Your purchased Invoice----------------------------------------------");
							
							showCustomer(customer);
							cart = new Cart();
							System.out.println("Arigato!!!!");
							in.nextLine();
						}
						else {
							System.out.println("Please go to shopping frist!!!");
							in.nextLine();
						}
						break;
					case "2":
						if(!cart.getPurchasedItems().isEmpty()) {
							//customer.cancelOrder();
							customer = new Customer();
							System.out.println("Cart was completely removed!!!");
						}
						else {
							System.out.println("There is no product in cart...");
						}
						
						in.nextLine();

						break;
					case "3":
						String st;
						if(!customers.isEmpty()) {
							do {
								showShoppingHistory(customers);
								System.out.println("1. Search Customer by customer ID");
								System.out.println("2. Go back");
								st = in.nextLine();
								switch(st) {
								case "1":
									System.out.println("Enter Customer ID : "); String cusID = in.nextLine();
									searchCustomer(cusID);	
									break;
								}
							}while(!st.equalsIgnoreCase("2"));
						}
						else {
							System.out.println("No any customers to view!!!");
						}
						break;
					}
				}while(!ch.equals("4"));				
				break;
					
			}
		}while(!option.equals("6"));
	}
	
	public static String menu() {
		String option;
		Scanner in = new Scanner(System.in);
		DrawingTable drawingTable1 = new DrawingTable();
		
		drawingTable1.setHeaders("              Menu              ");
		drawingTable1.addRow("   1. Create New Product   ");
		drawingTable1.addRow("   2. List of Product(s)   ");
		drawingTable1.addRow("   3. Go to Shopping    ");
		drawingTable1.addRow("   4. My Shopping Cart   ");
		drawingTable1.addRow("   5. Check Out  ");
	
		drawingTable1.print();
		System.out.print("Enter your choice : ");
		option = in.nextLine();
		return option;
	}
	
	public static void createNewProduct(List<Product> products) {
		String choice;
		again:
			while(true) {
				System.out.println("-----------------Create New Product---------------\n");
				System.out.print("Enter Your Product ID : "); String proID = in.nextLine();
				//Checking the duplicated product id
					for(Product product : products) {
						if(product.isDuplicatedProductID(proID) == true) {
							System.out.print("The new entered Product is already existed");
							System.out.print("New Product ID? (Y/N)");
							String a = in.nextLine();
								if(a.equalsIgnoreCase("y"))
									continue again;
								else 
									break again;
						}
					}
				//
					System.out.print("\nEnter Your product name : "); String proName = in.nextLine();
					System.out.print("Enter Your Product Description : "); String description = in.nextLine();
					System.out.print("Enter Quanlity of Your Product : "); double qty = in.nextDouble();
					System.out.print("Enter Price of Your Product : "); double price = in.nextDouble();
					Product newProduct = new Product(proID, proName, description, qty, price);
					products.add(newProduct);
				System.out.println("Do you want to add more Product? (Y/N)");
				in.nextLine();
				choice = in.nextLine();
				if(choice.equalsIgnoreCase("Y")) continue again;
				else break;
			}
	}
	
	public static void showProductList(List<Product> products) {
		String choice;
		DrawingTable drawingTable;
		again:
			while(true) {
				System.out.println("----------------------Product List-------------------------\n");
				drawingTable = new DrawingTable();
				drawingTable.setHeaders(" ID ", " Name ", " QTY ", " Price ", " Description ");
				for(Product product : products) {
					drawingTable.addRow(product.getID() , product.getName(), product.getQtyInStock() + "", product.getPrice() + "", product.getDescription());
				}
				
				drawingTable.print();
				System.out.println("Do you want to continue to Main Menu? (Y/N)");
				choice = in.nextLine();
				if(choice.equalsIgnoreCase("y")) {
					break again;
				}
				else {
					continue again;
				}
			}
	}
	
	public static void showShoppingHistory(List<Customer> customer) {
		DrawingTable drawingTable = new DrawingTable();
		System.out.println("-----------------Shopping History------------------");
		
		drawingTable.setHeaders("Customer ID", "Customer Name", "Shipping Add", "Total");
		System.out.println("Customer ID\tCustomer Name\tTotal");
		for(Customer cus : customers) {
			drawingTable.addRow(cus.getCustomerID(), cus.getCustomerName(), cus.getShippingAddress(), cus.getCart().calculateTotal() + "");
		}
		
		drawingTable.print();
		
//		System.out.println("1. Search Customer by customer ID");
//		System.out.println("2. Go back");
//		in.nextLine();
		
		
	}
	
	public static void showMyShoppingCart(Cart cart) {
		DrawingTable drawingTable = new DrawingTable();
		System.out.println("--------------------My Shopping Cart----------------------------\n");
		
		drawingTable.setHeaders(" NO ", " Name ", " Qty ", "Unit Price", " Discount ", " Price " );
		
		for(Purchase item : cart.getPurchasedItems()) {
			drawingTable.addRow(item.getOrderNo().toString(), item.getProductName(), 
					item.getQty() + "","$ " +item.getProductPrice(), item.getDiscount() + "",
					item.getPrice() + "");
		}
		drawingTable.print();
	}
	
	public static void showCustomer(Customer customer) {
		
		System.out.println(customer.toString());
		showMyShoppingCart(customer.getCart());
		System.out.println("\nSubTotal : " + customer.getCart().calculateSubTotal());
		System.out.println("Discount : " + customer.getCart().getDiscount());
		System.out.println("Total : " + customer.getCart().calculateTotal());
		in.nextLine();
		
	}
	
	public static void searchCustomer(String customerID) {
		boolean i = false;
		for(Customer customer : customers) {
			if(customer.getCustomerID().equalsIgnoreCase(customerID)) {
				showCustomer(customer);
				i = true;
			}
		}
		if(i == false) {
			System.out.println("Invalid Customer ID");
		}
	}
	
	public static void shoppingProduct(List<Product> products, Cart cart) {
		String choice;
		//cart = new Cart();
		System.out.println("\n-----------------------Let's go shopping products you want---------------------------------");
		again:
			while(true) {
				boolean i = false;
				System.out.print("Enter product code you want to buy : "); String proCode = in.nextLine();
				//
				for(Product product : products) {
					if(product.getID().equalsIgnoreCase(proCode)) {
						System.out.print("Enter product qty you want to buy : "); double qty = in.nextDouble();
						for(Product product1 : products) {
							if(product1.getID().equalsIgnoreCase(proCode)) {
								i = true;
								if(product1.isValidStock(qty) == false) {
									System.out.print("Not enough stock!!! Product with code " + product1.getID() + " has only " + product1.getQtyInStock() + " in stock");
									in.nextLine();
								}
								else 
									{
										product1.setQtyInStock(product1.getQtyInStock() - qty); //reduce product in stock	
										System.out.print("Enter discount for this product if you have: "); double discount = in.nextDouble();
										Purchase purchase = new Purchase(proCode, product1, qty, discount);
							
										cart.addItem(purchase);	
										
										System.out.println("------------------------------------------------------------");
										System.out.println("Do you want to shop more product?(Y/N)");
										in.nextLine();
										choice = in.nextLine(); 
										if(choice.equalsIgnoreCase("y")) continue again;
										else break again;
										
									}
							}
						}
					}
				}
				
				if(i == false) {
					System.out.println("Product ID not found");
				}	
				
				System.out.println("------------------------------------------------------------");
				System.out.println("Do you want to shop other product?(Y/N)");
				choice = in.nextLine(); 
			
				System.out.println(choice);
				if(choice.equalsIgnoreCase("y")) continue again;
				else break again;
			}
		
	}
	
}

