import java.util.Vector;


public class SubForum {

		private String name;
		private String subject;
		private Vector<OpeningPost> openingPosts;
		private Vector<Moderator> moderators;
		
		public SubForum(String name,String subject) {
			this.name=name;
			this.subject=subject;
			this.openingPosts = new Vector<OpeningPost>();
			this.moderators= new Vector<Moderator>();
		}
		
		public Vector<Moderator> getMederators() {
			return moderators;
		}

		public void addMederators(Moderator moderator) {
			this.moderators.add(moderator);
		}
		public String getName(){
			return this.name;
		}
		public String getSubject(){
			return this.subject;
		}
		
		public Vector<OpeningPost> view(){
			return this.openingPosts;
		}
		//uc10
		public OpeningPost postOpeningPost(Member writer,String title,String content){
			OpeningPost openingPost = addOpeningPost( writer, title, content);
			writer.notifyFriendsAboutPost();
			return openingPost;
		}
		//uc10
		private OpeningPost addOpeningPost(Member member, String title, String content) {
			OpeningPost openingPost=new OpeningPost(member,title,content);
			openingPosts.add(openingPost);
			return openingPost;
		}
		//11
		public Vector<String> getOpeningPostsStringList() {
			Vector<String> ans = new Vector<String>();
			for (OpeningPost openingPost:openingPosts)
				ans.add(openingPost.getTitle());
			return ans;
		}
		//11
		public Vector<String> showPost(String postName){
			for (OpeningPost openingPost:openingPosts){
				if (openingPost.getTitle().compareTo(postName)==0)
					return openingPost.getFollowPostsStringList();
			}
			return null;
		}
}
