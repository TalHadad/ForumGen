import java.util.Vector;


public class Administrator extends Moderator{

	private Boolean hasAdditionalChangeFlage;//uc 3
	
	public Administrator(String name, String password) {
		super(name, password);
		this.hasAdditionalChangeFlage=false;
	}
	//uc 3
	public boolean hasAdditionalChange() {
		return this.hasAdditionalChangeFlage;
	}
	

}
