package chinapub.cf.recommander.test;

public class fn {
	int fbqn(int n){
		if(n==0)
			return 0;
		else if (n==2||n==1)
			return 1;
		else if(n>2){
			int temp = fbqn(n-1)+fbqn(n-2);
			if(temp<0){
				try {
					throw new Exception("invalid value for int type,too large");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else
				return temp;
		}else
			try {
				throw new Exception("invalid value for n,pls enter n>=0");
			} catch (Exception e) {
				e.printStackTrace();
			}
		return 0;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
