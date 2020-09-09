#ifndef SCORE_H
#define SCORE_H

#include <iostream>
#include <cstdlib>
#include <string>
#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include <sstream>
#include <fstream>
#include "duck.h"
#include <SDL/SDL_ttf.h>
using namespace std;

struct LittleDuck
{
    int x;
    int y;
    int h;
    int w;
    SDL_Rect littleDuck;
};

struct Shot
{
    int x;
    int y;
    int h;
    int w;
    SDL_Rect shot;
};

struct Points
{
    int x;
    int y;
    int h;
    int w;
    SDL_Rect points;
};

// TAB lducks
void initLittleDucks();
void addDuckHuntedFail();
void addDuckHuntedWin();
bool isLittleDucksFull();
bool isNiveauWin();
void showLittleDucks(SDL_Surface *screen);

//SCORE
void initScore();
void addTotalScore(int score);
void showTotalScore(SDL_Surface *screen);
void afficherHitScore(int score, duck &d,SDL_Surface * screen, SDL_Surface *Score);//score = 500 1000 ou 1500


void initLittleDuckWhite1(LittleDuck &l);
void initLittleDuckGreen1(LittleDuck &l);
void initLittleDuckRed1(LittleDuck &l);
void initShot3(Shot &s);
void initShot2(Shot &s);
void initShot1(Shot &s);
void initShot0(Shot &s);
void initPoints1500(Points &p);
void initPoints1000(Points &p);
void initPoints500(Points &p);
void lireScore(string fichier);

/***NOT YET IMPLEMENTED***********
void reloadBalls(); //reinit ball=3
void showBalls(SDL_Surface *screen);
bool isEmptyBalls();
void shot();  // enlève 1 balle;
********************************/

//HIGH SCORES
void initHScores();
int addHScore();//return position classement 0 si nul
void showHScores(int posNewHScore,SDL_Surface * screen);
void saveHScores();

#endif // SCORE_H
