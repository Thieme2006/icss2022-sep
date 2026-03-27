package nl.han.ica.icss.parser;

import nl.han.ica.icss.ast.Expression;
import nl.han.ica.icss.ast.Selector;
import nl.han.ica.icss.ast.VariableReference;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

// Deze klasse is gemaakt om alle helper functions te scheiden van de Lister context functies,
// Hierdoor kom je dus amper helper functies tegen in de ASTListener.
public class BaseAstListener {
    public Selector createSelector(ICSSParser.SelectorContext ctx) {
        return switch (ctx.getStart().getType()) {
            case ICSSParser.ID_IDENT -> new IdSelector(ctx.ID_IDENT().getText());
            case ICSSParser.CLASS_IDENT -> new ClassSelector(ctx.CLASS_IDENT().getText());
            case ICSSParser.LOWER_IDENT -> new TagSelector(ctx.LOWER_IDENT().getText());
            default -> throw new IllegalStateException("Unexpected token type: " + ctx.getStart().getType());
        };
    }

    public Expression createExpression(ICSSParser.ExpressionContext ctx) {
        Expression result = createTerm(ctx.term(0));

        for (int i = 1; i < ctx.term().size(); i++) {
            Expression right = createTerm(ctx.term(i));

            switch (ctx.getChild(2 * i - 1).getText()) {
                case "+":
                    AddOperation op = new AddOperation();
                    op.lhs = result;
                    op.rhs = right;
                    result = op;
                    break;
                case "-":
                    SubtractOperation op1 = new SubtractOperation();
                    op1.lhs = result;
                    op1.rhs = right;
                    result = op1;
                    break;
            }
        }
        return result;
    }

    public Expression createTerm(ICSSParser.TermContext ctx) {
        Expression result = createValue(ctx.value(0));

        for (int i = 1; i < ctx.value().size(); i++) {
            Expression right = createValue(ctx.value(i));

            MultiplyOperation op = new MultiplyOperation();
            op.lhs = result;
            op.rhs = right;

            result = op;
        }

        return result;
    }

    public Expression createValue(ICSSParser.ValueContext ctx) {
        return switch (ctx.getStart().getType()) {
            case ICSSParser.COLOR -> new ColorLiteral(ctx.getText());
            case ICSSParser.PIXELSIZE -> new PixelLiteral(ctx.getText());
            case ICSSParser.PERCENTAGE -> new PercentageLiteral(ctx.getText());
            case ICSSParser.SCALAR -> new ScalarLiteral(ctx.getText());
            case ICSSParser.TRUE, ICSSParser.FALSE -> new BoolLiteral(ctx.getText());
            case ICSSParser.CAPITAL_IDENT, ICSSParser.LOWER_IDENT -> new VariableReference(ctx.getText());
            default -> throw new IllegalStateException("Unknown value: " + ctx.getText());
        };
    }

    public Expression createIfCondition(ICSSParser.If_conditionContext ctx) {
        if (ctx.TRUE() != null || ctx.FALSE() != null) {
            return new BoolLiteral(ctx.getText());
        } else if (ctx.comparison() != null) {
            return createComparison(ctx.comparison());
        } else if (ctx.value() != null) {
            return createValue(ctx.value());
        } else {
            throw new IllegalStateException("Unknown condition: " + ctx.getText());
        }
    }

    public Expression createComparison(ICSSParser.ComparisonContext ctx) {
        if (ctx.value().size() == 1) {
            return createValue(ctx.value(0));
        }

        Expression lhs = createValue(ctx.value(0));
        Expression rhs = createValue(ctx.value(1));
        String op = ctx.getChild(1).getText();

        Operator operator = switch (op) {
            case "==" -> Operator.EQ;
            case "!=" -> Operator.NEQ;
            case ">" -> Operator.GT;
            case ">=" -> Operator.GTE;
            case "<" -> Operator.ST;
            case "<=" -> Operator.STE;
            default -> throw new IllegalStateException("Unknown comparison operator: " + op);
        };

        ComparisonOperation comparisonOperation = new ComparisonOperation(operator);
        comparisonOperation.lhs = lhs;
        comparisonOperation.rhs = rhs;
        return comparisonOperation;
    }
}
