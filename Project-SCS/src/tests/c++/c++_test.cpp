#include <iostream>
#include <chrono>
#include <cstring>
#include <thread>
#include <pthread.h>
#include <fstream>
#include <mutex>
#include <list>
#include <iomanip>

using namespace std;
using namespace std::chrono;

ofstream g("src/tests/results/helloWorld.txt");

std::mutex mutex_;
int sharedNumber = 0;

class MyClass
{
public:
    void taskForContextSwitch(const std::string &message)
    {
        std::unique_lock<std::mutex> lock(mutex_);
        sharedNumber++;
    }
};

void taskForThreadCreation()
{
    int x = 5;
}

double allocateStatically()
{
    auto startTime = high_resolution_clock::now();
    for (int i = 0; i < 500; i++)
    {
        int x = 5;
    }
    auto endTime = high_resolution_clock::now();

    auto executionTime = duration_cast<nanoseconds>(endTime - startTime);

    return executionTime.count();
}

double accessStatically()
{
    int localVariable = 23;
    int value = 0;
    auto startTime = high_resolution_clock::now();
    for (int i = 0; i < 500; i++)
    {
        value = localVariable;
    }
    auto endTime = high_resolution_clock::now();

    auto executionTime = duration_cast<nanoseconds>(endTime - startTime);

    return executionTime.count();
}

double allocateDynamically()
{
    auto startTime = high_resolution_clock::now();
    int *array = (int *)malloc(500 * sizeof(int));
    auto endTime = high_resolution_clock::now();

    auto executionTime = duration_cast<nanoseconds>(endTime - startTime);

    return executionTime.count();
}

double accessDynamically()
{
    int x = 0;

    int *array = (int *)malloc(500 * sizeof(int));
    if (array == NULL)
    {
        return -1;
    }

    for (int i = 0; i < 500; i++)
    {
        array[i] = i;
    }

    auto startTime = high_resolution_clock::now();
    for (int i = 0; i < 500; i++)
    {
        x = array[i];
    }
    auto endTime = high_resolution_clock::now();
    auto executionTime = duration_cast<nanoseconds>((endTime - startTime) / 500);

    return executionTime.count();
}

double threadCreation()
{
    auto startTime = high_resolution_clock::now();
    std::thread thread_obj(taskForThreadCreation);
    auto endTime = high_resolution_clock::now();

    auto executionTime = duration_cast<nanoseconds>(endTime - startTime);

    // Wait for the thread to finish
    thread_obj.join();

    return executionTime.count();
}

double threadContextSwitch(int numOfThreads)
{
    MyClass obj;

    auto startTime = high_resolution_clock::now();
    std::thread threads[numOfThreads];

   // Launch threads in a loop
   for (int i = 0; i < numOfThreads; ++i) {
       threads[i] = std::thread(&MyClass::taskForContextSwitch, &obj, "t" + std::to_string(i + 1));
   }

   // Wait for all threads to finish
   for (int i = 0; i < numOfThreads; ++i) {
       threads[i].join();
   }


    auto endTime = high_resolution_clock::now();

    auto executionTime = duration_cast<nanoseconds>(endTime - startTime);

    return executionTime.count();
}

list<int> generateRandomList(int size) {
    list<int> randomList;

    // Seed the random number generator with the current time
    srand(time(NULL));

    for (int i = 0; i < size; i++) {
        // Generate a random integer and add it to the array
        randomList.push_back(rand() % size);
    }

    return randomList;
}

double insertIntoList(list<int> randomList, int value, int positionToInsert) {
    list<int>::iterator it = randomList.begin();
    advance(it, positionToInsert);  // Move iterator to position

    auto startTime = high_resolution_clock::now();
    randomList.insert(it, value);
    auto endTime = high_resolution_clock::now();

    auto executionTime = duration_cast<nanoseconds>(endTime - startTime);

    return executionTime.count();
}

double removeFromList(list<int> randomList, int position) {
    list<int>::iterator it = randomList.begin();
    advance(it, position);

    auto startTime = high_resolution_clock::now();
    randomList.erase(it);
    auto endTime = high_resolution_clock::now();

    auto executionTime = duration_cast<nanoseconds>(endTime - startTime);

    return executionTime.count();
}

double sortList(list<int> randomList) {
    auto startTime = high_resolution_clock::now();
    randomList.sort();
    auto endTime = high_resolution_clock::now();

    auto executionTime = duration_cast<nanoseconds>(endTime - startTime);

    return executionTime.count();
}

void runAll()
{
    cout << "allocateStatically ";
    for (int i = 0; i < 100; i++)
    {
        cout << allocateStatically() / 500 << " ";
    }
    cout << "\nallocateDynamically ";
    for (int i = 0; i < 100; i++)
    {
        cout << allocateDynamically() << " ";
    }
    cout << "\naccessStatically ";
    for (int i = 0; i < 100; i++)
    {
        cout << accessStatically() / 500 << " ";
    }
    cout <<"\naccessDynamically ";
    for (int i = 0; i < 100; i++)
    {
        cout << accessDynamically() << " ";
    }
    cout << "\nthreadCreation ";
    for (int i = 0; i < 100; i++)
    {
        cout << threadCreation() << " ";
    }
    cout << "\nthreadContextSwitch ";
    int numOfThreads = 100;
    for (int i = 0; i < 100; i++)
    {
        cout << threadContextSwitch(numOfThreads)/numOfThreads << " ";
    }
    cout << "\ninsertIntoList ";
    for (int i = 0; i < 100; i++)
    {
        list<int> randomList = generateRandomList(10000);
        cout << insertIntoList(randomList, 23, 500) << " ";
    }
    cout << "\nremoveFromList ";
    for (int i = 0; i < 100; i++)
    {
        list<int> randomList = generateRandomList(10000);
        cout << removeFromList(randomList, 500) << " ";
    }
    cout << "\nsortList ";
    for (int i = 0; i < 100; i++)
    {
        list<int> randomList = generateRandomList(10000);
        cout << sortList(randomList) << " ";
    }
}

int main(int argc, char **argv)
{
    if (argc != 2)
    {
        cout << "Usage:" << argv[0] << "<command>";
        exit(1);
    }

    char command[100];
    strcpy(command, argv[1]);

    if (strcmp(command, "allocateStatically") == 0)
    {
        for (int i = 0; i < 100; i++)
        {
            cout << allocateStatically() / 500 << " ";
        }
    }
    else if (strcmp(command, "allocateDynamically") == 0)
    {
        for (int i = 0; i < 100; i++)
        {
            cout << allocateDynamically() << " ";
        }
    }
    else if (strcmp(command, "accessStatically") == 0)
    {
        for (int i = 0; i < 100; i++)
        {
            cout << accessStatically() / 500 << " ";
        }
    }
    else if (strcmp(command, "accessDynamically") == 0)
    {
        for (int i = 0; i < 100; i++)
        {
            cout << accessDynamically() << " ";
        }
    }
    else if (strcmp(command, "threadCreation") == 0)
    {
        for (int i = 0; i < 100; i++)
        {
            cout << threadCreation() << " ";
        }
    }
    else if (strcmp(command, "threadContextSwitch") == 0)
    {
        int numOfThreads = 100;
        for (int i = 0; i < 100; i++)
        {
            cout << threadContextSwitch(numOfThreads) / numOfThreads << " ";
        }
    }
    else if(strcmp(command, "insertIntoList") == 0){
        for (int i = 0; i < 100; i++)
        {
            list<int> randomList = generateRandomList(10000);
            cout << insertIntoList(randomList, 23, 500) << " ";
        }
    } else if(strcmp(command, "removeFromList") == 0){
        for (int i = 0; i < 100; i++)
        {
            list<int> randomList = generateRandomList(10000);
            cout << removeFromList(randomList, 500) << " ";
        }
    } else if(strcmp(command, "sortList") == 0){
        cout << fixed << setprecision(0);
        for (int i = 0; i < 100; i++)
        {
            list<int> randomList = generateRandomList(10000);
            cout << sortList(randomList) << " ";
        }
    }
    else if (strcmp(command, "runAll") == 0)
    {
        runAll();
    }
    else
    {
        cout << "Invalid command";
    }

    return 0;
}
