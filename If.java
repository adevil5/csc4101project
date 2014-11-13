
class If extends Special {

    public If(Node t) {
    }

    @Override
    void print(Node t, int n, boolean p) {
        Printer.printIf(t, n, p);
    }
    
    @Override
    public Node eval(Node p, Environment env) {
        Node cond, exp;
        cond = p.getCdr().getCar();
        if(((BooleanLit)cond.eval(env)).getBooleanVal())
        {
            exp = p.getCdr().getCdr().getCar();
            return exp.eval(env);
        } else if(!(p.getCdr().getCdr().getCdr()).isNull())//Make sure there is an Else
        {
            exp = p.getCdr().getCdr().getCdr().getCar();

            return exp.eval(env);
        }
        System.out.println("ERROR: No Else Expression.");
        return new Nil();
    }
}
