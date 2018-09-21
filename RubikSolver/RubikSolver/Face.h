#ifndef FACE_H
#define FACE_H

class Face
{
private: //holds the colors for a face of the cube
    char colors[3][3];
    char *colorsTemp[3];
    char tempRow[3];
public: // recieves new colors from adjacent face and passes old colors to other adjacent face
    char* turnLeftUp(char[3]);
    char* turnRightUp(char[3]);
    char* turnTopLeft(char[3]);
    char* turnBottomLeft(char[3]);
    char* turnLeftDown(char[3]);
    char* turnRightDown(char[3]);
    char* turnTopRight(char[3]);
    char* turnBottomRight(char[3]);
    void rotateClockwise();
    void rotateCounterClockwise();
    char** getFace();
    Face(char);
    Face(char**);
    ~Face() {}
};
    
#endif