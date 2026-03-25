package nl.han.ica.icss.generator;


import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import java.util.ArrayList;

public class Generator {

	public String generate(AST ast) {
        return generateStylesheet(ast.root);
	}

	private String generateStylesheet(Stylesheet stylesheet) {
		StringBuilder builder = new StringBuilder();
		for(ASTNode childNode : stylesheet.getChildren()) {
			if(childNode instanceof Stylerule stylerule) {
				builder.append(generateStylerule(stylerule)).append("\n");
			}
		}
		return builder.toString();
	}

	private String generateStylerule(Stylerule stylerule) {
		return stylerule.selectors.getFirst().toString() + " {" + generateDeclaration(stylerule.body) + "\n}\n";
	}

	private String generateDeclaration(ArrayList<ASTNode> body) {
		StringBuilder builder = new StringBuilder();
		for (ASTNode childNode : body) {
			if (childNode instanceof Declaration declaration) {
				builder
						.append("\n  ")
						.append(declaration.property.name)
					.append(": ").append(generateExpression(declaration.expression))
					.append(";");
			}
		}
		return builder.toString();
	}

	private String generateExpression(Expression expression) {
		return switch(expression) {
			case PixelLiteral pixel -> pixel.value + "px";
			case PercentageLiteral perc -> perc.value + "%";
			case ColorLiteral color -> color.value;
			case ScalarLiteral scalar -> String.valueOf(scalar.value);
			case BoolLiteral bool -> bool.value ? "true" : "false";
			default -> null;
		};
	}
}