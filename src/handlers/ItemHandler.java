package handlers;

public class ItemHandler {
	
	private int id;
	private String itemName;
	private boolean isCombatUseable;
	private int price;
	private String desc;
	
	public ItemHandler(int id, String itemName, boolean isCombatUseable, int price, String desc) {
		this.id = id;
		this.itemName = itemName;
		this.isCombatUseable = isCombatUseable;
		this.price = price;
		this.desc = desc;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public boolean getIsCombatUseable() {
		return isCombatUseable;
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
		return "\nID: ["+this.getId()+"] Name: [" +this.getItemName()+ "], Combat Useable: ["+this.getIsCombatUseable()+"], Price: ["+this.getPrice()+"], Description: ["+this.getDesc()+"]";
	}

}
