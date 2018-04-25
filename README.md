# Algorithm-part-1
## Week1-Percolation(18.3.1)
The specification of assignment:(http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)
### Attention：
#### 1、Question about backwash
Except for the array uf[],we should build an array named uf_backwash[],which is served for the function of isFull().
#### 2、Question about array size
Except for the real pores(n*n), we need two(uf) or one(uf_backwash) pores for the beginning (and the end).As function of ifFull() is about the present pore back to the beginning.
#### 3、Corner case test
If the Array size is set up as (n*n) or (n*n+1),when n equals to 1 and opensitesnumber equals to 0,the answer of function percolates() will be wrong as "true".Because the beginning pore and the ending pore all union the the same only real pore.
#### 4、Question of exception
About the function's parameter such as "public boolean isFull(int row,int col)",we should throw java.lang.IllegalArgumentException("..."),rather than java.lang.IndexOutOfBoundsException(). 

## Week2-Deque(18.3.9)
The specification of assignment:(http://coursera.cs.princeton.edu/algs4/assignments/queues.html)
### Attention:
#### 1、Data structure 
Deque is linklist and need two linknode objects(first,last) as pointer.Randomizedqueue is array and do not need extra pointer object.
#### 2、RandomizedQueue
RandomizedQueue just like a data structure of bag,so the enqueue and dequeue operations all can be done at one end of the array.
#### 3、Permutation
In the specification of assignment,it needs variable n,which I think is non-essential(use n:Permutation2.java,not use n:Permutation.java).

## Week3-Collinear(18.4.4)
(The first time try programming by myself at the beginning of programming)
The specification of assignment:(http://coursera.cs.princeton.edu/algs4/assignments/collinear.html)
### Attention:
#### 1、Equals()
If the object is String,a.equals(b) compares the content of objects,but the '==' compares the first address of the objects.And for other type,both compares the first address of objects.
#### 2、Time complexity
The time complexity of the class BruteCollinearPoints is n<sup>4</sup>,but the FastCollinearPoints requires n<sup>2</sup>log<sup>n</sup>.so for the former,we can use the four time circulation,but for the latter,we must use binary search instead of other general ways to avoid repeating counting.
#### 3、Comparator
For the function of slopeOrder(),we have to return Comparator<>,so we new object in it for returning and directly override the function compare() in the slopeOrder() rather than do it outside the slopeOrder(),for we have to consider the parameters. 
#### 4、The capacity of array
When we do not know the capacity of the array,we always use ArrayList to reserve data.And to deliver the objects from constructor to the function segments(),in this way,we do not need to call segments() every time we want to add object in the constructor,for the objects have been saved in the ArrayList ([1]compare-we can use toString+equals in some cases.[2]print、search-use iterator).
#### 5、The longest line
If the points in the line is p-q-r-s,we need to add two points to ensure the longest line,that is p-s or s-p,otherwise the drawing results will make mistake.
#### 6、Sorting method
We can use Arrays.sort(...) to sort array or Collections.sort(Arraylist, new Comparator<...>() for sorting. 

## Week4-8puzzle(18.4.25)
The specification of assignment:(http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html)
### Attention:
#### 1、Swap()
If you change the authority of function swap() to public in class Board,the assignment submitted system will reminder that your API is wrong,and you should alter it to private.
#### 2、Manhattan()
The distance of manhattan is the differentials between current position and it's right place for all the points,so we can use two arrarys to save the right position of x/y for every points,and calculate the amnhattan distance. 
#### 3、Neighbors()
When you swap two points to get the neighbors,you need to use swap twice to restore arrary,for you have to use the same array for four times.
#### 4、Class of SearchNode
We need to construct an inner class named SearchNode which conclude moves and priority to choice the minimum manhattan distance every time,construct the preSearchNode to find the previous searchnode when we need to trace the moving process in the function of solution().And there are two types of constructor,and the former is for the ancestor board and it's twin,the latter is for other searchnodes.The boolean variable isInitial is served for function isSolvable().
#### 5、
