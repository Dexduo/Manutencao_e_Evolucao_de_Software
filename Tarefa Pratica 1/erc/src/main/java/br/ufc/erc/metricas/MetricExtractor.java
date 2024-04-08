package br.ufc.erc.metricas;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.code.*;
import spoon.reflect.visitor.filter.TypeFilter;

public class MetricExtractor extends AbstractProcessor<CtClass<?>> {

	@Override
	public void process(CtClass<?> element) {
		String qualifiedName = element.getQualifiedName();
		double totalLoC = computeLoC(element);
		double numberOffAttributes = computeNoA(element);
		double wmc = computeWMC(element);
		System.out.println(qualifiedName + ";" + totalLoC + ";" + numberOffAttributes + ";" + wmc);
	}

	private double computeLoC(CtClass<?> element) {
		int startLine = element.getPosition().getLine();
		int endLine = element.getPosition().getEndLine();
		double totalLoC = ((endLine - startLine) == 0) ? (1) : ((endLine - startLine) - 1);
		return totalLoC;
	}
	
	private double computeNoA(CtClass<?> element) {
		return element.getDeclaredFields().size();
	}
	
	private double computeWMC(CtClass<?> element) {
		double weightedMethodsPerClass = 0.0;

		for (CtMethod<?> method : element.getMethods()) {
			weightedMethodsPerClass += method.getElements(new TypeFilter<CtIf>(CtIf.class)).size();

			for (CtIf ifstmt : method.getElements(new TypeFilter<CtIf>(CtIf.class))) {
				if (ifstmt.getElseStatement() != null) {
					weightedMethodsPerClass++;
				}
			}

			weightedMethodsPerClass += method.getElements(new TypeFilter<CtSwitch<?>>(CtSwitch.class)).size();

			weightedMethodsPerClass += method.getElements(new TypeFilter<CtCase<?>>(CtCase.class)).size();

			weightedMethodsPerClass += method.getElements(new TypeFilter<CtFor>(CtFor.class)).size();

			weightedMethodsPerClass += method.getElements(new TypeFilter<CtForEach>(CtForEach.class)).size();

			weightedMethodsPerClass += method.getElements(new TypeFilter<CtWhile>(CtWhile.class)).size();

			weightedMethodsPerClass += method.getElements(new TypeFilter<CtDo>(CtDo.class)).size();

			weightedMethodsPerClass += method.getElements(new TypeFilter<CtBreak>(CtBreak.class)).size();

			weightedMethodsPerClass += method.getElements(new TypeFilter<CtContinue>(CtContinue.class)).size();

			for (CtBinaryOperator<?> operator : method
					.getElements(new TypeFilter<CtBinaryOperator<?>>(CtBinaryOperator.class))) {

				if (operator.getKind() == BinaryOperatorKind.AND || operator.getKind() == BinaryOperatorKind.OR
						|| operator.getKind() == BinaryOperatorKind.EQ
						|| operator.getKind() == BinaryOperatorKind.GE
						|| operator.getKind() == BinaryOperatorKind.GT
						|| operator.getKind() == BinaryOperatorKind.LE
						|| operator.getKind() == BinaryOperatorKind.LT) {
					weightedMethodsPerClass++;
				}
			}
			weightedMethodsPerClass += method.getElements(new TypeFilter<CtReturn<?>>(CtReturn.class)).size();
			
		}
		return weightedMethodsPerClass;
	}
	
	public static void main(String[] args) {
		SpoonAPI spoon = new Launcher();
		spoon.addInputResource("./res/banksys/src/main/java/");
		spoon.buildModel();
		spoon.addProcessor("br.ufc.erc.metricas.MetricExtractor");
		spoon.process();
	}

}
