#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "json.hpp"
#include "Message.h"

class Client{
    public:
    string name;
    string pass;
    int fd;
    bool isOnline;
    vector<Message*> client_msg;
    Client(string m_name, string m_pass, int m_fd, bool m_isOnline){
        name = m_name;
        pass = m_pass;
        fd = m_fd;
        isOnline = m_isOnline;
    };


};