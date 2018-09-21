#include<string>
#include<iostream>
#include "Rubik.h"
//using namespace std;

Rubik::Rubik(Face *oFront, Face *oBack, Face *oLeft, Face *oRight, Face *oTop, Face *oBottom)
{
    front = new Face(oFront->getFace());
    back = new Face(oBack->getFace());
    left = new Face(oLeft->getFace());
    right = new Face(oRight->getFace());
    top = new Face(oTop->getFace());
    bottom = new Face(oBottom->getFace());
}
Rubik::Rubik()
{
    answer = "";
    front = new Face('W');
    back = new Face('Y');
    left = new Face('B');
    right = new Face('G');
    top = new Face('R');
    bottom = new Face('O');   
    hstdout = GetStdHandle(STD_OUTPUT_HANDLE);
}
void Rubik::turnLeftUp()
{
    left->rotateCounterClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = back->getFace()[i][0];
    }
    back->turnLeftUp(top->turnLeftUp(front->turnLeftUp(bottom->turnLeftUp(tempRow))));
}
void Rubik::turnRightUp()
{
    right->rotateClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = back->getFace()[i][2];
    }
    back->turnRightUp(top->turnRightUp(front->turnRightUp(bottom->turnRightUp(tempRow))));
}
void Rubik::turnTopLeft()
{
    top->rotateClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = right->getFace()[0][i];
    }
    right->turnTopLeft(back->turnTopLeft(left->turnTopLeft(front->turnTopLeft(tempRow))));
}
void Rubik::turnBottomLeft()
{
    bottom->rotateCounterClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = right->getFace()[2][i];
    }
    right->turnBottomLeft(back->turnBottomLeft(left->turnBottomLeft(front->turnBottomLeft(tempRow))));
}
void Rubik::turnLeftDown()
{
    left->rotateClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = back->getFace()[i][0];
    }
    back->turnLeftDown(bottom->turnLeftDown(front->turnLeftDown(top->turnLeftDown(tempRow))));
}
void Rubik::turnRightDown()
{
    right->rotateCounterClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = back->getFace()[i][2];
    }
    back->turnRightDown(bottom->turnRightDown(front->turnRightDown(top->turnRightDown(tempRow))));
}
void Rubik::turnTopRight()
{
    top->rotateCounterClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = left->getFace()[0][i];
    }
    left->turnTopRight(back->turnTopRight(right->turnTopRight(front->turnTopRight(tempRow))));
}
void Rubik::turnBottomRight()
{
    bottom->rotateClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = left->getFace()[2][i];
    }
    left->turnBottomRight(back->turnBottomRight(right->turnBottomRight(front->turnBottomRight(tempRow))));
}
void Rubik::turnFrontClockwise()
{
    front->rotateClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = top->getFace()[2][i];
    }
    tempPtr = right->turnLeftDown(tempRow);
    tempCell = tempPtr[0];
    tempPtr[0] = tempPtr[2];
    tempPtr[2] = tempCell;
    tempPtr = left->turnRightUp(bottom->turnTopLeft(tempPtr));
    tempCell = tempPtr[0];
    tempPtr[0] = tempPtr[2];
    tempPtr[2] = tempCell;
    top->turnBottomRight(tempPtr);
}
void Rubik::turnFrontCounterClockwise()
{
    front->rotateCounterClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = top->getFace()[2][i];
    }
    tempCell = tempRow[0];
    tempRow[0] = tempRow[2];
    tempRow[2] = tempCell;
    tempPtr = bottom->turnTopRight(left->turnRightDown(tempRow));
    tempCell = tempPtr[0];
    tempPtr[0] = tempPtr[2];
    tempPtr[2] = tempCell;
    top->turnBottomLeft(right->turnLeftUp(tempPtr));
}
void Rubik::turnBackClockwise()
{
    back->rotateClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = top->getFace()[0][i];
    }
    tempPtr = right->turnRightDown(tempRow);
    tempCell = tempPtr[0];
    tempPtr[0] = tempPtr[2];
    tempPtr[2] = tempCell;
    tempPtr = left->turnLeftUp(bottom->turnBottomLeft(tempPtr));
    tempCell = tempPtr[0];
    tempPtr[0] = tempPtr[2];
    tempPtr[2] = tempCell;
    top->turnTopRight(tempPtr);
}
void Rubik::turnBackCounterClockwise()
{
    back->rotateCounterClockwise();
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = top->getFace()[0][i];
    }
    tempCell = tempRow[0];
    tempRow[0] = tempRow[2];
    tempRow[2] = tempCell;
    tempPtr = bottom->turnBottomRight(left->turnLeftDown(tempRow));
    tempCell = tempPtr[0];
    tempPtr[0] = tempPtr[2];
    tempPtr[2] = tempCell;
    top->turnTopLeft(right->turnRightUp(tempPtr));
}
bool Rubik::checkSolved()
{
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            if (back->getFace()[i][j] != 'Y' ||
                left->getFace()[i][j] != 'B' ||
                right->getFace()[i][j] != 'G' ||
                top->getFace()[i][j] != 'R' ||
                bottom->getFace()[i][j] != 'O')
            {
                return false;
            }
        }
    }
    return true;
}
void Rubik::printCube()
{
    cout << endl;
    GetConsoleScreenBufferInfo(hstdout, &csbi);
    for (int i = 0; i < 3; i++)
    {
        cout << "      ";
        for (int j = 0; j < 3; j++)
        {
            SetConsoleTextAttribute(hstdout, colors[getCellColorIndex(top->getFace()[i][j])]);
            cout << (char)219;
        }
        cout << endl;
    }
    for (int i = 0; i < 3; i++)
    {
        cout << "   ";
        for (int j = 0; j < 3; j++)
        {
            SetConsoleTextAttribute(hstdout, colors[getCellColorIndex(left->getFace()[i][j])]);
            cout << (char)219;
        }
        for (int j = 0; j < 3; j++)
        {
            SetConsoleTextAttribute(hstdout, colors[getCellColorIndex(front->getFace()[i][j])]);
            cout << (char)219;
        }
        for (int j = 0; j < 3; j++)
        {
            SetConsoleTextAttribute(hstdout, colors[getCellColorIndex(right->getFace()[i][j])]);
            cout << (char)219;
        }
        cout << "   ";
        for (int j = 0; j < 3; j++)
        {
            SetConsoleTextAttribute(hstdout, colors[getCellColorIndex(back->getFace()[i][j])]);
            cout << (char)219;
        }
        cout << endl;
    }
    for (int i = 0; i < 3; i++)
    {
        cout << "      ";
        for (int j = 0; j < 3; j++)
        {
            SetConsoleTextAttribute(hstdout, colors[getCellColorIndex(bottom->getFace()[i][j])]);
            cout << (char)219;
        }
        cout << endl;
    }
    cout << endl;
    FlushConsoleInputBuffer(GetStdHandle(STD_INPUT_HANDLE));
    SetConsoleTextAttribute(hstdout, csbi.wAttributes);
}
int Rubik::getCellColorIndex(char c)
{
    if (c == 'W') return 0;
    else if (c == 'B') return 1;
    else if (c == 'R') return 2;
    else if (c == 'O') return 3;
    else if (c == 'Y') return 4;
    return 5;
}

string Rubik::getAnswer()
{
    return answer;
}

bool Rubik::solveCube(Rubik *combo, int count)
{
    //cout << "\nCount: " << count << endl;
    //Rubik *combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);
    //combo->printCube();
    bool solved;
    if (count == 0)
    {
        return false;
    }
    combo->turnBackClockwise();
    if (checkSolved() == true)
    {
        answer = "|Turn Back Clockwise|" + answer;
        return true;
    }
    else
    {
        solved = solveCube(combo, count - 1);
        if(solved)
            answer = "|Turn Back Clockwise|" + answer;
        combo->turnBackCounterClockwise();
    }
    if (!solved)
    {
        /*delete(combo);
        combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
        combo->turnBackCounterClockwise();
        if (checkSolved() == true)
        {
            answer = "|Turn Back Counter Clockwise|" + answer;
            return true;
        }
        else
        {
            solved = solveCube(combo, count - 1);
            if(solved)
                answer = "|Turn Back Counter Clockwise|" + answer;
            combo->turnBackClockwise();
        }
        if (!solved)
        {
            /*delete(combo);
            combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
            combo->turnBottomLeft();
            if (checkSolved() == true)
            {
                answer = "|Turn Bottom Left|" + answer;
                return true;
            }
            else
            {
                solved = solveCube(combo, count - 1);
                if (solved)
                    answer = "|Turn Bottom Left|" + answer;
                combo->turnBottomRight();
            }
            if (!solved)
            {
              /*  delete(combo);
                combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
                combo->turnBottomRight();
                if (checkSolved() == true)
                {
                    answer = "|Turn Bottom Right|" + answer;
                    return true;
                }
                else
                {
                    solved = solveCube(combo, count - 1);
                    if (solved)
                        answer = "|Turn Bottom Right|" + answer;
                    combo->turnBottomLeft();
                }
                if (!solved)
                {
                    /*delete(combo);
                    combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
                    combo->turnFrontClockwise();
                    if (checkSolved() == true)
                    {
                        answer = "|Turn Front Clockwise|" + answer;
                        return true;
                    }
                    else
                    {
                        solved = solveCube(combo, count - 1);
                        if (solved)
                            answer = "|Turn Front Clockwise|" + answer;
                        combo->turnFrontCounterClockwise();
                    }
                    if (!solved)
                    {
                      /*  delete(combo);
                        combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
                        combo->turnFrontCounterClockwise();
                        if (checkSolved() == true)
                        {
                            answer = "|Turn Front Counter Clockwise|" + answer;
                            return true;
                        }
                        else
                        {
                            solved = solveCube(combo, count - 1);
                            if (solved)
                                answer = "|Turn Front Counter Clockwise|" + answer;
                            combo->turnFrontClockwise();
                        }
                        if (!solved)
                        {
                            /*delete(combo);
                            combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
                            combo->turnLeftDown();
                            if (checkSolved() == true)
                            {
                                answer = "|Turn Left Down|" + answer;
                                return true;
                            }
                            else
                            {
                                solved = solveCube(combo, count - 1);
                                if (solved)
                                    answer = "|Turn Left Down|" + answer;
                                combo->turnLeftUp();
                            }
                            if (!solved)
                            {
                                /*delete(combo);
                                combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
                                combo->turnLeftUp();
                                if (checkSolved() == true)
                                {
                                    answer = "|Turn Left Up|" + answer;
                                    return true;
                                }
                                else
                                {
                                    solved = solveCube(combo, count - 1);
                                    if (solved)
                                        answer = "|Turn Left Up|" + answer;
                                    combo->turnLeftDown();
                                }
                                if (!solved)
                                {
                                  /*  delete(combo);
                                    combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
                                    combo->turnRightDown();
                                    if (checkSolved() == true)
                                    {
                                        answer = "|Turn Right Down|" + answer;
                                        return true;
                                    }
                                    else
                                    {
                                        solved = solveCube(combo, count - 1);
                                        if (solved)
                                            answer = "|Turn Right Down|" + answer;
                                        combo->turnRightUp();
                                    }
                                    if (!solved)
                                    {
                                        /*delete(combo);
                                        combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
                                        combo->turnRightUp();
                                        if (checkSolved() == true)
                                        {
                                            answer = "|Turn Right Up|" + answer;
                                            return true;
                                        }
                                        else
                                        {
                                            solved = solveCube(combo, count - 1);
                                            if (solved)
                                                answer = "|Turn Right Up|" + answer;
                                            combo->turnRightDown();
                                        }
                                        if (!solved)
                                        {
                                            /*delete(combo);
                                            combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
                                            combo->turnTopLeft();
                                            if (checkSolved() == true)
                                            {
                                                answer = "|Turn Top Left|" + answer;
                                                return true;
                                            }
                                            else
                                            {
                                                solved = solveCube(combo, count - 1);
                                                if (solved)
                                                    answer = "|Turn Top Left|" + answer;
                                                combo->turnTopRight();
                                            }
                                            if (!solved)
                                            {
                                                /*delete(combo);
                                                combo = new Rubik(cube->front, cube->back, cube->left, cube->right, cube->top, cube->bottom);*/
                                                combo->turnTopRight();
                                                if (checkSolved() == true)
                                                {
                                                    answer = "|Turn Top Right|" + answer;
                                                    return true;
                                                }
                                                else
                                                {
                                                    solved = solveCube(combo, count - 1);
                                                    if (solved)
                                                        answer = "|Turn Top Right|" + answer;
                                                    combo->turnTopLeft();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    return solved;
}