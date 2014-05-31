package Server.Posts;
import Server.Users.*;
import Server.Forum.*;

public class AbstractPost {
	
	
	private String title;
	private String content;
	private Member writer;
	
	public AbstractPost(Member member, String title, String content) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}
	//11
	public String getTitle(){
		return this.title;
	}
	public Member getMember(){
		return this.writer;
	}
	public String getContent(){
		return this.content;
	}
	
	public void setContent(String content) {
		// TODO Auto-generated method stub
		
	}
	
	public int compareTo(AbstractPost other){
		 if(other.getTitle().compareTo(this.title)==0 && 
				 other.getContent().compareTo(this.content)==0 &&
				 other.getMember().getName().compareTo(this.writer.getName())==0)
			 return 0;
		 return -1;
	 }

}
