import sys
import time
import threading
import random

shared_number = 0
lock = threading.Lock()

class MyThreadForThreadCreation(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
    
    def run(self):
        x = 5
       

class MyThreadForThreadContextSwitch(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)

    def run(self):
        self.safe_increment()

    def safe_increment(self):
        global shared_number
        lock.acquire()
        try:
            shared_number = shared_number + 1
        finally:
            lock.release()


def allocate_dynamically():
    start_time = time.time_ns()
    x = [0] * 500
    end_time = time.time_ns()

    execution_time = end_time - start_time

    return execution_time


def access_dynamically():
    array = [i for i in range(500)]

    start_time = time.time_ns()
    for i in range(500):
        x = array[i]
    end_time = time.time_ns()

    execution_time = (end_time - start_time) / 500

    return execution_time


def thread_creation():
    start_time = time.time_ns()
    thread = MyThreadForThreadCreation()
    end_time = time.time_ns()

    # thread.start()
    # thread.join()
    execution_time = end_time - start_time

    return execution_time


def thread_context_switch(num_of_threads):

    start_time = time.time_ns()

    threads = [MyThreadForThreadContextSwitch() for _ in range(num_of_threads)]

    for thread in threads:
        thread.start()

    for thread in threads:
        thread.join()

    end_time = time.time_ns()

    execution_time = end_time - start_time

    return execution_time

def generate_random_list(size, min_value, max_value):
    random_list = [random.randint(min_value, max_value) for _ in range(size)]
    return random_list


def insert_into_list(random_list, value, position_to_insert):
    start_time = time.time_ns()
    random_list.insert(position_to_insert, value)
    end_time = time.time_ns()

    execution_time = end_time - start_time

    return execution_time


def remove_from_list(random_list, position):
    start_time = time.time_ns()
    del random_list[position]
    end_time = time.time_ns()

    execution_time = end_time - start_time

    return execution_time


def sort_list(random_list):
    start_time = time.time_ns()
    random_list.sort()
    end_time = time.time_ns()

    execution_time = end_time - start_time

    return execution_time


def runAll():
    print("allocateStatically", end = ' ')
    print("Impossible", end = ' ')

    print("\nallocateDynamically", end = ' ')
    for i in range(100):
        print(allocate_dynamically(), end = ' ')

    print("\naccessStatically", end = ' ')
    print("Impossible", end = ' ')

    print("\naccessDynamically", end = ' ')
    for i in range(100):
        print(access_dynamically(), end = ' ')

    print("\nthreadCreation", end = ' ')
    for i in range(100):
        print(thread_creation(), end = ' ')

    print("\nthreadContextSwitch", end  = ' ')
    num_of_threads = 100;
    for i in range(100):
        print(thread_context_switch(num_of_threads)/num_of_threads, end =' ')

    print("\ninsertIntoList", end  = ' ')
    for i in range(100):
        random_list = generate_random_list(10000, 0, 10000)
        print(insert_into_list(random_list, 23, 500), end = ' ')

    print("\nremoveFromList", end  = ' ')
    for i in range(100):
        random_list = generate_random_list(10000, 0, 10000)
        print(remove_from_list(random_list,  500), end = ' ')

    print("\nsortList", end = ' ')
    for i in range(100):
        random_list = generate_random_list(10000, 0, 10000)
        print(sort_list(random_list), end = ' ')


argc = len(sys.argv)

if argc != 2:
    print("Usage:", sys.argv[0], "<Command>")
    exit(1)

command = sys.argv[1]

match command:
    case "allocateStatically":
        print("Impossible")

    case "allocateDynamically":
       for i in range(100):
           print(allocate_dynamically(), end = ' ')

    case "accessStatically":
        print("Impossible")

    case "accessDynamically":
        for i in range(100):
            print(access_dynamically(), end = ' ')

    case "threadCreation":
        for i in range(100):
            print(thread_creation(), end = ' ')

    case "threadContextSwitch":
        num_of_threads = 100
        for i in range(100):
            print(thread_context_switch(num_of_threads) / num_of_threads , end = ' ')
    
    # case "threadMigration":
    #     print(command)
    #

    case "insertIntoList":
        for i in range(100):
            random_list = generate_random_list(10000, 0, 10000)
            print(insert_into_list(random_list, 23, 500), end = ' ')

    case "removeFromList":
        for i in range(100):
            random_list = generate_random_list(10000, 0, 10000)
            print(remove_from_list(random_list,  500), end = ' ')

    case "sortList":
        for i in range(100):
            random_list = generate_random_list(10000, 0, 10000)
            print(sort_list(random_list), end = ' ')

    case "runAll":
        runAll()

    case default:
        print(command)