package handlers;

public class ItemHandler {
	
	private int id;
	private String itemName;
	private int price;
	private String desc;
	
	public ItemHandler(int id, String itemName, int price, String desc) {
		this.id = id;
		this.itemName = itemName;
		this.price = price;
		this.desc = desc;
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "\nID: ["+this.getId()+"] Name: [" +this.getItemName()+ "], Price: ["+this.getPrice()+"], Description: ["+this.getDesc()+"]";
	}

}
