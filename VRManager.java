import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VRManager {
    private final List<Customer> customers = new ArrayList<>() ;
    private final List<Video> videos = new ArrayList<>() ;
    private static final VRUI vrUI = new VRUI();

    public static void main(String[] args) {
        VRManager vrManager = new VRManager();

        boolean quit = false ;
        while ( ! quit ) {
            int command = vrUI.showCommand() ;
            switch (command) {
                case 0 -> quit = true;
                case 1 -> vrManager.listCustomers();
                case 2 -> vrManager.listVideos();
                case 3 -> vrManager.registerCustomer();
                case 4 -> vrManager.registerVideo();
                case 5 -> vrManager.rentVideo();
                case 6 -> vrManager.returnVideo();
                case 7 -> vrManager.getCustomerReport();
                case 8 -> vrManager.clearRentals();
                case -1 -> vrManager.init();
                default -> {
                }
            }
        }
        vrUI.notifyMessage("Bye");
    }

    public void clearRentals() {
        String customerName = vrUI.getCustomerName();
        // duplication
        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }

        if ( foundCustomer == null ) {
            vrUI.printNoCustomerErrorMessage();
        } else {
            // CQRS
            // : Query
            vrUI.notifyRentalInfo(foundCustomer.getName(), foundCustomer.getRentals().size());
            for ( Rental rental: foundCustomer.getRentals() ) {
                vrUI.notifyVideoInfo(rental.getVideo().getTitle(), rental.getVideo().getPriceCode());
            }
            // : Command
            addRentalsToCustomer(foundCustomer);
        }
    }

    private static void addRentalsToCustomer(Customer foundCustomer) {
        List<Rental> rentals = new ArrayList<>() ;
        foundCustomer.setRentals(rentals);
    }

    public void returnVideo() {
        String customerName = vrUI.getCustomerName();
        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }
        if ( foundCustomer == null ) return ;

        String videoTitle = vrUI.getVideoTitleToRent();
        List<Rental> customerRentals = foundCustomer.getRentals() ;
        for ( Rental rental: customerRentals ) {
            // introduce delegation
            if ( rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented() ) {
                rental.returnVideo();
                rental.getVideo().setRented(false);
                break ;
            }
        }
    }

    public void listVideos() {
        vrUI.notifyMessage("List of videos");

        for ( Video video: videos ) {
            vrUI.notifyVideoInfo(video.getTitle(), video.getPriceCode()) ;
        }
        vrUI.notifyMessage("End of list");
    }

    public void listCustomers() {
        vrUI.notifyMessage("List of customers");
        for ( Customer customer: customers ) {
            vrUI.notifyRentalInfo(customer.getName(), customer.getRentals().size());
            for ( Rental rental: customer.getRentals() ) {
                vrUI.notifyVideoInfo(rental.getVideo().getTitle(), rental.getVideo().getPriceCode());
            }
        }
        vrUI.notifyMessage("End of list");
    }

    public void getCustomerReport() {
        String customerName = vrUI.getCustomerName();

        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }

        if ( foundCustomer == null ) {
            vrUI.printNoCustomerErrorMessage();
        } else {
            // report를 어디에 둘건가? VRUI or Customer
            String result = foundCustomer.getReport() ;
            checkCoupon(foundCustomer);
            System.out.println(result);
        }
    }

    private static void checkCoupon(Customer foundCustomer) {
        int totalPoint = foundCustomer.getTotalPoint();
        if ( totalPoint >= 10 ) {
            System.out.println("Congrat! You earned one free coupon");
        }
        if ( totalPoint >= 30 ) {
            System.out.println("Congrat! You earned two free coupon");
        }
    }

    // SRP 위반 (-> video, rental로 이동 필요)
    public void rentVideo() {
        String customerName = vrUI.getCustomerName();

        Customer foundCustomer = null ;
        for ( Customer customer: customers ) {
            if ( customer.getName().equals(customerName)) {
                foundCustomer = customer ;
                break ;
            }
        }

        if ( foundCustomer == null ) return ;
        String videoTitle = vrUI.getVideoTitleToRent();

        Video foundVideo = null ;
        for ( Video video: videos ) {
            if ( video.getTitle().equals(videoTitle) && !video.isRented()) {
                foundVideo = video ;
                break ;
            }
        }

        if ( foundVideo == null ) return ;

        Rental rental = new Rental(foundVideo) ;
        foundVideo.setRented(true);

        // encapsulate collection
        List<Rental> customerRentals = foundCustomer.getRentals() ;
        customerRentals.add(rental);
        foundCustomer.setRentals(customerRentals);
    }

    // customer, video 따로 등록

    public void registerCustomer(){
        String name = vrUI.getCustomerName();
        Customer customer = new Customer(name) ;
        customers.add(customer) ;
    }

    public void registerVideo(){
        String title = vrUI.getVideoTitleToRegister();
        int videoType = vrUI.getVideoType();
        int priceCode = vrUI.getPriceCode();

        Date registeredDate = new Date();
        Video video = new Video(title, videoType, priceCode, registeredDate) ;
        videos.add(video) ;
    }

    private void init() {
        Customer james = new Customer("James") ;
        Customer brown = new Customer("Brown") ;
        customers.add(james) ;
        customers.add(brown) ;

        Video v1 = new Video("v1", Video.CD, Video.REGULAR, new Date()) ;
        Video v2 = new Video("v2", Video.DVD, Video.NEW_RELEASE, new Date()) ;
        videos.add(v1) ;
        videos.add(v2) ;

        Rental r1 = new Rental(v1) ;
        Rental r2 = new Rental(v2) ;

        james.addRental(r1) ;
        james.addRental(r2) ;
    }
}
