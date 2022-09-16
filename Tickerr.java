import java.util.ArrayList;

public class Tickerr {
	String name;
	int cum_quant;
	int show_quant;
	float show_notional;
	int quantity_block;
	int block1;
	int block2;
	ArrayList<Order> Orders=new ArrayList<>();
	
	
	public Tickerr(String name, int qb, int qua, float pri) {
		super();
		this.name = name;
		this.quantity_block=qb;
		this.block1=cum_quant/qb;
		this.block2=0;
		update(qua,pri);
	}
	
	public boolean checkBlock() {
		block1=cum_quant/quantity_block;
		show_quant=block1*quantity_block;
//		System.out.println("Ticker: "+this.name+" show_q:"+show_quant);
		if (block1>block2) {
			int tempQ=block1*quantity_block;
			int i=0;
			float tempN=0;
			while (tempQ>0) {
				Order tempO=Orders.get(i);
				if (tempQ>=tempO.quant) {
					tempN+=tempO.quant*tempO.price;
					tempQ-=tempO.quant;
				}else {
					tempN+=tempQ*tempO.price;
					tempQ=0;
				}
//				System.out.println("Ticker: "+this.name+" notional:"+tempN);
				i++;
			}
			show_notional=tempN;
			
			block2=block1;
			return true;
		}else {
			return false;
		}
	}

	public void update(int qua, float pri) {
		Orders.add(new Order(qua,pri));
//		System.out.println("Tickerr:"+this.name+" add quantity:"+qua+" at price:"+pri);
		cum_quant+=qua;
	}
}
