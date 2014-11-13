
class Regular extends Special {

    // TODO: Add any fields needed.
    public Regular(Node t) {
    }

    @Override
    void print(Node t, int n, boolean p) {
        Printer.printRegular(t, n, p);
    }

    @Override
    public Node eval(Node p, Environment env) {

        Node front, args;
        front = p.getCar();
        args = eval_list(p.getCdr(), env);

        while (front.isSymbol()) {
            front = env.lookup(front);
        }
        if (front == null || front.isNull()) {
            System.out.println("Undefined function");
            return new Nil();
        }
        if (front.isProcedure()) { //built-in
            return front.apply(args);
        } else {
            return front.eval(env).apply(args);
        }
    }

    public Node eval_list(Node p, Environment env) {
        if (p == null || p.isNull()) {
            Node list = new Cons(new Nil(), new Nil());
            return list;
        } else {
            Node arg1, rest;
            arg1 = p.getCar();
            rest = p.getCdr();

            if (arg1.isSymbol()) {
                arg1 = env.lookup(arg1);
            }
            if (arg1 == null || arg1.isNull()) {
                return new Nil();
            }
            Node list = new Cons(arg1.eval(env),eval_list(rest, env));
            return list;
        }
    }
}
