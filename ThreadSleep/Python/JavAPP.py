from sympy import *
x, y, z = symbols('x, y, z')
print(diff(x*y, x, 1))
preview(euler = false, viewer='file', output = 'png', dvioptions=['-D','1200'], filename='output_javapp_0.png', expr =x*y)