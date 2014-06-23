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
	private final String regexStringDate = "\\d{8}";
	
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
	
	public void parse() throws Exception {
		if(parseCurrency()){
			currency = currencyParameter;
		}
		else{
			throw new Exception("Not a proper currency parameter");
		}
		
		startDate = parseDate(startDateParameter);
		endDate = parseDate(endDateParameter);
	}
	
	private boolean parseCurrency(){
		return currencyParameter.matches(regexCurrency);
	}
	
	private Date parseDate(String date) throws ParseException{
		if(date.matches(regexStringDate)){
			return new SimpleDateFormat("YYYY-MM-DD").parse(startDateParameter);
		}
		return null;
	}
}
