// BuiltIn.java -- the data structure for function closures

// Class BuiltIn is used for representing the value of built-in functions
// such as +.  Populate the initial environment with
// (name, new BuiltIn(name)) pairs.
// The object-oriented style for implementing built-in functions would be
// to include the Java methods for implementing a Scheme built-in in the
// BuiltIn object.  This could be done by writing one subclass of class
// BuiltIn for each built-in function and implementing the method apply
// appropriately.  This requires a large number of classes, though.
// Another alternative is to program BuiltIn.apply() in a functional
// style by writing a large if-then-else chain that tests the name of
// of the function symbol.
class BuiltIn extends Node {

    private Ident id;
    private Environment interaction_environment;

    public BuiltIn(Ident ident) {
        id = ident;
    }
    
    public BuiltIn(Environment env) {
        interaction_environment = env;
    }

    public Node getIdent() {
        return id;
    }

    // TODO: The method isProcedure() should be defined in
    // class Node to return false.
    @Override
    public boolean isProcedure() {
        return true;
    }

    @Override
    public void print(int n) {
        // there got to be a more efficient way to print n spaces
        for (int i = 0; i < n; i++) {
            System.out.print(' ');
        }
        System.out.println("#{Built-in Procedure");
        id.print(n + 3);
        for (int i = 0; i < n; i++) {
            System.out.print(' ');
        }
        System.out.println('}');
    }

    @Override
    public Node apply(Node args) {
        Node arg1 = args.getCar();
        Node arg2 = args.getCdr();
        if (arg1 == null || arg1.isNull()) {
            arg1 = new Nil();
        }
        if (arg2 == null || arg2.isNull()) {
            arg2 = new Nil();
        } else {
            arg2 = arg2.getCar();
        }

        //if else chain through the 20 or so builtin functions
        if (("b+").equals(id.getSymbol())) {
            if (arg1.isNumber() && arg2.isNumber()) {
                return new IntLit(((IntLit) arg1).getIntVal() + ((IntLit) arg2).getIntVal());
            } else {
                System.out.println("Error: Incorrect argument types for b+ method.");
                return new StrLit("");
            }
        } else if (("b-").equals(id.getSymbol())) {

            if (arg1.isNumber() && arg2.isNumber()) {
                return new IntLit(((IntLit) arg1).getIntVal() - ((IntLit) arg2).getIntVal());
            } else {
                 System.out.println("Error: Incorrect argument types for b- method.");
                return new StrLit("");
            }
        } else if (("b/").equals(id.getSymbol())) {

            if (arg1.isNumber() && arg2.isNumber()) {
                return new IntLit(((IntLit) arg1).getIntVal() / ((IntLit) arg2).getIntVal());
            } else {
                 System.out.println("Error: Incorrect argument types for b/ method.");
                return new StrLit("");
            }
        } else if (("b*").equals(id.getSymbol())) {

            if (arg1.isNumber() && arg2.isNumber()) {
                return new IntLit(((IntLit) arg1).getIntVal() * ((IntLit) arg2).getIntVal());
            } else {
                 System.out.println("Error: Incorrect argument types for b* method.");
                return new StrLit("");
            }
        } else if (("b<").equals(id.getSymbol())) {

           if (arg1.isNumber() && arg2.isNumber()) {
                return new BooleanLit(((IntLit) arg1).getIntVal() < ((IntLit) arg2).getIntVal());
            } else {
                System.out.println("Error: Incorrect argument types for b< method.");
            }
        } else if (("b=").equals(id.getSymbol())) {

            if (arg1.isNumber() && arg2.isNumber()) {
                return new BooleanLit(((IntLit) arg1).getIntVal() == ((IntLit) arg2).getIntVal());
            } else {
                System.out.println("Error: Incorrect argument types for b= method.");
            }
        } else if (("number?").equals(id.getSymbol())) {
            return new BooleanLit(arg1.isNumber());
        } else if (("eval").equals(id.getSymbol())) {
            return arg1;
        } else if (("null?").equals(id.getSymbol())) {
            return new BooleanLit(arg1.isNull());
        } else if (("car").equals(id.getSymbol())) {
            if (arg1.isNull()) {
                return arg1;
            }
            return arg1.getCar();
        } else if (("cdr").equals(id.getSymbol())) {
            if (arg1.isNull()) {
                return arg1;
            }
            return arg1.getCdr();
        } else if (("cons").equals(id.getSymbol())) {
            return new Cons(arg1, arg2);
        } else if (("pair?").equals(id.getSymbol())) {
            return new BooleanLit(arg1.isPair());
        } else if (("symbol?").equals(id.getSymbol())) {
            return new BooleanLit(arg1.isSymbol());
        } else if (("set-car!").equals(id.getSymbol())) {
            arg1.setCar(arg2);
            return arg1;
        } else if (("set-cdr!").equals(id.getSymbol())) {
            arg1.setCdr(arg2);
            return arg1;
        } else if (("procedure?").equals(id.getSymbol())) {
            return new BooleanLit(arg1.isProcedure());
        } else if (("newline").equals(id.getSymbol())) {
            return new StrLit("\n");
        } else if (("eq?").equals(id.getSymbol())) {

            if (arg1.isBoolean() && arg2.isBoolean()) {
                return new BooleanLit(((BooleanLit)arg1).getBooleanVal() == ((BooleanLit)arg2).getBooleanVal());
            } else if (arg1.isNumber() && arg2.isNumber()) {
                return new BooleanLit(((IntLit)arg1).getIntVal() == ((IntLit)arg2).getIntVal());
            } else if (arg1.isString() && arg2.isString()) {
                return new BooleanLit(((StrLit)arg1).getStrVal().equals(((StrLit)arg2).getStrVal()));
            } else if (arg1.isSymbol() && arg2.isSymbol()) {
                return new BooleanLit(((Ident)arg1).getSymbol().equals(((Ident)arg2).getSymbol()));
            } else if (arg1.isNull() && arg2.isNull()) {
                return new BooleanLit(true);
            } else if (arg1.isPair() && arg2.isPair()) {
                Node frontArgs = new Cons(arg1.getCar(), new Cons(arg2.getCar(), new Nil()));
                Node backArgs = new Cons(arg1.getCdr(), new Cons(arg2.getCdr(), new Nil()));
                return new BooleanLit(((BooleanLit)apply(frontArgs)).getBooleanVal() && ((BooleanLit)apply(backArgs)).getBooleanVal());
            }
            return new BooleanLit(false);
        } else if (("write").equals(id.getSymbol())) {
            arg1.print(0);
            return new StrLit("");
        } else if (("interaction-environment").equals(id.getSymbol())) {
            interaction_environment.print(0);
        } else if (("apply").equals(id.getSymbol())) {
            return arg1.apply(arg2);
        } else {
            System.out.println("BuiltIn function " + id.getSymbol()+ " not found.");
        }
        return null;
    }
}
//apply makes the environement, get params ready
//eval does evaulation of line using those things
//constants, special, cond, built-in, 