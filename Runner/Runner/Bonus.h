#ifndef BONUS_H
#define BONUS_H

#include "MovableElement.h"
#include <SFML/Graphics.hpp>

class Bonus : public MovableElement{
protected:
    int _type;
public:
    Bonus(int x, int y, int w, int h, int typeBonus, int speed);
    int getType() const override;
    ~Bonus();

};

#endif // BONUS_H
