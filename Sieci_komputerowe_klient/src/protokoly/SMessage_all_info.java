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
public class SMessage_all_info extends Protocol {
    private String msg;
    private String from;
    private String to;
    private boolean owner;
    
    public SMessage_all_info(String JSONtext) throws ParseException {
        super();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(JSONtext);
        JSONObject jsonObject = (JSONObject) obj;
        this.name = (String) jsonObject.get("name");
        this.msg = (String) jsonObject.get("msg");
        this.type =  Integer.parseInt(String.valueOf(jsonObject.get("type")));
        this.from = (String) jsonObject.get("from");
        this.to = (String) jsonObject.get("to");
    }

    public void setOwner() {
        if(to.equals(name)){
            this.owner = false;
            this.name = this.from;
        }
        else{
            this.owner = true;
            this.name = this.to;
        }
    }

    public boolean isOwner() {
        return owner;
    }
    
    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }
}
