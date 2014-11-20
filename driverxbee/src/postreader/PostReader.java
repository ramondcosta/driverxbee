/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postreader;
import java.net.*;
import java.io.*;
/**
 *
 * @author smart
 */
public class PostReader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerSocket server;
        Socket client;
        StringBuilder buffer = new StringBuilder();
        byte[] b = new byte[100];
        //char[] b = new char[100];
        try{
            server = new ServerSocket(80); 
            try{
                for(;;){
                    client = server.accept();
                    //BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    //System.out.println(3);
                    client.getInputStream().read(b);
                    //System.out.println(4);
                    System.out.println(new String(b, "UTF-8"));
                    //String line;
                    //while ((line = in.readLine()) != null) {
                    //    buffer.append(line);
                    //}      
                    //in.read(b,0,100);
                    //System.out.println(line);
                }
                //client.close();
            } catch (Exception e) {
                System.out.println("Accept failed: 4321");
                System.exit(-1);
            }
        } catch (Exception e) {
            System.out.println("Could not listen on port 4321");
            System.exit(-1);
        }
        
    }
    
}
