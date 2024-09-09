This is a compiler for the mattbatwings's redstone computer's costume assembler language.

It is open source and avaiable for everyone to improve and optimise.

Language general description:

---allowed variable names---
Variables are only allowed to have a string of lower case english letters.
Reserved names are not allowed.

Ex:
correct: hi, person, variable, gwilfqgwql, yes, no
incorrect: t7, _u, _2, 7e, if, ret, macro, var

---declaring variables---

There are two valid ways to declare a variable:
let [variable name] = [value];
or
var [variable name] = [value];
And in both the = sign is optional.

Ex:
let ten 10;

---setting variables---

This is how you can in run time change the value of a variable:
[variable name] = [value];
Here the equals is also optional, but recommended, since it may get confusing if it is not used.

Ex:
let a = 9;
a = 2;

---reading variables---

Variables can be read by writing theire name on a part of the code were a value is excepted.

Ex:
let a = 6;
let b = a;

---sums and subtractions---

Sums can be donne by writing a value, then a + and then another value, allowing recursion.
Subtractions are represented the same way but with a - instead.
It follows left to right order of operations.
Note: parenteses do not affect order of operations.
[value] + [value]
[value] - [value]

Ex:
let a = 8;
let b = 9;
let sum = a + b;
let difference = a - b;

---incrementing and decrementing---

Variables can be incremented and decremented by putting a + or - and the variable name afterwards.
Syntax:
+[variable name];
-[variable name];

Ex:

var a = 6;
+a;
let b = a;

---printing---

Values can be printed to the number display using print.
Syntax: print [value];

Ex:
let a = 10;
print a;

---scopes---

Scopes can be used to group multiple lines of code and make variables temporary.
Variables defined inside a scope are not accessable outside of the scope, and can be redefined normally.

Syntax: 

{ 
  [code]
}

Ex:

let a = 9;
{
  let b = 7;
  print b;
}
let b = a - 3;
print b;

---If statements---

If statements only run the line of code or scope directly after it if a value is 0 or positive.

Syntax:
  if ([value]) [code]
  or
  if ([value]) {
    [code]
  }

Ex:

let a = 7;
let b = 4;
if (a - b) {
  print a;
}

---While statements---

The same as if statements but run WHILE the value is 0 or positive.

Syntax:
  while ([value]) [code]
  while ([value]) {
    [code]
  }

Ex:

let i = 1;
while (10 - i) { /* acts as while i <= 10 */
  print i;
  +i;
}

---functions and macros---

functions can be recursive and return values, while macros directly replace in compile time any call to them, disallowing recursion
and returning while having the advantage of not taking space on the 16 depth only built in the computer stack.
function and macro names have to be unique and are not allowed to share names between each other. They follow the variable name restrictions.

Syntax:
function [function name] [code or scope]
macro [macro name] [code or scope]

both are runned using the "run" keyword.

Syntax:
  run [function/macro name];

---returning---

returning allowes to emidiately quit a **function** while giving a return value, stored in the reserved variable "ret".

Syntax:
  return [value];

Ex:
  function oneplusone
    return 1+1;
  run oneplusone;
  print ret;

(oh damn I finally finished this damn full description of the language)
