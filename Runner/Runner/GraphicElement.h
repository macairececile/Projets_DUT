#ifndef GRAPHICELEMENT_H
#define GRAPHICELEMENT_H
#include <SFML/Graphics.hpp>

class GraphicElement : public sf::Sprite {
private:
    sf::Texture _image;
    int _x, _y, _w, _h;

public:
    GraphicElement(sf::Texture &image, int x, int y, int w, int h);
    void draw(sf::RenderWindow *window);
    int getPos(int x, int y);
    void setPosition(int x, int y);
    int updatePosition(sf::Vector2f position);
};


#endif // GRAPHICELEMENT_H
