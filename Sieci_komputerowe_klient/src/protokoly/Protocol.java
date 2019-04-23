/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protokoly;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Marcin
 */
public class Protocol {
    int type;
    String name;

    public Protocol(int type, String name) {
        this.type = type;
        this.name = name;
    }
    
    public Protocol(String JSONtext) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(JSONtext);
        JSONObject jsonObject = (JSONObject) obj;
        this.type = Integer.parseInt(String.valueOf(jsonObject.get("type")));
    }
    
    public Protocol() {
    }

    public int getType() {
        return type;
    }
    
}
