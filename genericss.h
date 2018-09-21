#ifndef _GENERICSS_H_INCLUDED_
#define _GENERICSS_H_INCLUDED_

template<typename T>
T* mergeSort(T list[], int length)
{
    if (length == 1)
    {
        return list;
    }
    int middle = length / 2;
    T* left = new T[middle];
    T* right = new T[length - middle];
    for (int i = 0; i < middle; i++)
    {
        left[i] = list[i];
    }
    int rightLength = 0;
    for (int i = 0; i < length - middle; i++)
    {
        right[i] = list[i + middle];
        rightLength++;
    }
    T* sortedLeft = mergeSort(left, middle);
    T* sortedRight = mergeSort(right, rightLength);
    T* mergedList = new int[middle + rightLength];
    for (int i = 0, leftPos = 0, rightPos = 0; i < middle + rightLength; i++)
    {
        if (rightPos >= rightLength)
        {
            mergedList[i] = sortedLeft[leftPos++];
        }
        else if (leftPos >= middle)
        {
            mergedList[i] = sortedRight[rightPos++];
        }
        else if (sortedLeft[leftPos] < sortedRight[rightPos])
        {
            mergedList[i] = sortedLeft[leftPos++];
        }
        else
            mergedList[i] = sortedRight[rightPos++];
    }
    return mergedList;
}

template<typename T>
int binarySearch(T list[], int length, T value)
{
    int middle = length / 2;
    return _binarySearch(list, 0, length, middle, value);
}

template<typename T>
int _binarySearch(T list[], int first, int length, int middle, T value)
{
    if (length == 1 && list[middle] != value)
    {
        return -1;
    }
    else if (value == list[middle])
    {
        return middle;
    }
    else if (value < list[middle])
    {
        length = middle - first;
        middle /= 2;
        return _binarySearch(list, first, length, middle, value);
    }
    else
    {
        length -= middle;
        first = middle;
        middle += length / 2;
        return _binarySearch(list, first, length, middle, value);
    }
}

#endif