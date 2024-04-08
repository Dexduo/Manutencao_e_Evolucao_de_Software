package br.ufc.erc.tarefa_pratica_1;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtField;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;

public class Questao3{
	
	public static void main(String[] args) {
		SpoonAPI spoon = new Launcher();
		spoon.addInputResource("./res/banksys/src/main/java/");
		spoon.buildModel();
		
		for(CtType<?> type: spoon.getModel().getAllTypes()) {
			System.out.println(type.getSimpleName());
			
			for(CtMethod<?> method: type.getAllMethods()) {
				System.out.print("	'-> ");
				System.out.println(method.getSimpleName() + "#" + method.getSignature());
							
			}
			
		}
		
	}
	
}