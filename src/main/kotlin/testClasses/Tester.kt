package testClasses

interface Tester {
    fun testString(input: String): Pair<Boolean, String> {
        throw ClassNotFoundException("testString function not implemented")
    }
}