package pl.parser.nbp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;

public class NbpXmlDataParser {
	private static Logger log = Logger.getLogger(NbpXmlDataParser.class);
	
	final private static String NBP_XML_URL = "http://www.nbp.pl/kursy/xml/";
	final private static String NBP_DATES_FILE_URL = "http://www.nbp.pl/kursy/xml/dir.txt";
	
	public List<CurrencyCourse> getCurrencyDataBetweenDates(DateTime start, DateTime end, String type){
		List<CurrencyCourse> courses = new ArrayList<CurrencyCourse>();
		DateTime currentDay = start;
		
		while(currentDay.isEqual(end)){
			String xmlFileName = getNbpCurrencyCourseFilename(currentDay);
			
			try{
				Document currencyXmlData = getXmlFromUrl(NBP_XML_URL + xmlFileName);
				
				CurrencyCourse course = createCourseFromXml(currencyXmlData, type);
				
				if(course != null){
					courses.add(course);
				}
			}
			catch(DocumentException ex){
				log.warn(String.format("Problems retrieving data for %s file", xmlFileName));
				log.debug(ex);
			}
			
			currentDay = currentDay.plusDays(1);
		}
		return courses;
	}
	
	public CurrencyCourse createCourseFromXml(Document xmlDocument, String type) {
		Element root = xmlDocument.getRootElement();
		Element courseDateElem = root.element("data_publikacji");
		
		NumberFormat format = NumberFormat.getInstance(Locale.FRENCH);
	    
		DateTime courseDate = null;
		try{
			courseDate = new DateTime(new SimpleDateFormat("yyyy-MM-dd").parse(courseDateElem.getText()));
			
			for(Object currencyNode : root.elements("pozycja")){
				Element elem = (Element) currencyNode;
				
				if(elem.elementText("kod_waluty").equals(type)){
					CurrencyCourse course = new CurrencyCourse();
					course.setType(type);
					course.setDate(courseDate);
					course.setBuyCourse(format.parse(elem.elementText("kurs_kupna")).doubleValue());
					course.setSellCourse(format.parse(elem.elementText("kurs_sprzedazy")).doubleValue());
					
					return course;	
				}
			}
		}
		catch(ParseException ex){
			log.error(String.format("Problems parsing date for type %s", type));
		}
		log.warn(String.format("No course for %s on %s", type, courseDate));
		return null;
	}

	public String getNbpCurrencyCourseFilename(DateTime fileDate){
		String fileName = null;
		String formattedDate = new SimpleDateFormat("YYMMdd").format(fileDate.toDate());
		String fileRegex = "c\\d{3}z" + formattedDate;
		
		try{
			BufferedReader in = getFileReaderFromUrl(new URL(NBP_DATES_FILE_URL));
	        
			String inputLine;
	        while ((inputLine = in.readLine()) != null){
	        	if(inputLine.matches(fileRegex)){
	        		fileName = inputLine;
	        		break;
	        	}
	        }
	        in.close();
		}
		catch(MalformedURLException ex){
			log.error(ex);
		}
		catch(IOException ex){
			log.error(ex);
		}
		
		return fileName + ".xml";
	}
	
	public BufferedReader getFileReaderFromUrl(URL url) throws IOException{
		return new BufferedReader(new InputStreamReader(url.openStream()));
	}
	
	public Document getXmlFromUrl(String url) throws DocumentException{
		log.debug(String.format("Reading xml from url: %s", url));
		SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
	}
}
