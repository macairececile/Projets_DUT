#ifndef MOVABLEELEMENT_H
#define MOVABLEELEMENT_H
#include <SFML/Graphics.hpp>

class MovableElement{
protected:
    int _x, _y, _w, _h
    , _dx, _dy;
public:
    MovableElement(int x, int y, int w, int h, int dx, int dy);
    int getX() const;
    int getY() const;
    int getW();
    int getH();
    void move();
    void setdX(int dx);
    void setdY(int dy);
    void setX(int x);

    sf::Vector2f getPosition() const;
    void setPosition(int x, int y);
    int getdY() const;
    int getdX() const;
    void setY(int y);
    virtual int getType() const;

};


#endif // MOVABLEELEMENT_H
