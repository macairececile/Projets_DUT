#include <stdlib.h>

#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include "dog.h"
#include "curseur.h"
#include "look.h"
#include "menu.h"
#include "duck.h"
#include "score.h"

#include <SDL/SDL_ttf.h>
#include <sstream>//convertir score intToString
using namespace std;

const int SCREEN_WIDTH = 750;
const int SCREEN_HEIGHT = 761;
const int NB_DUCK_TO_WIN = 6;
const int NB_DUCK_BY_LEVEL = 10;
const int SCREEN_BPP = 32;

void pause() // appuie sur esc pour mettre le jeu en pause
{
    int pause = 1;
    SDL_Event event;

    while(pause)
    {
        SDL_WaitEvent(&event);
        switch(event.type)
        {
        case SDL_QUIT:
            pause = 0;
            break;

        case SDL_KEYDOWN:
            switch(event.key.keysym.sym)
            {
            case SDLK_ESCAPE:
                pause = 0;
                break;

            }
            break;


        }
    }
}


int main ()
{

    SDL_Event event;

    //CANARD ALEATOIRE
    srand(time(NULL));

    TTF_Init();
    SDL_Init(SDL_INIT_EVERYTHING);

    TTF_Font    *fontsMessage= NULL;
    TTF_Font *fonttext= NULL;
    SDL_Surface *message = NULL;
    SDL_Rect positionMessage;
    positionMessage.x = 265;
    positionMessage.y = 185;
    fontsMessage= TTF_OpenFont("duck_hunt.ttf",60);
    fonttext= TTF_OpenFont("duck_hunt.ttf",40);
    SDL_Color white;
    white.r = 255;
    white.g = 255;
    white.b = 255;

    bool quit = false;
    bool play = false;
    bool fin = false;

    // Menu
    Button play1;
    Button play2;
    Button quit1;
    Button quit2;
    Button hard1;
    Button hard2;
    initPlay1(play1);
    initPlay2(play2);
    initQuit1(quit1);
    initQuit2(quit2);
    initHard1(hard1);
    initHard2(hard2);

    SDL_Surface *screen = NULL;
    screen=SDL_SetVideoMode(SCREEN_WIDTH,SCREEN_HEIGHT,SCREEN_BPP,SDL_SWSURFACE);

    SDL_WM_SetCaption("Duck Hunt !", NULL); // Titre de la fenetre

    // Chargement des images
    SDL_Surface *PlayQuit= IMG_Load("button2.bmp");

    applySurface(quit1.x,quit1.y, PlayQuit, screen,&quit1.but);
    SDL_Surface *Duck = loadImageWithColorKey("duck.png", 228,255,0);
    SDL_Surface *menu = IMG_Load("menu.png");

    /***********background : *********/
    SDL_Rect cadre_dogHunt;
    cadre_dogHunt.x = 0;
    cadre_dogHunt.y = 0;
    cadre_dogHunt.h = 600;
    cadre_dogHunt.w = 748;
    SDL_Surface *background =loadImageWithColorKey("backGameBlit.png", 0, 0,0);
    /***********************************/
    SDL_Surface *NbBall = IMG_Load("shot.png"); // images selon le nombre de balle
    SDL_Surface *background2 = IMG_Load("backGame.png"); // fond 2 du jeu
    SDL_Surface *dog = loadImageWithColorKey("duck.png", 228, 255, 0);
    SDL_Surface *Score = loadImageWithColorKey("points.png",0,0,0); // images points du score

    bool mvtRandom=false;

    while(!quit && !play && !fin)
    {
        SDL_FillRect(screen,&screen->clip_rect, SDL_MapRGB(screen->format,255,255,255));

        //On applique les sprites sur l'écran
        applySurface(0,0, menu, screen, NULL);
        applySurface(play1.x,play1.y, PlayQuit, screen,&play1.but);
        applySurface(quit1.x,quit1.y, PlayQuit, screen,&quit1.but);
        applySurface(hard1.x,hard1.y, PlayQuit, screen,&hard1.but);

        while(SDL_PollEvent(&event))
            if(event.type==SDL_QUIT)
                quit=true;

        int x = event.button.x;
        int y = event.button.y;

        if ((x > play1.x) && (x < (play1.x + play1.but.w)) && (y > play1.y) && (y < (play1.y + play1.but.h)))
        {
            applySurface(play2.x, play2.y, PlayQuit, screen, &play2.but);
            SDL_Flip(screen);
            if (SDL_WaitEvent(&event))
            {
                if (event.button.button == SDL_BUTTON_LEFT)
                {
                    play = true;
                }
            }
        }

        else if ((x > quit1.x) && (x < (quit1.x + quit1.but.w)) && (y > quit1.y) && (y < (quit1.y + quit1.but.h)))
        {
            applySurface(quit2.x, quit2.y, PlayQuit, screen, &quit2.but);
            SDL_Flip(screen);
            if (SDL_WaitEvent(&event))
            {
                if (event.button.button == SDL_BUTTON_LEFT)
                {

                    quit = true;
                }
            }
        }

        else if ((x > hard1.x) && (x < (hard1.x + hard1.but.w)) && (y > hard1.y) && (y < (hard1.y + hard1.but.h)))
        {
            applySurface(hard2.x, hard2.y, PlayQuit, screen, &hard2.but);
            SDL_Flip(screen);
            if (SDL_WaitEvent(&event))
            {
                if (event.button.button == SDL_BUTTON_LEFT)
                {
                    mvtRandom=true;
                    play = true;
                }
            }
        }


        SDL_Flip(screen);

        SDL_Delay(10);

    }

    // Canard
    duck d1;
    duck d2;
    int niveau=0;
    initDuck(d1,niveau-1,mvtRandom);
    initDuck(d2,niveau-1,mvtRandom);
    Dog d;
    initDog(d);
    bool doglvlup=false;
    int ball=3;
    int  duckalive=2; //(0 ou 1 ou 2)
    initScore();
    bool clicActive=false;
    SDL_ShowCursor(0);// On enlève la souris
    bool instantDeath_d2=false ;bool instantDeath_d1=false;
    applySurface(0, 0, background2, screen, NULL);

    while(!quit && play && !fin)
    {
        applySurface(0, 0, background2, screen, NULL);




        /***********SHOW LITTLE DUCKS,balls,SCORE************/
        if(clicActive || (!isLittleDucksFull() && niveau !=0))
        {
            showLittleDucks(screen);
            showTotalScore(screen);
            //Afficher TIRS sur 3
            Shot s;
            if (ball==3)
                initShot3(s);
            if (ball==2)
                initShot2(s);
            if (ball==1)
                initShot1(s);
            if (ball==0)
                initShot0(s);
            applySurface(65,658, NbBall, screen, &s.shot);
        }
        /****************************************************/



        /*************** DUCKS*******************************/

        if(!d1.dead ){
            if(clicActive){
                moveDuck(d1); showDuck(d1, Duck, screen);}
            else if(!d1.escaped && niveau!=0){
                showDuckEscape(d1, Duck, screen);
            }

        }
        else if (!d1.fallen ){
            showDuckDead(d1, Duck, screen);
            if (instantDeath_d1){
                int score=500*(d1.couleur+1);
                addTotalScore(score);
                afficherHitScore(score, d1, screen, Score);
                SDL_Flip(screen); SDL_Delay(200);instantDeath_d1=false;
            }
            moveDuckDead(d1);
        }

        if(!d2.dead){
            if(clicActive){
                moveDuck(d2); showDuck(d2, Duck, screen);}
            else if(!d2.escaped && niveau!=0){
                showDuckEscape(d2, Duck, screen);
            }
        }
        else if(!d2.fallen){
            showDuckDead(d2, Duck, screen);
            if (instantDeath_d2){
                int score=500*(d2.couleur+1);
                addTotalScore(score);
                afficherHitScore(score, d2, screen, Score);
                SDL_Flip(screen); SDL_Delay(200);instantDeath_d2=false;
            }
            moveDuckDead(d2);
        }

        /****************************************************/


        /********* TRANSITION OU FIN ************************/
        if(!clicActive)
        {
            if (ball==0 && !d1.dead && !d2.dead && !d1.escaped && !d2.escaped)
            {
                message=TTF_RenderText_Solid(fonttext,"Cannards Disparus", white);

                SDL_Rect escape;
                escape.x=170; escape.y=20;
                SDL_BlitSurface( message, NULL , screen, &escape );
            }
            //ATTEND CANARDS SOIENT TOMBES ou ENFUIS(enfui pas géré)
            if((d1.fallen || d1.escaped || niveau==0) && (d2.fallen || d2.escaped || niveau==0) )
            {
                if(doglvlup || niveau==0)
                    showDog(d, screen ,dog);
                else {fontsMessage= TTF_OpenFont("duck_hunt.ttf",60);
                    showDogHunt(d, screen ,dog, d1,d2); applySurface(0, 0, background, screen, &cadre_dogHunt);
                }
                if(isDogShowOff(d)){
                    if (isLittleDucksFull() || niveau==0){
                        if(niveau!=0){
                            if (!isNiveauWin()){
                                applySurface(0, 0, background2, screen, NULL);
                                message=TTF_RenderText_Solid(fontsMessage,"GAME OVER", white);

                                SDL_Rect gameOver;
                                gameOver.x=170; gameOver.y=20;
                                SDL_BlitSurface( message, NULL , screen, &gameOver );
                                initHScores();
                                int pos =addHScore();
                                showHScores(pos,screen);
                                saveHScores();
                                SDL_Flip(screen);
                                SDL_Delay(500);
                                fin =true;
                            }
                            doglvlup=true;initDog(d);SDL_Delay(500); niveau++;
                        }
                        else
                        {
                            message=TTF_RenderText_Solid(fontsMessage,"Niveau 1", white);
                            SDL_BlitSurface( message, NULL , screen, &positionMessage );
                            SDL_Flip(screen);
                            SDL_Delay(1000);
                            niveau++;
                            ball=3;
                            duckalive=2;
                            initDuck(d2,niveau-1,mvtRandom);initDuck(d1,niveau-1,mvtRandom);
                            clicActive=true;
                        }
                        initLittleDucks();
                    }
                    else {
                        ball=3;
                        duckalive=2;
                        initDuck(d2,niveau-1,mvtRandom);initDuck(d1,niveau-1,mvtRandom);
                        if(doglvlup){
                            ostringstream mssg;
                            mssg.flush();
                            mssg << "Niveau "<<  niveau;
                            message=TTF_RenderText_Solid(fontsMessage,mssg.str().c_str(), white);
                            SDL_BlitSurface( message, NULL , screen, &positionMessage );
                            SDL_Flip(screen);
                            SDL_Delay(700);
                            doglvlup=false;
                        }
                        clicActive=true;
                    }

                }
            }
            /*****************************************************/
        }
        else
        {
            //*******afficher le VISEUR*****************
            int xmouse = event.motion.x;
            int ymouse = event.motion.y;
            showcurseur(screen,xmouse, ymouse);
            //*****************************************
        }


        while (SDL_PollEvent(&event)){
            if( event.type == SDL_QUIT )
                quit = true;
            if(clicActive && event.type == SDL_MOUSEBUTTONDOWN )
            {

                //SI ISSHOWOFF -> on s'interesse au clic après l'animation chien

                if (event.button.button == SDL_BUTTON_LEFT)
                {
                    //TIR
                    ball--;

                    //souris
                    int x = event.button.x;
                    int y = event.button.y;

                    //TOUCHER DUCK1
                    if (isHit(d1,x,y))
                    {
                        duckalive--;
                        addDuckHuntedWin();
                        instantDeath_d1=true;
                        initDeathDuck(d1, Duck, screen);
                    }

                    //TOUCHER DUCK2
                    else if (isHit(d2,x,y))
                    {
                        duckalive--;
                        addDuckHuntedWin();
                        instantDeath_d2=true;
                        initDeathDuck(d2, Duck, screen);
                    }

                    if(isLittleDucksFull())
                    {
                        clicActive=false;
                        ball =3;
                        if(isNiveauWin()){
                            initDog(d);
                        }
                        break;
                    }

                    //PLUS DE BALLES.. CANARDS SUIVANTS
                    else if(duckalive>0 && ball == 0 )
                    {
                        addDuckHuntedFail(); //sûr qu'il restait un canard sinon on aurait réinitialisé les ball
                        clicActive=false;
                        initDog(d);
                    }

                    else if(ball == 1 && duckalive==2){
                        duckalive--;addDuckHuntedFail();
                        //il pourra pas en tuer 2 avec une balle, on affiche un canard rouge;
                    }

                    else if(duckalive==0){
                        clicActive=false;
                        initDog(d);
                    }
                }
            }
            else if(event.type == SDL_KEYDOWN && event.key.keysym.sym==SDLK_ESCAPE){
                SDL_Rect imgPause;
                imgPause.x=5;imgPause.y=475;imgPause.w=100;imgPause.h=75;

                applySurface((SCREEN_WIDTH-imgPause.w)/2,(SCREEN_HEIGHT-imgPause.h)/2, Duck, screen, &imgPause);
                SDL_Flip(screen);
                pause();
            }
        }


        SDL_Flip(screen);
        SDL_Delay(20);
    }

    SDL_FreeSurface(screen);
    SDL_Quit();
    return EXIT_SUCCESS;
}


