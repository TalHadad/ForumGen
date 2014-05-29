/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package ClientSrc;

import Forum.*;
import Posts.*;
import Users.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;


public class ConnectionHandler {
    public static final String LOGIN_SUPER = "LOGIN_SUPER";
    public static final String LOGIN_MEMBER = "LOGIN";
    private ForumGen forumGenerator = null ;
    private Vector<String> subForums = new Vector<String>();
    private Vector<String> forums = new Vector<String>();
    private Vector<String> admins = new Vector<String>();
    private Vector<String> moderators = new Vector<String>();
    private Vector<String> topics = new Vector<String>();;
    private String msg = null;
    /*
    private boolean talLoggedIn;
    private boolean YaelIsSuperAdmin;
    private Vector<String> members;
    private Vector<String> forums;
    private Vector<String> loggdin;
    
    private Vector<String> talPosts;
    private Vector<String> guests;
    private Vector<String> subs;
    private Vector<String> mods;
    private Vector<String> complaints;
    */
    PrintWriter out;
    BufferedReader in;
    Socket socket;
    private String method;
    
    
    public ConnectionHandler(){
        forums.add("Cats");
        forums.add("Dogs");
        forums.add("Sadna");
        subForums.add("Black Cats");
        subForums.add("White Cats");
        admins.add("TalHadad");
        admins.add("AsafShen");
        moderators.add("Sivan");
        moderators.add("Vali_007");
        topics.add("Make sound");
        topics.add("Jump");
        topics.add("Eat");
//            try {
//                //listenSocket();
//                start();
//            } catch (IOException ex) {
//                java.util.logging.Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
//            }
    }
    
    public String initializeSystem(String userName, String password) {
        validateSuperAdmin(userName,password);
        forumGenerator = new ForumGen();
        return "SUCCESS";
    }
    
    public boolean isInitialize() {
        return (forumGenerator!=null);
    }
    
//done
    public String validateSuperAdmin(String userName, String password) {
        this.msg = Configuration.LOGIN_SUPER + " " + userName + " " + password;
        String serverResponse = null;
        try {
            //listenSocket();
            serverResponse = start();
            System.out.println("#########"+serverResponse);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.msg = null;
//            return(userName.contentEquals("t") && (password.contentEquals("888")));
        return serverResponse;
    }
    
//done
    public String createForum(String forumName, String policyName, String securityName) {
        
        String serverResponse = "";
        this.msg = Configuration.FORUM_CREATE + " " + forumName + " " + policyName + " " + securityName;
        try {
            //listenSocket();
            serverResponse = start();
            if(serverResponse.contains("SUCCESS")){
                return serverResponse;
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Read failed");
            System.exit(1);
        }
        this.msg = null;
        return "FAIL";
            
    }
    
    public Vector<String> DisplayForums() {
        String serverResponse = "";
        
        Vector<String> ans = new Vector<String>();
        this.msg = Configuration.DISPLAY_FORUMS;
        try {
            //listenSocket();
            serverResponse = start();
            String[] afterSplit = serverResponse.split(" ");
            for (int i = 0; i < afterSplit.length; i++)
                ans.add(afterSplit[i]);
        }
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Read failed");
            System.exit(1);
        }
        return ans;
    }
    
    public boolean EnterForum(String currentForum) {
        Forum newForum = forumGenerator.getForum(currentForum);
        if(newForum==null) return false;
        Member guest = new Member(null, null, null);
        return true;
    }
    
    public Vector<String> getSubForums(String currentForum) {
        if(forumGenerator!=null){
            Forum forum = forumGenerator.getForum(currentForum);
            Vector<String> ans = forum.showSubForumList();
            return ans;
        }
        return this.subForums;
    }
    
//done
    public String registerToForum(String currentForum, String userName,
            String password, String Email, String remainderQues, String remainderAns) {
        
        String response = "";
        this.msg = Configuration.REGISTER + " " + currentForum + " " + userName + " " + password + " " +
                Email + " " + remainderQues + " " + remainderAns;
        try {
            //listenSocket();
            response = start();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.msg = null;
//		Forum forum = forumGenerator.getForum(currentForum);
//		if(forum==null) return "Error - Forum Not Found";
//		return forum.regiserToForum(userName, password, eMail, lastName, email);
//        if (response.contains("SUCCESS"))
//            return true;
//        return false;
        return response;
    }
    
//done
    public String createSubForum(String ForumName,String subForumName, String subject,
            String moderatorUserName,String userName) {
        String serverResponse = "";
        this.msg = Configuration.SUBFORUM_CREATE + " " + ForumName + " " + subForumName + " " + subject + " " +
                moderatorUserName + " " + userName;
        try {
            //listenSocket();
            serverResponse = start();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.msg = null;
//        Forum forum = forumGenerator.getForum(ForumName);
//        if(forum==null) return false;
        //String response = forum.createSubForum(subForumName, subject, moderatorUserName,userName,Password);
//        String response = forum.createSubForum(subForumName, subject);
//        if(serverResponse.contains("ERROR") || response.startsWith("ERROR")){
//                System.out.println(response);
//                return false;
//		}
        return serverResponse;
    }
    
//done
    public String logout(String currentForum, String userName) {
        
        String serverResponse = "";
        this.msg = Configuration.LOGOUT_MEMBER + " " + currentForum + " " + userName;
        try {
            //listenSocket();
            serverResponse = start();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.msg = null;
//        if (serverResponse.contains("SUCCESS"))
//            return true;
//        return false;
        return serverResponse;
        
//		Forum forum = forumGenerator.getForum(currentForum);
//		if(forum==null) return false;
        
//		String response = forum.logout(userName, password);
//		if(response.startsWith("Error")){
//			System.out.println(response);
//			return false;
//		}
//		return true;
    }
    
//done
    public String login(String currentForum, String userName, String password) {
        String response = "";
        this.msg = Configuration.LOGIN_MEMBER + " " + currentForum + " " + userName + " " + password;
        try {
            //listenSocket();
            response = start();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.msg = null;
        
        if (response.contains("0"))
            response = "SUPER";
        else if (response.contains("1"))
            response = "ADMIN";
        else if (response.contains("2"))
            response = "MODERATOR";
        else if (response.contains("3"))
            response = "MEMBER";
        else response = "GUEST";
        
        return response;
        
//            Forum forum = forumGenerator.getForum(currentForum);
//            if(forum==null) return false;
//
//            String response = forum.login(userName, password);
//            if(response.startsWith("Error")){
//                    System.out.println(response);
//                    return false;
//            }
//            return true;
    }

//done
    public String deleteSubForum(String forumName,String subForumName) {
        String serverResponse = "";
        String response = "";
        this.msg = Configuration.SUBFORUM_REMOVE + " " + forumName + " " + subForumName;
        try {
            //listenSocket();
            serverResponse = start();
//            Forum forum = forumGenerator.getForum(forumName);
//            if(forum!=null && serverResponse.contains("SUCCESS")){
//                this.msg = null;
//                response = forum.deleteSubForum(subForumName, "some name", "password");
//                if (!response.contains("SUCCESS"))
//                    return false;                
//                forums.remove(forumName);
//                return true;
//        }
        
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.msg = null;
        return serverResponse;

    }
    
    public Vector<String> getThreads(String currentForum,
            String currentSubForum) {
//		Forum forum = forumGenerator.getForum(currentForum);
//		if(forum==null) return null;
//		return forum.showSubForum(currentSubForum);
        return topics;
    }
    
    public boolean publishNewThread(String currentForum,
            String currentSubForum, String threadTitle, String threadContent,
            String userName, String password) {
        Forum forum = forumGenerator.getForum(currentForum);
        if(forum==null) return false;
        
        SubForum subForum = forum.getSubForum(currentSubForum);
        if(subForum==null) return false;
        
        if(forum.isUserExist(userName, password)) return false;
        
        Member member = forum.getMember(userName);
        return subForum.addThread(member,threadTitle,threadContent);
        
    }
    
    public boolean deleteThread(String threadName,String subForum,
            String Forum, String userName,
            String password) {
        Forum forum = forumGenerator.getForum(Forum);
        if(forum==null) return false;
        SubForum sub = forum.getSubForum(subForum);
        if(sub==null) return false;
        if(forum.isUserExist(userName, password)) return false;
        
        Member member = forum.getMember(userName);
        return sub.deleteThread(member,threadName);
        
        
    }
    
    public boolean fileComplaint(String currentForum,
            String currentSubForum, String moderatorUsername, String complaint,
            String userName, String password) {
        // TODO Auto-generated method stub
        return true;
    }
    
    public boolean deleteThreadResponse(String responseNumber,
            String currentThreadTitle, String currentForum,
            String currentSubForum, String userName, String password) {
        Forum forum = forumGenerator.getForum(currentForum);
        if(forum==null) return false;
        
        SubForum subForum = forum.getSubForum(currentSubForum);
        if(subForum==null) return false;
        
        if(forum.isUserExist(userName, password)) return false;
        
        Member member = forum.getMember(userName);
        OpeningPost oPost =  subForum.getThread(currentThreadTitle);
        if(oPost==null) return false;
        return  oPost.deletePost(member,currentThreadTitle);
        
    }
    
    public boolean postThreadResponse(String responseContent,
            String currentThreadTitle, String currentForum,
            String currentSubForum, String userName, String password,String title) {
        Forum forum = forumGenerator.getForum(currentForum);
        if(forum==null) return false;
        
        SubForum subForum = forum.getSubForum(currentSubForum);
        if(subForum==null) return false;
        
        if(forum.isUserExist(userName, password)) return false;
        
        Member member = forum.getMember(userName);
        OpeningPost oPost =  subForum.getThread(currentThreadTitle);
        if(oPost==null) return false;
        return  oPost.AddPost(member,title,responseContent);
        
    }
    
    public Vector<String> getThreadResponses(String currentForum,
            String currentSubForum, String currentThreadTitle) {
        Forum forum = forumGenerator.getForum(currentForum);
        if(forum==null) return null;
        
        SubForum subForum = forum.getSubForum(currentSubForum);
        if(subForum==null) return null;
        
        OpeningPost oPost =  subForum.getThread(currentThreadTitle);
        if(oPost==null) return null;
        
        return oPost.getFollowPostsStringList();
    }
    
    public String getThreadContent(String currentThreadTitle,
            String currentSubForum, String forumName) {
        Forum forum = forumGenerator.getForum(forumName);
        if(forum==null) return null;
        
        SubForum subForum = forum.getSubForum(currentSubForum);
        if(subForum==null) return null;
        
        OpeningPost oPost =  subForum.getThread(currentThreadTitle);
        if(oPost==null) return null;
        
        return oPost.getContent();
    }
    
    public boolean enterNewProperty(String forumName,String property, String newValue,
            String userName, String password) {
        Forum forum = forumGenerator.getForum(forumName);
        if(forum==null) return false;
        //        public String editProperty(Member admin,String property, Object newVal){
        String response = forum.editProperty(userName, property, newValue);
        if (response.startsWith("Error")){
            System.out.println(response);
            return false;
        }
        return true;
    }
    
    
    /*****************************************************/////////
//done
    public boolean deleteForum(String forumName){
        String serverResponse = "";
        this.msg = Configuration.FORUM_REMOVE + " " + forumName;
        try {
            //listenSocket();
            serverResponse = start();
            if(forumGenerator!=null && serverResponse.contains("SUCCESS")){
                this.msg = null;
                return forumGenerator.deleteForum(forumName);
        }
        forums.remove(forumName);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.msg = null;
        return false;

    }
    
    public Vector<String> getAllAdminsInForum(String forumName){
        //if(forumGenerator!=null){
        //return forumGenerator.getAllAdminsInForum(forumName);
        //}
        return admins;
    }
    
    public Vector<String> getAllModerators(String forumName, String subForumName){
        //if(forumGenerator!=null){
        //return forumGenerator.getAllAdminsInForum(forumName);
        //}
        return moderators;
    }
    
    public boolean isMember (String forumName, String userName){
        return true;
    }
    
    public boolean isAdmin (String forumName, String userName){
        return true;
    }
    
    public boolean isModerator (String forumName, String userName){
        return true;
    }
    
    // check if there is such a member who is not an admin in this forum.
    public boolean addAdminToForum(String forumName, String adminName){
        return true;
    }
    
    public boolean removeAdminFromForum(String forumName, String adminName){
        return true;
    }
    
    /*****************connect to server**********************************/
    
    public void listenSocket(){
        //Create socket connection
        try{
            //   InetAddress address = InetAddress.getByName(host);
            socket = new Socket("93.172.108.50", 8888);//("93.172.108.50", 6667);
            out = new PrintWriter(socket.getOutputStream(),
                    true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: kq6py");
            System.exit(1);
        } catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }
    
    
//        public void actionPerformed(ActionEvent event){
//           Object source = event.getSource();
//
//           if(source == button){
//            //Send data over socket
//              String text = textField.getText();
//              out.println(text);
//              textField.setText(new String(""));
//              out.println(text);
//           }
//           //Receive text from server
//           try{
//             String line = in.readLine();
//             System.out.println("Text received: " + line);
//           } catch (IOException e){
//             System.out.println("Read failed");
//             System.exit(1);
//           }
//        }
    
    
    
    public String start() throws IOException {
        Socket lpSocket = null; // the connection socket
        PrintWriter out = null;
        
        // Get host and port
        String host = "10.0.0.8"; //"192.168.1.102";
        int port = 8889;
        
        System.out.println("Connecting to " + host + ":" + port);
        
        // Trying to connect to a socket and initialize an output stream
        try {
            lpSocket = new Socket(host, port); // host and port
            out = new PrintWriter(lpSocket.getOutputStream(), true);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Couldn't get I/O to " + host + " connection");
            System.exit(1);
        }
        
        System.out.println("Connected to server!");
        
//            String msg=this.msg;
//            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
        
//            while ((msg = userIn.readLine())!= null)
        
        out.println(msg);
        
        String ansFromServer = null;
        try {
            BufferedReader clientBR = new BufferedReader(new InputStreamReader(lpSocket.getInputStream()));
            ansFromServer = clientBR.readLine();
            System.out.println("Server response: " + ansFromServer);
        }
        catch (IOException e) {
            System.out.println("Something went really wrong.. \nI can't recieve messages from the server!!");
//                temp = e.toString();
        }
        System.out.println("Exiting...");
        
        // Close all I/O
        out.close();
//            userIn.close();
        lpSocket.close();
        
        return ansFromServer;
    }

    
}
