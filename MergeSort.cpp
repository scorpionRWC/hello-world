#include <iostream>
#include <ctime>
#include <Windows.h>
using namespace std;

void Merge(int A[], int left_point, int mid_point, int right_point)
{
    /*cout << "left_point = " << left_point << endl;
    cout << "mid_point = " << mid_point << endl;
    cout << "right_point = " << right_point << endl;*/

    int left_array_size = mid_point - left_point + 1;
    int right_array_size = right_point - mid_point;

    /*cout << "left_size = " << left_array_size << endl;
    cout << "right_size = " << right_array_size << endl;*/

    int *L = new int[left_array_size + 1];
    int *R = new int[right_array_size + 1];
    for (int i = 1; i <= left_array_size; i++)
    {
        L[i] = A[left_point + i - 1];
      //  cout << A[left_point + i - 1] << "*L ";
    }
    //cout << endl;
    for (int j = 1; j <= right_array_size; j++)
    {
        R[j] = A[mid_point + j];
       // cout << A[mid_point + j] << "*R ";
    }
    //cout << endl << endl;
    L[left_array_size + 1] = MAXINT;
    R[right_array_size + 1] = MAXINT;
    int i = 1;
    int j = 1;
    for (int k = left_point; k <= right_point; k++)
    {
        if (L[i] <= R[j])
        {
            A[k] = L[i];
            i++;
        }
        else
        {
            A[k] = R[j];
            j++;
        }
    }
}

void Merge_Sort(int A[], int left_point, int right_point)
{
    if (left_point < right_point)
    {
        int mid_point = (left_point + right_point) / 2;
        Merge_Sort(A, left_point, mid_point);
        Merge_Sort(A, mid_point + 1, right_point);
        Merge(A, left_point, mid_point, right_point);
    }
}

int main()
{
    const int n = 1000;
    int arr[n];
    srand((unsigned int)time(NULL));
    cout << "merge sort of size n = " << n << endl;
    for (int k = 0; k < n; k++)
    {
        arr[k] = rand() % n;
    }
    for (int k = 0; k < n; k++)
    {
        //cout << arr[k] << " "; // array before merge sort
    }
    cout << endl;
    cout << "starting algorithm...\n";
    int startTime = GetTickCount();
    Merge_Sort(arr, 0, n - 1);
    int endTime = GetTickCount();
    cout << "done!\n";
    cout << "runtime: " << double(endTime - startTime)*0.001 << " seconds" << endl;
    for (int k = 0; k < n; k++)
    {
        //cout << arr[k] << " "; // array after merge sort
    }
    getchar();
    return 0;
}