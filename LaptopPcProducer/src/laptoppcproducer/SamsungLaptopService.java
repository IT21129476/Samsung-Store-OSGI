package laptoppcproducer;

public interface  SamsungLaptopService {
	public void defaultList();
	public void printAccessoriesList();
	public String LoginVerification(String username,String password);
	public void addAccessories(String model, float Price);
	public void removeAccessories(int itemId);
	public int getListSize();
	public float calculateBill(int itemId, int Qty, int count);
	public float calcSubTotal(float total);
	public float calcBalance(float subTotal, float cash);

}
