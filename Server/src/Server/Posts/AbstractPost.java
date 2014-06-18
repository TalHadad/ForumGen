package Server.Posts;
import Server.Users.*;
import Server.Forum.*;

public class AbstractPost {
	
	private String id;
	private String title;
	private String content;
	private String writer;
	
	public AbstractPost(String id,String userName, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	public String getID() {
		return this.id;
	}
	//11
	public String getTitle(){
		return this.title;
	}
	public String getMember(){
		return this.writer;
	}
	public String getContent(){
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
		
	}
	
	public int compareTo(AbstractPost other){
		 if(other.getTitle().compareTo(this.title)==0 && 
				 other.getContent().compareTo(this.content)==0 &&
				 other.getMember().compareTo(this.writer)==0)
			 return 0;
		 return -1;
	 }

}
