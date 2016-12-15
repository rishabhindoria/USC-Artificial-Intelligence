
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class homework {

    static HashMap<String, ArrayList<String>> kb = new HashMap<String, ArrayList<String>>();
    static HashMap<String, String> map1 = new HashMap<String, String>();
    static HashMap<String, String> map2 = new HashMap<String, String>();
    static int value = 1;

    public static String negation(String input) {
        Stack<String> s = new Stack<String>();
        String left = "";
        String right = "";
        String op = "";
        String temp = "";
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i))) {
                index = input.substring(i, input.length()).indexOf(')');
                s.push(input.substring(i, i + index + 1));
                i += index + 1;
            }
            if (input.charAt(i) == ')') {
                String temp1 = "";
                String str = "";
                boolean flag = false;
                while (!s.isEmpty()) {
                    temp1 = s.pop();
                    if (temp1.equals("(")) {
                        break;
                    } else if (temp1.equals("&") || temp1.equals("|")) {
                        flag = true;
                        right = str;
                        str = "";
                        op = temp1;
                    } else {
                        str = temp1 + str;
                    }
                }
                if (flag) {
                    left = str;
                    if (!s.isEmpty()) {
                        if (s.peek().charAt(0) == '~') {
                            s.pop();
                            s.pop();
                            i++;
                            String t = neg_not(left, right, op);
                            s.push(t);
                        } else {
                            s.push("(" + left + op + right + ")");  //changed hereeee:::
                        }
                    } else {
                        s.push("(" + left + op + right + ")");
                    }

                } else {
                    flag = false;
                    if (str.contains("~")) {
                        if (s.peek().charAt(0) == '~') {
                            str = str.substring(1);
                            s.pop();
                            s.pop();
                            i++;
                            s.push(str);

                            flag = true;
                        }
                    }
                    if (!flag) {
                        s.push("(" + str + ")");
                    }
                }

            } else {
                s.push(input.charAt(i) + "");
            }
        }
        return s.pop();
    }

    static String implies(String left, String right) {
        String temp = "((";
        temp += "~";
        temp += left;
        temp += ")";
        temp += "|";
        temp += right;
        temp += ")";
        return temp;
    }

    static String or(String left, String right) {
        String temp = "(";
        temp += left;
        temp += "|";
        temp += right;
        temp += ")";
        return temp;
    }

    static String not(String left, String right) {
        String temp = left;
        temp += "~";
        temp += right;
        temp += ")";
        return temp;
    }

    static String neg_not(String left, String right, String op) {
        String temp = "((~" + left + ")";
        if (op.charAt(0) == '|') {
            temp += '&';
        } else if (op.charAt(0) == '&') {
            temp += '|';
        }
        temp += "(~" + right + "))";
        return temp;
    }

    static String and(String left, String right) {
        String temp = "(";
        temp += left;
        temp += "&";
        temp += right;
        temp += ")";
        return temp;
    }

    public static String pre_process(String input) {
        String temp = "";
        int count = 0;
        int index = 0;
        String output = "";
        map1 = new HashMap<String, String>();
        map2 = new HashMap<String, String>();
        for (int i = 0; i < input.length(); i++) {
            if (i > 0 && (Character.isUpperCase(input.charAt(i))) && (input.charAt(i - 1) == '~')) {
                index = input.substring(i, input.length()).indexOf(')');
                temp = input.substring(i, i + index + 1);
                if (!map1.containsKey("~" + temp)) {
                    map1.put("~" + temp, "#" + count);
                    map2.put("#" + count, "~" + temp);
                    count++;
                }
                output += map1.get("~" + temp);
                i += index;
            } else if (Character.isUpperCase(input.charAt(i))) {
                index = input.substring(i, input.length()).indexOf(')');
                temp = input.substring(i, i + index + 1);
                if (!map1.containsKey(temp)) {
                    map1.put(temp, "#" + count);
                    map2.put("#" + count, temp);
                    count++;
                }
                output += map1.get(temp);
                i += index;
            } else if (input.charAt(i) != '~') {
                output += input.charAt(i);
            }
        }
        return output;
    }

    public static int get_precedence(char op) {
        switch (op) {
            case '>':
                return 1;
            case '|':
                return 2;
            case '&':
                return 3;
            case '~':
                return 4;
        }
        return 0;
    }

    public static String prefix(String input) {
        Stack<String> s = new Stack<String>();
        String output = "";
        String temp = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            if (input.charAt(i) == '(') {
                while (!s.isEmpty() && s.peek().charAt(0) != ')') {
                    temp = s.pop();
                    if (temp.charAt(0) != '(' && temp.charAt(0) != ')') {
                        output += temp;
                    }
                }
                s.pop();
            }
            if ((input.charAt(i) == '|') || (input.charAt(i) == '&') || (input.charAt(i) == ')')) {
                s.push(input.charAt(i) + "");
            } else if (input.charAt(i) != '(' && input.charAt(i) != ')') {
                output += input.charAt(i);
            }
        }
        while (!s.isEmpty()) {
            if (s.peek().charAt(0) != '(' && s.peek().charAt(0) != ')') {
                output += s.pop();
            } else {
                s.pop();
            }
        }
        return output;
    }

    public static String calculate(String input) {
        Stack<String> s = new Stack<String>();
        String output = "";
        String temp1 = "";
        String temp2 = "";
        int max = 0;
        int max_v = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) == '|') || (input.charAt(i) == '&')) {
                temp1 = s.pop();
                temp2 = s.pop();
                String t1[] = temp1.split("&");
                String t2[] = temp2.split("&");
                output = "";
                if (input.charAt(i) == '|') {
                    for (int k = 0; k < t1.length; k++) {
                        for (int j = 0; j < t2.length; j++) {
                            output += t1[k] + input.charAt(i) + t2[j] + "&";
                        }
                    }
                    if (output != "") {
                        s.push(output.substring(0, output.length() - 1));
                    }
                } else {
                    s.push(temp1 + "&" + temp2);
                }
            } else if (input.charAt(i) != '#') {
                s.push("#" + input.charAt(i) + "");
            }
        }
        return s.pop();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader("input.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int n = Integer.parseInt(bufferedReader.readLine());
        Queue<String> queryStack = new LinkedList<String>();
        while (n > 0) {
            queryStack.add(bufferedReader.readLine().replaceAll("\\s", ""));
            n--;
        }
        n = Integer.parseInt(bufferedReader.readLine());
        while (n > 0) {
            String t = bufferedReader.readLine();
            process(t);
            n--;
        }
        try {
            File f = new File("output.txt");
            f.createNewFile();
            FileWriter fileWriter = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            while (!queryStack.isEmpty()) {
                Stack k = new Stack<String>();
                k.add(negate(queryStack.poll()));
                boolean result = perform(k, 0);
                if (result == true) {
                    bufferedWriter.write("TRUE");
                    if (queryStack.size() != 0) {
                        bufferedWriter.write("\n");
                    }
                } else {
                    bufferedWriter.write("FALSE");
                    if (queryStack.size() != 0) {
                        bufferedWriter.write("\n");
                    }
                }
            }
            bufferedWriter.close();
        } catch (Exception ex) {
        }
    }

    public static void process(String input) {
        Stack<String> s = new Stack<String>();
        input = input.replaceAll("\\s", "");
        input = input.replaceAll("=>", ">");
        String left = "";
        String op = "";
        String right = "";
        int index;
        if (input.contains(">")) //remove implication
        {
            for (int i = 0; i < input.length(); i++) {
                if (Character.isUpperCase(input.charAt(i))) {
                    index = input.substring(i, input.length()).indexOf(')');
                    s.push(input.substring(i, i + index + 1));
                    i += index + 1;
                }
                if (input.charAt(i) == ')') {
                    right = s.pop();
                    op = s.pop();
                    left = s.pop();
                    if (op.charAt(0) == '>') {
                        s.pop();
                        s.push(implies(left, right));
                    } else if (op.charAt(0) == '|') {
                        s.pop();
                        s.push(or(left, right));
                    } else if (op.charAt(0) == '&') {
                        s.pop();
                        s.push(and(left, right));
                    } else if (op.charAt(0) == '~') {
                        s.push(not(left, right));
                    }
                } else {
                    s.push(input.charAt(i) + "");
                }
            }
            input = s.pop();
        }
        while (input.contains("~(")) {
            input = negation(input);
        }
        input = pre_process(input);
        input = prefix(input);
        input = calculate(input);
        String copy = input;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                copy = copy.replace("#" + input.charAt(i), map2.get("#" + input.charAt(i)));
            }
        }
        String clauses[] = copy.split("&");
        HashMap<String, Integer> var = new HashMap<String, Integer>();
        String clause = "";
        String copy_clause = "";
        ArrayList<String> variables = new ArrayList<String>();
        int start = 0;
        int end = 0;
        String new_clause = "";
        for (int i = 0; i < clauses.length; i++) {
            clause = clauses[i];
            new_clause = "";
            for (int j = 0; j < clause.length(); j++) {
                if (Character.isUpperCase(clause.charAt(j))) {
                    start = clause.substring(j, clause.length()).indexOf('(');
                    if (j > 0 && clause.charAt(j - 1) == '~') {
                        new_clause += clause.substring(j - 1, j + start + 1);
                    } else {
                        new_clause += clause.substring(j, j + start + 1);
                    }
                    end = clause.substring(j, clause.length()).indexOf(')');
                    String t[] = clause.substring(j + start + 1, j + end).split(",");
                    for (int k = 0; k < t.length; k++) {
                        if (t[k].length() == 1) {
                            if (!var.containsKey(t[k])) {
                                new_clause += "p" + value + ",";
                                var.put(t[k], value);
                                value++;
                            } else {
                                new_clause += "p" + var.get(t[k]) + ",";
                            }
                        } else {
                            new_clause += t[k] + ",";
                        }
                    }
                    new_clause = new_clause.substring(0, new_clause.length() - 1);
                    new_clause += ")";
                    j += end;
                }
                if (clause.charAt(j) == '|' || clause.charAt(j) == '&') {
                    new_clause += clause.charAt(j);
                }
            }
            clauses[i] = new_clause;
            var = new HashMap<String, Integer>();
        }
        String n_t = "";
        //kbmaker
        for (int i = 0; i < clauses.length; i++) {
            ArrayList<String> results = new ArrayList<String>();
            ArrayList<String> t = get_predicates(clauses[i]);
            String temp = "";
            for (int j = 0; j < t.size(); j++) {
                temp = t.get(j);
                for (int k = 0; k < clauses.length; k++) {
                    if (temp.charAt(0) == '~') {
                        if (clauses[k].contains(temp)) {
                            if (!results.contains(clauses[k])) {
                                results.add(clauses[k]);
                            }
                        }
                    } else {
                        n_t = clauses[k];
                        index = clauses[k].indexOf(temp);
                        while (index >= 0) {
                            if (index == 0) {
                                if (!results.contains(clauses[k])) {
                                    results.add(clauses[k]);
                                }
                            } else if (clauses[k].charAt(index - 1) != '~') {
                                if (!results.contains(clauses[k])) {
                                    results.add(clauses[k]);
                                }
                            }
                            index = n_t.indexOf(temp, index + 1);
                        }
                    }
                }
                if (kb.containsKey(temp)) {
                    ArrayList<String> kk = kb.get(temp);
                    kk.addAll(results);
                    kb.put(temp, kk);
                } else {
                    kb.put(temp, results);
                }
                results = new ArrayList<String>();
            }
        }
    }

    public static ArrayList<String> get_predicates(String clause) {
        ArrayList<String> t = new ArrayList<String>();
        int index = 0;
        int count = 0;
        for (int i = 0; i < clause.length(); i++) {
            if (i > 0 && (Character.isUpperCase(clause.charAt(i))) && (clause.charAt(i - 1) == '~')) {
                index = clause.substring(i, clause.length()).indexOf('(');
                t.add("~" + clause.substring(i, i + index));
                i += clause.substring(i, clause.length()).indexOf(')') + 1;
            } else if (Character.isUpperCase(clause.charAt(i))) {
                index = clause.substring(i, clause.length()).indexOf('(');
                t.add(clause.substring(i, i + index));
                i += clause.substring(i, clause.length()).indexOf(')') + 1;
            }
        }
        return t;
    }

    public static String negate(String input) {
        if (input.charAt(0) == '~') {
            return input.substring(1, input.length());
        } else {
            return ("~" + input);
        }
    }

    public static boolean unification(String args1[], String args2[]) {
        int counter = 0;
        for (int i = 0; i < args1.length; i++) {
            String x = args1[i];
            String y = args2[i];
            if (x.charAt(0) >= 'a' && x.charAt(0) <= 'z' && y.charAt(0) >= 'a' && y.charAt(0) <= 'z') {
                counter++;
            } else if (x.charAt(0) >= 'a' && x.charAt(0) <= 'z' && y.charAt(0) >= 'A' && y.charAt(0) <= 'Z') {
                counter++;
            } else if (x.charAt(0) >= 'A' && x.charAt(0) <= 'Z' && y.charAt(0) >= 'a' && y.charAt(0) <= 'z') {
                counter++;
            } else if (x.equals(y)) {
                counter++;
            }
        }
        if (counter == args1.length) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean perform(Stack<String> querystack, int counter) {
        while (!querystack.isEmpty()) {
            String my_query = negate(querystack.pop());
            String predicate = "";
            int index = -1;
            for (int i = 0; i < my_query.length(); i++) {
                while (my_query.charAt(i) != '(') {
                    predicate += my_query.charAt(i);
                    i++;
                }
                index = i;
                break;
            }
            String parameters1[] = my_query.substring(index + 1, my_query.length() - 1).split(",");
            if (kb.containsKey(predicate)) {
                ArrayList<String> all_val = kb.get(predicate);
                for (int i = 0; i < all_val.size(); i++) {
                    if (counter > 800) {
                        return false;
                    }
                    String iter = all_val.get(i);
                    ArrayList<String> oredterms = new ArrayList<String>();
                    String splitter[] = iter.split("\\|");
                    String found_term = "";
                    for (String term : splitter) {
                        oredterms.add(term);
                        if (term.contains(predicate)) {
                            found_term = term;
                        }
                    }
                    String parameters2string = "";
                    for (int j = 0; j < found_term.length(); j++) {
                        if (found_term.charAt(j) == '(') {
                            j++;
                            while (found_term.charAt(j) != ')') {
                                parameters2string += found_term.charAt(j);
                                j++;
                            }
                            break;
                        }
                    }
                    String parameters2[] = parameters2string.split(",");
                    boolean result = unification(parameters1, parameters2);
                    if (result == true) {
                        HashMap<String, String> unify = new HashMap<String, String>();
                        for (int h = 0; h < parameters1.length; h++) {
                            String args1 = parameters1[h];
                            String args2 = parameters2[h];
                            if (!unify.containsKey(args2)) {
                                unify.put(args2, args1);
                            }
                        }
                        Stack<String> tempstack = new Stack<String>();
                        String array[] = querystack.toArray(new String[querystack.size()]);
                        ArrayList<String> list = new ArrayList<String>();
                        for (String x : array) {
                            list.add(x);
                        }
                        for (int si = 0; si < querystack.size(); si++) {
                            tempstack.push(array[si]);
                        }
                        for (int m = 0; m < oredterms.size(); m++) {
                            String current = oredterms.get(m);
                            Iterator it = unify.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry) it.next();
                                if (current.contains((String) pair.getKey())) {
                                    current = current.replace((String) pair.getKey(), (String) pair.getValue());
                                }
                            }
                            String check = "";
                            for (int f = 0; f < current.length(); f++) {

                                while (current.charAt(f) != '(') {
                                    check += current.charAt(f) + "";
                                    f++;
                                }
                                break;
                            }
                            if (!check.equals(predicate)) {
                                String memory = current;
                                String temp = "";
                                if (memory.contains("~")) {
                                    temp = memory.substring(1);
                                } else {
                                    temp = "~" + memory;
                                }
                                int count1 = 0;

                                for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
                                    String string = iterator.next();
                                    if (string.equals(temp)) {
                                        iterator.remove();
                                        count1 = 1;
                                    }
                                }
                                if (count1 != 1) {
                                    list.add(memory);
                                }
                            }
                        }
                        Stack<String> finals = new Stack<String>();
                        finals.addAll(list);
                        if (perform(finals, ++counter) == true) {
                            return true;
                        }
                    }
                }
                return false;
            } else {
                return false;
            }
        }
        return true;
    }
}
