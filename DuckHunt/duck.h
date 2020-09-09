#ifndef DUCK_H
#define DUCK_H

#include <iostream>
#include <cstdlib>
#include <string>
#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include <sstream>

using namespace std;

struct duck
{
    int direction_x;
    int direction_y;
    int direction_w;
    int direction_h;

    int type;
    int couleur;
    int vitesse;

    int mvt_x;
    int mvt_y;

    int compteur;
    bool dead;
    bool fallen;
    bool escaped;

    //mvt alea
    int rx;
    int ry;
    int alea;
    bool mvt_rand;

    SDL_Rect lectureduck;
};

void initDuck(duck &d, int extraVitesse,bool mvt_rand);

void moveDuck(duck &d); // mouvement des canards

void showDuck(duck &d, SDL_Surface *Duck, SDL_Surface *screen);

// CANARD MORT
void initDeathDuck(duck &d,SDL_Surface *Duck, SDL_Surface *screen);
bool isHit(duck &d,int x,int y);
void moveDuckDead(duck &d);
void showDuckDead(duck &d, SDL_Surface *Duck, SDL_Surface *screen);

// CANARD QUI S'ECHAPPE
void showDuckEscape(duck &d, SDL_Surface *Duck, SDL_Surface *screen);
#endif // DUCK_H
