package chinapub.cf.recommander.utils;

import java.util.Collection;

public class StringUtil {
	public static String connectString(Collection<String> stringList, String split){
		StringBuilder builder = new StringBuilder();
		boolean flag = false;
		
		for (String s : stringList){
			if( flag ) {
				builder.append( split );
			} else {
				flag = true;
			}

			builder.append(s);		
		}
		return builder.toString();
	}
	
	public static boolean isNotNull(String str){
		 if(!str.equals("") && str!=null){
			 return true;
		 }
		 return false;
	}
}
