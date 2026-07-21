package org.rottenkiwi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.rottenkiwi.jsonparsing.json;


public class Main {

   public static String findEventAction(String eventType, String payload) {
       HashMap<String, String> differentEventType = new HashMap<>();
       String action;

       differentEventType.put("WatchEvent", "Starred the repository ");
       differentEventType.put("CommitCommentEvent", "Commit comment created at ");
       differentEventType.put("CreateEvent", "Created the repository ");
       differentEventType.put("DeleteEvent", "Deleted the repository ");
       differentEventType.put("DiscussionEvent", "Discussion created at the repository ");
       differentEventType.put("ForkEvent","Forked the repository ");
       differentEventType.put("GollumEvent", "Wiki page created/updated ");
       differentEventType.put("PublicEvent", "This private repository was made public: ");
       differentEventType.put("PushEvent", "Pushed the repository ");
       differentEventType.put("IssueCommentEvent", "A comment has been created on this issue: ");
       differentEventType.put("MemberEvent", "Added to the repository: ");
       differentEventType.put("PullRequestReviewCommentEvent", "Created a pull request review ");
       differentEventType.put("ReleaseEvent", "Released the repository ");



       if(eventType.equals("IssuesEvent")){
            switch (payload){
                case "opened":
                    return "Opened the issue: ";
                case "closed":
                    return "Closed the issue: ";
                case "reopened":
                    return "Reopened the issue: ";
                case "assigned":
                    return "Assigned the issue: ";
                case "unassigned the issue: ":
                    return "Unassigned the issue: ";
                case "labeled":
                    return "Labeled the issue";
                case "unlabeled":
                    return "Unlabled the issue: ";
            }
       }

       else if(eventType.equals("PullRequestEvent")){
           switch (payload){
               case "opened":
                   return "Opened pull request: ";
               case "closed":
                   return "Closed pull request: ";
               case "reopened":
                   return "Reopened pull request: ";
               case "assigned":
                   return "Assigned pull request: ";
               case "unassigned":
                   return "Unassigned pull request: ";
               case "labeled":
                   return "Labeled pull request: ";
               case "unlabeled":
                   return "Unlabled pull request: ";
               case "merged":
                   return "Merged pull request: ";
           }
       }
       else if(eventType.equals("PullRequestReviewEvent")){
           switch (payload){
               case "created":
                   return "Made a pull request review: ";
               case "updated":
                   return "Updated a pull request review: ";
               case "dismissed":
                   return "Dismissed a pull request review: ";

           }

       }

       action = differentEventType.get(eventType);

        return action;
    }
    public static void findUserInformation(String gitUsername){
        String eventType;
        int i;
        String action;
        String payload = null;
        int count = 0;



        Response response = RestAssured.get( "https://api.github.com/users/" + gitUsername + "/events/public");
        String jsonString = response.asString();

        try {
            JsonNode node = json.parse(jsonString);

            if (node.size() == 0){
                System.out.println("This user has no public recent activity.");
            }
            for(i = 0; i < node.size(); ++i){

                eventType = node.get(i).get("type").asText();


                if(node.get(i).get("payload").has("action")) {
                    payload = node.get(i).get("payload").get("action").asText();
                    action = findEventAction(eventType, payload);
                }
                else {
                    action = findEventAction(eventType, payload);
                }
                System.out.println(action + node.get(i).get("repo").get("name").asText() + ".");

                ++count;

                if (count > 5){
                    break;
                }

            }


        }
      catch (IOException e){
            e.printStackTrace();
      }
        }

    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);
        String gitUsername;

        System.out.print("Please Enter GitHub UserName: ");
        gitUsername = scnr.next();

        System.out.println(gitUsername + "'s Recent Activity:");
        System.out.println("-----------");

        findUserInformation(gitUsername);

    }
}
