#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Protocol.h"
#include "json.hpp"

using json = nlohmann::json;
using namespace std;

class SMessage_info : public Protocol{
    public:
    
    string msg;

    SMessage_info(string m_msg, string m_name, int m_type){
        msg = m_msg;
        name = m_name;
        type = m_type;
    }

    SMessage_info(json jsonObject){
        name = jsonObject["name"].get<string>();
        msg = jsonObject["msg"].get<string>();
        type = jsonObject["type"].get<int>();
    };

    json ParseToJson(){
        json jsonObject;
        jsonObject["name"] = this->name;
        jsonObject["msg"] = this->msg;
        jsonObject["type"] = this->type;
        return jsonObject;
    };

};