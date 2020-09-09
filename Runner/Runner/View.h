#ifndef _VIEW_
#define _VIEW_

#include <SFML/Graphics.hpp>
#include <map>
#include "SlidingBackground.h"
#include "GraphicElement.h"
#include "AnimatedGraphicElement.h"
#include "Model.h"
#include "MovableElement.h"
#include <vector>

// CONSTANTES QUI CONTIENNENT TOUS LES FICHIERS IMAGES ET SONS
const std::string BALL_IMAGE = "./Images/ball.png";
const std::string TEXT = "./fonts/friday night.ttf";
const std::string TEXT2 = "./fonts/Antique Olive.ttf";
const std::string CITY1_IMAGE = "./Images/city_1.png";
const std::string CITY2_IMAGE = "./Images/city_2.png";
const std::string CITY3_IMAGE = "./Images/city_3.png";
const std::string CITY4_IMAGE = "./Images/city_4.png";
const std::string BUTTON_IMAGE = "./Images/button.png";
const std::string BALLS_IMAGE = "./Images/balls.png";
const std::string OBSTACLE = "./Images/ennemy_block.png";
const std::string LIFE = "./Images/bat.png";
const std::string CONTOUR_LIFE = "./Images/life.png";
const std::string LOGO = "./Images/logo.jpeg";
const std::string RULES = "./Images/rules.png";
const std::string MENU = "./Images/menu.png";
const std::string OBSTACLE1 = "./Images/ennemies.png";
const std::string OBSTACLE2 = "./Images/ennemies2.png";
const std::string PIECES = "./Images/bonus2.png";
const std::string BONUSLIFE = "./Images/bonusvie.png";


class View {
private:
    int play;
    int _w, _h;

    sf::RenderWindow * _window;
    Model * _model;

    sf::Font _font;
    sf::Text _text;
    sf::Font _font2;
    sf::Text _tscore;
    sf::Text _tvie;

    sf::Texture _ballTexture, _backgroundTexture, _backgroundTexture2, _logoTexture, _obstacleTexture, _rulesTexture, _menuTexture, _ennemies, _ennemies2
    ,_vieTexture, _DvieTexture, _pieceTexture, _bonuslifeTexture, _backgroundTexture3, _backgroundTexture4;
    AnimatedGraphicElement *_ballSprite;
    MovableElement *_obstacleSprite, *_ennemiesSprite, *_ennemies2Sprite, *_piece, *_bonuslife;
    std::map<const MovableElement*, GraphicElement*> _elementToGraphicElement;
    std::map<const MovableElement*, GraphicElement*> Garbage;
    SlidingBackground *_backgroundSprite, *_background2, *_background3, *_background4;
    GraphicElement *_logo, *_rules, *_menu, *_vie, *_dVie;

    sf::Clock clock;
    sf::Clock cbackground;
    sf::Time time=clock.restart();
    sf::Time timeS=clock.restart();
    sf::Time timeB = cbackground.restart();
    sf::Texture _obstacleT;

    sf::RectangleShape barreDeVie;

public:
    View(int w, int h);
    ~View();
    void setModel(Model * model);
    void draw();
    void synchronize();
    bool treatEvents();
    void RectLecture(std::vector<sf::IntRect> &RectLecture);
    void drawMenu();
    void drawGame();
    void drawEnd();
    void drawRules();
};

#endif
