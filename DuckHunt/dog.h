#ifndef DOG_H
#define DOG_H

#include <iostream>
#include <cstdlib>
#include <string>
#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include <sstream>
#include "duck.h"

using namespace std;

struct Dog
{
    bool show;
    int x;
    int y;
    int w;
    int h;
    int compteur;
    SDL_Rect lecturedog;
};

void showDog(Dog &d, SDL_Surface *screen, SDL_Surface *dog); // pour afficher le chien
void showDogHunt(Dog &d, SDL_Surface *screen, SDL_Surface *dog, duck &d1, duck &d2); // pour afficher le chien qui rattrape les canards

void initDog(Dog &d);


bool isDogShowOff(Dog &d);


#endif // DOG_H
