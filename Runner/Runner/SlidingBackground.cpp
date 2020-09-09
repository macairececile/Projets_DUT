#include <iostream>
#include <fstream>

using namespace std;

#include "SlidingBackground.h"
#include "GraphicElement.h"

//Constructeur
SlidingBackground::SlidingBackground(sf::Texture &image,int x, int y, int w, int h, unsigned int speed):
    _w(1200), _h(600), _speed(speed), x(x), y(y){

    _left = new GraphicElement(image,x, y, w,h);
    _right = new GraphicElement(image, x, y, w,h);

    _x =0;
    _y = _w;
    _left->setPosition(_x, 0);
    _right->setPosition(_y, 0);
}

//Fonction qui permet de dessiner deux GraphicElement, donc les fonds défilant.
void SlidingBackground::draw(sf::RenderWindow *window){
    move();
    _left->draw(window);
    _right->draw(window);
}


//Fonction qui permet de déplacer le slidingBackgroung
void SlidingBackground::move(){

    if(_y > 0) //Si y est supérieur à 0, on enlève à x et y la vitesse
    {
        _x -= _speed;
        _y -= _speed;
    }
    else  //Sinon x = 0 et y = la longeur
    {
        _x = 0;
        _y = _w;
    }
    _left->setPosition(_x, 0);  //On  met _left à la valeur de x et 0
    _right->setPosition(_y, 0); //On met _right à la valeur de y et 0

}


//Permet de changer la vitesse
void SlidingBackground::setSpeed(unsigned int speed){
    _speed = speed;

}

//Destructeur
SlidingBackground::~SlidingBackground()
{
    delete _left;
    delete _right;
}


