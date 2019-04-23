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
public class SMessage_info extends Protocol {
    private String msg;
    
    public SMessage_info(String JSONtext) throws ParseException {
        super();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(JSONtext);
        JSONObject jsonObject = (JSONObject) obj;
        this.name = (String) jsonObject.get("name");
        this.msg = (String) jsonObject.get("msg");
        this.type =  Integer.parseInt(String.valueOf(jsonObject.get("type")));
    }

    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }
    
    
}
