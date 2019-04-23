/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sieci_komputerowe_klient;

/**
 *
 * @author Marcin
 */
public class Message {
    private String name;
    private String text;
    private boolean owner;
    
    public Message(String name, String text, boolean owner) {
        this.name = name;
        this.text = text;
        this.owner = owner;
        System.out.println("Wiadomosc");
        System.out.println(name);
        System.out.println(text);
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public boolean isOwner() {
        return owner;
    }
    
    
}
