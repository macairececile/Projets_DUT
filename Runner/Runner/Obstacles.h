#ifndef OBSTACLES_H
#define OBSTACLES_H
#include "MovableElement.h"
#include <SFML/Graphics.hpp>

class Obstacles : public MovableElement{
protected:
    int _type;
public:
    Obstacles(int x, int y, int w, int h, int typeObstacle, int speed);
    int getType() const override;
    ~Obstacles();

};

#endif // OBSTACLES_H
