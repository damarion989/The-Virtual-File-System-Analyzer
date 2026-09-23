Phase 1

The base case is when the program reaches a file. Since it is one file, the method returns 1.

The general case is when the program reaches a folder. It goes through everything inside that folder and adds up the files it finds. Each time the method calls itself, it is checking something smaller inside the original folder. Eventually it reaches files, so the method stops.

Phase 2

If a folder only has empty folders inside it, the method will not find any files. In that case, it returns null, which means no file was found.

Before comparing file sizes, the code checks that the returned file is not null. That way, the program does not try to get the size of something that does not exist and crash.

Phase 3

I think the recursive version is easier to understand because the method keeps calling itself to look inside each folder. It also takes less code.

The iterative version works too, but it uses a stack and has more steps to keep up with. Recursion uses Java’s call stack automatically, while the iterative version uses a stack created in the code.

Phase 4

If a folder was accidentally added inside itself, both methods would keep checking the same folder over and over.

The recursive method would eventually cause a StackOverflowError because it keeps making method calls. The iterative method would keep putting the same folder on the stack until it ran out of memory. This happens because the problem is no longer getting smaller each time.
