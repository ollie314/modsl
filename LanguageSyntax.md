### Overview ###
Currently ModSL scripting language supports UML class and collaboration diagrams.

### Class Diagrams ###
Sample:
```
class diagram MyDiagram { 
   interface MyInterface { 
      +method1(p1,p2):void; 
   } 
   abstract class MyClass1 implements MyInterface { 
      var1; var2; +method1(p1,p2):void; abstract method2(); 
   }
   class MyClass2 implements MyInterface { 
      static method1(parameter1):Date;
      1->0..n(MyClass1);
   }	
} 
```

Notes:
  * The following entities are supported:
    * `class`
    * `abstract class`
    * `interfrace`
  * Supported elements:
    * `var`
    * `static var`
    * `method()` with optional parameters and return type: `method(p1,p2):String`
    * `abstract method()`
    * `static method`
  * Supported visibility modifiers:
    * `-` (private)
    * `#` (protected)
    * `+` (public)
  * Supported relationship operations:
    * `extends`
    * `implements`
    * `n->m` (aggregation). Multiplicity on either end can be a number, `*`, single character or a range like `1..n`
  * The word `diagram` in the diagram definition is optional.

### Collaboration Diagrams ###
Sample:
```
collaboration diagram MyDiagram { 
   MyClass1:MyObject1->MyClass2:MyObject2.method1();
   MyClass2:MyObject2->:MyObject3.method2()->:MyObject4.method3(); 
}
```

Notes:
  * The following entities are supported:
    * Caller (left side):
      * `Object`
      * `:Object`
      * `Class:Object`
    * Callee (right side):
      * `Object.method()`
      * `:Object.method()`
      * `Class:Object.method()`
  * Multiple calls/messages can be chained together, such as `obj_a->obj_b->obj_c`
  * Multiple calls between the same pair of entities is not supported yet (i.e. `{ a->b.c(); a->b.d(); }` will fail)