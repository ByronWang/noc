package noc.reflect;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.Cast;
import javassist.expr.ConstructorCall;
import javassist.expr.Expr;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.Handler;
import javassist.expr.Instanceof;
import javassist.expr.MethodCall;
import javassist.expr.NewArray;
import javassist.expr.NewExpr;

public class InspectExprEditor extends ExprEditor
{
    @Override public void edit(Cast c) throws CannotCompileException {
		// TODO Auto-generated method stub
		super.edit(c);
	}
	@Override public void edit(ConstructorCall c) throws CannotCompileException {
		// TODO Auto-generated method stub
		super.edit(c);
	}
	@Override public void edit(Handler h) throws CannotCompileException {
		// TODO Auto-generated method stub
		super.edit(h);
	}
	@Override public void edit(Instanceof i) throws CannotCompileException {
		// TODO Auto-generated method stub
		super.edit(i);
	}
	@Override public void edit(NewArray a) throws CannotCompileException {
		// TODO Auto-generated method stub
		super.edit(a);
	}
	private String from(Expr expr) {
        CtBehavior source = expr.where();
        return " in " + source.getName() + "(" + expr.getFileName() + ":" +
            expr.getLineNumber() + ")";
    }
    public void edit(FieldAccess arg) {
    	System.out.print(from(arg) + " :");
        String dir = arg.isReader() ? "read" : "write";
        System.out.println(" " + dir + " of " + arg.getClassName() +
            "." + arg.getFieldName() + from(arg));
    }
    public void edit(MethodCall arg) {
    	System.out.print(from(arg) + " :");
        System.out.println(" call to " + arg.getClassName() + "." +
            arg.getMethodName() + from(arg));
    }
    public void edit(NewExpr arg) {
    	System.out.print(from(arg) + " :");
        System.out.println(" new " + arg.getClassName() + from(arg));
    }
}