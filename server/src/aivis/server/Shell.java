package aivis.server;

import java.util.Scanner;
import javafx.scene.chart.PieChart.Data;

import aivis.machinelearning.*;
import aivis.common.*;
import aivis.database.DatabaseInfo;

public class Shell implements Runnable{
    private ServerMain serverMain;

    public Shell(ServerMain serverMain){
        this.serverMain = serverMain;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command = null;
        while(serverMain.state.compareTo("x") != 0){
            System.out.print("Input your command: ");
            command = scanner.nextLine();

            if(command.compareTo("help") == 0){
                System.out.println("shutdown");
                System.out.println("usercount");
                System.out.println("train");
                System.out.println("dbtest");
                System.out.println("videotest");
            }
            else if(command.compareTo("shutdown") == 0){
                shutDown();
            }
            else if (command.compareTo("usercount") == 0){
                userCount();
            }
            else if (command.compareTo("train") == 0){
                train();
            }
            else if (command.compareTo("dbtest") == 0){
                dbtest();
            }
            else if (command.compareTo("videotest") == 0){
                videotest();
            }
            else {
                System.out.println("Wrong Command");
            }

            command = null;
        }
        scanner.close();
    }

    private void shutDown(){
        System.out.println("Exit Server");
        System.exit(0);
    }

    private void userCount(){
        System.out.print("Number of User: ");
        System.out.println(serverMain.numUserProcess);
    }

    private void train(){
        Chatbot chatbot = new Chatbot();

        System.out.println("Start Train");

        chatbot.trainChatbot();

        System.out.println("End Train");
    }

    private void dbtest(){
        System.out.println("Start");

        String hostname = "localhost";
        String port = "3306";
        String dbName = "aivis";
        String userName = "admin";
        String password = "1+1=mysql";

        //DatabaseInfo databaseInfo = new DatabaseInfo(hostname, port, dbName, userName, password);
        DatabaseInfo databaseInfo = new DatabaseInfo(hostname, port, dbName, userName, password);

        if(databaseInfo.jdbcUrl != null){
            //String uID = "0001";
            String DocID = "0001";
            String filePath = "../data/data.txt";

            Self_Intro_Doc introDoc = new Self_Intro_Doc(databaseInfo);

            introDoc.DBRead(DocID, filePath);

            System.out.println("End");
        }
        else{
            System.out.println("Fail");
        }
    }

    private void videotest(){
        System.out.println("Start Video Analysis Test");

        EyeChecker eyeChecker = new EyeChecker();
        EmotionChecker emotionChecker = new EmotionChecker();

        eyeChecker.excute();
        emotionChecker.excute();

        System.out.println("End Video Analysis Test");
    }
}
