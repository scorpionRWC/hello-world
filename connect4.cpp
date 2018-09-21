#include<iostream>
#include<conio.h>
using namespace std;

class Player
{
private:
    char color;
    bool win;
public:
    int chooseCol();
    Player(char);
    void playerWon();
    bool getWin();
};

Player::Player(char c)
{
    color = c;
    win = false;
}

int Player::chooseCol()
{
    char input;
    bool again = false;
    do
    {
        cout << "\nEnter a column (1 - 7)... ";
        input = _getch();
        switch (input)
        {
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
            again = false; 
            break;
        default:
            again = true;
        }
    } while (again == true);
    return (int)(input - 49);
}

void Player::playerWon()
{
    win = true;
}

bool Player::getWin()
{
    return win;
}

class Board
{
private:
    static const int ROW = 6, COLUMN = 7;
    static const char BLANK = '.';
    char player1color, player2color;
    char board[ROW][COLUMN];
public:
    void printBoard();
    bool insertPiece(char, int);
    bool checkWin(char);
    bool checkTie();
    Board(char, char);
};

Board::Board(char p1c, char p2c)
{
    for (int i = 0; i < ROW; i++)
    {
        for (int j = 0; j < COLUMN; j++)
        {
            board[i][j] = BLANK;
        }
    }
    player1color = p1c;
    player2color = p2c;
}

void Board::printBoard()
{
    system("CLS");
    for (int i = 0; i < COLUMN; i++)
    {
        cout << i + 1 << " ";
    }
    cout << endl << endl;
    for (int i = 0; i < ROW; i++)
    {
        for (int j = 0; j < COLUMN; j++)
        {
            cout << board[i][j] << " ";
        }
        cout << endl;
    }
}

bool Board::insertPiece(char c, int col)
{
    for (int i = 0; i < ROW; i++)
    {
        if (board[i + 1][col] != BLANK && board[i][col] == BLANK)
        {
            board[i][col] = c;
            printBoard();
            return true;
        }
    }
    return false;
}

bool Board::checkWin(char color)
{
    // check horizontal win
    for (int i = 0; i < ROW; i++)
    {
        for (int j = 0; j < COLUMN - 3; j++)
        {
            if (board[i][j] == color && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3])
            {
                return true;
            }
        }
    }

    // check verical win
    for (int j = 0; j < COLUMN; j++)
    {
        for (int i = 0; i < ROW - 3; i++)
        {
            if (board[i][j] == color && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j])
            {
                return true;
            }
        }
    }

    // check diagonal (\) win
    for (int i = 0; i < ROW - 3; i++)
    {
        for (int j = 0; j < COLUMN - 3; j++)
        {
            if (board[i][j] == color && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3])
            {
                return true;
            }
        }
    }

    // check diagonal (/) win
    for (int j = 0; j < COLUMN; j++)
    {
        for (int i = ROW - 1; i >= 3; i--)
        {
            if (board[i][j] == color && board[i][j] == board[i - 1][j + 1] && board[i][j] == board[i - 2][j + 2] && board[i][j] == board[i - 3][j + 3])
            {
                return true;
            }
        }
    }
    return false;
}

bool Board::checkTie()
{
    for (int i = 0; i < ROW; i++)
    {
        for (int j = 0; j < COLUMN; j++)
        {
            if (board[i][j] == BLANK)
            {
                return false;
            }
        }
    }
    return true;
}

int main()
{
    const char color1 = 'R';
    const char color2 = 'B';
    Player *player1 = new Player(color1);
    Player *player2 = new Player(color2);
    Board board(color1, color2);
    board.printBoard();
    bool endGame = false;
    bool validColumn = false;
    bool isTie = false;
    do
    {
        cout << "\nPlayer 1...\n";
        do
        {
            validColumn = false;
            if (board.insertPiece(color1, player1->chooseCol()) == false)
            {
                validColumn = false;
                cout << "That column is already full...\n";
            }
            else validColumn = true;
            if (endGame = board.checkWin(color1) == true)
            {
                player1->playerWon();
            }
            if (board.checkTie() == true)
            {
                isTie = true;
                endGame = true;
            }
        } while (validColumn == false);
        if (endGame == false)
        {
            cout << "\nPlayer 2...\n";
            do
            {
                validColumn = false;
                if (board.insertPiece(color2, player2->chooseCol()) == false)
                {
                    validColumn = false;
                    cout << "That column is already full...\n";
                }
                else validColumn = true;
                if (endGame = board.checkWin(color2) == true)
                {
                    player2->playerWon();
                }
                if (board.checkTie() == true)
                {
                    isTie = true;
                    endGame = true;
                }
            } while (validColumn == false);
        }
    } while (endGame == false);

    if (isTie == true)
    {
        cout << "\nBoth Players have Tied!!!\n";
    }
    else if (player1->getWin() == true)
    {
        cout << "\nPlayer 1 is the WINNER!!!\n";
    }
    else
        cout << "\nPlayer 2 is the WINNER!!!\n";

    system("pause");
}