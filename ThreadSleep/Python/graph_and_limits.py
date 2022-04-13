from os import name
from matplotlib.pyplot import *
from numpy import *
from sympy.plotting import *
from sympy import *
from matplotlib import *

a = True
x, y, z = symbols('x,y,z')
r, u = symbols('r u')

class Function :
    def __init__(self, name, fx):
        self.name = name
        self.fx = fx

##FROM STACK_OVERFLOW    
def find_parens(s):
    toret = {}
    pstack = []

    for i, c in enumerate(s):
        if c == '(':
            pstack.append(i)
        elif c == ')':
            if len(pstack) == 0:
                raise IndexError("No matching closing parens at: " + str(i))
            toret[pstack.pop()] = i

    if len(pstack) > 0:
        raise IndexError("No matching opening parens at: " + str(pstack.pop()))

    return toret

def find_all(char, s) :
    
    all_index = []
    for i, c in enumerate(s):
        if c == char :
            all_index.append(i)
    return all_index
             
def string_to_latex(string) :
    
    latex = string
    has_frac = latex.count("/") > 0
    latex = latex.replace("**", "^")
    latex = latex.replace("*", " \\cdot ")
    latex = latex.replace("cos", "\\cos")
    latex = latex.replace("sin", "\\sin")
    latex = latex.replace("tan", "\\tan")
    latex = latex.replace("exp", "\\exp")
    latex = latex.replace("log", "\\log")
    latex = latex.replace("ln", "\\ln")
    
    
    if has_frac :
        parens_map = find_parens(latex)
        latex_frac = 0
        chars = list(latex)
        OFFSET_KEY_INDEX = 0
        for key in parens_map.keys():
            key_for_get = key
            key += OFFSET_KEY_INDEX
            i = 1
            while key - i >= 0 and chars[key - i] == ' ' :
                i += 1
            if key - i >= 0 and chars[key - i] == '/':
                chars.insert(key, '{')
                key += 1
                OFFSET_KEY_INDEX += 1
                index = parens_map.get(key_for_get) + OFFSET_KEY_INDEX
                chars[key] = "".join(chars[key + 1 : index]) + "}"
                chars[key + 1 : index + 1] = abs(key - index) * " "
            ###
            i = 1
            closing_p = parens_map.get(key_for_get) + OFFSET_KEY_INDEX
            while closing_p + i < len(chars) and chars[closing_p + i] == ' ' :
                i += 1
            if  closing_p + i < len(chars) and chars[closing_p + i] == '/' :
                index = closing_p
                s = "".join(chars[key + 1 : index]) + '}'
                chars.insert(key, "\\frac")
                latex_frac += 1
                chars.insert(key + 1, '{')
                key += 2
                index += 2
                OFFSET_KEY_INDEX += 2
                chars[key] = s
                chars[key + 1 : index + 1] = abs(key - index) * " "
        chars = list("".join(chars))
        frac_index = find_all('/', chars)
        brackets = chars.count("{") - latex_frac
        for index in frac_index :
            if brackets < len(frac_index) :
                i = 1
                while index + i < len(chars) and chars[index + i] == ' ' :
                    i += 1
                if (index + i) < len(chars) and chars[index + i] != '{':
                    chars[index + i] = '{' + chars[index + i] + '}'
            if latex_frac < len(frac_index) :
                i = 1
                while index - i >= 0 and chars[index - i] == ' ' :
                    i += 1
                if (index - i) >= 0 and chars[index - i] != '}':
                    chars[index - i] = '\\frac{' + chars[index - i] + '}'   
        latex = "".join(chars)
        latex = latex.replace("/", "").replace("(", "{(").replace(")", ")}")
        
    return r'$' + latex + '$'
    
def get_key(val, a_dict):
    for key, value in a_dict.items():
         if val == value:
             return key
    
# Multivariable limits
def limit_2var(a, var1, var2, function = None, functions = None, plot = False):
    if functions != None :
        #print("You entered an array of f(x)")
        for f in functions :
            fx = f.fx
            lim_var1 = limit(fx.subs(var2, 0), var1, a)
            lim_var2 = limit(fx.subs(var1, 0), var2, a)
            lim_x = limit(fx.subs(var2, var1), var1, a)
            lim_y = limit(fx.subs(var1, var2), var2, a)
            fx_r = simplify(fx.subs(x, r*cos(u)).subs(y, r*sin(u)))
            #print(fx_r)
            lim_r = limit(fx_r, r, a)
            name = str(f.name)
            if lim_var1 != lim_var2 or lim_x != lim_y or lim_r != lim_var1 or lim_r != lim_x or lim_x != lim_var1:
                print(None)
            else : 
                print(lim_var1)
    else :
        #print("You entered f(x)")
        fx = function.fx
        lim_var1 = limit(fx.subs(var2, 0), var1, a)
        lim_var2 = limit(fx.subs(var1, 0), var2, a)
        lim_x = limit(fx.subs(var2, var1), var1, a)
        lim_y = limit(fx.subs(var1, var2), var2, a)
        fx_r = simplify(fx.subs(x, r*cos(u)).subs(y, r*sin(u)))
        #print(fx_r)
        lim_r = limit(fx_r, r, a)
        name = str(function.name)
        if lim_var1 != lim_var2 or lim_x != lim_y or lim_r != lim_var1 or lim_r != lim_x or lim_x != lim_var1:
            print(None);
        else : 
            print(lim_var1)
        #title -> name if string_to_latex is used 
    if plot:
        plot3d(fx, title = name)
   

# r = Function("\\sqrt{x^2 + y^2}", sqrt(x**2 + y**2))
# list_functions = [r,  Function("\\frac{(x-y)}{x^2 + y^2}", (x-y)/(x**2 + y**2) ) , Function("\\frac{x}{y}", x/y) ]
# limit_2var(0, x, y,functions= list_functions, plot=True)from graph_and_limits import *


# fx_1 = Function(string_to_latex('x'), x)
# limit_2var(0, x, y, function = fx_1, plot = True)

a = "(x*y**2-y*x**2)/((x**2+y**2)**(3/2))"
# b = "x + 2 * (x/y)/(x + 2)"
# c = "(x**2 + y**2)/(x**2)"
# d = "(2*y**3*x)/((x**2+y**2)**2)"
#print(string_to_latex(a))
# print(string_to_latex(c))