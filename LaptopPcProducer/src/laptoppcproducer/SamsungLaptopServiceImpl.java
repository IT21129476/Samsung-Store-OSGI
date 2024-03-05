package laptoppcproducer;

import java.util.ArrayList;

public class SamsungLaptopServiceImpl implements  SamsungLaptopService{
	
	ArrayList<String> accessCategory = new ArrayList<String>();
	ArrayList<Float> accessPrice = new ArrayList<Float>();
	
	@Override
	public void defaultList() {
		
		accessCategory.add("Galaxy Book3 Ultra");
		accessPrice.add((float) 787428.00);
		
		accessCategory.add("Samsung Galaxy Book3 Ultra 16");
		accessPrice.add((float) 125142.00);
		
		accessCategory.add("Samsung Galaxy Book Go 14");
		accessPrice.add((float) 35000);
		
		accessCategory.add("Samsung Galaxy Book3 360");
		accessPrice.add((float) 461142.00);
		
	}
	
	@Override
	public void printAccessoriesList() {
		// TODO Auto-generated method stub
		System.out.println("--------------- Laptop List ---------------");
		for (int i=0; i < accessPrice.size(); i++)
			{
				System.out.println(i+1 + " " +accessCategory.get(i) +" Rs:"+accessPrice.get(i));
			}
		System.out.println("--------------------------------------------------");
	}

	@Override
	public String LoginVerification(String username, String password) {
		// TODO Auto-generated method stub
		if(username.equals("manager") && (password.equals("123"))) {
			return "manager";
		}else {
			return "invalid";
		}
	}

	@Override
	public void addAccessories(String model, float Price) {
		// TODO Auto-generated method stub
		accessCategory.add(model);
		accessPrice.add(Price);
		System.out.println("New Product Added Successfully");
		
	}

	@Override
	public void removeAccessories(int itemId) {
		// TODO Auto-generated method stub
		accessCategory.remove(itemId-1);
		accessPrice.remove(itemId-1);
		System.out.println("Product Removed Successfully");
		
	}

	@Override
	public int getListSize() {
		// TODO Auto-generated method stub
		return accessCategory.size();
	}

	@Override
	public float calculateBill(int itemId, int Qty, int count) {
		// TODO Auto-generated method stub
		float lapPrice = accessPrice.get(itemId-1);
		float total = (lapPrice*Qty);
		System.out.println((count+1) +") "+ accessCategory.get(itemId-1) +" x "+ Qty + " = " + total + "Rupees");
		return total;
	}

	@Override
	public float calcSubTotal(float total) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float calcBalance(float subTotal, float cash) {
		// TODO Auto-generated method stub
		return 0;
	}

}
