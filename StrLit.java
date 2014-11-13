
class StrLit extends Node {

    private String strVal;

    public StrLit(String s) {
        strVal = s;
    }

    @Override
    public void print(int n) {
        Printer.printStrLit(n, strVal);
    }

    public String getStrVal() {
        return strVal;
    }

    @Override
    public boolean isString() {
        return true;
    }
;
}
