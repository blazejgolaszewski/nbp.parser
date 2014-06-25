package pl.parser.nbp;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class NbpXmlDataParserTest {
	private NbpXmlDataParser parserMock = null;
	private NbpXmlDataParser parser = null;
	private Document xmlTestFile = null;
	private URL datesFileUrl = null;
	private BufferedReader datesReader = null;
	
	final private static String XML_TEST_FILENAME = "c073z070413.xml";
			
	@Before
	public void setUp() throws DocumentException, IOException{
		parser = new NbpXmlDataParser();
		parserMock = mock(NbpXmlDataParser.class);
		xmlTestFile = new SAXReader().read(getClass().getResource("/" + XML_TEST_FILENAME));
		datesFileUrl = getClass().getResource("/dir.txt");
		datesReader = new BufferedReader(new InputStreamReader(datesFileUrl.openStream()));
	}
	
	@Test
	public void testGeneratingCourseListFromXml() throws DocumentException {
		DateTime startDate = new DateTime(2007,4, 13, 0, 0);
		DateTime endDate = new DateTime(2007,4, 13, 0, 0);
		
		CurrencyCourse testCourse = new CurrencyCourse();
		testCourse.setType("EUR");
		testCourse.setDate(startDate);
		testCourse.setBuyCourse(3.7976);
		testCourse.setSellCourse(3.8744);
		
		when(parserMock.getNbpCurrencyCourseFilename(startDate)).thenReturn("a001z040102.xml");
		when(parserMock.getXmlFromUrl(anyString())).thenReturn(xmlTestFile);
		when(parserMock.createCourseFromXml(xmlTestFile, "EUR")).thenReturn(testCourse);
		when(parserMock.getCurrencyDataBetweenDates(startDate, endDate, "EUR")).thenCallRealMethod();
		
		List<CurrencyCourse> currencies = parserMock.getCurrencyDataBetweenDates(startDate, 
																			endDate,
																			"EUR");
		
		assertNotNull(currencies);
		assertEquals("Currencies list is empty", 1, currencies.size());
		
		CurrencyCourse createdCourse = currencies.get(0);
		
		assertNotNull(createdCourse);
		assertEquals("Course type was not set", testCourse.getType(), createdCourse.getType());
		assertEquals("Course date was not set", testCourse.getDate(), createdCourse.getDate());
		assertEquals("Buy course was not set", testCourse.getBuyCourse(), createdCourse.getBuyCourse(), 0.0001);
		assertEquals("Sell course was not set", testCourse.getSellCourse(), createdCourse.getSellCourse(), 0.0001);
	}
	
	@Test
	public void testCreateCourseFromXml(){
		DateTime startDate = new DateTime(2007,4, 13, 0, 0);
		
		CurrencyCourse testCourse = parser.createCourseFromXml(xmlTestFile, "EUR");
		
		assertNotNull(testCourse);
		assertEquals("Course type was not set", "EUR", testCourse.getType());
		assertEquals("Course date was not set", startDate, testCourse.getDate());
		assertEquals("Buy course was not set", 3.7976, testCourse.getBuyCourse(), 0.0001);
		assertEquals("Sell course was not set", 3.8744, testCourse.getSellCourse(), 0.0001);
	}
	
	@Test
	public void testGettingFileNameByDate() throws IOException{
		when(parserMock.getFileReaderFromUrl(any(URL.class))).thenReturn(datesReader);
		when(parserMock.getNbpCurrencyCourseFilename(any(DateTime.class))).thenCallRealMethod();
		
		String fileName = parserMock.getNbpCurrencyCourseFilename(new DateTime(2002, 5, 7, 0, 0));
		
		assertEquals("Wrong filename generated", "c087z020507.xml", fileName);
	}
}
