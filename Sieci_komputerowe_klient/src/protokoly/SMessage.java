/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protokoly;

import org.json.simple.JSONObject;

/**
 *
 * @author Marcin
 */
public class SMessage extends Protocol {
    String msg;

    public SMessage(String msg, int type, String name) {
        super(type, name);
        this.msg = msg;
    }
    
    public String objectToJSONString() {
            JSONObject obj = new JSONObject();
            obj.put("name", name);
            obj.put("type", type);
            obj.put("msg", msg);
            return obj.toJSONString()+"\n";
    }
}
