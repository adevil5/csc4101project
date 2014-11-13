
class Cond extends Special {

    // TODO: Add any fields needed.
    // TODO: Add an appropriate constructor.
    public Cond(Node t) {
    }

    @Override
    void print(Node t, int n, boolean p) {
        Printer.printCond(t, n, p);
    }
    
    @Override
    public Node eval(Node p, Environment env) {
        Node exp;
        exp = p.getCdr();       

        while((!(exp.getCar().getCar().eval(env).isBoolean())) 
                && (!((BooleanLit)(exp.getCar().getCar().eval(env))).getBooleanVal()) 
                && (!exp.isNull())) 
        {
            exp = exp.getCdr();
        }
        if(exp.isNull()) {
            return new Nil();
        }
        else {
            return (exp.getCar().getCdr().getCar().eval(env));
        }
    }
}
