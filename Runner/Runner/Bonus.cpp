#include <iostream>
#include <fstream>
#include <ctime>
#include <math.h>
#include "Bonus.h"
#include "MovableElement.h"

using namespace std;


//Constructeur
Bonus::Bonus(int x, int y, int w, int h, int typeBonus, int speed):
    _type{typeBonus}, MovableElement(x, y, w, h, speed,0){

}

//Destructeur
Bonus::~Bonus(){
}


//Fonction qui retourne le type du bonus.
int Bonus::getType() const{
    return _type;
}

