#include <iostream>
#include <ctime>
#include <Windows.h>
using namespace std;

int main()
{
    const int n = 1000;
    int arr[n];
    srand((unsigned int) time(NULL));
    cout << "insertion sort of size n = " << n << endl;
    for (int k = 0; k < n; k++)
    {
        arr[k] = rand() % n;
    }
    for (int k = 0; k < n; k++)
    {
        //cout << arr[k] << " "; // array before insertion sort
    }
    cout << endl;
    cout << "starting algorithm...\n";
    int startTime = GetTickCount();
    for (int j = 1; j < n; j++)
    {
        int key = arr[j];
        int i = j - 1;
        while (i >= 0 && arr[i] > key)
        {
            arr[i + 1] = arr[i];
            i--;
        }
        arr[i + 1] = key;
    }
    int endTime = GetTickCount();
    cout << "done!\n";
    cout << "runtime: " << double(endTime - startTime)*0.001 << " seconds" << endl;
    for (int k = 0; k < n; k++)
    {
        //cout << arr[k] << " "; // array after insertion sort
    }
    getchar();
    return 0;
}