// Special.h -- the parse tree node data structure for special forms
// hierarchy.  We'll discuss some of them in class.  The easiest solution
// is to not add any fields and to use empty constructors.

abstract class Special {
    abstract void print(Node t, int n, boolean p);
    abstract Node eval(Node p, Environment env);
}

