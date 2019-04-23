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
public class Close extends Protocol {

    public Close(int type, String name) {
        super(type, name);
    }
    
    public String objectToJSONString() {
            JSONObject obj = new JSONObject();
            obj.put("type", type);
            return obj.toJSONString()+"\n";
    }
}
