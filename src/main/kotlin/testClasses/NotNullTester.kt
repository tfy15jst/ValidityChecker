package testClasses

class NotNullTester : Tester {
    override fun testString(input: String): Pair<Boolean, String> {
        return if (input.isEmpty())
            Pair(false, "Is Null")
        else
            Pair(true, "")
    }
}