#ifndef MENU_H
#define MENU_H

#include "GraphicElement.h"
#include <SFML/Graphics.hpp>

class Menu : public GraphicElement{
public:
    Menu(sf::Texture &button, int x, int y, int w, int h):
        GraphicElement(button, x, y, w, h){}
};

#endif // MENU_H
