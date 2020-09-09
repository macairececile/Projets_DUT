#ifndef SCORE_H
#define SCORE_H
#include <iostream>
#include <fstream>
#include <SFML/Graphics.hpp>


const static int TAILLE_SCORE = 10;

class Score{

private:
    int _score;
    sf::Clock clock;
    sf::Time time=clock.restart();
    int _vie;
    int tailleReelle=0;
    int totalScore=0;
    int hScore[TAILLE_SCORE];


public:
    Score();
    void initHScores();
    void SaveHScore();
    int addHScore();
    int getScore();
    int getVie();
    void setScore(int score);
    void setVie(int vie);
};

#endif // SCORE_H
