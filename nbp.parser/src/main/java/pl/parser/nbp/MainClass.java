package pl.parser.nbp;

/**
 * Hello world!
 *
 */
public class MainClass 
{
    public static void main( String[] args )
    {
        if(args.length == 0 || args.length < 3){
        	usage();
        }
        else{
        	ParameterParser parser = new ParameterParser();
        	parser.setCurrencyParameter(args[0].trim());
        	parser.setStartDate(args[1].trim());
        	parser.setEndDate(args[2].trim());
        	
        	try{
        		parser.parse();
        		
        		NbpParser nbpParser = new NbpParser(parser.getCurrency(), 
        											parser.getStartDate(),
        											parser.getEndDate());
        		
        		nbpParser.loadData(new NbpXmlDataParser());
        		
        		System.out.println(nbpParser.getAvgCurrencyPrice());
        		System.out.println(nbpParser.getStandardDeviation());
        	}
        	catch(Exception ex){
        		System.out.println(ex.getMessage());
        	}
        }
    }
    
    private static void usage(){
    	System.out.println("Usage eg.: java pl.parser.nbp.MainClass EUR 2013-01-28 2013-01-31");
    }
}
