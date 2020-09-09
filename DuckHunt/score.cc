#include "score.h"
#include "duck.h"
#include"curseur.h"
#include "look.h"

#include <iostream>
#include <fstream>
#include <cstdlib>

/****************** Nom de la fonction **********************
* initLittleDucks                                           *
******************** Auteur , Dates *************************
* Thong Camille / 4 décembre 2015                           *
********************* Description ***************************
* initialise les petits canards blancs                      *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* petits canards blancs                                     *
************************************************************/

/****************** Nom de la fonction **********************
* addDuckHuntedFail                                         *
******************** Auteur , Dates *************************
* Thong Camille / 4 décembre 2015                           *
********************* Description ***************************
* change les petits canards blancs en rouge si on rate le   *
* canard                                                    *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* petits canards blancs et rouges                           *
************************************************************/

/****************** Nom de la fonction **********************
* addDuckHuntedWin                                          *
******************** Auteur , Dates *************************
* Thong Camille / 4 décembre 2015                           *
********************* Description ***************************
* change les petits canards blancs en vert si on touche le  *
* canard                                                    *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* petits canards blancs et rouges                           *
************************************************************/

/****************** Nom de la fonction **********************
* isLittleDucksFull                                         *
******************** Auteur , Dates *************************
* Thong Camille / 4 décembre 2015                           *
********************* Description ***************************
* si la barre des petits canards blancs est remplie de      *
* canards rouge et vert ou l'un ou l'autre                  *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* ligne complète                                            *
************************************************************/

/****************** Nom de la fonction **********************
* isNiveauWin                                               *
******************** Auteur , Dates *************************
* Thong Camille / 4 décembre 2015                           *
********************* Description ***************************
* si le niveau est gagné                                    *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* si le nombre de canards touchés est supérieur ou égal au  *
* nombre de canard qu'il faut touché pour passer au niveau  *
* suivant                                                   *
************************************************************/

/****************** Nom de la fonction **********************
* showLittleDucks                                           *
******************** Auteur , Dates *************************
* Thong Camille / 5 décembre 2015                           *
********************* Description ***************************
* affiche les petits canards blancs, rouges et verts        *
*********************** Entrées *****************************
* ecran                                                     *
*********************** Sorties *****************************
* petits canards blancs, rouges et verts                    *
************************************************************/

/****************** Nom de la fonction **********************
* initScore                                                 *
******************** Auteur , Dates *************************
* Thong Camille / 5 décembre 2015                           *
********************* Description ***************************
* initialise le score                                       *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* le score                                                  *
************************************************************/

/****************** Nom de la fonction **********************
* addTotalScore                                             *
******************** Auteur , Dates *************************
* Thong Camille / 5 décembre 2015                           *
********************* Description ***************************
* ajout du score pour le score final                        *
*********************** Entrées *****************************
* score                                                     *
*********************** Sorties *****************************
* le score                                                  *
************************************************************/

/****************** Nom de la fonction **********************
* showTotalScore                                            *
******************** Auteur , Dates *************************
* Thong Camille / 5 décembre 2015                           *
********************* Description ***************************
* affiche le score total du joueur                          *
*********************** Entrées *****************************
* ecran                                                     *
*********************** Sorties *****************************
* le score                                                  *
************************************************************/

/****************** Nom de la fonction **********************
* initLittleDuckWhite1                                      *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position des petits canards blancs sur l'ecran            *
*********************** Entrées *****************************
* structure des petits canards                              *
*********************** Sorties *****************************
* les petits canards blancs                                 *
************************************************************/

/****************** Nom de la fonction **********************
* initLittleDuckGreen1                                      *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position des petits canards verts sur l'ecran             *
*********************** Entrées *****************************
* structure des petits canards                              *
*********************** Sorties *****************************
* les petits canards verts                                  *
************************************************************/

/****************** Nom de la fonction **********************
* initLittleDuckRed1                                        *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position des petits canards rouges sur l'ecran            *
*********************** Entrées *****************************
* structure des petits canards                              *
*********************** Sorties *****************************
* les petits canards rouges                                 *
************************************************************/

/****************** Nom de la fonction **********************
* initShot3                                                 *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position des trois cartouches (il reste trois vies)       *
*********************** Entrées *****************************
* structure des cartouches de vie                           *
*********************** Sorties *****************************
* les cartouches de vie                                     *
************************************************************/

/****************** Nom de la fonction **********************
* initShot2                                                 *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position des deux cartouche (il reste 2 vies)             *
*********************** Entrées *****************************
* structure des cartouches de vie                           *
*********************** Sorties *****************************
* les cartouches de vie                                     *
************************************************************/

/****************** Nom de la fonction **********************
* initShot1                                                 *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position de la cartouche (il reste une vie)               *
*********************** Entrées *****************************
* structure des cartouches de vie                           *
*********************** Sorties *****************************
* les cartouches de vie                                     *
************************************************************/

/****************** Nom de la fonction **********************
* initShot0                                                 *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position de l'image lorsqu'il y a plus de vie             *
*********************** Entrées *****************************
* structure des cartouches de vie                           *
*********************** Sorties *****************************
* aucunes cartouches                                        *
************************************************************/

/****************** Nom de la fonction **********************
* initPoints500                                             *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position des points 500                                   *
*********************** Entrées *****************************
* structure des points                                      *
*********************** Sorties *****************************
* points 500                                                *
************************************************************/

/****************** Nom de la fonction **********************
* initPoints1000                                            *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position des points 1000                                  *
*********************** Entrées *****************************
* structure des points                                      *
*********************** Sorties *****************************
* points 1000                                               *
************************************************************/

/****************** Nom de la fonction **********************
* initPoints1500                                            *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* position des points 1500                                  *
*********************** Entrées *****************************
* structure des points                                      *
*********************** Sorties *****************************
* points 1500                                               *
************************************************************/

/****************** Nom de la fonction **********************
* afficherHitScore                                          *
******************** Auteur , Dates *************************
* Thong Camille / 6 décembre 2015                           *
********************* Description ***************************
* affiche les points que vaut chaque canard après les avoir *
* tués et juste avant qu'ils tombent (500 pour un canard    *
* noir, 1000 pour un canard rouge et 1500 pour un canard    *
* bleu                                                      *
*********************** Entrées *****************************
* points, structure des canards, ecran, image des points    *
*********************** Sorties *****************************
* les points                                                *
************************************************************/

/****************** Nom de la fonction **********************
* initHScores                                               *
******************** Auteur , Dates *************************
* Thong Camille / 8 décembre 2015                           *
********************* Description ***************************
* ouvre un fichier pour mettre les meilleurs scores         *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* score                                                     *
************************************************************/

/****************** Nom de la fonction **********************
* addHScore                                                 *
******************** Auteur , Dates *************************
* Thong Camille / 8 décembre 2015                           *
********************* Description ***************************
* ajoute les meilleurs scores                               *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* pos                                                       *
************************************************************/

/****************** Nom de la fonction **********************
* showHScore                                                *
******************** Auteur , Dates *************************
* Thong Camille / 8 décembre 2015                           *
********************* Description ***************************
* affiche les meilleurs scores                              *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* meilleurs scores                                          *
************************************************************/

/****************** Nom de la fonction **********************
* saveHScore                                                *
******************** Auteur , Dates *************************
* Thong Camille / 8 décembre 2015                           *
********************* Description ***************************
* sauvegarde les meilleurs scores dans un fichier texte     *
*********************** Entrées *****************************
*                                                           *
*********************** Sorties *****************************
* meilleurs scores                                          *
************************************************************/

using namespace std;

const int LDUCK_LEVEL_WIN =6;
const int NB_LITTLE_DUCKS =10;
bool duckHunted[NB_LITTLE_DUCKS];
int lDuck_i=-1;
int lducksWin=0;

const char * FILE_HSCORES = "scores.txt";
const int TAILLE_SCORE = 10;
int hScore [TAILLE_SCORE];
int tailleReelle=0;
int totalScore=0;

void initLittleDucks(){ // initialise les petits canards blancs
    lDuck_i=-1;
    lducksWin=0;
    for(int i=0;i<10;i++)
    {
        duckHunted[NB_LITTLE_DUCKS]=false;
    }
}

void addDuckHuntedFail(){ // change les petits canards blancs en rouge si on rate le canard
lDuck_i++;
duckHunted[lDuck_i]=false;
}

void addDuckHuntedWin(){ // change les petits canards blancs en vert si on touche un canard
lDuck_i++;
duckHunted[lDuck_i]=true;
lducksWin++;
}

bool isLittleDucksFull(){ // si la barre des petits canards blancs est en rouge et vert ou l'un ou l'autre
    return lDuck_i==NB_LITTLE_DUCKS-1;
}

bool isNiveauWin(){ // niveau gagné
    return lducksWin>=LDUCK_LEVEL_WIN;
}

void showLittleDucks( SDL_Surface *screen){ // affiche les petits canards blancs, rouges et verts
    SDL_Surface *Kill = loadImageWithColorKey("hit.png", 228, 255,0);
    LittleDuck l1;
    LittleDuck l2;
    LittleDuck l3;
    initLittleDuckWhite1(l1);
    initLittleDuckGreen1(l2);
    initLittleDuckRed1(l3);
    int i=0;
    // REUSSI/RATE
    while(i<=lDuck_i && i<10){
        if (duckHunted[i])
            applySurface(198+30*i,668, Kill, screen, &l2.littleDuck);
        else
            applySurface(198+30*i,668, Kill, screen, &l3.littleDuck);
        i++;
    }
    // RESTANTS
    while(i<10){
        applySurface(198+30*i,668, Kill, screen, &l1.littleDuck);
        i++;
    }

}


/*****************  TTF   ****************/
TTF_Font *fonts, *fontsMessage= NULL;
//TTF_Init();
SDL_Color white,red, gold;
int fontSize=25;
SDL_Surface *textScore = NULL;
SDL_Rect positiontext;

//convertir INT en STR
ostringstream mssg;//penser à inclure -->  #include <sstream>
//textScore=TTF_RenderText_Solid(fonts,intToString(score), textColor);
//SDL_BlitSurface( textScore, NULL , screen, &positiontext );
Points p500;
Points p1000;
Points p1500;
//SDL_Surface *Score = loadImageWithColorKey("points.png",0,0,0); DOIT ETRE DANS LE MAIN

void initScore(){
    SDL_Surface *Score = loadImageWithColorKey("points.png",0,0,0);
    white.r = 255; red.r=255; gold.r=255;
    white.g = 255; red.g=0; gold.g=215;
    white.b = 255; red.b=0; gold.b=0;
    fonts = TTF_OpenFont("duck_hunt.ttf",fontSize);
    fontsMessage= TTF_OpenFont("duck_hunt.ttf",60);
    positiontext.x = 565;
    positiontext.y = 675;
    int totalScore=0;
    mssg.flush();// -> ca fait quoi ??
    mssg.str("");//->reinitialise : texte "vide"
    mssg <<  totalScore; //(!) concatenation
    initPoints500(p500);
    initPoints1000(p1000);
    initPoints1500(p1500);

}

void addTotalScore(int score){
    totalScore+=score;
}

void showTotalScore(SDL_Surface *screen){
mssg.flush();
mssg.str("");
mssg <<  totalScore;
textScore=TTF_RenderText_Solid(fonts, mssg.str().c_str(), white);
SDL_BlitSurface( textScore, NULL , screen, &positiontext );
}

// POSITION DES PETITS CANARDS DU SCORE
void initLittleDuckWhite1(LittleDuck &l)
{
    l.littleDuck.x= 0;
    l.littleDuck.y= 0;
    l.littleDuck.h= 33;
    l.littleDuck.w= 27;
}

void initLittleDuckGreen1(LittleDuck &l)
{
    l.littleDuck.x=54;
    l.littleDuck.y=0;
    l.littleDuck.h= 33;
    l.littleDuck.w= 27;
}

void initLittleDuckRed1(LittleDuck &l)
{
    l.littleDuck.x=30;
    l.littleDuck.y=0;
    l.littleDuck.h= 33;
    l.littleDuck.w= 27;
}

// POSITION DES CARTOUCHES DE VIE (soit 3, soit 2, soit 1, soit 0)
void initShot3(Shot &s)
{
    s.shot.x=232;
    s.shot.y=2;
    s.shot.h=48;
    s.shot.w=73;

}

void initShot2(Shot &s)
{
    s.shot.x=156;
    s.shot.y=2;
    s.shot.h=48;
    s.shot.w=73;

}
void initShot1(Shot &s)
{
    s.shot.x=78;
    s.shot.y=2;
    s.shot.h=48;
    s.shot.w=73;

}
void initShot0(Shot &s)
{
    s.shot.x=2;
    s.shot.y=2;
    s.shot.h=48;
    s.shot.w=73;

}

// POSITION DES POINTS A COTE DU CANARD TOUCHE
void initPoints1500(Points &p)
{
    p.points.x = 5;
    p.points.y = 9;
    p.points.h = 15;
    p.points.w = 36;
}

void initPoints1000(Points &p)
{
    p.points.x = 5;
    p.points.y = 25;
    p.points.h = 15;
    p.points.w = 36;
}

void initPoints500(Points &p)
{
    p.points.x = 10;
    p.points.y = 41;
    p.points.h = 15;
    p.points.w = 36;
}


void afficherHitScore(int points, duck &d,SDL_Surface * screen, SDL_Surface *score){
    switch(points)
    {
    case 500:
    {
        applySurface(d.direction_x+ d.lectureduck.w,
                     d.direction_y+ d.lectureduck.h/2, score, screen,
                     &p500.points);
        break;
    }
    case 1000:
    {
        applySurface(d.direction_x+ d.lectureduck.w,
                     d.direction_y+ d.lectureduck.h/2, score, screen,
                     &p1000.points);
        break;
    }
    case 1500:
    {
        applySurface(d.direction_x+ d.lectureduck.w,
                     d.direction_y+ d.lectureduck.h/2, score, screen,
                     &p1500.points);
        break;
    }
    }
          //SDL_Delay(10000);//pour visionner verifier le HIT
}




/********************H SCORES*********************************/
void initHScores(){
    fstream f;

    f.open(FILE_HSCORES, ios::in );

    if (!f.fail()) //si il y a des scores à récupérer
    {
        for(int i=0; i<TAILLE_SCORE && f.eof() != true; i++){
            f >> hScore[i];
            tailleReelle++;//remplacer i par taille Reelle ?

            // TEST
            //cout << "score : " << tab[i] << endl;
        }
        tailleReelle--;
    }


    f.close();
}

int addHScore(){ // ajout meilleurs scores
    int pos=0;
    if(tailleReelle ==0 )
        pos=1;
    for(int i=0; i<tailleReelle; i++){
        if (hScore[i]<totalScore){
            pos=i+1;
            int tmp;
            for (int j=i;j<tailleReelle;j++){
                tmp=hScore[j];
                hScore[j]=totalScore;
                totalScore=tmp;
            }
            break;
        }
    }
    if(tailleReelle <TAILLE_SCORE )
    {hScore[tailleReelle]=totalScore;tailleReelle++;
    if(pos==0) pos=tailleReelle;
    }

    totalScore=0;
    return pos;
}

void showHScores(int posNewHScore,SDL_Surface * screen){ // affiche les meilleurs scores
    positiontext.x = 240;
    positiontext.y = 90;
    textScore=TTF_RenderText_Solid(fonts,"meilleurs scores", white);
    SDL_BlitSurface( textScore, NULL , screen, &positiontext );
    positiontext.x = 280;
    for (int i=0; i<tailleReelle;i++){
        mssg.flush();
        mssg.str("");
        mssg << i+1 << ". " <<  hScore[i];
        positiontext.y += 35;

        if((i+1)==posNewHScore)
            textScore=TTF_RenderText_Solid(fonts, mssg.str().c_str(), gold);
        else
            textScore=TTF_RenderText_Solid(fonts, mssg.str().c_str(), white);
        SDL_BlitSurface( textScore, NULL , screen, &positiontext );
    }
}


void saveHScores(){ // sauvegarder les meilleurs scores dans un fichier

    fstream f;

    f.open(FILE_HSCORES, ios::out );
    if (f.fail())
    {
        cerr << "ouverture du fichier en ecriture impossible" << endl;
        exit (EXIT_FAILURE);
    }
    for(int  i=0;i<tailleReelle;i++)
    {
        f << hScore[i] << endl;
    }

    f.close();
}
