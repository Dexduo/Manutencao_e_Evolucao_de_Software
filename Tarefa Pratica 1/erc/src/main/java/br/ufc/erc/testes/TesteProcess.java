package br.ufc.erc.testes;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtFieldReference;

public class Teste extends AbstractProcessor<CtClass<?>>{
	
	public void process(CtClass<?> element) {
		System.out.println(getAtt(element));
	}
	
	private static String getAtt(CtClass<?> element) {
		StringBuffer buffer = new StringBuffer();
		for(CtClass<?> field: element.getClass()) {
			buffer.append(field.getQualifiedName());
		}
		
		return buffer.toString();
	}
	
	public static void main(String[] args) {
		SpoonAPI spoon = new Launcher();
		spoon.addInputResource("./res/banksys/src/main/java/");
		spoon.buildModel();
		spoon.addProcessor("br.ufc.erc.testes.Teste");
		spoon.process();
		
//		for(CtType<?> type: spoon.getModel().getAllTypes()) {
////			System.out.println(type.getSimpleName());
//			
//			for(CtFieldReference<?> field: type.getAllFields()) {
//				System.out.println(field.getQualifiedName());
//			}
//			
//		}
		
	}
	
}