package org.rottenkiwi;

import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.rottenkiwi.jsonparsing.json;


public class Main {

   /* public static String findEventAction(String eventType, String payloadType) {
        //Add switch cases
      /* if (payloadType.equals("Started")) {
            switch (eventType) {
                case "PushEvent":
                    System.out.println("Pushed too " + tbd + ".");
                    break;

                case "WatchEvent":
                    System.out.println("Starred " + tbd + ".");
                    break;

                case "ForkEvent":
                    System.out.println("Forked " + tbd + ".");
                    break;

                default:
                    System.out.println("Unspecifed Event: " + tbd + ".");

            }
        }


         String action;

        return action;
    }*/
    public static void findUserInformation(String gitUsername){
        String eventTypes;
        int i;

        Response response = RestAssured.get( "https://api.github.com/users/" + gitUsername + "/events/public");
        String jsonString = response.asString();
        //System.out.println(response.prettyPrint());
        try {
            JsonNode node = json.parse(jsonString);

            //node reference
            System.out.println(node);

            //make something that lets a user know if size is 0 "No current Activity"

            for(i = 0; i < node.size(); ++i){
                System.out.println(node.get(i).get("type"));
            }
            // (true)System.out.println(node.isArray());
            //System.out.println(node.get("event").asText());

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

        findUserInformation(gitUsername);

    }
}
