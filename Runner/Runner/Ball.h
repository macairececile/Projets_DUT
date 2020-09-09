#ifndef BALL_H
#define BALL_H

#include "MovableElement.h"


class Ball : public MovableElement {    
public:
    Ball(int x, int y, int w, int h, int dx, int dy):
        MovableElement(x, y, w, h, dx, dy){}
    const int g = 5;
    void move();
};


#endif // BALL_H
