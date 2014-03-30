
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

}
