import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.io.*;
import java.util.*;

public class ButtonTools extends JButton {

    protected static int occurred = 0;
    private final HashMap<Integer, Integer> parensMap = new HashMap<>();
    private int pairs;
    private final ArrayList<String> allStr = new ArrayList<>();

    public static String convertToLatex(String s) {
        char[] allChars = s.toCharArray();
        String[] listOfChars = new String[allChars.length];
        StringBuilder latex = new StringBuilder();
        int i = 0;
        for (char c : allChars) {
            listOfChars[i] = Character.toString(c);
            ++i;
        }
        for (String str: listOfChars) {
            switch (str) {
                case "(" -> str = "{(";
                case ")" -> str = ")}";
                case "/" -> str = "";
            }
            latex.append(str);
        }


        return latex.toString();
    }


    private static boolean verifyVariable(String s, int variableCount) {
        return false;
    }

    private static int countVariable(String s) {
        return 0;
    }

    public static void derivate(JTextComponent tf1, JTextComponent tf2,
                                 File functionsFile,
                                 File pyFile,
                                 String variable) throws IOException {

        Process python;
        String str, writeToPy, pyOutput;
        StringBuilder sbGraph, sbToPy, output;
        BufferedReader input;
        try (
                BufferedWriter pythonWriter = new BufferedWriter(new FileWriter(pyFile));
                BufferedWriter functionWriter = new BufferedWriter(new FileWriter(functionsFile, true))
        ) {
            if (occurred == 0) {
                Date date = new Date();
                functionWriter.write("-------------------- New session : " + date + "\n");
            }
            //Call limit of the function in input
            str = tf1.getText();
//            if(!verifyVariable(str, variables)){
//                JOptionPane.showInputDialog(tf1, "NOON");
//                JOptionPane.showMessageDialog(tf1,
//                        "Variables has been modified. Mistakes can occur.",
//                        "Variable Information",
//                        JOptionPane.INFORMATION_MESSAGE);
//            };
            sbGraph =  new StringBuilder("Derivative of : ");
            sbGraph.append(str).append(";\n");
            functionWriter.write(sbGraph.toString());
            String text = """
            from sympy import *
            x, y, z = symbols('x, y, z')
            print(diff(""";

            sbToPy = new StringBuilder(text);
            sbToPy.append(str).append(", ").append(variable).append(", 1))");
            pythonWriter.write(sbToPy.toString());
        }

        python = Runtime.getRuntime().exec("python " + pyFile.getAbsolutePath());
        input = new BufferedReader(new
                InputStreamReader(python.getInputStream()));
        BufferedReader error = new BufferedReader(new
                InputStreamReader(python.getErrorStream()));

        // read the error from the command
        StringBuilder errorOut = new StringBuilder();
        String err;
        while ((err = error.readLine()) != null) {
            errorOut.append(err).append("\n");
            System.out.println(err);
        }
        if(!errorOut.isEmpty()){
            JOptionPane.showMessageDialog(tf1,
                    processErrorMessage(errorOut.toString()),
                    "Function Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        output = new StringBuilder();
        while ((pyOutput = input.readLine()) != null) {
            output.append(pyOutput);
        }
        System.out.println(output);
        if (tf2 != null){
            tf2.setText("");
            tf2.setText(output.toString());
        }
        input.close();
        error.close();


    }

    public static void writeToPython(JTextComponent tf1, JTextComponent tf2,
                                     File functionsFile,
                                     File pyFile, boolean graph) throws IOException {

        Process python;
        String str, writeToPy, pyOutput;
        StringBuilder sbGraph, sbToPy, output;
        BufferedReader input;
        try (
                BufferedWriter pythonWriter = new BufferedWriter(new FileWriter(pyFile));
                BufferedWriter functionWriter = new BufferedWriter(new FileWriter(functionsFile, true))
        ) {
            if (occurred == 0) {
                Date date = new Date();
                functionWriter.write("-------------------- New session : " + date + "\n");
            }
            //Call limit of the function in input
            str = tf1.getText();
//            if(!verifyVariable(str, variables)){
//                JOptionPane.showInputDialog(tf1, "NOON");
//                JOptionPane.showMessageDialog(tf1,
//                        "Variables has been modified. Mistakes can occur.",
//                        "Variable Information",
//                        JOptionPane.INFORMATION_MESSAGE);
//            };
            sbGraph = graph ? new StringBuilder("Graph of : ") : new StringBuilder("Limit of : ");
            sbGraph.append(str).append(";\n");
            functionWriter.write(sbGraph.toString());

            sbToPy = new StringBuilder("\nfrom graph_and_limits import *\n\nfx_1 = Function(string_to_latex('");
            sbToPy.append(str).append("'), ").append(str).append(")");
            writeToPy = graph ? "\nlimit_2var(0, x, y, function = fx_1, plot = True)"
                    : "\nlimit_2var(0, x, y, function = fx_1)";
            sbToPy.append(writeToPy);
            pythonWriter.write(sbToPy.toString());
        }

        python = Runtime.getRuntime().exec("python " + pyFile.getAbsolutePath());
        input = new BufferedReader(new
                InputStreamReader(python.getInputStream()));
        BufferedReader error = new BufferedReader(new
                InputStreamReader(python.getErrorStream()));

        // read the error from the command
        StringBuilder errorOut = new StringBuilder();
        String err;
        while ((err = error.readLine()) != null) {
            errorOut.append(err).append("\n");
            System.out.println(err);
        }
        if(!errorOut.isEmpty()){
            JOptionPane.showMessageDialog(tf1,
                    processErrorMessage(errorOut.toString()),
                    "Function Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        output = new StringBuilder();
        while ((pyOutput = input.readLine()) != null) {
            output.append(pyOutput);
        }
        if (tf2 != null) tf2.setText(output.toString());
        input.close();
        error.close();
    }

    private static String processErrorMessage(String pyErrorMessage) {
        return """
                    Please verify your input.
                    You must add a valid function.
                    Also, note that it is preferable to use x, y, z as variables.
                    Don't forget the "*" sign between elements.
                    Use "**" for exponentiation.
                    """;
    }

    public static void writeToPython(JTextComponent tf1, File functionsFile, File pyFile) throws IOException {
        writeToPython(tf1, null, functionsFile, pyFile, true);
    }

    public static void writeToPython(JTextComponent tf1, JTextComponent tf2,
                                     File functionsFile,
                                     File pyFile)
            throws IOException {
        writeToPython(tf1, tf2, functionsFile, pyFile, false);
    }

    public HashMap<Integer, Integer> parens(String s, int offset, boolean start) {
        if (start) {
            allStr.clear();
            parensMap.clear();
            long count1 = s.chars().filter(ch -> ch == '(').count();
            long count2 = s.chars().filter(ch -> ch == ')').count();
            if (count1 != count2) {
                throw new IllegalArgumentException("Check the input. " +
                                "Some parenthesis might not have been closed.");
            }
            pairs = (int) count1;
        }
        allStr.add(s);
        String sub;
        int index1, index2, from1, from2;
        from1 = 0;
        from2 = 0;
        sub = s;
        if(parensMap.size() != pairs) {
            if ((index1 = sub.indexOf("(", from1)) != -1 &&
                    (index2 = sub.indexOf(")", from2)) != -1) {

                sub = s.substring(index1 + 1, index2);
                if (!sub.contains("(") && !sub.contains(")")) {
                    parensMap.put(index1 + offset, index2 + offset);
                    from1 = index1;
                    from2 = index2;
                    sub = s.substring(from2 + 1);
                    return parens(sub, offset + from2 + 1);
                } else if (sub.contains("(") && sub.contains(")")) {
                    return parens(sub, offset + index1 + 1);
                } else if (sub.contains("(") && !sub.contains(")")) {
                    int nextP = s.indexOf(")", index2 + 1);
                    sub = s.substring(index1 + 1, nextP + 1);
                    return parens(sub, offset + index1 + 1);
                } else if (!sub.contains("(") && sub.contains(")")) {
                    //Don't think this can happen
                    int nextP = s.indexOf("(", index2);
                    sub = s.substring(index1 + 1, nextP);
                    System.out.println("Happened !");
                }
                //else if (sub.contains("(") && sub.)
            }
            StringBuilder verify = new StringBuilder();
            String isValid;
            for (int i = 0; i < (s = allStr.get(0)).length(); ++i) {
                if (!parensMap.containsValue(i) && !parensMap.containsKey(i)) {
                    if (s.charAt(i) == '(' || s.charAt(i) == ')') {
                        verify.append(s.charAt(i));
                    }else{
                        verify.append(" ");
                    }
                } else {
                    verify.append(" ");
                }
            }
            isValid = verify.toString();
            if (isValid.contains("(") || isValid.contains(")")) {
                parens(isValid, 0);
            }
        }
        return parensMap;

    }

    public HashMap<Integer, Integer> parens(String s, int off) {
        return parens(s, off, false);
    }

    public HashMap<Integer, Integer> parens(String s) {
            return parens(s, 0, true);
    }



    public static void main(String[] args) {
            new ButtonTools().parens("(1 + 2)(3 + 4)(5 + 6)(1 + 1)(2 + (1 + x)*2)");
    }
}
