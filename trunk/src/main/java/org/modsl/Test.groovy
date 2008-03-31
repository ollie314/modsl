package org.modsl

import org.modsl.primitives.*

class_diagram() {
	
	cls("Class1").m("m1", "String").s("s1", "int").ext("Class0")
	
	cls("Class0")
	
	cls("Class2").o2m("Class1").m2o("Class3")
	
	cls("Class3").z21("Class0")
	
}