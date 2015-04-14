package chinapub.cf.recommander.test;

import java.io.IOException;

public class ExceptionTest {

	public static void main(String[] args) {
		int i=1;
		if(i==1)
			try {
				throw new IOException("");
			} catch (IOException e) {
				System.out.println("ioexception throwed");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("exception throwed");
				e.printStackTrace();
			}finally{
				System.out.println("print finally");
			}
		System.out.println("print out");
	}

}
