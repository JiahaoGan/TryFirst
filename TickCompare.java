import java.util.Comparator;

public class TickCompare implements Comparator<Tick> {

	@Override
	public int compare(Tick o1, Tick o2) {
		int ts1=o1.getTs_int();
		int ts2=o2.getTs_int();
		int tsComp=ts1-ts2;
		
		if (tsComp!=0) {
			return tsComp;
		}
		
		String t1=o1.getTicker();
		String t2=o2.getTicker();
		return t1.compareTo(t2);
	}

}
