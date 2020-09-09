#include <iostream>
#include <fstream>
#include <ctime>
#include <math.h>
#include "Obstacles.h"
#include "MovableElement.h"

using namespace std;

//Constructeur
Obstacles::Obstacles(int x, int y, int w, int h, int typeObstacle, int speed):
    _type{typeObstacle}, MovableElement(x, y, w, h, speed,0){

}

//Destructeur
Obstacles::~Obstacles(){
}

//Permet de retourner le type de l'obstacle
int Obstacles::getType() const{
    return _type;
}

