package application;


class InvalidDescriptionException  extends Exception  {  
 public InvalidDescriptionException (String str)  {  
     super(str);  
 }   
 

 static void validate (String line1 , String line2, String line3, String line4, String line5) throws InvalidDescriptionException{   
	 
    if(((line1==null)||(line1=="")||(line2=="")||(line3=="")||(line4=="")||(!(line5==null))||(line5=="")||(line2==null)||(line3==null)||(line4==null))){   
     throw new InvalidDescriptionException("InvalidDescriptionException..."); 
    }  
    
    else if((line1.split("\\s+").length!=1) || (line2.split("\\s+").length!=1) || (line3.split("\\s+").length!=1) || (line4.split("\\s+").length!=1) ){   
        throw new InvalidDescriptionException("InvalidDescriptionException..."); 
    }  
    
    else {
     System.out.println("ValidDescription"); 
     }   
  }    

 
}  