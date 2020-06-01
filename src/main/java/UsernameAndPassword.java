
public class UsernameAndPassword {
     
	public String Username;
	
	public String Password;
	
	public int ok=0;
	
	public int ok2=0;
	
	public UsernameAndPassword(String Username, String Password) {
		this.Username=Username;
		this.Password=Password;
	}
	
	private void StoreUsername() {
		String[]Users={"usr_1","usr_2","usr_3"};
		
		String[]Passwords= { "pass_1","pass_2","pass_3" }; 
		
		for(int i=0;i<Users.length;i++) {
			if(Users[i]==this.Username) {
			//	return Users[i];
				 ok=1;
			}
			else
			{
				System.out.println("the user is not registered");
			}
		}
			
			for(int j=0;j<Passwords.length;j++) {
				if(Passwords[j]==this.Password) {
				//	return Passwords[j];
					 ok2=1;
					
				}
				else
				{
					System.out.println("incorrect password");
				}
			}
			
			if(ok == 1 && ok2 == 1) {
				System.out.println("Login OK!");
			}
	}
}
