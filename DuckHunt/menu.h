#ifndef MENU_H
#define MENU_H

#include <iostream>
#include <cstdlib>
#include <string>
#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include <sstream>

using namespace std;

struct Button
{
    int x;
    int y;
    SDL_Rect but;
 };

void
initPlay1(Button &p);

void
initPlay2(Button &p);

void
initQuit1(Button &p);

void
initQuit2(Button &p);

void
initHard1(Button &p);

void
initHard2(Button &p);


#endif // MENU_H
