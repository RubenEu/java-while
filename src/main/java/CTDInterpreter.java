import ctd.*;
import org.apache.tools.ant.taskdefs.Copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CTDInterpreter {
    /**
     * Variable name maps its value.
     */
    private Map<String, Integer> variables;
    /**
     * Label name indicate its ctd index (jump index).
     */
    private Map<String, Integer> labels;
    /**
     * Store the results in order (prints).
     */
    private List<String> results;

    public CTDInterpreter() {
        variables = new HashMap<>();
        labels = new HashMap<>();
        results = new ArrayList<>();
    }

    /**
     * Return the Integer value of an object (string or integer).
     * @param _arg Variable or integer.
     * @return Integer value.
     */
    private Integer getArgValue(Object _arg) {
        String arg = (String) _arg;
        Integer value = 0;
        try {
            value = Integer.parseInt(arg);
        } catch(NumberFormatException e) {
            value = variables.getOrDefault(arg, 0);
        }
        return value;
    }

    private void registerLabels(List<CTD> ctds) {
        for(int index = 0; index < ctds.size(); index++) {
            CTD _ctd = ctds.get(index);
            if(_ctd instanceof LabelCTD) {
                LabelCTD ctd = (LabelCTD) _ctd;
                labels.put((String) ctd.getResult(), index + 1);
            }
        }
    }

    public void execute(List<CTD> ctds) {
        int index = 0;
        // Register labels before
        registerLabels(ctds);
        // Execute the ctds
        while(index < ctds.size()) {
            CTD _ctd = ctds.get(index);
            if(_ctd instanceof AssignmentCTD) {
                AssignmentCTD ctd = (AssignmentCTD) _ctd;
                Integer leftValue = getArgValue(ctd.getArg1());
                Integer rightValue = getArgValue(ctd.getArg2());
                // Operation (+, -, *, /).
                Integer result = 0;
                if(ctd.getOp().equals("+"))
                    result = leftValue + rightValue;
                else if(ctd.getOp().equals("-"))
                    result = leftValue - rightValue;
                else if(ctd.getOp().equals("*"))
                    result = leftValue * rightValue;
                else if(ctd.getOp().equals("/"))
                    result = leftValue / rightValue;
                else
                    System.err.println("Operation in Assignment not allowed.");
                // Update the value.
                variables.put((String) ctd.getResult(), result);
                // Go to the next line.
                index += 1;
            } else if(_ctd instanceof CastingCTD) {
                // TODO

                // Go to the next line.
                index += 1;
            } else if(_ctd instanceof CommentCTD) {
                // Nothing
                // Go to the next line.
                index += 1;
            } else if(_ctd instanceof CopyCTD) {
                CopyCTD ctd = (CopyCTD) _ctd;
                Integer leftValue = getArgValue(ctd.getArg1());
                // Update the value.
                variables.put((String) ctd.getResult(), leftValue);
                // Go to the next line.
                index += 1;
            } else if(_ctd instanceof ErrorCTD) {
                // TODO

                // Go to the next line.
                index += 1;
            } else if(_ctd instanceof GotoCTD) {
                GotoCTD ctd = (GotoCTD) _ctd;
                // Go to the jump line.
                index = labels.get((String) ctd.getResult());
            } else if(_ctd instanceof HaltCTD) {
                // TODO

                // Go to the next line.
                index += 1;
            } else if(_ctd instanceof IfCTD) {
                IfCTD ctd = (IfCTD) _ctd;
                Integer leftValue = getArgValue(ctd.getArg1());
                Integer rightValue = getArgValue(ctd.getArg2());
                // Calculate the index
                if(ctd.getOp().equals("==")) {
                    if(leftValue == rightValue)
                        index = labels.get((String) ctd.getResult());
                    else
                        index += 1;
                } else if(ctd.getOp().equals("<")) {
                    if(leftValue < rightValue)
                        index = labels.get((String) ctd.getResult());
                    else
                        index += 1;
                } else {
                    System.err.println("Operation in If not allowed.");
                }
            } else if(_ctd instanceof LabelCTD) {
                // All the labels are registered.
                // Go to the next line.
                index += 1;
            } else if(_ctd instanceof PrintCTD) {
                PrintCTD ctd = (PrintCTD) _ctd;
                Integer result = getArgValue(ctd.getArg1());
                results.add(result.toString());
                // Go to the next line.
                index += 1;
            } else if(_ctd instanceof UnaryOperationCTD) {
                // TODO

                // Go to the next line.
                index += 1;
            } else {
                // Not implemented CTD.
                System.err.println("Not implemented ctd.");
                // Go to the next line.
                index += 1;
            }
        }
    }

    public List<String> getResults() {
        return results;
    }
}
