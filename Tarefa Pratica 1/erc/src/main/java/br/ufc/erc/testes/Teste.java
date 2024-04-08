package br.ufc.erc.testes;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtField;
import spoon.reflect.reference.CtFieldReference;

public class Teste{
	
	public static void main(String[] args) {
		SpoonAPI spoon = new Launcher();
		spoon.addInputResource("./res/banksys/src/main/java/");
		spoon.buildModel();
		
		for(CtType<?> type: spoon.getModel().getAllTypes()) {
//			System.out.println(type.getSimpleName());
			
			for(CtFieldReference<?> field: type.getAllFields()) {
				System.out.println(field.getQualifiedName());
			}
			
		}
		
	}
	
}