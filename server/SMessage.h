#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Protocol.h"
#include "json.hpp"

using json = nlohmann::json;
using namespace std;

class SMessage : public Protocol{
    public:
    
    string msg;

    SMessage(json jsonObject){
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