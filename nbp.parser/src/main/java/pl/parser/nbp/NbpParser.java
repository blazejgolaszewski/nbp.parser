package pl.parser.nbp;

import java.math.BigDecimal;
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
	
	public BigDecimal getAvgCurrencyBuyPrice() throws Exception{
		if(courses == null){
			throw new Exception("loadData() has not been run");
		}
		double courseSum = 0.0;
		
		for(CurrencyCourse course : courses){
			courseSum += course.getBuyCourse();
		}
		
		return new BigDecimal(courseSum/courses.size()).setScale(4, BigDecimal.ROUND_HALF_UP);
	}
	
	public BigDecimal getStandardDeviation(){
		double varianceSum = 0.0;
		double sellCourseSum = 0.0;
		double mean = 0.0;
		
		for(CurrencyCourse course : courses){
			sellCourseSum += course.getSellCourse();
		}
		
		mean = sellCourseSum / courses.size();
		
		for(CurrencyCourse course : courses){
			varianceSum += Math.pow(course.getSellCourse() - mean,2);
		}
		
		return new BigDecimal(Math.sqrt(varianceSum/courses.size())).setScale(4, BigDecimal.ROUND_HALF_UP);
	}
}
