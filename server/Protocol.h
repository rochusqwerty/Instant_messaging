#pragma once
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "json.hpp"

using json = nlohmann::json;
using namespace std;

class Protocol{
    public:
        int type;
        string name;

        Protocol(){

        };
};