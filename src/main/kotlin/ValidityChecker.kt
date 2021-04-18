import testClasses.Tester
import java.io.File
import java.io.InputStream
import kotlin.collections.List

//Example input testFile.txt SocialSecurityTester NotNullTester outputFile.txt
fun main(args: Array<String>) {
    if (args.size < 3) {
        println("Too few input arguments: <testFilePath> <validityCheckName-1> ... <validityCheckName-n> <logFilePath>")
        return
    }

    val file = args[0]
    val tests = args.slice(1 until args.size - 1)
    val outputFile = args.last()

    val testDataList = readTestFile(file)

    if (testDataList.isEmpty()) {
        println("Empty testFile")
        return
    }

    try {
        performTests(testDataList, tests)
    } catch (e: Exception) {
        println("ERROR: " + e.message)
        return
    }

    logResults(testDataList, outputFile)

    println("Tests completed, results logged in file $outputFile")
}

fun logResults(testDataList: List<DataInfo>, outputFile: String) {
    val writer = File(outputFile).bufferedWriter()

    for (testData in testDataList) {
        writer.write(testData.formatFailures())
    }

    writer.close()
}

fun performTests(testDataList: List<DataInfo>, tests: List<String>) {
    for (test in tests) {
        val testClass: Any
        try {
            testClass = Class.forName("testClasses.$test").newInstance()
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("Could not find class $test")
        }

        if (testClass is Tester) {
            performTest(testDataList, testClass, test)
        } else {
            throw IllegalArgumentException("$test does not implement TestClass interface")
        }
    }
}

private fun performTest(testDataList: List<DataInfo>, testClass: Tester, testName: String) {
    for (testData in testDataList) {
        val result: Pair<Boolean, String>
        try {
            result = testClass.testString(testData.getString())
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("$testName ${e.message}")
        }

        if (!result.first) {
            testData.addFailure(testName, result.second)
        }
    }
}

fun readTestFile(file: String): List<DataInfo> {
    val data: InputStream = File(file).inputStream()
    val outputList = mutableListOf<DataInfo>()

    data.bufferedReader().useLines { lines -> lines.forEach { outputList.add(DataInfo(it)) } }
    return outputList
}
