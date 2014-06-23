package pl.parser.nbp;

import java.util.Date;

public class NbpParser {
	private String currency;
	private Date startDate;
	private Date endDate;
	
	public NbpParser(String currency, Date startDate, Date endDate) {
		this.currency = currency;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public double getAvgCurrencyPrice(){
		// TODO
		
		return 0.0;
	}
	
	public double getStandardDeviation(){
		// TODO
		
		return 0.0;
	}
}
