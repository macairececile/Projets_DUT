#include "Ball.h"
#include <iostream>
#include <fstream>
#include <SFML/Graphics/Sprite.hpp>

using namespace std;

//Fonction qui permet de déplacer la balle
void Ball::move(){
    _x += _dx;  //A _x on ajoute la vitesse de déplacement en x.

// On vérifie que les coordonnées sont dans la fenêtre.
    if (_x < 0)
    {
        _x = 0;
    }
    if(_x+_w > 800)
    {
        _x = 800 - _w;
    }


    _y += _dy;  //A _y on ajoute la vitesse de déplacement en y.

//On vérifie que les coordonnées sont dans la fenêtre.
    if (_y < 0)
    {
        _y = 0;
    }
    if(_y+_h > 600)
    {
        _y = 600 - _h;
    }
}

