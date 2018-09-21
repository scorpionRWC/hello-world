#include<string>
#include<iostream>
#include"Rubik.h"

int main()
{
    Rubik *cube = new Rubik();
    cout << cube->solveCube(cube, 5) << endl;
    cube->turnBottomRight();
    cube->turnLeftUp();
    cube->turnRightDown();
    cube->turnBackClockwise();
    cube->turnTopLeft();
    cube->turnFrontCounterClockwise();
    cube->turnBackCounterClockwise();
    cube->turnBottomLeft();
    cube->turnBottomLeft();
    cube->turnLeftDown();
    cube->turnLeftDown();
    cube->turnFrontClockwise();
    cube->turnBottomRight();
    cube->turnRightUp();
    cube->turnTopRight();
    cube->turnTopRight();
    cube->turnBackClockwise();
    cube->turnTopLeft();
    cube->turnLeftDown();
    cube->turnBottomLeft();
    cube->turnLeftDown();
    cube->turnTopRight();
    cube->turnRightDown();
    cube->turnRightDown();
    cube->turnFrontClockwise();
    cout << cube->solveCube(cube, 21) << endl;
    cout << cube->getAnswer() << endl;

    system("pause");
    return 0;
}