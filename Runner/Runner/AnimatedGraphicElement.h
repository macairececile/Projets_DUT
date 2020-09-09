#ifndef ANIMATEDGRAPHICELEMENT_H
#define ANIMATEDGRAPHICELEMENT_H
#include <SFML/Graphics.hpp>
#include "GraphicElement.h"

class AnimatedGraphicElement : public GraphicElement{
private:
    int _current_clip_rect;
    sf::Clock clock;
    sf::Time time=clock.restart();
    std::vector<sf::IntRect> _clip_rects;
public:
    AnimatedGraphicElement(const std::vector<sf::IntRect> & clipRects, sf::Texture &image, int x, int y, int w, int h);
    void draw(sf::RenderWindow * window);
};




#endif // ANIMATEDGRAPHICELEMENT_H
