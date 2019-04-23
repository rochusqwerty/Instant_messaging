#include <iostream>
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <string.h>
#include <netdb.h>
#include <unistd.h>
#include<pthread.h>
#include <vector>

#include "Sign_in_up.h"
#include "Sign_in_up_info.h"
#include "Protocol.h"
#include "Client.h"
#include "List_info.h"
#include "SMessage.h"
#include "SMessage_info.h"
#include "SMessage_all_info.h"
#include "Message.h"

#define PORT 10000

using json = nlohmann::json;
using namespace std;

vector<Client*> clients;


json parse_str_json(string obj){
    return json::parse(string(obj));
}

void JsonToClient(int client_sock, char* msg){
    int len = strlen(msg);
    int p = 0;
    int q = 0;
    
    printf("SEND: \n");
    while((p = write(client_sock, msg + q, len)) > 0){
        printf("%s", msg +q);
        q+= p;
        len -= p;
        }
    printf("\n");
}

/*
    FUNKCJA WYKONYWANA PRZEZ KAŻDY WĄTEK.
*/
void *connection_handler(void *socket_desc){
    int client_sock = *(int*)socket_desc;
    int signI = 0;
    char bufforTmp[1];
    char buffor[128] = "";
    
    while(true){
        
        signI = 0;
        memset(bufforTmp, 0, sizeof(bufforTmp));
        memset(buffor, 0, sizeof(buffor));
        read(client_sock, bufforTmp, 1);
        while(bufforTmp[0] != '\n'){
                    buffor[signI++] = bufforTmp[0];
                    read(client_sock, bufforTmp, 1);
                    
        }
        printf("%s\n", buffor);
        
        json jsonObject;
        jsonObject = parse_str_json(buffor);   
        int type = jsonObject["type"].get<int>();
/*
    TYPE 0 ODEBRANIE INFORMACJI OD KLIENTA O PRÓBIE REJESTRACJI
    TYPE 10 SERWER ODSYŁA INFORMACJĘ O POMYŚLNEJ LUB NIEPOMYŚLEJ PRÓBIE REJESTRACJI.
*/
        if (type == 0){
            Sign_in_up_info* sign_up_info = new Sign_in_up_info();
            string tempName = jsonObject["name"].get<string>();
            bool next = true;
            for (size_t i=0; i<clients.size(); i++){
                if (clients.at(i)->name == tempName){
                    sign_up_info  = new Sign_in_up_info(false, "Login jest już zajęty!",10);
                    next = false;
                    break;
                }
            }
            if (next){
                sign_up_info  = new Sign_in_up_info(true, "Pomyślnie zarejestrowany",10);
                clients.push_back(new Client(jsonObject["name"].get<string>(),jsonObject["pass"].get<string>(),0,false));
            }
            json jObj = sign_up_info->ParseToJson();
            string temp = jObj.dump();
            temp.append("\n");
            char* sign_up_message = (char*)temp.c_str();
            JsonToClient(client_sock,sign_up_message);
/*
    TYPE 12 PRZESŁANIE LISTY UŻYTKOWNIKÓW.
*/
            signI = 0;
            memset(bufforTmp, 0, sizeof(bufforTmp));
            memset(buffor, 0, sizeof(buffor));
            List_info* list_info = new List_info(12);
                for (size_t i=0; i<clients.size();i++){
                    list_info->clients_name.push_back(clients.at(i)->name);
                }
                
                for (size_t i=0; i<clients.size();i++){
                    if (clients.at(i)->isOnline){
                        list_info->name = list_info->clients_name.at(i);
                        
                        json jObjList = list_info->ParseToJson();
                        string temp = jObjList.dump();
                        temp.append("\n");
                        char* list_info_message = (char*)temp.c_str();
                        JsonToClient(clients.at(i)->fd,list_info_message);
                    }
                }
        }
/*
    TYPE 1 ODPOWIADA ZA ODEBRANIE INFORMACJI OD KLIENTA O PRÓBIE LOGOWANIA
    TYPE 11 SERWER ODSYŁA INFORMACJĘ O POMYŚLNEJ LUB NIEPOMYŚLEJ PRÓBIE LOGOWANIA.
*/
        if (type == 1){
            Sign_in_up_info* sign_in_info  = new Sign_in_up_info();
            string tempName = jsonObject["name"].get<string>();
            string tempPass = jsonObject["pass"].get<string>();
            bool next = true;
            for (size_t i=0; i<clients.size();i++){
                if (clients.at(i)->name == tempName){
                    if (clients.at(i)->pass == tempPass){
                        clients.at(i)->isOnline = true;
                        clients.at(i)->fd = client_sock;
                        sign_in_info  = new Sign_in_up_info(true, "Pomyślnie zalogowany",11);
                    }
                    else{
                        sign_in_info  = new Sign_in_up_info(false, "Nieprawidłowe hasło!",11);
                    }
                    next = false;
                    break;

                }
            }

            if (next){
                sign_in_info  = new Sign_in_up_info(false, "Nieprawidłowy login",11);
            }
            json jObj = sign_in_info->ParseToJson();
            string temp = jObj.dump();
            temp.append("\n");
            char* sign_in_message = (char*)temp.c_str();
            JsonToClient(client_sock,sign_in_message);
/*
    TYPE 12 PRZESŁANIE LISTY UŻYTKOWNIKÓW.
*/
            signI = 0;
            memset(bufforTmp, 0, sizeof(bufforTmp));
            memset(buffor, 0, sizeof(buffor));
            if (sign_in_info->correct){
                List_info* list_info = new List_info(12);
                for (size_t i=0; i<clients.size();i++){
                        list_info->clients_name.push_back(clients.at(i)->name);
                }
                list_info->name = tempName;
                json jObjList = list_info->ParseToJson();
                string temp = jObjList.dump();
                temp.append("\n");
                char* list_info_message = (char*)temp.c_str();
                JsonToClient(client_sock,list_info_message);
            }

/*
    TYPE 131 PRZESŁANIE HISTORII KONWERSACJI.
*/
            
            signI = 0;
            memset(bufforTmp, 0, sizeof(bufforTmp));
            memset(buffor, 0, sizeof(buffor));
            for (size_t i=0; i<clients.size(); i++){
                if(client_sock == clients.at(i)->fd){
                    for (size_t j=0; j<clients.at(i)->client_msg.size(); j++){
                        SMessage_all_info* sMessage_all_info = new SMessage_all_info(clients.at(i)->name, clients.at(i)->client_msg.at(j)->msg, clients.at(i)->client_msg.at(j)->from, clients.at(i)->client_msg.at(j)->to, 131);
            
                        json jObj = sMessage_all_info->ParseToJson();
                        string temp = jObj.dump();
                        temp.append("\n");
                        char* sMessage_all_info_message = (char*)temp.c_str();
                        JsonToClient(client_sock,sMessage_all_info_message);
                    }
                    break;
                }
            }
            


        }

/*
    TYPE 30 ODEBRANIE OD KLIENTA WIADOMOŚCI, KTÓRĄ NALEŻY PRZEKAZAĆ DANEMU UŻYTKOWNIKOWI.
*/
        if (type == 30){
            string tempMessage = jsonObject["msg"].get<string>();
            string tempNameTo = jsonObject["name"].get<string>();
            string tempNameFrom;
            Message* message = new Message(tempNameTo,tempMessage);
            for (size_t i=0; i<clients.size(); i++){
                if (clients.at(i)->fd == client_sock){
                    tempNameFrom = clients.at(i)->name;
                    message->from = tempNameFrom;
                    clients.at(i)->client_msg.push_back(message);
                    break;
                }
            }
/*
    TYPE 130 PRZEKAZANIE DANEMU UŻYTKOWNIKOWI WIADOMOŚCI.
*/

            SMessage_info* sMessage_info = new SMessage_info(tempMessage, tempNameFrom, 130);
            json jObj = sMessage_info->ParseToJson();
            string temp = jObj.dump();
            temp.append("\n");
            char* sMessage_info_message = (char*)temp.c_str();
            for (size_t i=0; i<clients.size(); i++){
                if(tempNameTo == clients.at(i)->name){
                    if(clients.at(i)->isOnline){
                        JsonToClient(clients.at(i)->fd,sMessage_info_message);  
                    }   
                    clients.at(i)->client_msg.push_back(message);
                    break;
                }
            }
        }
            
        
/*
    TYPE 4 ZAMKNIĘCIE POŁĄCZENIA.
*/
        
        if (type == 4){
            for (size_t i=0; i<clients.size();i++){
                if (clients.at(i)->fd == client_sock){
                    clients.at(i)->fd = 0;
                    clients.at(i)->isOnline = false;
                }
            }

            close(client_sock);
            break;
        }


    }
    return 0;
}

void runServer(){
    
    int fd = socket(PF_INET,SOCK_STREAM,0);
    int client_sock;
    int on=1;
    socklen_t sizeAddrClient;
    struct sockaddr_in addrClient,addrServer;
    

    setsockopt(fd,SOL_SOCKET,SO_REUSEADDR,(char*)&on,sizeof(on));
    memset(&addrServer,0,sizeof(addrServer));
    addrServer.sin_family = PF_INET;
    addrServer.sin_port = htons(PORT);
    addrServer.sin_addr.s_addr = INADDR_ANY;

    bind(fd,(struct sockaddr*)&addrServer,sizeof(addrServer));
    
    listen(fd,1);
    
    pthread_t thread_id;
    sizeAddrClient = sizeof(addrServer);
    while((client_sock = accept(fd, (struct sockaddr *)&addrClient,&sizeAddrClient) )){
        printf("new connection: %s\n",inet_ntoa((struct in_addr)addrClient.sin_addr));
        if(pthread_create( &thread_id , NULL ,  connection_handler , (void*) &client_sock)<0){
            printf("cos tam");
        }
            
    }
    
    

}


int main(){

    runServer();
    return 0;
}