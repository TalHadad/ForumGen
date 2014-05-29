package Forum;
import java.util.Date;

import Users.*;
import Posts.*;
public class Policy {
	
	private Configuration configuraion;
	private String policy;
	private Boolean hasEmailPolicy;//uc6
	private Boolean extendedDeletionPolicy;// true iff writer+admin+moderator;false iff writer+admin
	private int minPostForModerator;
	private int minSeniorityMonths;
	private Boolean onlyApointAdministratorCanRemoveModerators;
	private Boolean canRemoveSingleModerators;
	private Date expirationDateForPassword;
	
	public Date getExpirationDateForPassword() {
		return expirationDateForPassword;
	}

	public void setExpirationDateForPassword(Date expirationDateForPassword) {
		this.expirationDateForPassword = expirationDateForPassword;
	}

	public Policy(String policy,Boolean hasEmailPolicy,Boolean extendedDeletionPolicy
			,int minPostForModerator,int minSeniorityMonths
			,Boolean onlyApointAdministratorCanRemoveModerators,Boolean canRemoveSingleModerators
			,Date expirationDateForPassword){
		this.policy = policy;
		this.hasEmailPolicy=hasEmailPolicy;
		this.extendedDeletionPolicy=extendedDeletionPolicy;
		this.minPostForModerator=minPostForModerator;
		this.minSeniorityMonths=minSeniorityMonths;
		this.onlyApointAdministratorCanRemoveModerators=onlyApointAdministratorCanRemoveModerators;
		this.canRemoveSingleModerators=canRemoveSingleModerators;
		this.configuraion=new Configuration();
		this.expirationDateForPassword=expirationDateForPassword;
	}
	public Policy(){
		this.canRemoveSingleModerators=false;
		this.configuraion = new Configuration();
		this.expirationDateForPassword= new Date(1,0,0);
		this.extendedDeletionPolicy=false;
		this.hasEmailPolicy=false;
		this.minPostForModerator=10;
		this.minSeniorityMonths=10;
		this.onlyApointAdministratorCanRemoveModerators=false;
		
	}

	
	public Boolean canRemoveModerator(int requesterType,String requesterName, String apointedAdmin, Boolean isSingle){
		Boolean ans=false;
		if (requesterType==this.configuraion.ADMINISTRATOR){
			if (onlyApointAdministratorCanRemoveModerators)
				if (requesterName.compareTo(apointedAdmin)==0)
					ans=true;
		}
		return ans && (canRemoveSingleModerators || !isSingle);
	}
	
	public Boolean canApointedToModerator(int numOfPosts,int seniorityMonths){
		return (numOfPosts>=this.minPostForModerator) && (seniorityMonths>=this.minSeniorityMonths);
	}
	
	public Boolean canRemovePost(int requesterType){
		return (extendedDeletionPolicy)&&(requesterType==this.configuraion.MODERATOR);
	}
	//uc6
	public boolean hasEmailConfirmation() {
		return this.hasEmailPolicy;
	}
	

	public void setVal(String newVal) {
		this.policy = newVal;
	}
}