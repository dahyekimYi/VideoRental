import java.util.Date;

// Data Class
public class Rental {

	public static final double REGULAR_VIDEO_CHARGE_RATE = 1.5;
	public static final double NEW_RELEASE_VIDEO_CHARGE_RATE = 3.0;
	public static final double REGULAR_VIDEO_CHARGE_BASE_PRICE = 2.0;
	public static final int REGULAR_VIDEO_EXTRA_CHARGE_OVER_DATE = 2;

	private Video video ;

	public enum RentalStatus {
		RENTED,
		RETURNED
	}
	private RentalStatus status;
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video;
		this.video.setRented(true);
		status = RentalStatus.RENTED;
		rentDate = new Date();
	}

	public Video getVideo() {
		return video;
	}

	public RentalStatus getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == RentalStatus.RETURNED ) {
			this.status = RentalStatus.RETURNED;
			returnDate = new Date() ;
		}
	}

	public int getDaysRentedLimit() {
		// date
		int limit = 0 ;
		int daysRented = getDaysRented();

		if ( daysRented <= 2) return limit ;

		return video.getDaysRentedLimit();
	}

	public int getDaysRented() {
		int daysRented;
		if (getStatus() == RentalStatus.RETURNED) { // returned Video
			long diff = returnDate.getTime() - rentDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		} else { // not yet returned
			long diff = new Date().getTime() - rentDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		}
		return daysRented;
	}

	double getCharge(Rental rental, int daysRented) {
		double charge = 0;
		switch (rental.getVideo().getPriceCode()) {
			case Video.REGULAR:
				charge += REGULAR_VIDEO_CHARGE_BASE_PRICE;
				/**
				 * Magic Number가 무엇을 의미??
				 */
				if (daysRented > REGULAR_VIDEO_EXTRA_CHARGE_OVER_DATE)
					charge += (daysRented - REGULAR_VIDEO_EXTRA_CHARGE_OVER_DATE) *
							REGULAR_VIDEO_CHARGE_RATE;
				break;
			case Video.NEW_RELEASE:
				charge = daysRented * NEW_RELEASE_VIDEO_CHARGE_RATE;
				break;
		}
		return charge;
	}

	int getPoint(int daysRented) {
		int point = 0;
		point++;

		if ((getVideo().getPriceCode() == Video.NEW_RELEASE) )
			point++;

		if ( daysRented > getDaysRentedLimit() )
			point -= Math.min(point, getVideo().getLateReturnPointPenalty()) ;
		return point;
	}

}
