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
public class Sign_in_up_info extends Protocol {
    private String info;
    private Boolean correct;
    
    public Sign_in_up_info(String JSONtext) throws ParseException {
        super();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(JSONtext);
        JSONObject jsonObject = (JSONObject) obj;
        this.name = (String) jsonObject.get("name");
        this.info = (String) jsonObject.get("info");
        this.type =  Integer.parseInt(String.valueOf(jsonObject.get("type")));
        this.correct = (Boolean) jsonObject.get("correct");
    }
    
    public Sign_in_up_info()  {
    }

    public String getInfo() {
        return info;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    
    
}
