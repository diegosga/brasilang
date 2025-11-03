package br.edu.pucgoias.brasilang.model.sintaxe.statement;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.pucgoias.brasilang.model.sintaxe.expression.AbstractExpression;
import br.edu.pucgoias.brasilang.model.translate.TranslationContext;

public class StringOperations implements AbstractStatement {

    private final String name;
    private final List<AbstractExpression> arguments;

    public StringOperations(String name, List<AbstractExpression>  arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public void translate(TranslationContext ctx) {
        ctx.addInclude("<string.h>");
        
        String translatedName = name;
        if (name.equals("concatenar")) {
            translatedName = "strcat";
        } else if (name.equals("copiar")) {
            translatedName = "strcpy";
        } else if (name.equals("comparar")) {
            translatedName = "strcmp";
        }

        final String args = arguments.stream()
                .map(a -> a.translate(ctx))
                .collect(Collectors.joining(", "));

        ctx.getBuilder().appendLine(translatedName + "(" + args + ");");
    }

    @Override
    public String toString() {
        return "StringOperations{\n" +
                "  name=" + name + "\n" +
                "  arguments=" + arguments + "\n" +
                "}";
    }
}
