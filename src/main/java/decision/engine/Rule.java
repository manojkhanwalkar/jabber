package decision.engine;

import java.awt.desktop.OpenFilesEvent;

public class Rule
{

    public enum OperandType { integer, string}

    public enum Operator { eq, lt, gt , lte, gte, neq}

    String field;
    Operator operator ;
    String operand ;

    OperandType type ;


    public Rule(String field, Operator operator, String operand, OperandType type) {
        this.field = field;
        this.operator = operator;
        this.operand = operand;
        this.type = type;
    }

    public Rule()
    {

    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }
}
