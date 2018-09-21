#ifndef RUBIK_H
#define RUBIK_H
#include<Windows.h>
//#include<string>
#include "Face.h"
using namespace std;

class Rubik
{
private:
    string answer;
    Face *front, *back, *left, *right, *top, *bottom;
    char tempRow[3];
    char *tempPtr;
    char tempCell;
    const WORD colors[6] = { 0x0F, 0x01, 0x04, 0x02 | 0x04, 0x0E, 0x02 };
    HANDLE hstdout;
    CONSOLE_SCREEN_BUFFER_INFO csbi;
public: // these are used relative to the front face, each function here will call a series of functions for each Face that will be changed. checkSolve() will return a bool to indicate whether or not the resulting change has solved the Rubik's cube.
    string getAnswer();
    void turnLeftUp();
    void turnRightUp();
    void turnTopLeft();
    void turnBottomLeft();
    void turnLeftDown();
    void turnRightDown();
    void turnTopRight();
    void turnBottomRight();
    void turnFrontClockwise();
    void turnFrontCounterClockwise();
    void turnBackClockwise();
    void turnBackCounterClockwise();
    bool checkSolved();
    int getCellColorIndex(char);
    void printCube();
    Rubik();
    Rubik(Face*, Face*, Face*, Face*, Face*, Face*);
    bool solveCube(Rubik*, int);
    ~Rubik() {}
};

#endif