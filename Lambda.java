
class Lambda extends Special {

    public Lambda(Node t) {
    }

    @Override
    void print(Node t, int n, boolean p) {
        Printer.printLambda(t, n, p);
    }
    
    @Override
    public Node eval(Node p, Environment env) {
        return new Closure(p.getCdr(), env);
    }
}
