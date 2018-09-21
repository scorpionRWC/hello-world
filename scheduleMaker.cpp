#include<iostream>
#include<string>
#include<iomanip>
using namespace std;

class Employee
{
private:
    string name;
    double wage;
    int maxWeeklyHours;
    string week[7] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
    bool hoursAvailable[7][24];
public:
    void addHour(int, int);
    void addWage(double);
    void addMaxWeeklyHours(int);
    void printHours();
    string getName();
    Employee();
    Employee(string);
    Employee(string, double);
    Employee(string, double, int);
    void addName(string);
};

Employee::Employee()
{
    name = "";
    for (int i = 0; i < 7; i++)
    {
        for (int j = 0; j < 24; j++)
        {
            hoursAvailable[i][j] = false;
        }
    }
}

Employee::Employee(string n)
{
    name = n;
    for (int i = 0; i < 7; i++)
    {
        for (int j = 0; j < 24; j++)
        {
            hoursAvailable[i][j] = false;
        }
    }
}

Employee::Employee(string n, double w)
{
    name = n;
    wage = w;
    for (int i = 0; i < 7; i++)
    {
        for (int j = 0; j < 24; j++)
        {
            hoursAvailable[i][j] = false;
        }
    }
}


Employee::Employee(string n, double w, int i)
{
    name = n;
    wage = w;
    maxWeeklyHours = i;
    for (int i = 0; i < 7; i++)
    {
        for (int j = 0; j < 24; j++)
        {
            hoursAvailable[i][j] = false;
        }
    }
}

void Employee::addWage(double w)
{
    wage = w;
}

void Employee::addName(string n)
{
    name = n;
}

string Employee::getName()
{
    return name;
}

void Employee::addMaxWeeklyHours(int i)
{
    maxWeeklyHours = i;
}

void Employee::addHour(int day, int hour)
{
    hoursAvailable[day][hour] = true;
}

void Employee::printHours()
{
    cout << name << ": $" << fixed << setprecision(2) << wage << endl;
    cout << setw(10) << "HOUR:";
    for (int w = 0; w < 24; w++)
    {
        cout << setw(3) << w;
    }
    cout << endl;
    for (int i = 0; i < 7; i++)
    {
        cout << setw(10) << week[i];
        for (int j = 0; j < 24; j++)
        {
            if (hoursAvailable[i][j] == true)
            {
                cout << setw(3) << j;
            }
            else
            {
                cout << setw(3) << "-";
            }
        }
        cout << endl;
    }
    cout << endl;
}

class Schedule
{
private:
    string cell[24][7];
    string week[7] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
    Employee staff[7];
public:
    void addSpot(int, int, string);
    Schedule();
    void printSchedule();
    void addStaff(Employee*);
    void createSchedule();
};

void Schedule::createSchedule()
{
    addSpot(9, 4, staff[3].getName());
}

void Schedule::addSpot(int hour, int day, string name)
{
    cell[hour][day] = name;
}

void Schedule::addStaff(Employee *s)
{
    for (int i = 0; i < 7; i++)
    {
        staff[i] = s[i];
    }
}

void Schedule::printSchedule()
{
    cout << "   ";
    for (int i = 0; i < 7; i++)
    {
        cout << setw(15) << week[i];
    }
    cout << endl;
    for (int i = 0; i < 24; i++)
    {
        cout << setw(3) << left << i;
        for (int j = 0; j < 7; j++)
        {
            cout << setw(15) << cell[i][j];
        }
        cout << endl;
    }
    cout << endl;
}

Schedule::Schedule()
{
    for (int i = 0; i < 7; i++)
    {
        for (int j = 0; j < 24; j++)
        {
            cell[i][j] = "";
        }
    }
}

int main()
{
    Employee * e1 = new Employee("Roger", 9.25);

    for (int i = 0; i < 7; i += 3)
    {
        for (int j = 7; j < 15; j++)
        {
            e1->addHour(i, j);
        }
    }

    Employee * e2 = new Employee("Smithy", 8.50);

    for (int i = 1; i < 7; i += 2)
    {
        for (int j = 12; j < 20; j++)
        {
            e2->addHour(i, j);
        }
    }

    Employee * e3 = new Employee("Gooch", 8.75);

    for (int i = 0; i < 7; i ++)
    {
        for (int j = 12; j < 22; j++)
        {
            e3->addHour(i, j);
        }
    }

    Employee * e4 = new Employee("Noicer", 10.50);

    for (int i = 0; i < 7; i++)
    {
        for (int j = 7; j < 15; j++)
        {
            e4->addHour(i, j);
        }
    }

    Employee * e5 = new Employee("Beeks", 12);

    for (int i = 0; i < 7; i++)
    {
        for (int j = 7; j < 22; j++)
        {
            e5->addHour(i, j);
        }
    }

    Employee * e6 = new Employee("Tigero", 8);

    for (int i = 5; i < 7; i++)
    {
        for (int j = 7; j < 22; j++)
        {
            e6->addHour(i, j);
        }
    }

    Employee * e7 = new Employee("Greggy", 9);

    for (int i = 0; i < 5; i++)
    {
        for (int j = 7; j < 22; j++)
        {
            e7->addHour(i, j);
        }
    }

    Employee staff[7] = { *e1, *e2, *e3, *e4, *e5, *e6, *e7 };
    for (int i = 0; i < 7; i++)
    {
        staff[i].printHours();
    }
    Schedule schedule;
    schedule.printSchedule();
    schedule.addStaff(staff);
    schedule.createSchedule();
    schedule.printSchedule();
    system("pause");
}