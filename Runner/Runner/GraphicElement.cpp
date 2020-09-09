#include "GraphicElement.h"
#include <iostream>
#include <fstream>
#include <SFML/Graphics/Sprite.hpp>

using namespace std;


//Constructeur
GraphicElement::GraphicElement(sf::Texture &image, int x, int y, int w, int h):
    sf::Sprite(image),  _x(x), _y(y), _w(w), _h(h) {
    this->setPosition(x,y);
}


//Fonction qui permet de dessiner sur la fenêtre un GraphicElement.
void GraphicElement::draw(sf::RenderWindow *window){
    window->draw(*this);
}

//Fonction qui retourne la position d'un GraphicElement.
int GraphicElement::getPos(int x, int y){
    return x, y;
}

//Fonction qui permet de changer la position d'un GraphicElement.
void GraphicElement::setPosition(int x, int y){
    sf::Sprite::setPosition(x,y);
}

int GraphicElement::updatePosition(sf::Vector2f position){
    this->setPosition(position.x, position.y);
}


