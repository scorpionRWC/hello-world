#include<iostream>
#include<conio.h>
#include<cstdlib>
using namespace std;

class TicTacToe
{
private:
    char mark;
    char grid[9];
    int score[9];
    bool player;
public:
    void printBoard();
    void addMark(int);
    void removeMark(int);
    bool availablePos(int);
    int checkEnd();
    void comTurn();
    bool isPlayer();
    void switchPlayer();
    int treeScore(int, TicTacToe*);
    void setPlayer(bool);
    TicTacToe();
};

void TicTacToe::setPlayer(bool p)
{
    player = p;
}

bool TicTacToe::isPlayer()
{
    return player;
}

void TicTacToe::switchPlayer()
{
    player = !player;
}

TicTacToe::TicTacToe()
{
    player = true;
    for (int i = 0; i < 9; i++)
    {
        grid[i] = i + 49;
    }
}

void TicTacToe::printBoard()
{
    system("CLS");
    cout << endl;
    cout << "         " << grid[0] << " | " << grid[1] << " | " << grid[2] << endl;
    cout << "        -----------\n";
    cout << "         " << grid[3] << " | " << grid[4] << " | " << grid[5] << endl;
    cout << "        -----------\n";
    cout << "         " << grid[6] << " | " << grid[7] << " | " << grid[8] << endl;
    cout << endl;
}

void TicTacToe::addMark(int pos)
{
    if (isPlayer())
        grid[pos] = 'X';
    else
        grid[pos] = 'O';
}

void TicTacToe::removeMark(int pos)
{
    grid[pos] = char(pos + 49);
}

bool TicTacToe::availablePos(int pos)
{
    if (pos < 49 || pos > 57)
        return false;
    if (grid[pos - 49] != 'X' && grid[pos - 49] != 'O')
        return true;
    else return false;
}

int TicTacToe::checkEnd()
{
    if (grid[0] == grid[1] && grid[1] == grid[2] ||
        grid[3] == grid[4] && grid[4] == grid[5] ||
        grid[6] == grid[7] && grid[7] == grid[8] ||
        grid[0] == grid[3] && grid[3] == grid[6] ||
        grid[1] == grid[4] && grid[4] == grid[7] ||
        grid[2] == grid[5] && grid[5] == grid[8] ||
        grid[0] == grid[4] && grid[4] == grid[8] ||
        grid[2] == grid[4] && grid[4] == grid[6])
        return (int)player;
    for (int i = 0; i < 9; i++)
    {
        if (grid[i] != 'X' && grid[i] != 'O')
            return -1;
    }
    return 2;
}

int TicTacToe::treeScore(int pos, TicTacToe *N)
{
    int best = -10000;
    int worst = 10000;
    int tempScore = 0;
    TicTacToe *Node = new TicTacToe();
    for (int i = 0; i < 9; i++)
        Node->grid[i] = N->grid[i];
    Node->setPlayer(N->isPlayer());
    Node->addMark(pos);
    //Node->printBoard();
    int end = Node->checkEnd();
    if (end == 0)//if computer wins
        return 1;
    if (end == 1)//if player wins
    {
        //cout << "\nHi!\n";
        return -5;
    }
    if (end == 2)
    {//if tie
        return 1;
    }
    //if (end == -1) return 0;
    Node->switchPlayer();
    for (int i = 0; i < 9; i++)
    {
        if (Node->grid[i] != 'X' && Node->grid[i] != 'O')
        {
             tempScore += treeScore(i, Node);
             if (worst > tempScore)
                 worst = tempScore;
             if (best < tempScore)
                 best = tempScore;
        }
    }
    if(isPlayer())
        return worst;
    else 
        return best;
}

void TicTacToe::comTurn()
{
    int bestMove;
    TicTacToe *Copy = new TicTacToe();
    Copy->switchPlayer(); // because player is true by default
    for (int i = 0; i < 9; i++)
    {
        Copy->grid[i] = grid[i];
    }
    for (int i = 0; i < 9; i++)
    {
        if (Copy->grid[i] != 'X' && Copy->grid[i] != 'O')
        {
            Copy->score[i] = treeScore(i, Copy);
            Copy->addMark(i);
            if (Copy->checkEnd() == 0) Copy->score[i] = 1000;
            Copy->removeMark(i);
            //cout << "\nPosition " << i + 1 << " has a score of " << Copy->score[i];
        }
    }
    bestMove = 0;
    for (int i = 1; i < 9; i++)
    {
        if (Copy->score[i] > Copy->score[bestMove])
            bestMove = i;
    }
    addMark(bestMove);
}

int main()
{
    start:
    TicTacToe T;
    char xo;
    int endGame = -1; // -1 = not over, 0 = computer won, 1 = player won, 2 = tie
    bool goodInput = true;
    do {
        if (T.isPlayer())
        {
            T.printBoard();
            do
            {
                cout << "Choose a position: ";
                xo = _getch();
                goodInput = T.availablePos((int)xo);
                if (!goodInput) cout << "Invalid position...\n";
            } while (!goodInput);
            T.addMark(xo - 49);
        }
        else
            T.comTurn();
        endGame = T.checkEnd();
        T.switchPlayer();
    } while (endGame == -1);
    T.printBoard();
    if (endGame == 1)
        cout << "\n\nYou beat the Tic Tac Toe Boss!!!\n\n\n\n";
    else if (endGame == 0)
        cout << "\n\nThe Tic Tac Toe Boss beat you!!!\n\n\n\n";
    else
        cout << "\n\nYou tied with the Tic Tac Toe Boss!!!\n\n\n\n";
    cout << "Press any button to continue...\n\n\n";
    _getch();
    goto start;
}