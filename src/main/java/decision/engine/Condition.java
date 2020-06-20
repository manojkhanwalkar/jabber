package decision.engine;

import decision.data.ServiceResponse;

import java.awt.desktop.OpenFilesEvent;

public class Condition
{

    public enum OperandType { integer, string}

    public enum Operator { eq, lt, gt , lte, gte, neq}

    String field;
    Operator operator ;
    String operand ;

    OperandType type ;


    public Condition(String field, Operator operator, String operand, OperandType type) {
        this.field = field;
        this.operator = operator;
        this.operand = operand;
        this.type = type;
    }

    public Condition()
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

    @Override
    public String toString() {
        return "Condition{" +
                "field='" + field + '\'' +
                ", operator=" + operator +
                ", operand='" + operand + '\'' +
                ", type=" + type +
                '}';
    }


    public OperandType getType() {
        return type;
    }

    public void setType(OperandType type) {
        this.type = type;
    }

    boolean evaluate(ServiceResponse serviceResponse)
    {
        String lhs = serviceResponse.getServiceDecisionElements().get(field);

        if (lhs==null) return false;

        switch (type)
        {
            case integer:
                return evaluateInteger(lhs);

            case string:
                return evaluateStr(lhs);

            default:
                System.out.println("Not implemented ");
                return false;
        }



    }

    boolean evaluateInteger(String s)
    {
        Integer lhs = Integer.valueOf(s);
        Integer rhs = Integer.valueOf(operand);

        return evaluateObject(lhs.equals(rhs), lhs.compareTo(rhs));
    }

    private boolean evaluateObject(boolean equals, int i) {
        switch(operator)
        {
            case eq:
                if(equals)
                    return true;
               break;
            case gt:
                if (i >0)
                    return true;
                break;
            case lt:
                if (i <0)
                    return true;
                break;
            case gte:
                if(i >=0)
                    return true;
                break;
            case lte:
                if(i <=0)
                    return true;
                break;
            case neq:
                if(!equals)
                    return true;
                break;

            default : System.out.println("Not implemented"); return false;
        }

        return false;
    }


    boolean evaluateStr(String lhs)
    {
        return evaluateObject(lhs.equals(operand), lhs.compareTo(operand));
    }


}
