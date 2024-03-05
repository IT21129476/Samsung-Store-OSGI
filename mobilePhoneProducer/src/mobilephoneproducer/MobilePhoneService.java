package mobilephoneproducer;

public interface MobilePhoneService {
    void printMobilesList();
    String getMobileSpecs(int itemId);
    void addToCart(int itemId, int qty);
    String generateInvoice();
    boolean login(String username, String password);
    void addMobile(String model, float price, String specs);
    void removeMobile(int itemId);
    int getMobilesCount();
    
}
