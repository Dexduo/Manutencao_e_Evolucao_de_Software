package br.ufc.erc.vrd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.code.CtCatch;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

public class DesignRulesChecker {
	public static void main(String[] args) {
		SpoonAPI spoon = new Launcher();
		spoon.addInputResource("./res/banksys/src/main/java/");
		spoon.buildModel();
		checkGoodDocumentation(spoon);
		checkEmptyCatchBlock(spoon);
		checkViewAndPersistenceCoupling(spoon);

	}

	private static void checkGoodDocumentation(SpoonAPI spoon) {
		List<String> notDocumented = new ArrayList<String>();
		for (CtMethod<?> method : spoon.getModel().getElements(new TypeFilter<>(CtMethod.class))) {
			if (method.hasModifier(ModifierKind.PUBLIC) && (method.getTopDefinitions().size() == 0)) {
				if (method.getDocComment().length() < 20) {
					notDocumented.add(method.getParent(CtType.class).getQualifiedName() + "#" + method.getSignature());
				}
			}
		}

		if (notDocumented.size() > 0) {
			System.out.println("The system has " + notDocumented.size()
					+ " public methods that should be documented with proper API documentation: \n"
					+ StringUtils.join(notDocumented, "\n"));
		} else {
			System.out.println("The system has a good documentation.");
		}
		System.out.println("==============================================================================");
	}

	private static void checkEmptyCatchBlock(SpoonAPI spoon) {
		List<String> emptyCatchBlocks = new ArrayList<String>();
		for (CtCatch catchBlock : spoon.getModel().getElements(new TypeFilter<>(CtCatch.class))) {
			if (catchBlock.getBody().getStatements().size() == 0) {
				emptyCatchBlocks.add(catchBlock.getParent(CtType.class).getQualifiedName() + "#"
						+ catchBlock.getParent(CtMethod.class).getSignature());
			}
		}

		if (emptyCatchBlocks.size() > 0) {
			System.out.println("The system has " + emptyCatchBlocks.size() + " catch empty blocks: \n"
					+ StringUtils.join(emptyCatchBlocks, "\n"));
		} else {
			System.out.println("The system is free from empty catch blocks.");
		}
		System.out.println("==============================================================================");
	}

	private static void checkViewAndPersistenceCoupling(SpoonAPI spoon) {
		List<CtClass<?>> viewClasses = new ArrayList<CtClass<?>>();
		Set<String> persistenceClassNames = new HashSet<>();
		for (CtPackage onePackage : spoon.getModel().getElements(new TypeFilter<>(CtPackage.class))) {
			if (onePackage.getQualifiedName().equals("banksys.view")) {
				for (CtClass<?> oneClass : onePackage.getElements(new TypeFilter<>(CtClass.class))) {
					viewClasses.add(oneClass);
				}
			} else if (onePackage.getQualifiedName().equals("banksys.persistence")) {
				for (CtClass<?> oneClass : onePackage.getElements(new TypeFilter<>(CtClass.class))) {
					persistenceClassNames.add(oneClass.getQualifiedName());
				}
			}
		}

		StringBuffer buffer = new StringBuffer();
		for (CtClass<?> viewClass : viewClasses) {
			for (CtTypeReference<?> reference : viewClass.getReferencedTypes()) {
				if (!(reference.isShadow() || reference.isImplicit()))
					if (persistenceClassNames.contains(reference.getQualifiedName())) {
						buffer.append(viewClass.getQualifiedName() + " is referring " + reference.getQualifiedName() + "\n");
					}
			}
		}

		if (!buffer.isEmpty()) {
			System.out.println("The system has coupling between view and persistence packages.");
			System.out.println(buffer.toString());
		} else {
			System.out.println("The system has no coupling between view and persistence packages.");
		}
	}
}
