package pl.parser.nbp;

/**
 * Hello world!
 *
 */
public class MainClass 
{
    public static void main( String[] args )
    {
        if(args.length == 0){
        	// TODO - add proper help function
        	System.out.println("Help function");
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
        		
        		System.out.println(nbpParser.getAvgCurrencyPrice());
        		System.out.println(nbpParser.getStandardDeviation());
        	}
        	catch(Exception ex){
        		System.out.println(ex.getMessage());
        	}
        }
    }
}
