import java.util.ArrayList;
import java.util.List;

public class Customer {

	private String name;

	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public void addRental(Rental rental) {
		rentals.add(rental);

	}

	// Feature Envy / Divergent Change
	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		List<Rental> rentals = getRentals();

		double totalCharge = 0;
		//int totalPoint = 0;

		for (Rental rental : rentals) {
			int daysRented = rental.getDaysRented();
			double charge = rental.getCharge(rental, daysRented);
			//int point = rental.getPoint(daysRented);

			result += "\t" + rental.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + charge
					+ "\tPoint: " + point + "\n";

			// Charge - Point
			totalCharge += charge;
			//totalPoint += point ;
		}

		result += "Total charge: " + totalCharge + "\tTotal Point:" + getTotalPoint() + "\n";


		if ( totalPoint >= 10 ) {
			System.out.println("Congrat! You earned one free coupon");
		}
		if ( totalPoint >= 30 ) {
			System.out.println("Congrat! You earned two free coupon");
		}
		return result ;
	}

	public int getTotalPoint() {
		int totalPoint = 0;
		for (Rental rental : rentals) {
			totalPoint += rental.getPoint(daysRented);
		}
		return totalPoint;
	}

}
