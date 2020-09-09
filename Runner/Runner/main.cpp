#include "View.h"
#include "Model.h"
#include <SFML/Audio.hpp>

//const std::string SOUND = "./sons/music.wav";

int main()
{
  srand(time(NULL));

  // Gestion du son
//  sf::Sound music;
//  sf::SoundBuffer buffer;
//  if(!buffer.loadFromFile(SOUND))
//      std::cerr << "ERROR when loading image file: " << std::endl;;
//  music.setBuffer(buffer);
//  music.play();

  Model model(SCREEN_WIDTH, SCREEN_HEIGHT);
  View view(SCREEN_WIDTH, SCREEN_HEIGHT);
  view.setModel(&model);

  while(view.treatEvents()){
    model.nextStep();
    view.synchronize();
    view.draw();
  }

  return EXIT_SUCCESS;
}

