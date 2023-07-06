package application;

public class InvalidValueException extends Exception{
	
	public InvalidValueException (String str)  {  
	     super(str);  
	 }   
	 

	 static void validate (String line1 , String line2, String line3, String line4) throws InvalidValueException{    
		 
		 if( ( !line1.equals("1") && !line1.equals("2") ) || 
				( (line1.equals("1") && (Integer.parseInt(line2)>11 || Integer.parseInt(line2)<9))  || (line1.equals("2") && (Integer.parseInt(line2)<35  || Integer.parseInt(line2)>45) ) )	
			){
			 throw new InvalidValueException("InvalidValueException..."); 
			}
		 
		 else if ( ( (line1.equals("1") && (Integer.parseInt(line3)>180 || Integer.parseInt(line3)<120))  || (line1.equals("2") && (Integer.parseInt(line3)>360  || Integer.parseInt(line3)<240) ) ) ||
				 (line1.equals("1") && line4.equals("1"))
				 ) {
			 throw new InvalidValueException("InvalidValueException..."); 
		 }
		 
		 else {
			 System.out.println("ValidValues"); 
		 }
	  }    
	
}
