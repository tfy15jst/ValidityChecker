package testClasses

class SocialSecurityTester : Tester {
    override fun testString(input: String): Pair<Boolean, String> {
        return if (!isSocialSecurityNumber(input)) {
            Pair(false, "Is not a valid social security number")
        } else {
            Pair(true, "")
        }
    }

    private fun isSocialSecurityNumber(input: String): Boolean {
        val numbers: List<Int>

        return if (input.length == 12 || input.length == 13) {
            numbers = input.split("").mapNotNull { it.toIntOrNull() }

            var sum = 0
            for ((i, number) in numbers.withIndex()) {
                sum += numberRule(i, number)
            }

            val res = (10 - (sum % 10)) % 10

            res == numbers.last()
        } else {
            false
        }
    }

    private fun numberRule(i: Int, number: Int): Int {
        if (i < 2 || i == 11)
            return 0

        var res = number

        if (i % 2 == 0)
            res *= 2
        if (res >= 10)
            res = 1 + (res - 10)

        return res
    }
}