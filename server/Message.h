#pragma once
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "json.hpp"

class Message{

    public:
        string from;
        string to;
        string msg;

        Message(string m_to, string m_msg){
            to = m_to;
            msg = m_msg;
        };

       json ParseToJson(){
        json jsonObject;
        jsonObject["from"] = this->from;
        jsonObject["to"] = this->to;
        jsonObject["msg"] = this->msg;
        return jsonObject;
    }; 


};