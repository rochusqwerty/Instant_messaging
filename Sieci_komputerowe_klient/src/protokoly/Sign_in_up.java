/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protokoly;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Marcin
 */
public class Sign_in_up extends Protocol{
    private String pass;

    public Sign_in_up(String name, String pass, int type) {
        super(type, name);
        this.pass = pass;
    }
    
    public Sign_in_up(String JSONtext) throws ParseException {
        super();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(JSONtext);
        JSONObject jsonObject = (JSONObject) obj;
        this.name = (String) jsonObject.get("name");
        this.pass = (String) jsonObject.get("pass");
        this.type = (int) jsonObject.get("type");
    }
    
    public String objectToJSONString() {
            JSONObject obj = new JSONObject();
            obj.put("name", name);
            obj.put("pass", pass);
            obj.put("type", type);
            return obj.toJSONString()+"\n";
    }

    public String getPass() {
        return pass;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    
    
}
