#ifndef SLIDINGBACKGROUND_H
#define SLIDINGBACKGROUND_H
#include <SFML/Graphics.hpp>
#include "GraphicElement.h"

class SlidingBackground : public sf::Sprite{
private:
    unsigned int _speed;
    GraphicElement *_left, *_right;
    int x, y, _w, _h;
    int _x, _y;

public:
    SlidingBackground(sf::Texture &image, int x, int y, int w, int h, unsigned int speed);
    ~SlidingBackground();
    virtual void draw(sf::RenderWindow *window);
    void setSpeed(unsigned int speed);
    void move();

};

#endif // SLIDINGBACKGROUND_H
