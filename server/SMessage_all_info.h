#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Protocol.h"
#include "json.hpp"

using json = nlohmann::json;
using namespace std;

class SMessage_all_info : public Protocol{
    public:
    string name;
    string msg;
    string from;
    string to;
    int type;

    SMessage_all_info(string m_name, string m_msg, string m_from, string m_to, int m_type){
        name =m_name;
        msg = m_msg;
        from =m_from;
        to =m_to;
        type =m_type;


    }



    json ParseToJson(){
        json jsonObject;
        jsonObject["name"] = this->name;
        jsonObject["msg"] = this->msg;
        jsonObject["from"] = this->from;
        jsonObject["to"] = this->to;
        jsonObject["type"] = this->type;
        return jsonObject;
    };

};