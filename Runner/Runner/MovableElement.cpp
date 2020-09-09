#include <iostream>
#include <fstream>
#include "MovableElement.h"
using namespace std;

//=======================================
// Constructeurs
//=======================================

MovableElement::MovableElement(int x, int y, int w, int h, int dx, int dy):
    _x(x), _y(y), _w(w), _h(h), _dx(dx), _dy(dy){}


//Permet de donner un déplacement au MovableElement.
void MovableElement::move()
{
    this->_x +=_dx;
    this->_y +=_dy;
}

//=======================================
// Accesseurs en écriture
//=======================================

//Retourne la valeur _x d'un MovableElement (position horizontale)
int MovableElement::getX() const
{
    return this->_x;
}

//=======================================
// Accesseurs en lecture
//=======================================


//Retourne la valeur _y d'un MovableElement (position verticale)
int MovableElement::getY() const
{
    return _y;
}

//Retourne la valeur _h d'un MovableElement (la hauteur)
int MovableElement::getH()
{
    return _h;
}

//Retourne la valeur _w d'un MovableElement (la longeur)
int MovableElement::getW()
{
    return _w;
}

//Permet de changer la valeur dx (la vitesse horizontale)
void MovableElement::setdX(int dx)
{
    _dx = dx;

}

//Permet de changer la valeur dy (vitesse verticale)
void MovableElement::setdY(int dy)
{
    _dy = dy;
}

//Retourne la valeur dx (la vitesse horizontale)
int MovableElement::getdX() const
{
    return _dx;
}

//Retourne la valeur dy (vitesse verticale)
int MovableElement::getdY() const
{
    return _dy;
}

//Retourne un vecteur qui contient la position d'un MovableElement
sf::Vector2f MovableElement::getPosition() const {
    return sf::Vector2f(getX(),getY());
}

//Permet de changer la positon, c'est-à-dire x et y.
void MovableElement::setPosition(int x, int y){
    _x = x;
    _y = y;
}

//Permet de changer la position verticale y
void MovableElement::setY(int y){
    _y = y;
}

//Permet de changer la position horizontale x
void MovableElement::setX(int x){
    _x = x;
}

int MovableElement::getType() const{
}
