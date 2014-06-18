package Configuration;

import DataBase.ForumsHandler;

public class TestDB {

	public static void main(String[] args) {
		ForumsHandler forumsHandler=new ForumsHandler();
		forumsHandler.addForum("azxs", false, false, 1, 1, false, false, 1,"");
		forumsHandler.printForumList();

	}

}
