/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sieci_komputerowe_klient;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Marcin
 */
public class Client {
    private String name;
    //private HashMap<Integer, Chat> conversations;
    private ArrayList<Message> listOfMessage;

    public Client(String name) {
        this.name = name;
        this.listOfMessage = new ArrayList();
        System.out.println(name);
    }
    
    public Client(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Message> getListOfMessage() {
        return listOfMessage;
    }

    public void setListOfMessage(ArrayList<Message> listOfMessage) {
        this.listOfMessage = listOfMessage;
    }
    
    public void addMessage(Message message) {
        listOfMessage.add(message);
    }
    
}
