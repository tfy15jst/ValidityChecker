class DataInfo(private val data: String) {
    private val failures: MutableList<String> = mutableListOf()

    fun getString(): String {
        return data
    }

    fun addFailure(testName: String, failure: String) {
        failures.add("$testName: $failure")
    }

    fun formatFailures(): String {
        val failureText = StringBuilder()
        failureText.append(data)

        if (failures.isEmpty())
            failureText.append(" PASSED")
        else
            failureText.append(" FAILED")

        failures.forEach { failureText.append(", $it") }

        failureText.append("\n")

        return failureText.toString()
    }
}