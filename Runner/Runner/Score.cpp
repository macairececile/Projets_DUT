#include <iostream>
#include <fstream>
#include "Score.h"

using namespace std;

const char * FILE_HSCORES = "score.txt";


// Les fonctions initHScores, SaveHScore et addHScore sont implémentées mais elles ne sont pas utilisées dans le code car
// elles ne marchent pas comme on le souhaiterait.

Score::Score(){
    _vie = 100;
    _score = 0;
}

void Score::initHScores(){
    fstream f;

    f.open(FILE_HSCORES, ios::in );

    if (!f.fail())
    {
        for(int i=0; i<TAILLE_SCORE && f.eof() != true; i++){
            f >> hScore[i];
            tailleReelle++;
        }
        tailleReelle--;
    }


    f.close();
}

void Score::SaveHScore(){
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

int Score::addHScore(){ // ajout meilleurs scores
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


//Fonction qui retourne le score
int Score::getScore(){
    return _score;
}


//Fonction qui retourne la vie
int Score::getVie(){
    return _vie;
}


//Fonction qui permet de changer le score
void Score::setScore(int score){
    _score = score;
}


//Fonction qui permet de changer la vie
void Score::setVie(int vie){
    _vie = vie;
}



