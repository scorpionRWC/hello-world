#include<iostream>
#include<conio.h>
using namespace std;

class Sudoku
{
private:
    const int BOX = 4; // side length of the inner box, 2 for a 4 by 4 box, etc.
    const int SIDE = BOX*BOX; // side length of the outer box, 4 for a 4 by 4 box, etc.
    int **puzzle;
    bool **given;
    bool checkSmallerBox(int, int);
public:
    void printPuzzle();
    Sudoku();
    bool checkRow();
    bool checkCol();
    bool checkBox();
    bool solvePuzzle();
};

Sudoku::Sudoku()
{
    puzzle = new int*[SIDE];
    given = new bool*[SIDE];
    for (int i = 0; i < SIDE; i++)
    {
        puzzle[i] = new int[SIDE];
        given[i] = new bool[SIDE];
    }
    for (int i = 0; i < SIDE; i++)
    {
        for (int j = 0; j < SIDE; j++)
        {
            puzzle[i][j] = 0;
            given[i][j] = true;
        }
    }

    //these are the given values for the puzzle
    puzzle[0][0] = 1;
    puzzle[1][2] = 2;
    puzzle[3][3] = 3;
    puzzle[2][1] = 4;
   // puzzle[2][3] = 1;
}

void Sudoku::printPuzzle()
{
    for (int i = 0; i < SIDE; i++)
    {
        for (int j = 0; j < SIDE; j++)
        {
            cout << puzzle[i][j];
            if (j != 0 && (j+1) % BOX == 0 && j != SIDE - 1)
            {
                cout << " ";
            }
        }
        cout << endl;
        if (i != 0 && (i+1) % BOX == 0 && i != SIDE - 1)
        {
            cout << endl;
        }
    }
    cout << endl;
}

bool Sudoku::checkRow()
{
    for (int i = 0; i < SIDE; i++)
    {
        for (int j = 0; j < SIDE; j++)
        {
            for (int k = 0; k < SIDE; k++)
            {
                if (k != j && puzzle[i][j] != 0 && puzzle[i][k] != 0 && puzzle[i][j] == puzzle[i][k])
                {
                    return false;
                }
            }
        }
    }
    return true;
}

bool Sudoku::checkCol()
{
    for (int i = 0; i < SIDE; i++)
    {
        for (int j = 0; j < SIDE; j++)
        {
            for (int k = 0; k < SIDE; k++)
            {
                if (j != k && puzzle[k][i] != 0 && puzzle[j][i] != 0 && puzzle[k][i] == puzzle[j][i])
                {
                    return false;
                }
            }
        }
    }
    return true;
}

bool Sudoku::checkBox()
{
    for (int i = 0; i < SIDE; i += BOX)
    {
        for (int j = 0; j < SIDE; j += BOX)
        {
            if (checkSmallerBox(i, j) == false)
            {
                return false;
            }
        }
    }
    return true;
}

bool Sudoku::checkSmallerBox(int x, int y)
{
    for (int i = x; i < x + BOX; i++)
    {
        for (int j = y; j < y + BOX; j++)
        {
            for (int q = x; q < x + BOX; q++)
            {
                for (int w = y; w < y + BOX; w++)
                {
                    if ((i != q || j != w) && puzzle[i][j] != 0 && puzzle[q][w] != 0 && puzzle[i][j] == puzzle[q][w])
                    {
                        return false;
                    }
                }
            }
        }
    }
    return true;
}

bool Sudoku::solvePuzzle()
{
    for (int i = 0, q = 0; i < SIDE; i++, q++)
    {
        for (int j = 0, w = 0; j < SIDE; j++, w++)
        {
            q = i;
            w = j;
            if (puzzle[i][j] == 0 || given[i][j] == false)
            {
                given[i][j] = false;
                do {
                    puzzle[i][j]++;
            //      system("pause");
              //      system("cls");
                //    printPuzzle();
                } while (checkRow() == false || checkCol() == false || checkBox() == false);
                if (puzzle[i][j] > SIDE)
                {
                    puzzle[i][j] = 0;
                    given[i][j] = true;
                    do
                    {
                        w--;
                        if (w < 0) 
                        {
                            w = SIDE - 1;
                            q--;
                            if (q < 0)
                            {
                                return false;
                            }
                        }
                    } while (given[q][w] == true);
                    w--;
                }
            }
            i = q;
            j = w;
        }
    }
    return true;
}

int main()
{
    Sudoku sud;
    cout << "Original Sudoku Puzzle:\n";
    sud.printPuzzle();
    cout << "-------------------\n";
    if (sud.solvePuzzle() == true)
    {
        cout << "Sudoku Puzzle Solved:\n";
        sud.printPuzzle();
    }
    else
    {
        cout << "Puzzle has no solution...";
    }
    cout << "-------------------\n";
    system("pause");
}