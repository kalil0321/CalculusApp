import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    public static String verifyText(String s) {
        System.out.println("The input is : " + s);
        char[] chars = s.toCharArray();
        StringBuilder string = new StringBuilder();
        Character c1, c2;
        int end = chars.length - 1;
        for (int i = 0; i < end; ++i) {
            c1 = chars[i];
            c2 = chars[i + 1];
            if (Character.isLetter(c2) && Character.isDigit(c1)
                || (Character.isLetter(c2) && Character.isLetter(c1)
                    && (c1 == 'x' || c1 == 'y' || c1 == 'z')
                    && (c2 == 'x' ||  c2 == 'y' ||  c2 == 'z') )){
                string.append(c1).append("*");
            }else{string.append(c1);}
            if(i == end - 1){
                string.append(c2);
            }
        }
        if (string.isEmpty()) string.append(chars[0]);
        System.out.println("The output is :" + string);
        return string.toString();
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
            str = verifyText(tf1.getText());
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
            text = "\npreview(euler = false, viewer='file', output = 'png', dvioptions=['-D','1200'], " +
                    "filename='output_javapp_"
                    + occurred + ".png', expr ="+ str + ")";
            pythonWriter.write(sbToPy.append(text).toString());
        }

        python = Runtime.getRuntime().exec("python3 " + pyFile.getAbsolutePath());
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
            str = verifyText(tf1.getText());
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
            //preview(euler = false, viewer='file', output = 'png', filename='output_01u.png', dvioptions=['-D','1200'], expr = 21/y)
            String text = "\npreview(euler = false, viewer='file', output = 'png', dvioptions=['-D','1200'], " +
                    "filename='output_javapp_"
                    + occurred + ".png', expr ="+ str + ")";
            pythonWriter.write(sbToPy.append(text).toString());
        }

        python = Runtime.getRuntime().exec("python3 " + pyFile.getAbsolutePath());
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

    public HashMap<Integer, Integer> parens(String s) {

        char opening = '(';
        char closing = ')';
        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == opening) {
                stack.add(i);
            }else if(s.charAt(i) == closing){
                if (stack.isEmpty())
                    JOptionPane.showMessageDialog(this, "Error with the input");
                map.put(stack.pop(), i);
            }
        }
        return map;
    }

    public static void main(String[] args) {

        ButtonTools b;
//        String s = "(1 + 2)(3 + 4)(5 + 6)(1 + 1)(2 + (1 + x)*2)";
//        DoubleSummaryStatistics tParens = new DoubleSummaryStatistics();
//        DoubleSummaryStatistics tParensStack = new DoubleSummaryStatistics();
//
         b = new ButtonTools();
////        System.out.println(b.parens(s));
////        System.out.println(b.parensStack(s));
//        long startTime, endTime, durationInNano;
//        double val = 0;
//        for (int i = 0; i < 1; i++) {
//            startTime = System.nanoTime();
//            b.parens(s); //Measure execution time for this method
//            endTime = System.nanoTime();
//            durationInNano = (endTime - startTime);
//            val = (durationInNano * 10e-3);
//            tParens.accept(val);
//        }
//        System.out.println(tParens);
//
        b.verifyText("3xy + 7x - 16");
        b.verifyText("3cos(x)");


    }
}
