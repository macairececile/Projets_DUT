#ifndef HEADER_H_INCLUDED
#define HEADER_H_INCLUDED

#include <iostream>
#include <cstdlib>
#include <string>
#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include <SDL/SDL_ttf.h>
#include <sstream>


using namespace std;

const int SCREEN_WIDTH=640;
const int SCREEN_HEIGHT=480;
const int SCREEN_BPP=32;

const int TAILLE=20;

// -- loadImage ---------------------------------
// chargement d'une image
// ----------------------------------------------

SDL_Surface *load_image( std::string filename )
{
    //Temporary storage for the image that's loaded
    SDL_Surface* loadedImage = NULL;

    //The optimized image that will be used
    SDL_Surface* optimizedImage = NULL;
     //Load the image
    loadedImage = SDL_LoadBMP( filename.c_str() );
    //If nothing went wrong in loading the image
    if( loadedImage != NULL )
    {
        //Create an optimized image
        optimizedImage = SDL_DisplayFormat( loadedImage );

        //Free the old image
        SDL_FreeSurface( loadedImage );
    }
      //Return the optimized image
    return optimizedImage;
}


// -- loadImageWithColorKey ---------------------
// chargement d'une image
// * paramètres entrées :
// - "filename" : nom de l'image
// - "r,g,b"    : couleur de la ColorKey; cette
//   couleur devient transparente !
// * paramètre de sortie : SDL_Surface contenant
//   l'image.
// ----------------------------------------------
SDL_Surface *loadImageWithColorKey(string filename, int r, int g, int b)
{
    //The image that's loaded
    SDL_Surface* loadedImage = NULL;

    //The optimized image that will be used
    SDL_Surface* optimizedImage = NULL;

    //Load the image
    loadedImage = IMG_Load( filename.c_str() );

    //If the image loaded
    if( loadedImage != NULL )
    {
        //Create an optimized image
        optimizedImage = SDL_DisplayFormat( loadedImage );

        //Free the old image
        SDL_FreeSurface( loadedImage );

        //If the image was optimized just fine
        if( optimizedImage != NULL )
        {
            //Map the color key
            Uint32 colorkey = SDL_MapRGB( optimizedImage->format, r, g, b );

             //Set all pixels of color R 0, G 0xFF, B 0xFF to be transparent
            SDL_SetColorKey( optimizedImage, SDL_SRCCOLORKEY, colorkey );
        }


     }
   //Return the optimized image
   return optimizedImage;

}

// -- applySurface ------------------------------
// cf atelier03 pour plus d'explication
// ----------------------------------------------

void
applySurface(int x, int y, SDL_Surface* source,
         SDL_Surface* destination, SDL_Rect* clip)
{
    SDL_Rect offset;
    offset.x = x;
    offset.y = y;
    SDL_BlitSurface( source, clip, destination, &offset );
}

struct ball
{
    int x;     // abscisse du centre de la balle
    int y;     // ordonnée du centre de la balle
    int w;     // largeur de la balle
    int h;     // hauteur de la balle
    int mvt_x; // mouvement sur l'axe des abscisses
    int mvt_y; // mouvement sur l'axe des ordonnées
};

struct Button
 {
     int x;
     int y;
     SDL_Rect but;
 };

void
initPlay1(Button &p)
{
    p.x = 100;
    p.y = 300;
    p.but.x = 0;
    p.but.y = 101;
    p.but.h = 59;
    p.but.w = 149;
}

void
initPlay2(Button &p)
{
    p.x = 100;
    p.y = 300;
    p.but.x = 200;
    p.but.y = 101;
    p.but.h = 59;
    p.but.w = 149;
}

void
initQuit1(Button &p)
{
    p.x = SCREEN_WIDTH-251;
    p.y = 300;
    p.but.x = 0;
    p.but.y = 0;
    p.but.h = 59;
    p.but.w = 149;
}

void
initQuit2(Button &p)
{
    p.x = SCREEN_WIDTH-251;
    p.y = 300;
    p.but.x = 200;
    p.but.y = 0;
    p.but.h = 59;
    p.but.w = 149;
}

void
initBall(ball &b)
{
    b.x = SCREEN_WIDTH/2;
    b.y = SCREEN_HEIGHT/2;
    b.w = TAILLE;
    b.h = TAILLE;
    b.mvt_x=2; // mouvement de la balle en abscisse
    b.mvt_y=2; // mouvement de la balle en ordonnée
}
bool
collision(SDL_Rect a, SDL_Rect b)
{
    int leftA, leftB;
    int rightA, rightB;
    int topA, topB;
    int bottomA, bottomB;

    leftA = a.x;
    rightA = a.x + a.w;
    topA = a.y;
    bottomA = a.y + a.h;

    leftB = b.x;
    rightB = b.x + b.w;
    topB = b.y;
    bottomB = b.y + b.h;

    if(bottomA <= topB)
        return false;
    if(topA >= bottomB)
        return false;
    if(rightA <= leftB)
        return false;
    if(leftA >= rightB)
        return false;

    return true;
}

void
moveBall(ball &b, SDL_Rect player01, SDL_Rect player02)
{
    SDL_Rect tmp;
    b.x+=b.mvt_x;
    b.y+=b.mvt_y;

    tmp.x=b.x-TAILLE/2;
    tmp.y=b.y-TAILLE/2;
    tmp.h=TAILLE;
    tmp.w=TAILLE;

    // Correction Mouvement Horizontal
    if ((b.x+TAILLE>SCREEN_WIDTH) || (b.x-TAILLE/2<0) || (collision(tmp, player01)) || (collision(tmp,player02)))
    {
        b.x=SCREEN_WIDTH-TAILLE;
        b.mvt_x*=-1;
    }

    // Correction Mouvement Vertical
    if ((b.y+TAILLE>SCREEN_HEIGHT) || (b.y-TAILLE/2<0))
    {
        b.y=SCREEN_HEIGHT-TAILLE;
        b.mvt_y*=-1;
    }

    SDL_Delay(10);

}

void
showBall(ball b, SDL_Surface*s)
{
    SDL_Rect r;
    r.x=b.x-TAILLE/2;
    r.y=b.y-TAILLE/2;
    r.w=TAILLE;
    r.h=TAILLE;

    SDL_FillRect(s,&r, SDL_MapRGB(s->format,255,0,0));
}

void
moveplayer01(SDL_Rect & player01)
{
    Uint8 *keystates = SDL_GetKeyState( NULL );

    // Si la touche "a" est préssée
    if (keystates[SDLK_a])
        player01.y+=2;

    // Si la touche "q" est préssée
    if (keystates[SDLK_q])
        player01.y-=2;

    if (player01.y<0)
    {
        player01.y+=2;
    }
    if ((player01.y+player01.h)>SCREEN_HEIGHT)
    {
        player01.y-=2;
    }
}

void
moveplayer02(SDL_Rect & player02)
{
    Uint8 *keystates = SDL_GetKeyState( NULL );

    // Si la touche du haut est préssée
    if (keystates[SDLK_UP])
        player02.y+=2;

    // Si la touche du bas est préssée
    if (keystates[SDLK_DOWN])
        player02.y-=2;

    if (player02.y<0)
    {
        player02.y+=2;
    }

    if ((player02.y+player02.h)>SCREEN_HEIGHT)
    {
        player02.y-=2;
    }
}

void ReInitball (ball & b, int & cpt1, int & cpt2, SDL_Rect & player01 , SDL_Rect & player02)
{
        if (b.x <= player01.x)
        {
            cpt1++;
            initBall(b);
        }

        if (b.x + b.w >= player02.x + player02.w)
        {
            cpt2++;
            initBall(b);
        }
}

// Score
void
showMessageScreen(string message,int x,int y,
          TTF_Font *font,int fontSize,SDL_Color textColor,SDL_Surface* &screen)
{
    string mot="";
    string space=" ";
    int i=0,j;
    SDL_Surface *mes=NULL;

    j = message.find(space);
    while( j != string::npos )
    {
        mot=message.substr(i,j-i);
        if(mot != "")
        {
            mes=TTF_RenderText_Solid(font,mot.c_str(),textColor);
            applySurface(x,y,mes,screen,NULL);
            x+=mes->w;
            SDL_FreeSurface(mes);
        }

        x+=fontSize;
        i=j+1;
        j = message.find(space,i);
    }

    mot=message.substr(i);
    mes=TTF_RenderText_Solid(font,mot.c_str(),textColor);
    applySurface(x,y,mes,screen,NULL);
    SDL_FreeSurface(mes);
}

#endif // HEADER_H_INCLUDED

