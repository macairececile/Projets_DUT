#include <iostream>
#include <cstdlib>
#include <string>
#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include <SDL/SDL_ttf.h>
#include <sstream>
#include "header.h"

using namespace std;

int main(int argc, char* argv[])
{
    SDL_Event event;
    SDL_Surface *source;

    bool quit = false;
    bool play = false;
    bool fin = false;
    ball b;

    // Initialisation des compteurs du jeu pour le score
    int cpt1=0;
    int cpt2=0;

    ostringstream cpt1P;
    ostringstream cpt2P;

    // Coordonnées de la raquette 1 du joueur 1
    SDL_Rect player01;

    player01.x=10;
    player01.y=(SCREEN_HEIGHT/2)-player01.h/2;
    player01.h=10;
    player01.w=10;

    // Coordonnées de la raquette 2 du joueur 2
    SDL_Rect player02;

    player02.x=10;
    player02.y= (SCREEN_HEIGHT/2)-player02.h/2;
    player02.h=10;
    player02.w=10;

    // Menu
	Button play1;
    Button play2;
    Button quit1;
    Button quit2;

    initPlay1(play1);
    initPlay2(play2);
    initQuit1(quit1);
    initQuit2(quit2);

	TTF_Init();
    //Déclaration d'une variable de type fonte
    TTF_Font *fonts;
    //Déclaration d'une variable permettant de gréer la couleur du texte
    SDL_Color textColor={0,0,0};
    int fontSize=36;
    string police="Hawaii-Killer.ttf";

    // Initialisation de l'écran de jeu
    SDL_Init(SDL_INIT_EVERYTHING);

    SDL_Surface *screen = NULL;

    screen=SDL_SetVideoMode(SCREEN_WIDTH,SCREEN_HEIGHT,SCREEN_BPP,SDL_SWSURFACE);

    SDL_WM_SetCaption("Pong !", NULL); // Titre de la fenetre

    // Chargement des images
    SDL_Surface *PlayQuit= load_image("button.bmp");
    SDL_Surface *menu = load_image("title.bmp");

    initBall(b);

    SDL_Surface *background = load_image("fond.bmp");

    SDL_Surface *ball = load_image("ball.bmp");

    SDL_Surface *p1 = load_image("player01.bmp");
    SDL_Surface *p2 = load_image("player02.bmp");

    //ouverture de la fonte
    fonts = TTF_OpenFont(police.c_str(),fontSize);

    while(!quit && !play && !fin)
    {
        SDL_FillRect(screen,&screen->clip_rect, SDL_MapRGB(screen->format,255,255,255));
        SDL_Flip(screen);

        //On applique les sprites sur l'écran
        applySurface( 0, 0, menu, screen, NULL);
        applySurface(play1.x,play1.y, PlayQuit, screen,&play1.but);
        applySurface(quit1.x,quit1.y, PlayQuit, screen,&quit1.but);

        while(SDL_PollEvent(&event))
            if(event.type==SDL_QUIT)
                quit=true;

        Uint8 *keystates = SDL_GetKeyState( NULL );

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

        SDL_Delay(10);

    }

    while(!quit && play && !fin)
    {
        //On applique les sprites sur l'écran
        applySurface(0, 0, background, screen, NULL);
        applySurface(player01.x, player01.y, p1, screen, NULL);
        applySurface(player02.x, player02.y, p2, screen, NULL);
        applySurface(b.x, b.y, ball, screen, NULL);

        while (SDL_PollEvent(&event))
            if( event.type == SDL_QUIT )
                quit = true;
        }

        moveplayer01(player01);
        moveplayer02(player02);
        moveBall(b, player01, player02);

        // Score
        /********************/
        /********************/
        cpt1P.flush();
        cpt1P.str("");
        cpt1P <<  cpt1;

        cpt2P.flush();
        cpt2P.str("");
        cpt2P <<  cpt2;

        showMessageScreen(cpt2P.str(),300,25,fonts,fontSize,textColor,screen);
        showMessageScreen(300,25,fonts,40,2,screen, textColor);
        showMessageScreen(cpt1P.str(),300,25,fonts,fontSize,textColor,screen);

        SDL_Flip(screen);

        if (cpt1 == 5)
        {
            fin = true;
        }

        else if (cpt2 == 5)
        {
            fin =true;
        }

        SDL_Delay(10);
    }

    while(!quit && play && fin)
    {

        applySurface(0, 0, background, screen, NULL);

        while (SDL_PollEvent(&event))
            if( event.type == SDL_QUIT )
                quit = true;

        if (cpt1 == 5)
        {
            showMessageScreen(" Player 1, c'est gagné :D ",200,200,fonts,40,textColor,screen);
            SDL_Flip(screen);
        }

        else if (cpt2 == 5)
        {
            showMessageScreen(" Player 2, c'est gagné :D " ,200,200,fonts,40,textColor,screen);
            SDL_Flip(screen);
        }

        SDL_Delay(20);

    }

    SDL_FreeSurface(screen);
    SDL_Quit();
    return EXIT_SUCCESS;
}
