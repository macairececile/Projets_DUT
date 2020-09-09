#ifndef CURSEUR_H
#define CURSEUR_H

#include <iostream>
#include <cstdlib>
#include <string>
#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include <sstream>

using namespace std;

void showcurseur(SDL_Surface *screen, int xmouse, int ymouse);

#endif // CURSEUR_H
