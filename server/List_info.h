#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Protocol.h"
#include "json.hpp"

using json = nlohmann::json;
using namespace std;

class List_info : public Protocol{
    public:
        vector<string> clients_name;
        
        List_info(int m_type){
            type = m_type;
        };

        void setName(string m_name){
            name = m_name;
        }

        json ParseToJson(){
            json jsonObject;
            jsonObject["clients_name"] = this->clients_name;
            jsonObject["name"] = this->name;
            jsonObject["type"] = this->type;
            return jsonObject;
        };
};