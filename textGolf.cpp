#include<iostream>
#include<string>
#include<ctime>
using namespace std;

class Golf
{
private:
    int power;
    int drive;
    int approach;
    int putt;
    string name;
public:
    int getPower();
    int getDrive();
    int getApproach();
    int getPutt();
    string getName();
    Golf(int, int, int, int, string);
};

int Golf::getPower()
{
    return power;
}
int Golf::getDrive()
{
    return drive;
}
int Golf::getApproach()
{
    return approach;
}
int Golf::getPutt()
{
    return putt;
}
string Golf::getName()
{
    return name;
}
Golf::Golf(int p, int d, int a, int t, string n)
{
    power = p;
    drive = d;
    approach = a;
    putt = t;
    name = n;
}

int main()
{
    char ch;
    do {
        int swing;
        int playerNum;
        string pname;
        int par;

        int shots = 0;
        int holeLength;
        srand((int)time(0));
        holeLength = rand() % 460 + 90;
        if (holeLength < 150) par = 3;
        else if (holeLength >= 350) par = 5;
        else par = 4;
        Golf golfer[3] = { Golf(120,90,85,80, "Bob"),
            Golf(110,90,90,85, "John"),
            Golf(100,90,85,100, "Sara") };

        for (int g = 0; g < 3; g++)
        {
            cout << g + 1 <<".) " << golfer[g].getName() << endl;
            cout << "Power:     "; for (int i = 0; i < golfer[g].getPower() / 5 - 10; i++) cout << "*"; cout << endl;
            cout << "Drive:     "; for (int i = 0; i < golfer[g].getDrive() / 5 - 10; i++) cout << "*"; cout << endl;
            cout << "Approach:  "; for (int i = 0; i < golfer[g].getApproach() / 5 - 10; i++) cout << "*"; cout << endl;
            cout << "Putt:      "; for (int i = 0; i < golfer[g].getPutt() / 5 - 10; i++) cout << "*"; cout << endl << endl;
        }
        /*
        cout << "2.) " << golfer[1].getName() << endl;
        cout << "Power:     "; for (int i = 0; i<golfer[1].getPower() / 5 - 10; i++) cout << "*"; cout << endl;
        cout << "Drive:     "; for (int i = 0; i<golfer[1].getDrive() / 5 - 10; i++) cout << "*"; cout << endl;
        cout << "Approach:  "; for (int i = 0; i<golfer[1].getApproach() / 5 - 10; i++) cout << "*"; cout << endl;
        cout << "Putt:      "; for (int i = 0; i<golfer[1].getPutt() / 5 - 10; i++) cout << "*"; cout << endl << endl;
        cout << "3.) " << golfer[2].getName() << endl;
        cout << "Power:     "; for (int i = 0; i<golfer[2].getPower() / 5 - 10; i++) cout << "*"; cout << endl;
        cout << "Drive:     "; for (int i = 0; i<golfer[2].getDrive() / 5 - 10; i++) cout << "*"; cout << endl;
        cout << "Approach:  "; for (int i = 0; i<golfer[2].getApproach() / 5 - 10; i++) cout << "*"; cout << endl;
        cout << "Putt:      "; for (int i = 0; i<golfer[2].getPutt() / 5 - 10; i++) cout << "*"; cout << endl << endl;*/
        do {
            cout << "Enter the number of the golfer: ";
            cin >> playerNum;
            playerNum -= 1;
            if (playerNum >= 0 && playerNum <= 2) {
                pname = golfer[playerNum].getName();
                cout << "You chose " << pname << "!\n\n";
            }
            else cout << "Invalid Number\n\n";
        } while (playerNum < 0 || playerNum > 2);
        cout << "Random Hole Generated\nPar: " << par << "\nHole Length: " << holeLength << " Yards" << endl;
        do {
            int max;
            double far;
            cout << endl << holeLength << " Yards Away\n";
            if (holeLength <= 10) cout << "On Green... Using Putter\n";
            else if (holeLength <= 20) { cout << "Using Pitching Wedge\n"; max = 20; }
            else if (holeLength <= 50) { cout << "Using 9 Iron\n"; max = 50; }
            else if (holeLength <= 70) { cout << "Using 8 Iron\n"; max = 70; }
            else if (holeLength <= 90) { cout << "Using 7 Iron\n"; max = 90; }
            else if (holeLength <= 110) { cout << "Using 6 Iron\n"; max = 110; }
            else if (holeLength <= 120) { cout << "Using 5 Iron\n"; max = 120; }
            else if (holeLength > 120) { cout << "Using Driver\n"; max = 150; }
            cout << "What percent of your power do you want to use? ";
            int acc = 100;
            cin >> swing;
            if (swing > 100) acc -= swing - 100;
            int accd = 100 + rand() % (101 - golfer[playerNum].getDrive());
            int acca = 100 + rand() % (101 - golfer[playerNum].getApproach());
            int accp = 100 + rand() % (101 - golfer[playerNum].getPutt());
            // cout << accd/100.0 << " " << acca/100.0 << " " << accp/100.0 << endl;
            if (holeLength <= 10) far = (acc / 100.0)*(max)*(swing / 100.0)*(accp / 100.0)*(golfer[playerNum].getPower() / 100.0);
            else if (holeLength > 120) far = (acc / 100.0)*(max)*(swing / 100.0)*(accd / 100.0)*(golfer[playerNum].getPower() / 100.0);
            else far = (acc / 100.0)*(max)*(swing / 100.0)*(acca / 100.0)*(golfer[playerNum].getPower() / 100.0);
            holeLength -= far;
            if(holeLength==-1) holeLength = 0;
            if (holeLength<0) { cout << "\nPassed the hole!\n"; holeLength *= -1; }
            shots++;
        } while (holeLength != 0);
        cout << "\n\nIn the hole!\n";
        cout << "\nPar: " << par << endl;
        cout << "Number of shots taken: " << shots << endl;
        cout << "Press '1' to continue... ";

        cin >> ch;
        cout << endl << endl << endl;
    } while (ch == '1');
}