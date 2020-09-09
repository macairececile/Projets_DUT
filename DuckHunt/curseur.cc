#include "curseur.h"
#include "look.h"

using namespace std;

/****************** Nom de la fonction **********************
* showcurseur                                               *
******************** Auteur , Dates *************************
* Macaire Cécile / 29 novembre 2015                         *
********************* Description ***************************
* affiche la cible à la place du curseur de la souris       *
*********************** Entrées *****************************
* l'écran, coordonnée en x et y de la souris                *
*********************** Sorties *****************************
* renvoie l'image de la cible                               *
************************************************************/

void showcurseur(SDL_Surface *screen, int xmouse, int ymouse)
{
    SDL_Surface *Target =  loadImageWithColorKey("viseur.png",0,0,0); // cible du jeu

    SDL_Rect position;
    position.x = 0;
    position.y = 0;
    position.h = 30;
    position.w = 30;

    applySurface(xmouse-15, ymouse-15, Target, screen, &position);
}

