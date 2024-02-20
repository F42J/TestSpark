package org.jetbrains.research.testspark.core.generation.prompt

import org.jetbrains.research.testspark.core.generation.prompt.configuration.ClassRepresentation
import org.jetbrains.research.testspark.core.generation.prompt.configuration.MethodRepresentation
import org.jetbrains.research.testspark.core.generation.prompt.configuration.PromptGenerationContext
import org.jetbrains.research.testspark.core.generation.prompt.configuration.PromptTemplates

class PromptGenerator(
    private val context: PromptGenerationContext,
    private val promptTemplates: PromptTemplates,
) {
    /**
     * Generates a prompt for generating unit tests in Java for a given class.
     *
     * @return The generated prompt.
     */
    fun generatePromptForClass(interestingClasses: List<ClassRepresentation>): String {
        val prompt = PromptBuilder(promptTemplates.classPrompt)
            .insertLanguage("Java")
            .insertName(context.cut.qualifiedName!!)
            .insertTestingPlatform("JUnit 4")
            .insertMockingFramework("Mockito 5")
            .insertCodeUnderTest(context.cut.fullText, context.classesToTest)
            .insertMethodsSignatures(interestingClasses)
            .insertPolymorphismRelations(context.polymorphismRelations)
            .build()

        return prompt
    }

    /**
     * Generates a prompt for a method.
     *
     * @return The generated prompt.
     */
    fun generatePromptForMethod(
        method: MethodRepresentation,
        interestingClassesFromMethod: List<ClassRepresentation>,
    ): String {
        val prompt = PromptBuilder(promptTemplates.methodPrompt)
            .insertLanguage("Java")
            .insertName("${context.cut.qualifiedName}.${method.name}")
            .insertTestingPlatform("JUnit 4")
            .insertMockingFramework("Mockito 5")
            .insertCodeUnderTest(method.text, context.classesToTest)
            .insertMethodsSignatures(interestingClassesFromMethod)
            .insertPolymorphismRelations(context.polymorphismRelations)
            .build()

        return prompt
    }

    /**
     * Generates a prompt for a specific line number in the code.
     *
     * @param lineUnderTest the textual content of the line which to generate the prompt
     * @return the generated prompt string
     */
    fun generatePromptForLine(
        lineUnderTest: String,
        method: MethodRepresentation,
        interestingClassesFromMethod: List<ClassRepresentation>,
    ): String {
        val prompt = PromptBuilder(promptTemplates.linePrompt)
            .insertLanguage("Java")
            .insertName(lineUnderTest.trim())
            .insertTestingPlatform("JUnit 4")
            .insertMockingFramework("Mockito 5")
            .insertCodeUnderTest(method.text, context.classesToTest)
            .insertMethodsSignatures(interestingClassesFromMethod)
            .insertPolymorphismRelations(context.polymorphismRelations)
            .build()

        return prompt
    }
}
