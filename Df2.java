import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Df2 {
	public static void main(String[] args) {
		ArrayList<String> t=new ArrayList<String>();
		t.add("00:01,A,5,5.5");  
		t.add("00:00,A,4,5.6");
		t.add("00:00,B,5,5.5");  
		t.add("00:02,B,4,5.6");  
		t.add("00:02,A,1,5.6");
		t.add("00:02,C,10,5.6");
		
		Df2 d=new Df2();
		d.exp(t);
		d.toCumulativeDelayed(t, 4)	;
		}
	private Df2() {
		// empty
	}

	public static void exp(List<String> ticks) {
		ArrayList<Tick> tickss=processData(ticks);
		for (Tick i:tickss) {
			System.out.println("TimeStamp:"+i.getTimeStamp()+" ticker:"+i.getTicker()+" quant:"+i.getQuant());
		}
	}
	
	public static List<String> toCumulative(List<String> ticks) {
		throw new RuntimeException();
	}

	public static List<String> toCumulativeDelayed(List<String> ticks, int quantityBlock) {
		ArrayList<Tick> tickss = processData(ticks);
		ArrayList<Tickerr> tickers=new ArrayList<>();
		ArrayList<String> ticker_name=new ArrayList<>();
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<String> temp=new ArrayList<String>();
		
		String lsts=tickss.get(0).getTimeStamp();//基准时间
		String lsticker = tickss.get(0).getTicker();// 当前公司
		int lsquan = tickss.get(0).getQuant();
		float lsprice = tickss.get(0).getPrice();
		ticker_name.add(lsticker);
		tickers.add(new Tickerr(lsticker, quantityBlock, lsquan, lsprice));
		temp.add(lsts);//编辑回答
		
		if (tickss.size() != 1) {
			for (int i = 1; i < tickss.size(); i++) {
//				System.out.println("Checkpoint1:i: "+i);
				String ts = tickss.get(i).getTimeStamp();// 当前时间
				String ticker = tickss.get(i).getTicker();// 当前公司
				int quan = tickss.get(i).getQuant();
				float price = tickss.get(i).getPrice();
				
				if (lsts.equals(ts)) {// 如果时间一样
					if (!lsticker.equals(ticker)) {// 如果公司一样
						Tickerr lsTicker = tickers.get(ticker_name.indexOf(lsticker));
						if (lsTicker.checkBlock()) {
							temp.add(lsticker);
							temp.add(String.valueOf(lsTicker.show_quant));
							temp.add(String.valueOf(lsTicker.show_notional));
						}
					}
				} else {
					Tickerr lsTicker = tickers.get(ticker_name.indexOf(lsticker));
					if (lsTicker.checkBlock()) {
						temp.add(lsticker);
						temp.add(String.valueOf(lsTicker.show_quant));
						temp.add(String.valueOf(lsTicker.show_notional));
					}
					if (temp.size() != 1) {
						String tempp = convertt(temp);
						res.add(tempp);
					}
					temp.clear();
					temp.add(ts);
				}
				
				quan = tickss.get(i).getQuant();
				price = tickss.get(i).getPrice();
				
				
				if (ticker_name.contains(ticker)) {
					Tickerr targ = tickers.get(ticker_name.indexOf(ticker));
//					System.out.println("Checkpoint2:i: "+i);
					targ.update(quan, price);
				} else {
					ticker_name.add(ticker);
					tickers.add(new Tickerr(ticker, quantityBlock, quan, price));
				}
				lsts = ts;
				lsticker = ticker;
			}
		}
		Tickerr lsTicker = tickers.get(ticker_name.indexOf(lsticker));
		if (lsTicker.checkBlock()) {
			temp.add(lsticker);
			temp.add(String.valueOf(lsTicker.show_quant));
			temp.add(String.valueOf(lsTicker.show_notional));
		}
		if (temp.size() != 1) {
			String tempp = convertt(temp);
			res.add(tempp);
		}
//		System.out.println("completed");
		for (String r:res) {
			System.out.println(r);
		}
		return res;
	}

	public static ArrayList<Tick> processData(List<String> ticks) {
		ArrayList<Tick> tickss = new ArrayList<Tick>();
		for (String tick : ticks) {
			String[] newtick = tick.split(",");
			int hr, min;
			String ts = newtick[0];
			String[] ts_temp = ts.split(":");
			hr = Integer.parseInt(ts_temp[0]);
			min = Integer.parseInt(ts_temp[1]);
			int ts_int = hr * 60 + min;
			String ticker = newtick[1];
			int quan = Integer.parseInt(newtick[2]);
			float price = Float.parseFloat(newtick[3]);
			tickss.add(new Tick(ts, ts_int, ticker, quan, price));
		}
		TickCompare tickComp = new TickCompare();
		Collections.sort(tickss, tickComp);
		return tickss;
	}
	
	public static String convertt(List<String> temp) {
		StringBuilder str = new StringBuilder("");
		 
        for (String eachstring: temp) {
            str.append(eachstring).append(",");
        }
        String csl = str.toString();
 
        if (csl.length() > 0) {
        	csl = csl.substring(0, csl.length() - 1);
        }
        return csl;    
	}
}
