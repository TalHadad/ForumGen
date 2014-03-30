package acceptanceTests;

import bridge.ForumBridge;
import driver.Driver;
import junit.framework.TestCase;

public class ForumTests extends TestCase {
	
	protected ForumBridge bridge;
	
	public ForumTests(){
        super();
    }

    public ForumTests(String name){
        super(name);
    }

    /**
     * Set Up method
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.bridge= Driver.getBridge();
        this.initSuperAdmin();
        this.initForums();
        this.initUsers();
    }

    private void initSuperAdmin(){
    	//this.bridge.initialize("Yael", "1111", "blabla");
    }
    
    private void initForums() {
        this.bridge.createForum("Yael", "Cats", "a-z", "a-z", 4, 20, 200);
        this.bridge.createForum("Yael", "Software Engineer", "a-z", "a-z", 4, 20, 200);
        this.bridge.createForum("Yael", "Shopping", "a-z", "a-z", 4, 20, 200);
    }

    private void initUsers() {
        this.bridge.register("Vali","2222", "Cats");
        this.bridge.register("Asaf", "3333", "Software Engineer");
        
        this.bridge.setAsAdmin("Vali", "Cats");
        this.bridge.setAsModerator("Asaf", "Software Engineer");
        
    }
    
    /**
     * Tear Down method
     */
    protected void tearDown() throws Exception {
        bridge.cleanAllData();
        super.tearDown();
    }
}
