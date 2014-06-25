package pl.parser.nbp;

import java.util.List;

import org.joda.time.DateTime;

public class NbpParser {
	private String currency;
	private DateTime startDate;
	private DateTime endDate;
	private List<CurrencyCourse> courses;
	
	public NbpParser(String currency, DateTime startDate, DateTime endDate) {
		this.currency = currency;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public void loadData(NbpXmlDataParser parser){
		courses = parser.getCurrencyDataBetweenDates(startDate, endDate, currency);
	}
	
	public double getAvgCurrencyPrice() throws Exception{
		if(courses == null){
			throw new Exception("loadData() has not been run");
		}
		double courseSum = 0.0;
		
		for(CurrencyCourse course : courses){
			courseSum += course.getBuyCourse();
		}
		
		return courseSum/courses.size();
	}
	
	public double getStandardDeviation(){
		// TODO
		
		return 0.0;
	}
}
