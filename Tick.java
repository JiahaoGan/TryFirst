public class Tick {
	String timestamp;
	int ts_int;
	String ticker;
	int quant;
	float price;
	
	public Tick(String timestamp, int ts_int, String ticker, int quant, float price) {
		super();
		this.timestamp = timestamp;
		this.ts_int = ts_int;
		this.ticker = ticker;
		this.quant = quant;
		this.price = price;
	}

	public String getTimeStamp() {
		return timestamp;
	}

	public int getTs_int() {
		return ts_int;
	}

	public String getTicker() {
		return ticker;
	}

	public int getQuant() {
		return quant;
	}

	public float getPrice() {
		return price;
	}
	
}