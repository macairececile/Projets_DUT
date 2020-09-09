#include "GraphicElement.h"
#include "AnimatedGraphicElement.h"
#include <iostream>
#include <fstream>
#include <SFML/Graphics/Sprite.hpp>

using namespace std;


//Constructeur
AnimatedGraphicElement::AnimatedGraphicElement(const std::vector<sf::IntRect> &clipRects, sf::Texture &image, int x, int y, int w, int h):
    _clip_rects(clipRects),
    GraphicElement(image, x, y, w, h),


    _current_clip_rect(0){
    setPosition(x,y);
}

//Fonction qui permet de déssiner.
void AnimatedGraphicElement::draw(sf::RenderWindow *window){

    if (_current_clip_rect < 8)
    {
        setTextureRect(_clip_rects.at(_current_clip_rect));
        time = clock.getElapsedTime();
        if(time.asMilliseconds() > 50)
        {
            _current_clip_rect++;
            time = clock.restart();
        }
    }
    else {
        _current_clip_rect = 0;

    }

    window->draw(*this);
}


