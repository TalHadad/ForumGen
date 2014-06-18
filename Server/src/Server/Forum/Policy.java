package Server.Forum;
import java.util.Date;

import Server.Users.*;
import Configuration.*;
import Server.Posts.*;
public class Policy {
	
	private Configuration configuraion;
	private Boolean hasEmailPolicy;//uc6
	private Boolean extendedDeletionPolicy;// true iff writer+admin+moderator;false iff writer+admin
	private int minPostForModerator;
	private int minSeniorityMonths;
	private Boolean onlyApointAdministratorCanRemoveModerators;
	private Boolean canRemoveSingleModerators;
	private int expirationDateForPassword;
	private String interactiveNotifyingPolicys;
	
	public int getExpirationDateForPassword() {
		return expirationDateForPassword;
	}

	public void setExpirationDateForPassword(int expirationDateForPassword) {
		this.expirationDateForPassword = expirationDateForPassword;
	}

	public Policy(Boolean hasEmailPolicy,Boolean extendedDeletionPolicy
			,int minPostForModerator,int minSeniorityMonths
			,Boolean onlyApointAdministratorCanRemoveModerators,Boolean canRemoveSingleModerators
			,int expirationDateForPassword, String interactiveNotifyingPolicys){
		
		this.hasEmailPolicy=hasEmailPolicy;
		this.extendedDeletionPolicy=extendedDeletionPolicy;
		this.minPostForModerator=minPostForModerator;
		this.minSeniorityMonths=minSeniorityMonths;
		this.onlyApointAdministratorCanRemoveModerators=onlyApointAdministratorCanRemoveModerators;
		this.canRemoveSingleModerators=canRemoveSingleModerators;
		this.configuraion=new Configuration();
		this.expirationDateForPassword = expirationDateForPassword;
		this.interactiveNotifyingPolicys = interactiveNotifyingPolicys;
	}
	public Policy(){
		this.canRemoveSingleModerators=false;
		this.configuraion = new Configuration();
		this.expirationDateForPassword= 100;
		this.extendedDeletionPolicy=false;
		this.hasEmailPolicy=false;
		this.minPostForModerator=10;
		this.minSeniorityMonths=10;
		this.onlyApointAdministratorCanRemoveModerators=false;
		this.interactiveNotifyingPolicys = Configuration.OFFLINE;
		
	}

	
	public Policy(boolean hasEmailPolicy1, boolean extendedDeletionPolicy1,
			boolean minPostForModerator1, boolean minSeniorityMonths1,
			boolean onlyApointAdministratorCanRemoveModerators1,
			boolean canRemoveSingleModerators1,
			boolean expirationDateForPassword1, boolean interactiveNotifyingPolicys1) {
		// TODO Auto-generated constructor stub
	}

	public boolean canRemoveModerator(int requesterType,String requesterName, String apointedAdmin, Boolean isSingle){
		Boolean ans=false;
		if (requesterType==this.configuraion.ADMINISTRATOR){
			if (onlyApointAdministratorCanRemoveModerators)
				if (requesterName.compareTo(apointedAdmin)==0)
					ans=true;
		}
		return ans && (canRemoveSingleModerators || !isSingle);
	}
	
	public boolean canApointedToModerator(int numOfPosts,int seniorityMonths){
		return ((numOfPosts >= this.minPostForModerator) && (seniorityMonths >= this.minSeniorityMonths));
	}
	
	public boolean canRemovePost(int requesterType){
		return (extendedDeletionPolicy)&&(requesterType==this.configuraion.MODERATOR);
	}
	//uc6
	public boolean hasEmailConfirmation() {
		return this.hasEmailPolicy;
	}


	public int getMinSeniorityMonths() {
		return this.minSeniorityMonths;
	}

	public boolean getOnlyApointAdministratorCanRemoveModerators() {
		return this.onlyApointAdministratorCanRemoveModerators;
	}

	public int getMinPostForModerator() {
		return this.minPostForModerator;
	}

	public boolean getExtendedDeletionPolicy() {
		return this.extendedDeletionPolicy;
	}

	public boolean getCanRemoveSingleModerators() {
		return this.canRemoveSingleModerators;
	}

	public int getMonthsPasswordValidFor() {
		return this.expirationDateForPassword;
	}

	public String getInteractiveNotifyingPolicys() {
		return this.interactiveNotifyingPolicys;
	}

}
