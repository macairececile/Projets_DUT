#include "duck.h"
#include "look.h"
#include "score.h"
#include <iostream>
#include <cstdlib>
#include <string>

/****************** Nom de la fonction **********************
* initDuck                                                  *
******************** Auteur , Dates *************************
* Macaire Cécile / 1 décembre 2015                          *
********************* Description ***************************
* trajectoire aléatoire des canards dans les deux modes,    *
*  couleur des canards et leur vitesse spécifique,          *
*********************** Entrées *****************************
* struture du canard, booléen pour savoir dans quel mode on *
* est, vitesse                                              *
*********************** Sorties *****************************
* position et couleur du canard                             *
************************************************************/

/****************** Nom de la fonction **********************
* moveDuck                                                  *
******************** Auteur , Dates *************************
* Macaire Cécile / 1 décembre 2015                          *
********************* Description ***************************
* mouvement des canards en mode normal                      *
*********************** Entrées *****************************
* struture du canard                                        *
*********************** Sorties *****************************
* mouvement des canards                                     *
************************************************************/

/****************** Nom de la fonction **********************
* showDuck                                                  *
******************** Auteur , Dates *************************
* Macaire Cécile / 1 décembre 2015                          *
********************* Description ***************************
* affiche les canards dans le jeu                           *
*********************** Entrées *****************************
* structure du canard, ecran et sprite                      *
*********************** Sorties *****************************
* les canards                                               *
************************************************************/

/****************** Nom de la fonction **********************
* showDuckEscape                                            *
******************** Auteur , Dates *************************
* Macaire Cécile / 1 décembre 2015                          *
********************* Description ***************************
* affiche les canards qui s'échappent (non touchés par le   *
* joueur                                                    *
*********************** Entrées *****************************
* structure du canard, ecran et sprite                      *
*********************** Sorties *****************************
* les canards                                               *
************************************************************/

/****************** Nom de la fonction **********************
* showDuckDead                                              *
******************** Auteur , Dates *************************
* Macaire Cécile / 1 décembre 2015                          *
********************* Description ***************************
* affiche les canards qui tombent lorsque le joueur en a    *
* touché un                                                 *
*********************** Entrées *****************************
* structure du canard, ecran et sprite                      *
*********************** Sorties *****************************
* les canards                                               *
************************************************************/

/****************** Nom de la fonction **********************
* moveDuckDead                                              *
******************** Auteur , Dates *************************
* Macaire Cécile / 2 décembre 2015                          *
********************* Description ***************************
* gere le mouvement des canards lorsqu'ils tombent          *
*********************** Entrées *****************************
* structure du canard                                       *
*********************** Sorties *****************************
* les canards                                               *
************************************************************/

/****************** Nom de la fonction **********************
* initDeathDuck                                             *
******************** Auteur , Dates *************************
* Macaire Cécile / 3 décembre 2015                          *
********************* Description ***************************
* initialise les canards morts                              *
*********************** Entrées *****************************
* structure du canard, ecran, sprite                        *
*********************** Sorties *****************************
* les canards                                               *
************************************************************/

/****************** Nom de la fonction **********************
* isHit                                                     *
******************** Auteur , Dates *************************
* Macaire Cécile / 3 décembre 2015                          *
********************* Description ***************************
* booléen pour savoir si le canard est touché               *
*********************** Entrées *****************************
* structure du canard, coordonnées en x et en y             *
*********************** Sorties *****************************
* les canards                                               *
************************************************************/

//JE CODE MOUVEMENT ALEATOIRE LINEAIRE

using namespace std;

const bool MVT_ALEATOIRE = false;
const int TIME_ALEATOIRE = 70;

const int MVT_ESCAPE =6;

const int SCREEN_WIDTH = 751;
const int SCREEN_HEIGHT = 761;
const int SCREEN_BPP = 32;

const int LARGEUR_HITBOX = 58;

/*************
 * Attention pas confondre HAUTEUR_HITBOX=63 et Lectureduck.h=71... pour animation */
const int HAUTEUR_HITBOX = 63;
const int FALL_DUCK = 4;
const int ACC_CHUTE = 42;

void initDuck(duck &d, int extraVitesse, bool mvtRandom)
{
    d.compteur=1;
    d.dead=false;
     d.escaped=false;
    d.mvt_rand=mvtRandom;

    //TRAJECTOIRE RANDOM (mode hard)
    d.alea=TIME_ALEATOIRE; //chance de changer de trajectoire (augmente avec temps)
    d.rx=0;
    d.ry=0;

    d.fallen=false;//(pour retenir le moment ou le duck tombe au sol)

    /***************************************************************/
    d.couleur = rand()%3;
    d.type = rand()%2;
    d.direction_x = rand()%(SCREEN_WIDTH-LARGEUR_HITBOX);
    d.direction_y = rand()%(200-HAUTEUR_HITBOX);

    /************************************************/
    d.lectureduck.w = 62;
    d.lectureduck.h = 76;
    d.mvt_x = 1+extraVitesse;
    d.mvt_y = 1+extraVitesse;

    if (d.couleur == 0) // canard noir
    {
        d.lectureduck.y = 230;
        d.vitesse = 1;
    }

    if (d.couleur == 1) // canard rouge
    {
        d.lectureduck.y = 317;
        d.vitesse = 2;
    }

    if (d.couleur == 2) // canard bleu
    {
        d.lectureduck.y = 402;
        d.vitesse = 3;
    }
}

void moveDuck(duck &d) // mouvement du canard (mode normal)
{
    SDL_Rect tmp;
    if(MVT_ALEATOIRE || d.mvt_rand){

        int continu=rand()%d.alea;
        if(continu==0) //si 0 on change la direction !
        {

            d.rx=(rand()%3);    //2 Risques sur 3 alterer vitesse
            if(d.rx==0)         //1 sur 3 changer de sens
                d.mvt_x*=-1;
            else if(d.mvt_x<0)
                d.rx*=-1;

            d.ry=(rand()%3);
            if(d.ry==0)
                d.mvt_y*=-1;
            else if(d.mvt_y<0)
                d.ry*=-1;
            d.alea=TIME_ALEATOIRE;
        }
        d.alea--;
    }

    d.direction_x+=d.mvt_x*d.vitesse+d.rx;
    d.direction_y+=d.mvt_y*d.vitesse+d.ry;
    tmp.x = d.direction_x;
    tmp.y = d.direction_y;
    tmp.h = HAUTEUR_HITBOX;
    tmp.w = LARGEUR_HITBOX;

    //CADRE COLLISION
    if(tmp.x+tmp.w>SCREEN_WIDTH) {
        d.mvt_x*=-1;d.direction_x=SCREEN_WIDTH-tmp.w;

    }
    else if(tmp.x<0) {
        d.mvt_x*=-1;d.direction_x=0;
    }

    if(tmp.y+tmp.h>SCREEN_HEIGHT-290)
    {
        d.mvt_y*=-1;d.direction_y=SCREEN_HEIGHT-290-tmp.h;
    }
    else if(tmp.y<0){
        d.mvt_y*=-1;d.direction_y=0;
    }
}

void moveDuckDead(duck &d){ // mouvement des canards lorsqu'ils tombent
    //accelerer la vitesse chute -> selon hauteur
    d.direction_y+=FALL_DUCK +d.direction_y/ACC_CHUTE;
}


void showDuck(duck &d, SDL_Surface *Duck, SDL_Surface *screen) // affiche le canard à l'ecran
{
    int corr =0;
    switch(d.compteur)//enchainement sprite
    {
    case 0: {d.lectureduck.x=16+d.type*198;

    }
        break;
    case 1: {d.lectureduck.x=79+d.type*211; corr=7;

    }
        break;
    case 2: {d.lectureduck.x=148+d.type*217; corr=2;

    }
        break;
    }
    d.lectureduck.w=62+d.type*11;
    applySurface(d.direction_x-corr*(1-d.type)
                 , d.direction_y, Duck, screen, &d.lectureduck);
    d.compteur=(d.compteur+1)%3;
}

void showDuckEscape(duck &d, SDL_Surface *Duck, SDL_Surface *screen){ // canards qui s'echappent
    int corr =0;
     d.compteur=(d.compteur+1)%3;
    switch(d.compteur)//enchainement sprite
    {
    case 0: {d.lectureduck.x=16+d.type*198;

    }
        break;
    case 1: {d.lectureduck.x=79+d.type*211; corr=7;

    }
        break;
    case 2: {d.lectureduck.x=148+d.type*217; corr=2;

    }
        break;
    }
    d.lectureduck.w=62+d.type*11;

    d.direction_y-=MVT_ESCAPE;
    applySurface(d.direction_x-corr*(1-d.type), d.direction_y, Duck, screen, &d.lectureduck);
    if (d.direction_y < (-d.lectureduck.h))
        d.escaped=true;

}

void showDuckDead(duck &d, SDL_Surface *Duck, SDL_Surface *screen){ // canard qui tombe
    int tmp=d.compteur%2;
    if(d.compteur==1){

        d.lectureduck.w = 72;
        d.lectureduck.h = 75;
        d.lectureduck.x=447;
        d.lectureduck.y= 240+82*d.couleur;
        applySurface(d.direction_x, d.direction_y, Duck, screen, &d.lectureduck);

    }
    else{
        if(d.compteur==2){
            d.lectureduck.h=71;
            d.lectureduck.w = 48;
        }

        switch(tmp)//enchainement sprite
        {
        case 1:
        {

            switch(d.couleur)//les sprites sont pas très alignés..
                //on peut corriger l'image à la place de faire ca.
            {
            case 0: d.lectureduck.x=537;
                break;
            case 1: d.lectureduck.x=531;
                break;
            case 2: d.lectureduck.x=529;
                break;
            }
            break;
        }
        case 0:
        {
            switch(d.couleur)//les sprites sont pas très alignés..
                //on peut corriger l'image à la place de faire ca.
            {
            case 0: d.lectureduck.x=574;// largeur 48
                break;
            case 1: d.lectureduck.x=568;
                break;
            case 2: d.lectureduck.x=566;
                break;
            }
            break;
        }
        }

        if(d.direction_y+HAUTEUR_HITBOX<SCREEN_HEIGHT-290)
        {
            applySurface(d.direction_x+((1-tmp)*(-16)), d.direction_y, Duck, screen, &d.lectureduck);
        }
        else
            d.fallen=true;
    }

    d.compteur++;
}




void initDeathDuck(duck &d,SDL_Surface *Duck, SDL_Surface *screen) // pour les canards morts
{
    d.dead=true;d.compteur=1;
}

bool isHit(duck &d,int x,int y){ // le canard est touché
    if(!d.dead)
        return ((d.direction_x <= x && x <= (d.direction_x + LARGEUR_HITBOX)) && (d.direction_y <= y && y <= (d.direction_y + HAUTEUR_HITBOX)));
    return false;

}


