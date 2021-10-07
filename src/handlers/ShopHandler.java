package handlers;

/**
 * @author Joshua Jean-Philippe
 * We will construct shops with this
 */
public class ShopHandler {

    private int itemId;
	private int stock;
	
	public ShopHandler(int itemId, int stock) {
        this.itemId = itemId;
		this.stock = stock;
	}

	public int getItemId() {
		return itemId;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
