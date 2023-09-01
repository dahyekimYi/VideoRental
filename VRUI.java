import java.util.Scanner;

public class VRUI {
	private static final Scanner scanner = new Scanner(System.in);

	public String getCustomerName(){
		System.out.println("Enter customer name: ") ;
        return scanner.next();
	}

	public String getVideoTitleToRent(){
		System.out.println("Enter video title to rent: ") ;
		return scanner.next();
	}

	public String getVideoTitleToRegister(){
		System.out.println("Enter video title to register: ") ;
		return scanner.next();
	}

	public int getVideoType(){
		System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
		return scanner.nextInt();
	}

	public int getPriceCode(){
		System.out.println("Enter price code( 1 for Regular, 2 for New Release ):") ;
		return scanner.nextInt();
	}

	public void printNoCustomerErrorMessage(){
		System.out.println("No customer found");
	}

	public void notifyMessage(String message){
		System.out.println(message);
	}

	public void notifyRentalInfo(String name, int rentalSize){
		System.out.println("Name: " + name +
				"\tRentals: " + rentalSize) ;
	}

	public void notifyVideoInfo(String title, int priceCode){
		System.out.println("\tTitle: " + title + " ") ;
		System.out.println("\tPrice Code: " + priceCode) ;
	}

	public int showCommand() {
		System.out.println("\nSelect a command !");
		System.out.println("\t 0. Quit");
		System.out.println("\t 1. List customers");
		System.out.println("\t 2. List videos");
		System.out.println("\t 3. Register customer");
		System.out.println("\t 4. Register video");
		System.out.println("\t 5. Rent video");
		System.out.println("\t 6. Return video");
		System.out.println("\t 7. Show customer report");
		System.out.println("\t 8. Show customer and clear rentals");

        return scanner.nextInt();

	}
}
