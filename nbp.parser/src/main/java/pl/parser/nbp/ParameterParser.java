package pl.parser.nbp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParameterParser {
	private String currencyParameter;
	private String startDateParameter;
	private String endDateParameter;
	private String currency;
	private Date startDate;
	private Date endDate;
	
	private final String regexCurrency = "\\S{3}";
	private final String regexStringDate = "\\d{4}-\\d{2}-\\d{2}";
	
	public void setCurrencyParameter(String currency) {
		this.currencyParameter = currency.toUpperCase();
	}
	
	public void setEndDate(String endDate) {
		this.endDateParameter = endDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDateParameter = startDate;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void parse() throws ParseException {
		if(parseCurrency()){
			currency = currencyParameter;
		}
		else{
			throw new ParseException("Couldn't parse currency", 0);
		}
		
		startDate = parseDate(startDateParameter);
		endDate = parseDate(endDateParameter);
	}
	
	private boolean parseCurrency(){
		return currencyParameter.matches(regexCurrency);
	}
	
	private Date parseDate(String date) throws ParseException{
		if(date.matches(regexStringDate)){
			return new SimpleDateFormat("yyyy-MM-DD").parse(date);
		}
		else{
			throw new ParseException("Couldn't parse date", 0);
		}
	}
}
