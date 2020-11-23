package nicestring

fun String.isNice(): Boolean {

    var isNiceString = false
    var satisfiedConstraintCount = 0

//    true ok
    val containString = !(this.contains("ba") || this.contains("bu") || this.contains("be"))

//    m>=3 ok otherwise not ok
    val vowelCount = "aeiou".sumBy { ch ->
        if (this.contains(ch)) {
            return@sumBy this.length - this.replace(ch.toString(),"").length
        } else return@sumBy 0
    }

    fun String.ni(): Boolean {
        var isDouble = false
        for (i in 0..(this.lastIndex)) {
            if (i != this.lastIndex) {
                if (this[i] == this[i + 1]) {
                    isDouble = true
                }
            }
        }
        return isDouble
    }

    if (containString) satisfiedConstraintCount++
     if (vowelCount >= 3) satisfiedConstraintCount++
     if (this.ni()) satisfiedConstraintCount++

    if (satisfiedConstraintCount >= 2) isNiceString = true

    return isNiceString

}
