import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {

	private static final double REGULAR_VIDEO_CHARGE_RATE = 1.5;
	private static final double NEW_RELEASE_VIDEO_CHARGE_RATE = 3.0;
	private static final double REGULAR_VIDEO_CHARGE_BASE_PRICE = 2.0;
	private static final int REGULAR_VIDEO_EXTRA_CHARGE_OVER_DATE = 2;
	
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
		int totalPoint = 0;

		for (Rental each : rentals) {
			double eachCharge = 0;
			int eachPoint = 0 ;
			int daysRented = 0;

			if (each.getStatus() == 1) { // returned Video
				long diff = each.getReturnDate().getTime() - each.getRentDate().getTime();
				daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
			} else { // not yet returned
				long diff = new Date().getTime() - each.getRentDate().getTime();
				daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
			}

			// Type Code
			switch (each.getVideo().getPriceCode()) {
				case Video.REGULAR:
					eachCharge += REGULAR_VIDEO_CHARGE_BASE_PRICE;
					/**
					 * Magic Number가 무엇을 의미??
					 */
					if (daysRented > REGULAR_VIDEO_EXTRA_CHARGE_OVER_DATE)
						eachCharge += (daysRented - REGULAR_VIDEO_EXTRA_CHARGE_OVER_DATE) * REGULAR_VIDEO_CHARGE_RATE;
					break;
				case Video.NEW_RELEASE:
					eachCharge = daysRented * NEW_RELEASE_VIDEO_CHARGE_RATE;
					break;
			}

			eachPoint++;

			if ((each.getVideo().getPriceCode() == Video.NEW_RELEASE) )
				eachPoint++;

			if ( daysRented > each.getDaysRentedLimit() )
				eachPoint -= Math.min(eachPoint, each.getVideo().getLateReturnPointPenalty()) ;

			result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n";

			// Charge - Point
			totalCharge += eachCharge;

			totalPoint += eachPoint ;
		}

		result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";


		if ( totalPoint >= 10 ) {
			System.out.println("Congrat! You earned one free coupon");
		}
		if ( totalPoint >= 30 ) {
			System.out.println("Congrat! You earned two free coupon");
		}
		return result ;
	}
}
