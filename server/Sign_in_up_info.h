#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "Protocol.h"
#include "json.hpp"

using json = nlohmann::json;
using namespace std;

class Sign_in_up_info : public Protocol{
    public:
        bool correct;
        string info;

        Sign_in_up_info(){
            
        }
        Sign_in_up_info(bool m_correct, string m_info, int m_type){
            correct = m_correct;
            info = m_info;
            type = m_type;
        }

        
        json ParseToJson(){
            json jsonObject;
            jsonObject["correct"] = this->correct;
            jsonObject["info"] = this->info;
            jsonObject["name"] = this->name;
            jsonObject["type"] = this->type;
            return jsonObject;
        };

        
};