
public class Policy {

	private String policy;
	private Boolean hasEmailPolicy;//uc6
	
	
	public Policy(String policy,Boolean hasEmailPolicy){
		this.policy = policy;
		this.hasEmailPolicy=hasEmailPolicy;
	}
	
	public String getPolicy(){
		return policy;
	}
	//uc6
	public boolean hasEmailConfirmation() {
		return hasEmailPolicy;
	}
}
