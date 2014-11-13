
class Let extends Special {

    public Let(Node t) {
    }

    @Override
    void print(Node t, int n, boolean p) {
        Printer.printLet(t, n, p);
    }
    
    @Override
    public Node eval(Node p, Environment env) {
        Node args, exp;
        Environment local = new Environment(env);
        args = p.getCdr().getCar(); 
        exp = p.getCdr().getCdr().getCar();
        args = eval_body(args, local);
        return exp.eval(local);
    }
    
    public Node eval_body(Node p, Environment env) {
    if(p == null || p.isNull())
    {
	Node emptyList = new Cons(new Nil(), new Nil());
	return emptyList;
    }else
    {
	Node arg, exp, rest;
	arg = p.getCar().getCar();
	exp = p.getCar().getCdr().getCar();
	rest = p.getCdr();

	if(arg.isSymbol())
	{ //define var in local scope
	    env.define(arg,exp.eval(env));
	    return eval_body(rest, env);
	}else if (arg.isPair())//found exp to be evaluated, exp->eval(env)
	{
	    return arg.eval(env);
	}else
	{
	    return new Nil();
	}
    }
}

}
