package br.ufc.erc.ovw;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtFieldReference;

public class Overview {

	public static void main(String[] args) {
		SpoonAPI spoon = new Launcher();
		//
		spoon.addInputResource("./res/banksys/src/main/java/");
		spoon.buildModel();
		
		for(CtType<?> type : spoon.getModel().getAllTypes()) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(type.getQualifiedName());
			buffer.append("\n");
			
			for(CtFieldReference<?> field : type.getAllFields()) {
				buffer.append(field.getType().getQualifiedName());
				buffer.append(" ");
				buffer.append(field.getQualifiedName());
				buffer.append(";\n");
			}
			
			for(CtExecutableReference<?> method : type.getDeclaredExecutables()) {
				buffer.append(method.getType().getQualifiedName());
				buffer.append(" ");
				buffer.append(method.getSignature());
				buffer.append(";\n");				
			}
			System.out.println(buffer.toString());
			System.out.println("====================================");
		}
		
	}
}
