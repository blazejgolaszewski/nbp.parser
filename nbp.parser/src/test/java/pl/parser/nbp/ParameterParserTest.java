package pl.parser.nbp;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class ParameterParserTest {
	private ParameterParser parser;
	
	@Before
	public void setUp(){
		parser = new ParameterParser();
	}
	
	@Test
	public void testParseAllParametersProperly() throws Exception {
		parser.setCurrencyParameter("EUR");
		parser.setStartDate("2013-01-28");
		parser.setEndDate("2013-01-31");
		
		parser.parse();
		
		assertEquals("Currency not properly parsed", "EUR", parser.getCurrency());
		assertEquals("Start date not properly parsed", new DateTime(2013, 1, 28, 0, 0).toDate(), parser.getStartDate());
		assertEquals("End date not properly parsed", new DateTime(2013, 1, 31, 0, 0).toDate(), parser.getEndDate());
	}
	
	@Test(expected = ParseException.class)
	public void testNoProperCurrencyGiven() throws Exception{
		parser.setCurrencyParameter("EURo");
		
		parser.parse();
	}
	
	@Test(expected = ParseException.class)
	public void testNoProperStartDateGiven() throws Exception{
		parser.setCurrencyParameter("EUR");
		parser.setStartDate("yyyy-mm-dd");
		parser.setEndDate("2013-01-31");
		
		parser.parse();
	}
	
	@Test(expected = ParseException.class)
	public void testNoProperEndDateGiven() throws Exception{
		parser.setCurrencyParameter("EUR");
		parser.setStartDate("2013-01-28");
		parser.setEndDate("yyyy-mm-dd");
		
		parser.parse();
	}
}
