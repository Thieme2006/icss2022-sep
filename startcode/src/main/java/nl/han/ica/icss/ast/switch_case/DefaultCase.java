package nl.han.ica.icss.ast.switch_case;

import nl.han.ica.icss.ast.ASTNode;

import java.util.ArrayList;
import java.util.Objects;

public class DefaultCase extends ASTNode {
    public ArrayList<ASTNode> body = new ArrayList<>();

    public DefaultCase() { }

    public DefaultCase(ArrayList<ASTNode> body) {
        this.body = body;
    }

    @Override
    public String getNodeLabel() {
        return "Default";
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        return this.body;
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        DefaultCase defaultCase = (DefaultCase) o;
        return Objects.equals(body, defaultCase.body);
    }
}
