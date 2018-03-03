# Algorithm-part-1
## Week1-Percolation
The specification of assignment:(http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)
### Attention：
#### 1、Question about backwash
Except for the array uf[],we should build an array named uf_backwash[],which is served for the function of isFull().
#### 2、Question about array size
Except for the real pores(n*n), we need two(uf) or one(uf_backwash) pores for the beginning (and the end).As function of ifFull() is about the present pore back to the beginning.
#### 3、Corner case test
If the Array size is set up as (n*n) or (n*n+1),when n equals to 1 and opensitesnumber equals to 0,the answer of function percolates() will be wrong as true.Because the beginning pore and the ending pore all union the the same only real pore.
#### 4、Question of exception
About the function's parameter such as "public boolean isFull(int row,int col)",we should throw java.lang.IllegalArgumentException("..."),rather than java.lang.IndexOutOfBoundsException(). 
