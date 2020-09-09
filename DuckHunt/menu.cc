#include "menu.h"
#include "look.h"

using namespace std;

/****************** Nom de la fonction **********************
* initPlay1                                                 *
******************** Auteur , Dates *************************
* Macaire Cécile / 20 novembre 2015                         *
********************* Description ***************************
* position du bouton play rouge dans le menu                *
*********************** Entrées *****************************
* structure du bouton                                       *
*********************** Sorties *****************************
* bouton                                                    *
************************************************************/

/****************** Nom de la fonction **********************
* initPlay2                                                 *
******************** Auteur , Dates *************************
* Macaire Cécile / 20 novembre 2015                         *
********************* Description ***************************
* position du bouton play jaune dans le menu                *
*********************** Entrées *****************************
* structure du bouton                                       *
*********************** Sorties *****************************
* bouton                                                    *
************************************************************/

/****************** Nom de la fonction **********************
* initQuit1                                                 *
******************** Auteur , Dates *************************
* Macaire Cécile / 20 novembre 2015                         *
********************* Description ***************************
* position du bouton quit rouge dans le menu                *
*********************** Entrées *****************************
* structure du bouton                                       *
*********************** Sorties *****************************
* bouton                                                    *
************************************************************/

/****************** Nom de la fonction **********************
* initQuit2                                                 *
******************** Auteur , Dates *************************
* Macaire Cécile / 20 novembre 2015                         *
********************* Description ***************************
* position du bouton quit jaune dans le menu                *
*********************** Entrées *****************************
* structure du bouton                                       *
*********************** Sorties *****************************
* bouton                                                    *
************************************************************/

/****************** Nom de la fonction **********************
* initHard1                                                 *
******************** Auteur , Dates *************************
* Macaire Cécile / 25 novembre 2015                         *
********************* Description ***************************
* position du bouton hard rouge dans le menu                *
*********************** Entrées *****************************
* structure du bouton                                       *
*********************** Sorties *****************************
* bouton                                                    *
************************************************************/

/****************** Nom de la fonction **********************
* initHard2                                                 *
******************** Auteur , Dates *************************
* Macaire Cécile / 25 novembre 2015                         *
********************* Description ***************************
* position du bouton hard 2 dans le menu                    *
*********************** Entrées *****************************
* structure du bouton                                       *
*********************** Sorties *****************************
* bouton                                                    *
************************************************************/

const int SCREEN_WIDTH = 751;
const int SCREEN_HEIGHT = 761;

void
initPlay1(Button &p)
{
    p.x = 300;
    p.y = 100;
    p.but.x = 5;
    p.but.y = 5;
    p.but.h = 59;
    p.but.w = 149;
}

void
initPlay2(Button &p)
{
    p.x = 300;
    p.y = 100;
    p.but.x = 164;
    p.but.y = 5;
    p.but.h = 59;
    p.but.w = 149;
}

void
initQuit1(Button &p)
{
    p.x = //SCREEN_WIDTH-251;
            300;
    p.y = 300;
    p.but.x = 5;
    p.but.y = 143;
    p.but.h = 59;
    p.but.w = 149;
}

void
initQuit2(Button &p)
{
    p.x = //SCREEN_WIDTH-251;
            300;
    p.y = 300;
    p.but.x = 164;
    p.but.y = 143;
    p.but.h = 59;
    p.but.w = 149;
}

void
initHard1(Button &p)
{
    p.x = 300;//SCREEN_WIDTH-251;
    p.y = 200;
    p.but.x = 5;
    p.but.y = 74;
    p.but.h = 59;
    p.but.w = 149;
}

void
initHard2(Button &p)
{
    p.x = 300;//SCREEN_WIDTH-251;
    p.y = 200;
    p.but.x = 164;
    p.but.y = 74;
    p.but.h = 59;
    p.but.w = 149;
}

