import java.util.Date;

// Data Class
public class Rental {
	private Video video ;
	private enum RentalStatus {
		RENTED = 0,
		RETRUNED
	}
	private RentalStatus status;
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.video = video ;
		status = RENTED;

		rentDate = new Date() ;
	}

	public Video getVideo() {
		return video;
	}

	public int getStatus() {
		return status;
	}

	public void returnVideo() {
		if ( status == RETURNED ) {
			this.status = RETURNED;
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
		int daysRented ;
		if (getStatus() == RETRUNED) { // returned Video
			long diff = returnDate.getTime() - rentDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		} else { // not yet returned
			long diff = new Date().getTime() - rentDate.getTime();
			daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		}
		if ( daysRented <= 2) return limit ;

		// video
		switch ( video.getVideoType() ) {
			case Video.VHS: limit = 5 ; break ;
			case Video.CD: limit = 3 ; break ;
			case Video.DVD: limit = 2 ; break ;
		}
		return limit ;
	}
}
