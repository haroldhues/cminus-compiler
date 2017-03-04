package io.github.haroldhues.SyntaxTree;

import java.util.function.Consumer;

import io.github.haroldhues.CompileErrorException;
import io.github.haroldhues.Parser;
import io.github.haroldhues.Tokens.TokenType;





public class StatementNode extends SyntaxTreeNode {
    public enum Type {
        Expression,
        Compound,
        Selection,
        Iteration,
        Return,
        Read,
        Write
    }

    public SyntaxTreeNode statementNode;

    public StatementNode(Parser parser, Consumer<SyntaxTreeNode> visitor) throws CompileErrorException {
        if(parser.currentIs(TokenType.Write)) {
            statementNode = new WriteStatementNode(parser, visitor);
        } else if(parser.currentIs(TokenType.Read)) {
            statementNode = new ReadStatementNode(parser, visitor);
        } else if(parser.currentIs(TokenType.Return)) {
            statementNode = new ReturnStatementNode(parser, visitor);
        } else if(parser.currentIs(TokenType.While)) {
            statementNode = new IterationNode(parser, visitor);
        } else if(parser.currentIs(TokenType.If)) {
            statementNode = new SelectionNode(parser, visitor);
        } else if(parser.currentIs(TokenType.LeftBrace)) {
            statementNode = new CompoundStatementNode(parser, visitor);
        } else {
            statementNode = new ExpressionStatementNode(parser, visitor);
        }
        visitor.accept(this);
    }

    public String toString() {
        return statementNode.toString();
    }
    
    public boolean equals(Object other) {
		return equalsBuilder(this)
			.property(o -> o.statementNode)
			.result(this, other);
    }
}