/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sieci_komputerowe_klient;

import protokoly.List_info;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import protokoly.Close;
import protokoly.Protocol;
import protokoly.SMessage_all_info;
import protokoly.SMessage_info;
import protokoly.Sign_in_up;
import protokoly.Sign_in_up_info;

/**
 *
 * @author Marcin
 */
public class Connection {
    
    private Socket socket = null;
    private String host = "192.168.0.101";
    private int port = 10000;
    private BufferedReader serverInPut;
    private DataOutputStream serverOutPut;
    private ObservableList<String> listView = FXCollections.observableArrayList();


    public Connection() {
        try {
            socket = new Socket(host, port);
            serverInPut = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOutPut = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            
        }
    }
    
    public void sendRequest(String text) throws IOException {
        serverOutPut.writeBytes(text);
        serverOutPut.flush();
    }

    public Protocol reciveInfo() throws IOException, ParseException{
        String JSONtext = null;
        Protocol sign_up = null;
        if (serverInPut.ready()){
            JSONtext = serverInPut.readLine();
            sign_up = new Sign_in_up_info(JSONtext);
            return sign_up;
        }
        return null;
    }
    
    public Message reciveData() throws IOException, ParseException{
        String JSONtext = null;
        Protocol data = null;
        if (serverInPut.ready()){
            JSONtext = serverInPut.readLine();
            System.out.println(JSONtext);
            data = new Protocol(JSONtext);
            if(data.getType() == 12){
                List_info data1 = new List_info(JSONtext);
                listView = data1.getList();
            }
            else if (data.getType() == 130){
                System.out.println("typ13bb");
                SMessage_info msg_info = new SMessage_info(JSONtext);
                return new Message(msg_info.getName(), msg_info.getMsg(), false);
            }
            else if (data.getType() == 131){
                System.out.println("typ13all");
                SMessage_all_info msg_all_info = new SMessage_all_info(JSONtext);
                msg_all_info.setOwner();
                return new Message(msg_all_info.getName(), msg_all_info.getMsg(), msg_all_info.isOwner());
            }
        }
        return null;
    }
    
    //Zamykanie połączenie
    public void close_Connection() throws IOException {
        Close close = new Close(04,null);
        serverOutPut.writeBytes(close.objectToJSONString());
        serverOutPut.flush();
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

    public ObservableList<String> getListView() {
        return listView;
    }
   
    
}
