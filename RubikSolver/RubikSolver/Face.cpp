#include "Face.h"

Face::Face(char **grid)
{
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            colors[i][j] = grid[i][j];
        }
    }
}
Face::Face(char c)
{
    for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            colors[i][j] = c;
        }
    }
}
char* Face::turnLeftUp(char c[3])
{
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = colors[i][0];
        colors[i][0] = c[i];
    }
    return tempRow;
}
char* Face::turnRightUp(char c[3])
{
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = colors[i][2];
        colors[i][2] = c[i];
    }
    return tempRow;
}
char* Face::turnTopLeft(char c[3])
{
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = colors[0][i];
        colors[0][i] = c[i];
    }
    return tempRow;
}
char* Face::turnBottomLeft(char c[3])
{
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = colors[2][i];
        colors[2][i] = c[i];
    }
    return tempRow;
}
char* Face::turnLeftDown(char c[3])
{
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = colors[i][0];
        colors[i][0] = c[i];
    }
    return tempRow;
}
char* Face::turnRightDown(char c[3])
{
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = colors[i][2];
        colors[i][2] = c[i];
    }
    return tempRow;
}
char* Face::turnTopRight(char c[3])
{
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = colors[0][i];
        colors[0][i] = c[i];
    }
    return tempRow;
}
char* Face::turnBottomRight(char c[3])
{
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = colors[2][i];
        colors[2][i] = c[i];
    }
    return tempRow;
}
char** Face::getFace()
{
    for (int i = 0; i < 3; i++)
    {
        colorsTemp[i] = colors[i];
    }

    return colorsTemp;
}
void Face::rotateClockwise()
{
    for (int i = 0, j = 2; i < 3; i++, j--)
    {
        tempRow[i] = colors[0][i];
        colors[0][i] = colors[j][0];
    }
    for (int i = 0; i < 3; i++)
    {
        colors[i][0] = colors[2][i];
    }
    for (int i = 0, j = 2; i < 3; i++, j--)
    {
        colors[2][i] = colors[j][2];
    }
    for (int i = 0; i < 3; i++)
    {
        colors[i][2] = tempRow[i];
    }
}
void Face::rotateCounterClockwise()
{
    for (int i = 0; i < 3; i++)
    {
        tempRow[i] = colors[0][i];
        colors[0][i] = colors[i][2];
    }
    for (int i = 0, j = 2; i < 3; i++, j--)
    {
        colors[i][2] = colors[2][j];
    }
    for (int i = 2; i >= 0; i--)
    {
        colors[2][i] = colors[i][0];
    }
    for (int i = 0, j = 2; i < 3; i++, j--)
    {
        colors[i][0] = tempRow[j];
    }
}