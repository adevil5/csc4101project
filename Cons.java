class Cons extends Node {
    private Node car;
    private Node cdr;
    private Special form;
    private Ident sCar;
  
    // parseList() `parses' special forms, constructs an appropriate
    // object of a subclass of Special, and stores a pointer to that
    // object in variable form.  It would be possible to fully parse
    // special forms at this point.  Since this causes complications
    // when using (incorrect) programs as data, it is easiest to let
    // parseList only look at the car for selecting the appropriate
    // object from the Special hierarchy and to leave the rest of
    // parsing up to the interpreter.
    private void parseList() {
    	
    	  if (!car.isSymbol()) {
            form = new Regular(this);
        }
    	  else {
    	  	sCar=(Ident)car;
    	  	String s=sCar.getName();

    	    if (s.equalsIgnoreCase("quote")) {
                  form = new Quote(this);
              }
    	    else if (s.equalsIgnoreCase("lambda")) {
                  form = new Lambda(this);
              }
    	    else if (s.equalsIgnoreCase("begin")) {
                  form = new Begin(this);
              }
    	    else if (s.equalsIgnoreCase("if")) {
                  form = new If(this);
              }
    	    else if (s.equalsIgnoreCase("let")) {
                  form = new Let(this);
              }
    	    else if (s.equalsIgnoreCase("cond")) {
                  form = new Cond(this);
              }
    	    else if (s.equalsIgnoreCase("define")) {
                  form = new Define(this);
              }
    	    else if (s.equalsIgnoreCase("set!")) {
                  form = new Set(this);
              }
    	    else {
                  form = new Regular(this);
              }
    	  }
    }
    // TODO: Add any helper functions for parseList as appropriate.

    public Cons(Node car, Node cdr) {
	this.car = car;
	this.cdr = cdr;
	parseList();
    }

    @Override
    public Node getCar() {
        return this.car;
    }

    @Override
    public Node getCdr() {
        return this.cdr;
    }
    
    @Override
    public void setCar(Node p){
        this.car = p;
    }
    
    @Override
    public void setCdr(Node p){
        this.cdr = p;
    }
    
    @Override
    void print(int n) {
	form.print(this, n, false);
    }

    @Override
    void print(int n, boolean p) {
	form.print(this, n, p);
    }
    
    @Override
    public boolean isPair() {
        return true;
    }

}
