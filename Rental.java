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
		this.video = video ;
		status = RentalStatus.RENTED;

		rentDate = new Date() ;
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
	public Date getRentDate() {
		return rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	// Feature Envy
	public int getDaysRentedLimit() {
		// date
		int limit = 0 ;
		int daysRented = getDaysRented();

		if ( daysRented <= 2) return limit ;

		// video
		switch ( video.getVideoType() ) {
			case Video.VHS: limit = 5 ; break ;
			case Video.CD: limit = 3 ; break ;
			case Video.DVD: limit = 2 ; break ;
		}
		return limit ;
	}

	public int getDaysRented() {
		int daysRented;
		if (getStatus() == 1) { // returned Video
			long diff = returnDate.getTime() - rentDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		} else { // not yet returned
			long diff = new Date().getTime() - rentDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		}
		return daysRented;
	}
}
