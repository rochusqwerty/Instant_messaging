#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Protocol.h"
#include "json.hpp"

using json = nlohmann::json;
using namespace std;

class Sign_in_up : public Protocol{
    public:
        string pass;
        
        Sign_in_up(json jsonObject){
            name = jsonObject["name"].get<string>();
            pass = jsonObject["pass"].get<string>();
            type = jsonObject["type"].get<int>();
        };
        json ParseToJson(){
            json jsonObject;
            jsonObject["name"] = this->name;
            jsonObject["pass"] = this->pass;
            jsonObject["type"] = this->type;
            return jsonObject;
        };

};

