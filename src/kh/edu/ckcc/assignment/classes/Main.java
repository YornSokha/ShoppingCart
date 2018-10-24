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
//		Scanner scanner = new Scanner(System.in);
		/*Initialize 3 products and add to list of Products */		 
		products.add(new Product("001","Laptop","Product of Cambodia", 10, 1000));
		products.add(new Product("002","Desktop","Product of Cambodia", 100, 1100));
		products.add(new Product("003","Mouse","Product of Cambodia", 100, 10));
		
		boolean quit = false;
		while(!quit) {
			option = menu();
			switch(option) {
				case "1":
						createNewProduct();
					break;
					
				case "2":
						showProductList();
					break;
					
				case "3":
						shoppingProduct(cart);
					break;
				case "4":
					String key;
					boolean back = false;
					if(!cart.getPurchasedItems().isEmpty()) {
						do {
							showMyShoppingCart(cart);
							System.out.println("	1. Add more purchase");
							System.out.println("	2. Remove purchase");
							System.out.println("	3. Back");
							key = scanner.nextLine();
							switch(key) {
							case "1":
								System.out.println("---------------------Shopping more product(s)---------------------");
								shoppingProduct(cart);
								break;
								
							case "2":
								System.out.println("Input purchaseNo to remove : "); String purNo = scanner.nextLine();
								boolean exist = false;
									for(Purchase pur : cart.getPurchasedItems()) {
										if(pur.getOrderNo().equals(purNo)) {
												//cart.removeItem(pur);		
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
					                                
					                        }
					                        cart.removeItem(pur);				         
					                        System.out.println("You products have been removed!");
					                        exist = true;
					                        break;
										}
										
									}
									if(!exist){
										System.out.println("Invalid product name or Order ID");
									}
								
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
					Customer customer = null;	
					do {
						System.out.println("	1. Add new Customer to check out");
						System.out.println("	2. Cancel Order");
						System.out.println("	3. Go back to Main Menu");
						ch = scanner.nextLine();
						switch(ch) {
						case "1":
							if(!cart.getPurchasedItems().isEmpty()) {
								System.out.println("-------------------------------Before Check out, give me your information-------------------------------");
								System.out.println("Enter your identification Number : "); String id = scanner.nextLine();
								System.out.println("Enter your name : "); String name = scanner.nextLine();
								System.out.println("Enter your email : "); String email = scanner.nextLine();
								System.out.println("Enter your shipping address : "); String shippingAddress = scanner.nextLine();
								System.out.println("Enter your billing address :  "); String billingAddress = scanner.nextLine();
								System.out.println("\n-------------------------------Do you have discount card?---------------------------------------------");
								System.out.println("\nEnter Pertage on your discount card : "); double discountCard = scanner.nextDouble();
								//
								cart.setDiscount(discountCard);
								customer = new Customer(id, name, email, shippingAddress, billingAddress);
								customer.placeOrder(cart);
								customers.add(customer);
								
								System.out.println("\n------------------------------------Your purchased Invoice----------------------------------------------");
								
								showCustomer(customer);
								cart = new Cart();
								System.out.println("Arigato!!!!");
								scanner.nextLine();
							}
							else {
								System.out.println("Please go to shopping frist!!!");
								scanner.nextLine();
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
							scanner.nextLine();
							break;
						}
					}while(!ch.equals("3"));				
					break;				
				case "6":
					String st;
					if(!customers.isEmpty()) {
						do {
							showShoppingHistory();
							System.out.println("1. Search Customer's invoice");
							System.out.println("2. Go back");
							st = scanner.nextLine();
							switch(st) {
								case "1":
									System.out.println("Enter Customer ID : "); 
									String cusID = scanner.nextLine();
									searchCustomer(cusID);	
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
//            if(scanner.next().equalsIgnoreCase("Y"))
//                menu();
//            else
//                quit = true;
			if(!scanner.nextLine().equalsIgnoreCase("Y"))
				quit = true;
		}
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
		drawingTable1.addRow("   6. View Shopping History");
	
		drawingTable1.print();
		System.out.print("Enter your choice : ");
		option = in.nextLine();
		return option;
	}
	
    private static Product findProduct(String id) {
        for(Product product : products)
            if(product.getID().equalsIgnoreCase(id))
                return product;
        return null;
    }
    
	public static void createNewProduct() {
		 do{
             System.out.println("----------------- Create New Product --------------------\n");
             System.out.print("Enter your product code:\n");
//             scanner.nextLine();
             String code = scanner.nextLine();
             Product p = findProduct(code);
             if(p == null) {
            	 System.out.print("Enter your product name:\n");
                 String name = scanner.nextLine();
                 System.out.print("Enter your product description:\n");
                 String description = scanner.nextLine();
                 System.out.print("Enter your product price:\n");
                 double price = scanner.nextDouble();
                 System.out.print("Enter your product quantity:\n");
                 double qty = scanner.nextDouble();
                 addNewProduct(new Product(code, name, description, price, qty));
                 System.out.println("Product name : " + name + " with code " + code + " is added into stock.");
             }
             else
                 System.out.println("Product code : " + code + " is already existed!");
             System.out.println("---------------------------------------------------\n" +
                     "Do you want to add more product? (Y/N)");
             scanner.nextLine();
         }while(scanner.nextLine().equalsIgnoreCase("Y"));
      
	}
	
    private static void addNewProduct(Product product){
        products.add(product);
    }
	
	public static void showProductList() {
		DrawingTable drawingTable;
		System.out.println("----------------------Product List-------------------------\n");
		drawingTable = new DrawingTable();
		drawingTable.setHeaders(" ID ", " Name ", " QTY ", " Price ", " Description ");
		for(Product p : products) {
			drawingTable.addRow(p.getID() , p.getName(), p.getQtyInStock() + "", "$ " +  p.getPrice() + "", p.getDescription());		
			}
		drawingTable.print();
	}
	
	public static void showShoppingHistory() {
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
			drawingTable.addRow(item.getOrderNo(), item.getProductName(), 
					item.getQty() + "","$ " + item.getProductPrice(), (item.getDiscount() * 100) + "",
					"$ " + item.getPrice() + "");
		}
		drawingTable.print();
	}
	
	public static void showCustomer(Customer customer) {
		
		System.out.println(customer.toString());
		showMyShoppingCart(customer.getCart());
		System.out.println("\nSubTotal : " + customer.getCart().calculateSubTotal());
		System.out.println("Discount : " + customer.getCart().getDiscount());
		System.out.println("Total : " + customer.getCart().calculateTotal());
		scanner.nextLine();
		
	}
	
	public static void searchCustomer(String customerID) {
		boolean i = false;
		for(Customer customer : customers) {
			if(customer.getCustomerID().equals(customerID)) {
				showCustomer(customer);
				i = true;
			}
		}
		if(i == false) {
			System.out.println("Invalid Customer ID");
		}
	}
	
	public static void shoppingProduct(Cart cart) {
		String choice;
		//cart = new Cart();
		System.out.println("\n-----------------------Let's go shopping products you want---------------------------------");
		again:
			while(true) {
				boolean i = false;
				System.out.print("Enter product code you want to buy : "); 
				String proCode = scanner.nextLine();
				System.out.println(proCode);
				Product product = findProduct(proCode);
				if(product != null) {
					i = true;
					System.out.print("Enter product qty you want to buy : "); double qty = scanner.nextDouble();
							if(product.isValidStock(qty)) {
								
								product.setQtyInStock(product.getQtyInStock() - qty); //reduce product in stock	
								System.out.print("Enter discount for this product if you have: "); double discount = scanner.nextDouble();
								Purchase purchase = new Purchase("" + purchaseNo++, product, qty, discount);
								cart.addItem(purchase);	
								System.out.println("------------------------------------------------------------");
								System.out.println("Do you want to shop more product?(Y/N)");
								scanner.nextLine();
								choice = scanner.nextLine(); 
								if(choice.equalsIgnoreCase("y")) continue again;
								else break again;
							}
							else {
							
								System.out.print("Not enough stock!!! Product with code " + product.getID() + " has only " + product.getQtyInStock() + " in stock");
								scanner.nextLine();		
							}
				}
				
				if(i == false) {
					System.out.println("Product ID not found");
				}	
				
				System.out.println("------------------------------------------------------------");
				System.out.println("Do you want to shop other product?(Y/N)");
				choice = scanner.nextLine(); 
			
				System.out.println(choice);
				if(choice.equalsIgnoreCase("y")) continue again;
				else break again;
			}
		
	}
	
}

