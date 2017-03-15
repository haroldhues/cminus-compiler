package io.github.haroldhues.SyntaxTree;

import io.github.haroldhues.CompileErrorException;
import io.github.haroldhues.Location;
import io.github.haroldhues.Parser;
import io.github.haroldhues.Tokens.Token;
import io.github.haroldhues.Tokens.TokenType;

public class ExpressionStatementNode extends StatementNode {
	public ExpressionNode expression;

	public static ExpressionStatementNode parse(Parser parser) throws CompileErrorException {
		ExpressionNode expression = null;
		if (!parser.parseTokenIf(TokenType.Semicolon)) {
			expression = ExpressionNode.parse(parser);
			parser.parseToken(TokenType.Semicolon);
		}
		
		ExpressionStatementNode statement = new ExpressionStatementNode(parser.currentLocation(), expression);
		return statement;
	}

	public ExpressionStatementNode(Location location, ExpressionNode expression) {
    	super(location);
		this.expression = expression;
	}

    public StatementNode.Type statementType() {
        return StatementNode.Type.Expression;
    }

	public boolean allPathsReturn() {
		return false; // There can be no returns from an expression
	}

    public void visit(SyntaxTreeVisitor visitor) throws CompileErrorException {
        visitor.accept(this, () -> {
            SyntaxTreeNode.visit(expression, visitor);
        });
    }

	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (expression != null) {
			builder.append(expression.toString().trim());
		}
		builder.append(new Token(TokenType.Semicolon));
		return builder.toString();
	}

	public boolean equals(Object other) {
		return equalsBuilder(this)
			.property(o -> o.expression)
			.result(this, other);
	}
}