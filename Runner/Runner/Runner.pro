TEMPLATE = app
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt
QMAKE_CXXFLAGS += -std=c++11
SOURCES += \
    View.cpp \
    Model.cpp \
    Ball.cpp \
    GraphicElement.cpp \
    MovableElement.cpp \
    SlidingBackground.cpp \
    Score.cpp \
    AnimatedGraphicElement.cpp \
    Obstacles.cpp \
    main.cpp \
    Bonus.cpp

LIBS += -lsfml-audio -lsfml-graphics -lsfml-window -lsfml-system


include(deployment.pri)
qtcAddDeployment()

HEADERS += \
    View.h \
    Model.h \
    Ball.h \
    GraphicElement.h \
    MovableElement.h \
    SlidingBackground.h \
    Score.h \
    AnimatedGraphicElement.h \
    Obstacles.h \
    Bonus.h

