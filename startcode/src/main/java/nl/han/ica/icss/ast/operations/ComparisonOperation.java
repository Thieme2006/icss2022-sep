package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;

public class ComparisonOperation extends Operation {

    public Operator operator;

    public ComparisonOperation(Operator operator) {
        this.operator = operator;
    }

    @Override
    public String getNodeLabel() {
        return switch (this.operator) {
            case EQ -> "Equals (==)";
            case NEQ -> "NotEquals (!=)";
            case GT -> "GreaterThen (>)";
            case GTE -> "GreaterThenOrEquals (>=)";
            case ST -> "SmallerThen (<)";
            case STE -> "SmallerThenOrEquals (<=)";
        };
    }
}
