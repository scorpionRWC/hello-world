#include<iostream>
#include<string>
#include<cmath>
using namespace std;

class Lex
{
private:
    int p;  // position of current character being read in the string
public:
    string digitToken(string);
    string nextToken(string);
    double lex(string, double);
    double stringToDigit(string);
    double expression(string, double);
    double factor(string, double);
    double value(string, double);
    Lex();
};

Lex::Lex()
{
    p = 0;
}

double Lex::stringToDigit(string str)
{
    double num = 0;
    bool isDouble = false;
    int decPos; // position of the decimal
    for (unsigned int i = 0; i < str.length(); i++)
    {
        if (str.at(i) == '.')
        {
            decPos = i;
            isDouble = true;
        }
    }
    if (!isDouble)
    {
        for (unsigned int i = 0; i < str.length(); i++)
        {
            num += (str.at(i) - 48) * (pow(10, str.length() - i - 1));
        }
    }
    else
    {
        for (int i = 0; i < decPos; i++)
        {
            num += (str.at(i) - 48) * (pow(10, decPos - i - 1));
        }
        for (unsigned int i = decPos + 1; i < str.length(); i++)
        {
            //cout << "Pow is " << (pow(10, i - decPos)) << endl;
            num += (str.at(i) - 48) / (pow(10, i - decPos));
        }
    }
    //cout << endl << "Number is " << num << endl;
    return num;
}

string Lex::digitToken(string d)
{
    string number = "";
    for (unsigned int i = p; i < d.length(); i++, p++)
    {
        if ((d.at(i) - 48 >= 0 && d.at(i) - 48 <= 9) || d.at(i) == '.')
        {
            number += d.at(i);
        }
        else break;
    }
    return number;
}

string Lex::nextToken(string s)
{
    string token = "";
    for (unsigned int i = p; i < s.length(); i++, p++)
    {
        if (s.at(i) == '(')
        {
            //cout << "\nHere1\n";
            token = "(";
            return token;
        }
        else if (s.at(i) == '+')
        {
            token = "+";
            return token;
        }
        else if (s.at(i) == '-')
        {
            token = "-";
            return token;
        }
        else if (s.at(i) == '*')
        {
            token = "*";
            return token;
        }
        else if (s.at(i) == '/')
        {
            token = "/";
            return token;
        }
        else if (s.at(i) == ')')
        {
            token = ")";
            return token;
        }
        else if (s.at(i) - 48 >= 0 && s.at(i) - 48 <= 9)
        {
            token = digitToken(s);
            return token;
        }
        else if (s.at(i) != ' ')
        {
            token = "Syntax Error";
            return token;
        }
    }
    return token;
}

double Lex::expression(string s, double d)
{
    string t;
    d = value(s, d);
    do
    {
        t = nextToken(s);
        //cout << "\nExpress Token: " << t << endl;
        if (t == "+")
        {
            p++;
            d += value(s, d);
        }
        else if (t == "-")
        {
            p++;
            d -= value(s, d);
        }
    } while (t == "+" || t == "-");
    return d;
}

double Lex::factor(string s, double d)
{
    //string m;
    //cin >> m;
    string t = nextToken(s);
    /*cout << "\nFactor Token: " << t << endl;
    if (t == "")
    {
        cout << "\nHeh\n";
        return d;
    }
    else */if (t.at(0) - 48 >= 0 && t.at(0) - 48 <= 9)
    {
        d = stringToDigit(t);
    }
    else if (t == "(")
    {
        p++;
        do
        {
            //p++;
            d = expression(s, d);
            p--;
        } while (nextToken(s) != ")");
    }
    /*else if (t == ")")
    {
        p++;
        //return d;
    }*/
    return d;
}

double Lex::value(string s, double d)
{
    string t;
    d = factor(s, d);
    do
    {
        t = nextToken(s);
      //  cout << "\nValue Token: " << t << endl;
        if (t == "*")
        {
            p++;
            d *= factor(s, d);
        }
        else if (t == "/")
        {
            p++;
            d /= factor(s, d);
        }
        else if (t == "Syntax Error")
        {
            cout << "\nSyntax Error\n";
            return -1;
        }
    } while (t == "*" || t == "/");
    return d;
}

double Lex::lex(string in, double answer)
{
    return expression(in, answer);
}

int main()
{
    while (true)
    {
        Lex L;
        string input;
        cin >> input;
        cout << L.lex(input, 0) << endl;
    }
}