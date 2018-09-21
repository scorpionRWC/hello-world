#include<iostream>
#include<ctime>
#include"genericss.h"
using namespace std;

int main()
{
    const int AMOUNT = 10;
    int nums[AMOUNT];
    srand((unsigned)time(0));
    int *test;

    for (int i = 0; i < AMOUNT; i++)
    {
        nums[i] = rand() % AMOUNT + 1;
        cout << nums[i] << endl;
    }
    test = mergeSort(nums, AMOUNT);
    cout << endl;
    for (int i = 0; i < AMOUNT; i++)
    {
        cout << test[i] << endl;
    }
    cout << endl;
    int value = 5;
    cout << "Index of value '" << value << "' is: " << binarySearch(test, AMOUNT, value) << endl;
    system("pause");
}