#include "dog.h"
#include "look.h"
using namespace std;

/****************** Nom de la fonction **********************
* initDog                                                   *
******************** Auteur , Dates *************************
* Macaire Cécile / 29 novembre 2015                         *
********************* Description ***************************
* coordonnées du sprite du chien et position dans le jeu    *
*********************** Entrées *****************************
* struture du chien                                         *
*********************** Sorties *****************************
* position du chien                                         *
************************************************************/

/****************** Nom de la fonction **********************
* showDog                                                   *
******************** Auteur , Dates *************************
* Macaire Cécile / 29 novembre 2015                         *
********************* Description ***************************
* affiche le chien et le fait bouger au début du niveau     *
*********************** Entrées *****************************
* struture du chien, ecran, les sprites                     *
*********************** Sorties *****************************
* image du chien qui se déplace                             *
************************************************************/

/****************** Nom de la fonction **********************
* showDogHunt                                               *
******************** Auteur , Dates *************************
* Macaire Cécile / 29 novembre 2015                         *
********************* Description ***************************
* affiche le chien qui rattrape les canards en fonction de  *
* la couleur des canards touchés                            *
*********************** Entrées *****************************
* struture du chien, ecran, les sprites, structure du canard*
*********************** Sorties *****************************
* image du chien qui se déplace                             *
************************************************************/

/****************** Nom de la fonction **********************
* isDogShowOff                                              *
******************** Auteur , Dates *************************
* Macaire Cécile / 30 novembre 2015                         *
********************* Description ***************************
* booléen qui retourne lorsque le chien ne s'affiche plus   *
*********************** Entrées *****************************
* struture du chien                                         *
*********************** Sorties *****************************
* retourne le chien qui ne s'affiche plus                   *
************************************************************/

/****************** Nom de la fonction **********************
* initDog2                                                  *
******************** Auteur , Dates *************************
* Macaire Cécile / 29 novembre 2015                         *
********************* Description ***************************
* coordonnées du sprite du chien et position dans le jeu    *
*********************** Entrées *****************************
* struture du chien                                         *
*********************** Sorties *****************************
* position du chien                                         *
************************************************************/

// On définie les constantes qui vont etre appelées plusieurs fois dans le code

const int LARGEUR_DOG1 = 114;
const int HAUTEUR_DOG1 = 86;
const int LARGEUR_DOG4 = 71;


void initDog(Dog &d) // initialisation du chien
{
    d.show=true;
    d.lecturedog.x = 0;
    d.lecturedog.y = 8;
    d.lecturedog.h = HAUTEUR_DOG1;
    d.lecturedog.w = LARGEUR_DOG1;
    d.x = 50; // position en x du chien dans le jeu
    d.y = 500; // position en y du chien dans le jeu
    d.compteur=1;
}

void showDog(Dog &d, SDL_Surface *screen, SDL_Surface *dog) // pour afficher le chien à l'écran
{
    d.show=true;
    if (d.compteur == 1)
    {
        d.lecturedog.x = 0;
        d.lecturedog.y = 8;
        d.lecturedog.h = HAUTEUR_DOG1;
        d.lecturedog.w = LARGEUR_DOG1;
    }
    else if(d.compteur < 26)
    {
        d.x+=10; // le chien avance en x par 10
        d.lecturedog.x=(d.lecturedog.x+114)%456; // on parcours les sprites du chien jusqu'a 456
    }

    else
    {
        switch (d.compteur)
        {
        // le chien lève la tête
        case 26:
            d.lecturedog.x = 6;
            d.lecturedog.y = 119;
            d.lecturedog.h = LARGEUR_DOG1;
            d.lecturedog.w = 113;
            d.y = 500;
            SDL_Delay(50);
            break;
        // le chien commence à sauter
        case 27:
            d.lecturedog.x = 211;
            d.lecturedog.y = 133;
            d.lecturedog.w = LARGEUR_DOG4;
            d.lecturedog.h = LARGEUR_DOG4;
            d.y = 500;
            d.x = 280;
            SDL_Delay(500);
            break;
        // le chien saute
        case 28:
            d.lecturedog.x = 128;
            d.lecturedog.y = 110;
            d.lecturedog.h = 96;
            d.lecturedog.w = 76;
            d.x = 300;
            d.y = 400;
            SDL_Delay(100);
            break;
        // le chien saute encore
        case 29:
            d.lecturedog.y = 136;
            d.lecturedog.h = 67;
            d.lecturedog.w = 70;
            d.lecturedog.x = 213;
            d.x = 340;
            d.y = 450;
            break;
        default:

            break;
        }
    }

    if(d.compteur < 30)
        d.compteur++;
    else
    {d.show=false;}
    if (d.show)
    {
        applySurface(d.x, d.y, dog, screen, &d.lecturedog);
        SDL_Delay(40);
        SDL_FreeSurface(screen);
    }
}

void showDogHunt(Dog &d, SDL_Surface *screen, SDL_Surface *dog, duck &d1, duck &d2){

    if (d.compteur==1){//Initialise image
        d.lecturedog.h = HAUTEUR_DOG1;
        d.lecturedog.y = 125;
        d.x = 340;
        d.y = 488;

        if (d1.dead && d2.dead){ // si canard 1 et canard 2 sont touchés
            d.lecturedog.w = LARGEUR_DOG1;
            if((d1.couleur==0 && d2.couleur==1)||(d1.couleur==1 && d2.couleur==0))
                d.lecturedog.x = 1159;
            else if((d1.couleur==1 && d2.couleur==2)||(d1.couleur==2 && d2.couleur==1))
                d.lecturedog.x = 929;
            else if((d1.couleur==2 && d2.couleur==0)||(d1.couleur==0 && d2.couleur==2))
                d.lecturedog.x = 809;
            else { //if(d1.couleur==d2.couleur){
                switch(d1.couleur){
                case 0:
                    d.lecturedog.x = 579;    break;//dog noir
                case 1:
                    d.lecturedog.x = 695;    break;
                case 2:
                    d.lecturedog.x = 1044;    break;
                default:
                    break;
                }
            }
        }
        else if ((d1.dead && !d2.dead) || (!d1.dead && d2.dead)){
            d.lecturedog.w = 90;

            if(d2.dead)
                d1.couleur=d2.couleur;

            switch(d1.couleur){
            case 0:
                d.lecturedog.x = 485;    break;//dog noir
            case 1:
                d.lecturedog.x = 305;    break;
            case 2:
                d.lecturedog.x = 394;    break;
            default:
                break;
            }
        }
    }
    if(!d1.dead && !d2.dead){
        d.x=359;

        d.lecturedog.y = 10;
        d.lecturedog.w = 65;
        int tmp=d.compteur%3;//rire moins rapide que deplacement
        switch (tmp){
        case 0:
            d.lecturedog.x = 575;    break;
        case 1:
            d.lecturedog.x = 640;    break;
        default:
            break;
        }
    }
    d.compteur++;
    if(d.compteur<30)
        d.y-=2;
    else if(d.compteur>30)
        d.y+=2;
    else
        SDL_Delay(300);
    ;
    if(d.compteur>70)
        d.show=false;
    applySurface(d.x, d.y, dog, screen, &d.lecturedog);
    //SDL_FreeSurface(screen);
}



bool isDogShowOff(Dog &d){
    return !d.show;
}

void initDog2(Dog &d) // initialisation du chien
{
    d.lecturedog.x = 0;
    d.lecturedog.y = 8;
    d.lecturedog.h = HAUTEUR_DOG1;
    d.lecturedog.w = LARGEUR_DOG1;
    d.x = 50; // position en x du chien dans le jeu
    d.y = 500; // position en y du chien dans le jeu
    d.compteur=1;
}
