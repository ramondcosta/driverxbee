package driverxbee;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.XBeeException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.net.*;
import java.io.*;

public class DriverXbee {

    /**
     *
     * @param status
     * @throws java.io.IOException
     */
    public static void sendPost(boolean status) throws Exception{
        String url, parameters;
        url = "http://localhost";
        parameters = "sensor="+status+"";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "WiserTool1.0");
        
        // Envia o pacote
        con.setDoOutput(true);
        DataOutputStream write = new DataOutputStream(con.getOutputStream());
        //OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        //out.write(parameters);
        //out.flush();
        //out.close();
        write.writeChars(parameters);
        write.flush();
        write.close();
        con.disconnect();
        // ok!
        System.out.println("OK! SEND IT!");
    }
    public static void listenSocket() throws Exception{
    //Create socket connection
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        String str;
        str = "vaca do mato";
        //OutputStream out;
        try{
          socket = new Socket("localhost", 4321);
          out = new PrintWriter(socket.getOutputStream(),true);
          out.println("rapaz");
          //in = new BufferedReader(new InputStreamReader(
          //      socket.getInputStream()));
        }catch (UnknownHostException e) {
          System.out.println("Unknown host: Wiskey");
          System.exit(1);
        }catch  (IOException e) {
          System.out.println("No I/O");
          System.exit(1);
        }
}
    public static void main(String[] args) {
       boolean message;
       XBeeResponse response ;
       XBee xbee = new XBee();
       long atual_time, prev_time, change_time = 0;
       boolean status = false;
       Calendar cal;
       SimpleDateFormat sdf;
       cal = Calendar.getInstance();            
       //sdf = new SimpleDateFormat("d 'de' MMMM 'de' Y");
       sdf = new SimpleDateFormat("d/M/yy");
       System.out.println( sdf.format(cal.getTime()) );
  
       try{
            xbee.open("COM8",9600);
            while(true){
                //System.out.println(change_time);
                prev_time = System.currentTimeMillis() - change_time;
                response = xbee.getResponse();
                atual_time = System.currentTimeMillis();
                //System.out.println(atual_time - prev_time);
                if((atual_time - prev_time < 1900) != status){
                         
                    status=!status;
                    System.out.println(status);
                    //if((atual_time - change_time) > 10000){
                    try{
                        prev_time = System.currentTimeMillis();
                        DriverXbee.sendPost(status);
                        change_time = System.currentTimeMillis() - prev_time;
                        //System.out.println(change_time);
                        System.out.println("OK... No error with the SendPost Function");
                    }
                    catch(Exception e){
                        System.out.println("OH OH... there's an ERROR with the SendPost :/");
                    }
                    //}
                    //change_time = System.currentTimeMillis(); 
                }
                else change_time = 0; 
                // ou vai??? Não irá funciona se o Xbee.getResponse não for THREAD!
                
                //RxResponseIoSample ioSample = (RxResponseIoSample) response;
                //System.out.println(ioSample.isD0Enabled() + " " + ioSample.isD1Enabled());
            }   
            //xbee.close();
       }
       catch(XBeeException e){
           System.out.println("OH OH... there's an ERROR with all the PROGRAM :/");
       }
    }
    
}

