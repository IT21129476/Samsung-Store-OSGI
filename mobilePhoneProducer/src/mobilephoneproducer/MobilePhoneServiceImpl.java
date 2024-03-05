package mobilephoneproducer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MobilePhoneServiceImpl implements MobilePhoneService {

    private final ArrayList<String> mobileModels = new ArrayList<>();
    private final ArrayList<Float> mobilePrices = new ArrayList<>();
    private final Map<String, String> mobileSpecs = new HashMap<>();
    private final Map<Integer, Integer> cart = new HashMap<>();

    public MobilePhoneServiceImpl() {
        
        mobileModels.add("Samsung Galaxy S21"); mobilePrices.add(70000f); mobileSpecs.put("Samsung Galaxy S21", "8GB RAM, 128GB Storage, Exynos 2100");
        mobileModels.add("Samsung Galaxy S22"); mobilePrices.add(80000f); mobileSpecs.put("Samsung Galaxy S22", "8GB RAM, 256GB Storage, Snapdragon 888");
        mobileModels.add("Samsung Galaxy S23"); mobilePrices.add(90000f); mobileSpecs.put("Samsung Galaxy S23", "12GB RAM, 512GB Storage, Exynos 2100");
        mobileModels.add("Samsung Galaxy Note20"); mobilePrices.add(100000f); mobileSpecs.put("Samsung Galaxy Note20", "8GB RAM, 256GB Storage, Snapdragon 865+");
        mobileModels.add("Samsung Galaxy Z Flip3"); mobilePrices.add(85000f); mobileSpecs.put("Samsung Galaxy Z Flip3", "8GB RAM, 128GB Storage, Snapdragon 888");
        mobileModels.add("Samsung Galaxy A52"); mobilePrices.add(35000f); mobileSpecs.put("Samsung Galaxy A52", "6GB RAM, 128GB Storage, Snapdragon 720G");
    }

    
    @Override
    public void printMobilesList() {
    	System.out.println("|---------------------------------------|");
        System.out.println("|*** Available Samsung Mobile Phones ***|");
        System.out.println("|---------------------------------------|");
        for (int i = 0; i < mobileModels.size(); i++) {
            System.out.println((i + 1) + ". " + mobileModels.get(i) + " - Rs:" + mobilePrices.get(i));
        }
        System.out.println("|---------------------------------------|\n");
    }

    @Override
    public String getMobileSpecs(int itemId) {
        if (itemId >= 1 && itemId <= mobileModels.size()) {
        	System.out.println("|--------------------------------------------------------------|");
        	System.out.println("|************************ Specification ***********************|");
        	System.out.println("|--------------------------------------------------------------|\n");
            String model = mobileModels.get(itemId - 1);
            return model + " - " + mobileSpecs.get(model);
            
        }
        return "Invalid Product ID.";
    }

    @Override
    public void addToCart(int itemId, int qty) {
        if (itemId >= 1 && itemId <= mobileModels.size()) {
            cart.put(itemId, cart.getOrDefault(itemId, 0) + qty);
            System.out.println( mobileModels.get(itemId - 1) + ", Quantity: " + qty + " added to cart  successfully " );
        } else {
            System.out.println("Invalid Product ID.");
        }
    }

    @Override
    public String generateInvoice() {
        if (cart.isEmpty()) {
            return "Cart is Empty.";
        }
        StringBuilder invoice = new StringBuilder();
        float subtotal = 0f;
        System.out.println("|---------------------------------------------------------------|");
        System.out.println("|   ********************     Invoice     ********************   |");
        System.out.println("|---------------------------------------------------------------|");
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            int itemId = entry.getKey();
            int qty = entry.getValue();
            String model = mobileModels.get(itemId - 1);
            float price = mobilePrices.get(itemId - 1);
            float total = price * qty;
            subtotal += total;
            invoice.append(model).append(" ").append(qty).append(" Qty -> Rs.").append(price).append(" * ").append(qty).append(" = Rs.").append(total).append("\n");
        }
        float discount = subtotal * 0.10f; 
        float totalAfterDiscount = subtotal - discount;
      
        invoice.append("Subtotal: Rs.").append(subtotal).append("\n");
        invoice.append("Discount (10%): Rs.").append(discount).append("\n");
       
        invoice.append("Total : Rs.").append(totalAfterDiscount).append("\n");
       
        invoice.append("Thank you for making a purchase with us. Come again!");
        return invoice.toString();
    }

    @Override
    public boolean login(String username, String password) {
        return "manager".equals(username) && "123".equals(password);
    }

    @Override
    public void addMobile(String model, float price, String specs) {
        mobileModels.add(model);
        mobilePrices.add(price);
        mobileSpecs.put(model, specs);
        System.out.println("Product added successfully.");
        printMobilesList();
    }

    @Override
    public void removeMobile(int itemId) {
        if (itemId > 0 && itemId <= mobileModels.size()) {
            String model = mobileModels.remove(itemId - 1);
            mobilePrices.remove(itemId - 1);
            mobileSpecs.remove(model);
            System.out.println("Product removed successfully.");
            printMobilesList();
        } else {
            System.out.println("Invalid Product ID.");
        }
    }
    
    @Override
    public int getMobilesCount() {
        return mobileModels.size();
    }
}














