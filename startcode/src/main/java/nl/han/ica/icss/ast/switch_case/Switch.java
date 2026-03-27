package nl.han.ica.icss.ast.switch_case;

import nl.han.ica.icss.ast.ASTNode;
import nl.han.ica.icss.ast.Expression;

import java.util.ArrayList;

public class Switch extends ASTNode {

    public Expression condition;
    public ArrayList<Case> cases = new ArrayList<>();
    public DefaultCase defaultCase; // Kan null zijn.

    public Switch() { }

    public Switch(Expression condition, ArrayList<Case> cases) {
        this.condition = condition;
        this.cases = cases;
    }

    public Switch(Expression condition, ArrayList<Case> cases, DefaultCase defaultCase) {
        this.condition = condition;
        this.cases = cases;
        this.defaultCase = defaultCase;
    }

    @Override
    public String getNodeLabel() {
        return "Switch";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(condition);
        children.addAll(cases);
        if(defaultCase != null) children.add(defaultCase);

        return children;
    }
}
