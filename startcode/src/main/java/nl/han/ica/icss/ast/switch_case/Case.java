package nl.han.ica.icss.ast.switch_case;

import nl.han.ica.icss.ast.ASTNode;

import java.util.ArrayList;
import java.util.Objects;

public class Case extends ASTNode {

    public ASTNode condition;
    public ArrayList<ASTNode> body = new ArrayList<>();

    public Case() { }

    public Case(ASTNode condition, ArrayList<ASTNode> body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "case";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();
        children.add(this.condition);
        children.addAll(this.body);
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        Case caseNode = (Case) o;
        return Objects.equals(condition, caseNode.condition) &&
                Objects.equals(body, caseNode.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, body);
    }
}
