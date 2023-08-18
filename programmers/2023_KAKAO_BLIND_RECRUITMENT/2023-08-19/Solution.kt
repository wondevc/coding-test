class Solution {
    fun solution(today: String, terms: Array<String>, privacies: Array<String>): IntArray {
        var answer = mutableListOf<Int>()

        val todayForIntValue = stringDateConvert(today)

        val processedTerms = termsProcess(terms)
        val processedPrivacies = privaciesProcess(privacies)

        processedPrivacies.forEachIndexed { index, privacy ->
            val expiryDate: Int = processedTerms[privacy.terms] ?: 0
            val calculatedPrivacyDate = getAddedExpiryDate(privacy.privacyDate, expiryDate)

            if (todayForIntValue >= calculatedPrivacyDate) {
                answer.add(index + 1)
            }
        }

        return answer.toIntArray()
    }

    private fun getAddedExpiryDate(from: Int, expiryDate: Int): Int {
        return from + (expiryDate * DAYS_OF_MONTH)
    }

    private fun stringDateConvert(date: String): Int {
        val (year, month, day) = date.split(".")

        return (year.toInt() * MONTHS_OF_YEAR * DAYS_OF_MONTH) + (month.toInt() * DAYS_OF_MONTH) + day.toInt()
    }

    private fun termsProcess(terms: Array<String>): Map<String, Int> {
        return terms.associate {
            val (terms, expiryDate) = it.split("\\s".toRegex())

            terms to expiryDate.toInt()
        }
    }

    private fun privaciesProcess(privacies: Array<String>): List<Privacy> {
        return privacies.map {
            val (privacyDate, terms) = it.split("\\s".toRegex())

            Privacy(
                terms,
                stringDateConvert(privacyDate)
            )
        }
    }

    companion object {
        private const val MONTHS_OF_YEAR = 12
        private const val DAYS_OF_MONTH = 28
    }
}

data class Privacy (
    val terms: String,
    val privacyDate: Int
)