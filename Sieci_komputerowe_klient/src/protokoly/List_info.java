/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protokoly;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import protokoly.Protocol;

/**
 *
 * @author Marcin
 */
public class List_info extends Protocol {
    private ObservableList<String> list;
    
    public List_info(String JSONtext) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(JSONtext);
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("name");
        ObservableList<String> list = FXCollections.observableArrayList((ArrayList<String>)jsonObject.get("clients_name"));
        if(list.contains(name)){
            list.remove(name);
        }
        this.list=list;
    }

    public ObservableList<String> getList() {
        return list;
    }

    
}
