package handlers;

public class ItemHandler {
	
	private int itemId;
	private String itemName;
	private int price;
	private String desc;
	
	public ItemHandler(int itemId, String itemName, int price, String desc) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.price = price;
		this.desc = desc;
	}

	public int getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	@Override
	public String toString() {
		return "\nName: [" +this.getItemName()+ "],  Price: ["+this.getPrice()+"], Description: ["+this.getDesc()+"]";
	}

}
