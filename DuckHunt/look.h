#ifndef LOOK_H
#define LOOK_H

#include <iostream>
#include <cstdlib>
#include <string>
#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include <sstream>

using namespace std;

SDL_Surface *load_image( std::string filename );

SDL_Surface *loadImageWithColorKey(string filename, int r, int g, int b);

void applySurface(int x, int y, SDL_Surface* source, SDL_Surface* destination, SDL_Rect* clip);

#endif // LOOK_H
