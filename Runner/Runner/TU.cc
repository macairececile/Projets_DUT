#define BOOST_TEST_DYN_LINK
#define BOOST_TEST_MODULE Model

#include <boost/test/unit_test.hpp>

#include "AnimatedGraphicElement.h"
#include "Model.h"
#include "MovableElement.h"
#include "Ball.h"
#include "GraphicElement.h"
#include "SlidingBackground.h"

// les tests groupes par "cas"

BOOST_AUTO_TEST_CASE(test_retourneX){

    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);
    BOOST_CHECK(_element->getX() == 5);
}

BOOST_AUTO_TEST_CASE(test_retourneY)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);

    BOOST_CHECK(_element->getY() == 10);
}

BOOST_AUTO_TEST_CASE(test_retourneW)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);

    BOOST_CHECK(_element->getW() == 50);
}

BOOST_AUTO_TEST_CASE(test_retourneH)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);

    BOOST_CHECK(_element->getH() == 100);
}

BOOST_AUTO_TEST_CASE(test_retournedX)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);

    BOOST_CHECK(_element->getdX() == 10);
}

BOOST_AUTO_TEST_CASE(test_retournedY)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);

    BOOST_CHECK(_element->getdY() == 10);
}

BOOST_AUTO_TEST_CASE(test_retournedXbis)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);
    _element->setdX(120);
    BOOST_CHECK(_element->getdX() == 120);
}

BOOST_AUTO_TEST_CASE(test_retournedYbis)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);
    _element->setdY(100);
    BOOST_CHECK(_element->getdY() == 100);
}

BOOST_AUTO_TEST_CASE(test_setPosition)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);
    _element->setPosition(40, 60);
    BOOST_CHECK(_element->getX() == 40);
    BOOST_CHECK(_element->getY() == 60);
}

BOOST_AUTO_TEST_CASE(test_setY)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);
    _element->setY(50);
    BOOST_CHECK(_element->getY() == 50);

}

BOOST_AUTO_TEST_CASE(test_move)
{
    MovableElement *_element;
    _element = new MovableElement(5, 10, 50, 100, 10, 10);
    _element->move();
    BOOST_CHECK(_element->getX() == 15);
    BOOST_CHECK(_element->getY() == 20);
}

BOOST_AUTO_TEST_CASE(test_collisions)
{
    Model *model = new Model(50,50);
    MovableElement *_balle;
    Obstacles *_obstacle;
    _balle = new MovableElement(5, 10, 50, 100, 10, 10);
    _obstacle = new Obstacles(5, 10, 50, 100, 1);

    BOOST_CHECK(model->collision(_balle, _obstacle) == true);

}

BOOST_AUTO_TEST_CASE(test_getBallPosition)
{
    Model * _model = new Model(50, 50);
    int x, y;
    _model->getBallPosition(x, y);
    BOOST_CHECK(x == 50);
    BOOST_CHECK(y == -50);
}

BOOST_AUTO_TEST_CASE(test_getBallDimension)
{
    Model * _model = new Model(50, 50);
    int w, h;
    _model->getBallDimension(w, h);
    BOOST_CHECK(w == 30);
    BOOST_CHECK(h == 30);
}

BOOST_AUTO_TEST_CASE(test_moveBall)
{
    Model * _model = new Model(50, 50);
    bool left;
    _model->moveBall(left);
    BOOST_CHECK(left == false);
}

BOOST_AUTO_TEST_CASE(test_getNewMovableElements)
{
    Model * _model = new Model(50, 50);
    std::vector<const MovableElement * > _new_elements;
    _model->getNewMovableElements();
    BOOST_CHECK(_new_elements.size() == 0);
}


